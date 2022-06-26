package com.iman.snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout = findViewById(R.id.coordinator_layout);
    }

    public void simpleSnackbar(View v) {
        Snackbar.make(coordinatorLayout, "Simple Snackbar ...", Snackbar.LENGTH_INDEFINITE).show();
    }

    public void snackbarWithAction(View v) {
        Snackbar.make(coordinatorLayout, "No Internet Connection ...", Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(coordinatorLayout, "Connecting ...", Snackbar.LENGTH_SHORT).show();
                    }
                }).show();

    }

    public void customSnackbar(View v) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "No Internet Connection ...", Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(coordinatorLayout, "Connecting ...", Snackbar.LENGTH_SHORT).show();
                    }
                });

        snackbar.setActionTextColor(Color.rgb(0, 130, 200));

        View sbView = snackbar.getView();
        TextView sbTextView = sbView.findViewById(com.google.android.material.R.id.snackbar_text);
        sbTextView.setTextColor(Color.GREEN);

        snackbar.show();
    }

    public void info(View v) {
        int color = 0xffffffff - ColoredSnackbar.BLUE + 0xff000000;
        Snackbar snackbar = ColoredSnackbar.info(Snackbar.make(coordinatorLayout, String.valueOf(color), Snackbar.LENGTH_INDEFINITE));
        TextView sbTextView = snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        sbTextView.setTextColor(0xffffffff - ColoredSnackbar.BLUE + 0xff000000);
        snackbar.show();
    }

    public void warning(View v){
        int color = 0xffffffff - ColoredSnackbar.ORANGE + 0xff000000;
        Snackbar snackbar = ColoredSnackbar.warning(Snackbar.make(coordinatorLayout, color + " ", Snackbar.LENGTH_INDEFINITE));
        TextView sbTextView = snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        sbTextView.setTextColor(color);
        snackbar.show();
    }

    public void success(View v){
        int color = 0xffffffff - ColoredSnackbar.GREEN + 0xff000000;
        Snackbar snackbar = ColoredSnackbar.success(Snackbar.make(coordinatorLayout, color + " ", Snackbar.LENGTH_INDEFINITE));
        TextView sbTextView = snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        sbTextView.setTextColor(color);
        snackbar.show();
    }

    public void alert(View v){
        int color = 0xffffffff - ColoredSnackbar.BLUE + 0xff000000;
        Snackbar snackbar = ColoredSnackbar.alert(Snackbar.make(coordinatorLayout, color + " ", Snackbar.LENGTH_INDEFINITE));
        TextView sbTextView = snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        sbTextView.setTextColor(color);
        snackbar.show();
    }
}
