package com.iman.androidweather;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.iman.androidweather.data.AssetDataBaseHelper;

public class App extends Application {

    public static final String API_KEY = "dd839b618cf4e609bdb9db2863ca9730";
    private static final String URL_FORMAT_BY_CITY_NAME = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&APPID=" + API_KEY;
    private static final String URL_FORMAT_BY_CITY_ID = "http://api.openweathermap.org/data/2.5/weather?id=%d&units=metric&APPID=" + API_KEY;
    public static final String URL_FORECAST_BY_ID = "http://api.openweathermap.org/data/2.5/forecast?id=%d&units=metric&appid=" + API_KEY;

    private static RequestQueue requestQueue;
    private static App appInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        new AssetDataBaseHelper(getApplicationContext()).checkDb();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public  static synchronized RequestQueue getRequestQueue(){
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(appInstance.getApplicationContext());
        }
        return requestQueue;
    }
}
