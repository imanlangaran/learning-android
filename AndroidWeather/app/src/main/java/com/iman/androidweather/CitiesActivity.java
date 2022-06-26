package com.iman.androidweather;

import android.os.Bundle;
import android.text.BoringLayout;
import android.util.DisplayMetrics;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.iman.androidweather.data.CityDBHelper;
import com.iman.androidweather.data.CityModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CitiesActivity extends AppCompatActivity implements AddCityFragment.AddCityInterface {

    RecyclerView recyclerView;
    CityRecyclerViewAdapter adapter;
    CityDBHelper dbHelper;
    List<CityModel> cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCityFragment fragment = new AddCityFragment();
                fragment.show(getSupportFragmentManager(), "addcity");
            }
        });

        dbHelper = new CityDBHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateDisplay();
    }

    public void updateDisplay(){
        cityList = dbHelper.getCities("selected = 1", null);
        adapter = new CityRecyclerViewAdapter(cityList, dbHelper);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void addCity(long cityId) {
        dbHelper.updateCitySelected(cityId, true);
        updateDisplay();
    }


}
