package com.iman.androidweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.iman.androidweather.data.CityDBHelper;
import com.iman.androidweather.data.CityModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    MyPagerAdapter pagerAdapter;
    List<Fragment> weatherFragments;
    ProgressBar pb;

    JsonObjectRequest request;
    List<CityModel> cityList;

//    Handler updateHandler;
//    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb = findViewById(R.id.pb);

        viewPager = findViewById(R.id.view_pager);
        init();
//        updateHandler = new Handler();
//        runnable = new Runnable() {
//            @Override
//            public void run() {
//                loadWeatherData();
//                updateHandler.postDelayed(this, 10000);
//            }
//        };

    }

    @Override
    protected void onResume() {
        super.onResume();

        loadWeatherData();
//        updateHandler.post(runnable);

    }

    @Override
    protected void onPause() {
        super.onPause();
//        updateHandler.removeCallbacks(runnable);
    }

    private void init() {
        CityDBHelper dbhelper = new CityDBHelper(this);
        cityList = dbhelper.getCities("selected = 1", null);
//        Toast.makeText(this, "CityList : " + cityList.size(), Toast.LENGTH_SHORT).show();
        weatherFragments = new ArrayList<>();
//        for (CityModel city : cityList){
//            weatherFragments.add(WeatherFragment.newInstance(city.getId()));
//        }
//

    }

    private void loadWeatherData() {

        String url = prepareUrl();
        request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pb.setVisibility(View.INVISIBLE);
                        Log.i("weather", "response : \n" + response);
                        weatherFragments.clear();
                        try {
                            int cnt = response.getInt("cnt");
                            if (cnt == 0) return;
                            JSONArray jsonList = response.getJSONArray("list");
                            for (int i = 0; i < cnt; i++) {
                                JSONObject res = jsonList.getJSONObject(i);
                                String cityName = res.getString("name").toUpperCase() + ", " + res.getJSONObject("sys").getString("country");
                                double temperature = res.getJSONObject("main").getDouble("temp");
                                JSONObject jsonDetails = res.getJSONArray("weather").getJSONObject(0);
                                String details = jsonDetails.getString("description");
                                JSONObject sys = res.getJSONObject("sys");
                                long sunrise = sys.getLong("sunrise");
                                long sunset = sys.getLong("sunset");
                                int weatherId = jsonDetails.getInt("id");

                                Bundle args = new Bundle();
                                args.putString("cityName", cityName);
                                args.putDouble("temperature", temperature);
                                args.putLong("sunrise", sunrise);
                                args.putLong("sunset", sunset);
                                args.putInt("weatherId", weatherId);
                                args.putString("details", details);
                                args.putLong("cityId", res.getLong("id"));
                                weatherFragments.add(WeatherFragment.newInstance(args));
                            }
                            updateDisplay();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("weather", "error : " + error.getMessage());
                        pb.setVisibility(View.INVISIBLE);
                    }
                });

        pb.setVisibility(View.VISIBLE);
        App.getRequestQueue().add(request);
    }

    private void updateDisplay() {
        if (weatherFragments == null) {
            weatherFragments = new ArrayList<>();
        }
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), weatherFragments);
        viewPager.setAdapter(pagerAdapter);
    }


    private String prepareUrl(){
        StringBuilder sb = new StringBuilder("http://api.openweathermap.org/data/2.5/group?id=");
        for (int i = 0 ; i < cityList.size() ; i++){
            sb.append(String.valueOf(cityList.get(i).getId()));
            if (i < cityList.size() - 1){
                sb.append(",");
            }
        }
        sb.append("&units=metric&APPID=" + App.API_KEY);
        return sb.toString();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Locations").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(MainActivity.this, CitiesActivity.class));
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;

        public MyPagerAdapter(@NonNull FragmentManager fm, List<Fragment> fragments) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.fragments = fragments;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

}
