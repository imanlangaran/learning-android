package com.iman.androidweather;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.Date;

public class TextViewWeather extends TextView {


    public TextViewWeather(Context context) {
        super(context);
        init(context);
    }

    public TextViewWeather(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public TextViewWeather(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/weathericons.ttf");
        setTypeface(typeface);
    }

    public void setWeatherIcon(long id,long sunrise, long sunset){
        String weather_icon_code = "";
        if(id == 800){ // clear
            long currentTime = new Date().getTime();
            if(currentTime >= sunrise && currentTime < sunset){
                weather_icon_code = getResources().getString(R.string.weather_sunny);
            } else {
                weather_icon_code = getResources().getString(R.string.weather_clear_night);
            }
        } else {
            if(id > 100){
                id = id / 100;
            }
            switch ((int) id){
                case 2 :
                    weather_icon_code = getResources().getString(R.string.weather_thunder);
                    break;
                case 3 :
                    weather_icon_code = getResources().getString(R.string.weather_drizzle);
                    break;
                case 5 :
                    weather_icon_code = getResources().getString(R.string.weather_rainy);
                    break;
                case 6 :
                    weather_icon_code = getResources().getString(R.string.weather_snowy);
                    break;
                case 7 :
                    weather_icon_code = getResources().getString(R.string.weather_foggy);
                    break;
                case 8 :
                    weather_icon_code = getResources().getString(R.string.weather_cloudy);
                    break;
                default: break;
            }
        }
        setText(Html.fromHtml(weather_icon_code));
    }

}
