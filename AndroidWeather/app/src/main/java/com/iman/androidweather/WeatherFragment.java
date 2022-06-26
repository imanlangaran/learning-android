package com.iman.androidweather;

import android.app.DownloadManager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.iman.androidweather.App.URL_FORECAST_BY_ID;

public class WeatherFragment extends Fragment {
    private static final String TAG = WeatherFragment.class.getSimpleName();


    TextView tv_city, tv_temp, tv_details;
    TextViewWeather tv_weatherIcon;

    RecyclerView rv_forecast;


    private String cityName;
    private String details;
    private double temperature;
    private long sunrise;
    private long sunset;
    private int weatherId;
    private long cityId;

    private boolean forecastLoaded = false;
    private JSONArray forecastData;

    public static WeatherFragment newInstance(Bundle args) {
        WeatherFragment fragment = new WeatherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        cityName = args.getString("cityName");
        temperature = args.getDouble("temperature");
        sunrise = args.getLong("sunrise");
        sunset = args.getLong("sunset");
        weatherId = args.getInt("weatherId");
        details = args.getString("details");
        cityId = args.getLong("cityId");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_card, container, false);
        tv_city = view.findViewById(R.id.city);
        tv_temp = view.findViewById(R.id.temp);
        tv_details = view.findViewById(R.id.details);
        tv_weatherIcon = view.findViewById(R.id.iconWeather);
        rv_forecast = view.findViewById(R.id.forecast_rv);

        fill();
        return view;
    }

    private void fill() {
        tv_city.setText(cityName);
        tv_temp.setText(String.format(Locale.getDefault(), "%.0f %s", temperature, Html.fromHtml("&#8451;")));
        tv_details.setText(details);
        tv_weatherIcon.setWeatherIcon(weatherId, sunrise, sunset);
        requestForecastData();
    }

    private void requestForecastData() {
//        Log.i("TagWeatherFrag", "request data");

        String url = String.format(Locale.getDefault(), URL_FORECAST_BY_ID, cityId);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                Log.i("TagWeatherFrag", "response : \n" + response);
                try {
                    if (response.getString("cod").equals("200")) {
                        JSONArray jsonList = response.getJSONArray("list");
                        handleForecastData(jsonList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TagWeatherFrag", "error : " + error.getMessage());
            }
        });
        App.getRequestQueue().add(request);
    }

    private void handleForecastData(JSONArray jsonList) throws JSONException {
        WeatherPack[] wps = new WeatherPack[3];
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        int offset = c.get(Calendar.HOUR_OF_DAY);
        offset = (offset < 12) ? 0 : 1;

//        Log.i("TagWeatherFrag", "handle1");

        for (int i = 0; i < 8 * wps.length; i++) {
            if (wps[i/8] == null) wps[i/8] = new WeatherPack();
            JSONObject json = jsonList.getJSONObject(i);
//            Log.i("TagWeatherFrag", "json : " + json.toString());
            double temp = json.getJSONObject("main").getDouble("temp");
            JSONObject wObj = json.getJSONArray("weather").getJSONObject(0);
            int id = wObj.getInt("id");
            String main = wObj.getString("main");
            wps[i / 8].ids[i % 8] = (id == 800) ? id : id / 100;
            wps[i / 8].temps[i % 8] = temp;
            wps[i / 8].mains[i % 8] = main;
        }

//        Log.i("TagWeatherFrag", "handle2");


        forecastData = new JSONArray();
        for (int i = 0; i < wps.length; i++) {
//            Log.i("TagWeatherFrag", "wps[" + i + "].ids = " + Arrays.toString(wps[i].ids));
//            Log.i("TagWeatherFrag", "wps[" + i + "].temps = " + Arrays.toString(wps[i].temps));
//            Log.i("TagWeatherFrag", "wps[" + i + "].mains = " + Arrays.toString(wps[i].mains));

            int dayNumber = (dayOfWeek + offset + i) % 7;
            if (dayNumber == 0) dayNumber = 7;
            JSONObject obj = new JSONObject();
            obj.put("temp", wps[i].getMeanTemp());
            obj.put("id", wps[i].getWeatherId());
            obj.put("main", wps[i].getMain());
            obj.put("day", WeatherPack.DAYS[dayNumber - 1]);

            forecastData.put(obj);
        }

//        Log.i("TagWeatherFrag", "handle3");


        updateDisplay();

    }

    private void updateDisplay() {
//        Log.i("TagWeatherFrag", forecastData.toString());
        ForecastRecyclerViewAdapter adapter = new ForecastRecyclerViewAdapter(forecastData);
        rv_forecast.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_forecast.setItemAnimator(new DefaultItemAnimator());
        rv_forecast.setAdapter(adapter);
        forecastLoaded = true;
    }

    static class WeatherPack {
        int[] ids = new int[8];
        double[] temps = new double[8];
        String[] mains = new String[8];
        public static final String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        private int modeIndex = -1;
        public int dayNumber = 0;

        public double getMeanTemp() {
            double sum = 0.0;
            for (double x : temps) {
                sum += x;
            }
            return sum / temps.length;
        }

        public int getWeatherId() {
            HashMap<Integer, Integer> hm = new HashMap<>();
            int max = 0;
            modeIndex = 0;
            for (int i = 0; i < ids.length; i++) {
                int count = 0;
                if (hm.containsKey(ids[i])) {
                    count = hm.get(ids[i]) + 1;
                    hm.put(ids[i], count);
                } else {
                    count = 1;
                    hm.put(ids[i], 1);
                }
                if (count > max) {
                    max = count;
                    modeIndex = i;
                }
            }
            return ids[modeIndex];
        }

        public String getMain() {
            if (modeIndex != -1) {
                return mains[modeIndex];
            } else {
                return "";
            }
        }


    }


}
