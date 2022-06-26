package com.iman.fragment1;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;

public class ScreenUtility {
    private float dpWight, dpHeight;
    private Activity activity;

    public ScreenUtility (Activity activity){
        this.activity = activity;

        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        float density = activity.getResources().getDisplayMetrics().density;
        dpWight = metrics.widthPixels / density;
        dpHeight = metrics.heightPixels / density;
    }

    public float getDpWight() {
        return dpWight;
    }

    public float getDpHeight() {
        return dpHeight;
    }
}
