package com.iman.colorpicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.skydoves.colorpickerpreference.ColorEnvelope;
import com.skydoves.colorpickerpreference.ColorListener;
import com.skydoves.colorpickerpreference.ColorPickerView;
import com.skydoves.colorpickerpreference.FlagMode;
import com.skydoves.colorpickerpreference.FlagView;

public class MainActivity extends AppCompatActivity {

    private ColorPickerView colorPicker;
    private View mView;
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mView = findViewById(R.id.mView);
        colorPicker = findViewById(R.id.colorPicker);
        mTv = findViewById(R.id.tv);

        mView.setBackgroundColor(colorPicker.getSavedColor(Color.GREEN));

        colorPicker.setColorListener(new ColorListener() {
            @Override
            public void onColorSelected(ColorEnvelope colorEnvelope) {
                mView.setBackgroundColor(colorEnvelope.getColor());
                String txt = "";
                txt += "getColor = " + colorEnvelope.getColor();
                txt += "\ngetColorRGB = ";
                for (int i : colorEnvelope.getColorRGB()){
                    txt += "-" + i + "-";
                }
                txt += "\ngetColorHtml = " + colorEnvelope.getColorHtml();
                mTv.setText(txt);
            }
        });

        colorPicker.setPreferenceName("lastColor");

        colorPicker.setFlagView(new CustomFlag(this, R.layout.flag_layout));
        colorPicker.setFlagMode(FlagMode.ALWAYS);
    }



    public class CustomFlag extends FlagView {

        private TextView textView;
        private View view;

        public CustomFlag(Context context, int layout) {
            super(context, layout);
            textView = findViewById(R.id.flag_color_code);
            view = findViewById(R.id.flag_color_layout);
        }

        @Override
        public void onRefresh(ColorEnvelope colorEnvelope) {
            onRefresh(colorEnvelope.getColor());
        }

        public void onRefresh(int color) {
            textView.setText("#" + String.format("%06X", (0xFFFFFF & color)));
            view.setBackgroundColor(color);
        }
    }

    public class CustomBubble extends FlagView{
        private ImageView view;
        public CustomBubble(Context context, int layout){
            super(context, layout);
            view = findViewById(R.id.mBubble);
        }

        @Override
        public void onRefresh(ColorEnvelope colorEnvelope) {
            view.setBackgroundTintList(new ColorStateList(new int[][]{{0}}, new int[]{colorEnvelope.getColor()}));
        }
    }







    @Override
    protected void onDestroy() {
        colorPicker.saveData();
        super.onDestroy();
    }






}