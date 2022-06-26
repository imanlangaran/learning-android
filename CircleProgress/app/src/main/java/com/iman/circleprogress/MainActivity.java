package com.iman.circleprogress;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    CircleProgress circleProgress;
    SeekBar seekBar;
    CheckBox cbAutoColored;
    CheckBox cbShowPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circleProgress = findViewById(R.id.circle_progress);
        seekBar = findViewById(R.id.seekBar_progress);
        cbAutoColored = findViewById(R.id.cb_autoColored);
        cbAutoColored.setChecked(circleProgress.isAutoColored());
        cbShowPercent = findViewById(R.id.cb_show_percent);
        cbShowPercent.setChecked(circleProgress.isShowPercent());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    circleProgress.setProgressWithAnimation(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        cbAutoColored.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                circleProgress.setAutoColored(isChecked);
            }
        });

        cbShowPercent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                circleProgress.setShowPercent(isChecked);
            }
        });
    }
}
