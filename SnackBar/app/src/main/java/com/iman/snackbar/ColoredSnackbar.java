package com.iman.snackbar;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class ColoredSnackbar {

    public static final int BLUE = 0xFF3F51B5;
    public static final int ORANGE = 0xFFF44336;
    public static final int GREEN = 0xFF4CAF50;
    public static final int RED = 0xFFE91E63;

    private static View getSnackbarLayout(Snackbar snackbar){
        if (snackbar != null){
            return snackbar.getView();
        }
        return null;
    }

    private static Snackbar colorSnackbar(Snackbar snackbar, int color){
        View sbView = getSnackbarLayout(snackbar);
        if (sbView != null) {
            sbView.setBackgroundColor(color);
        }
        return snackbar;
    }

    public static Snackbar info (Snackbar snackbar){
        return colorSnackbar(snackbar, BLUE);
    }

    public static Snackbar warning(Snackbar snackbar){
        return colorSnackbar(snackbar, ORANGE);
    }

    public static Snackbar success(Snackbar snackbar){
        return colorSnackbar(snackbar, GREEN);
    }

    public static Snackbar alert(Snackbar snackbar){
        return colorSnackbar(snackbar, RED);
    }

}
