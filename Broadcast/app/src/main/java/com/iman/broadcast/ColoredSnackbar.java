package com.iman.broadcast;

import android.graphics.Color;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class ColoredSnackbar {

    private static final int BLUE = 0xff0000ff;
    private static final int ORANGE = 0xFFFF5722;
    private static final int RED = 0xffff0000;
    private static final int GREEN = 0xff00ff00;

    private static View getSnackBarLayout(Snackbar snackbar){
        if (snackbar == null){
            return null;
        }else {
            return snackbar.getView();
        }
    }

    private static Snackbar colorSnackBar(Snackbar snackbar, int color){
        View sbView = ColoredSnackbar.getSnackBarLayout(snackbar);
        if (sbView != null){
            sbView.setBackgroundColor(color);
        }
        return snackbar;
    }

    public static Snackbar info(Snackbar snackbar){
        return colorSnackBar(snackbar, BLUE);
    }

    public static Snackbar warning(Snackbar snackbar){
        return colorSnackBar(snackbar, ORANGE);
    }

    public static Snackbar success(Snackbar snackbar){
        return colorSnackBar(snackbar, GREEN);
    }

    public static Snackbar error(Snackbar snackbar){
        return colorSnackBar(snackbar, RED);
    }

}
