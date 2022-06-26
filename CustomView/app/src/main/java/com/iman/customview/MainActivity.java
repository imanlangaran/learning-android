package com.iman.customview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    ValueBar valueBar;
    ValueSelector valueSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        valueBar = findViewById(R.id.value_bar);
        valueBar.setMaxValue(100);
        valueBar.setValue(0);

        valueSelector = findViewById(R.id.value_selector);
        valueSelector.setMinValue(0);
        valueSelector.setMaxValue(100);

        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueBar.setValue(valueSelector.getValue());
            }
        });
    }
}
