package com.iman.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv;

        132-245


        switch (...){
            String s;
            case :
                int num1, num2, pos, res;

                if (s.contains("+")){

                    pos = s.indexOf("+");
                    num1 = Integer.valueOf(s.substring(0, pos - 1));
                    num2 = Integer.valueOf(s.substring(pos+1));

                    res = num1 + num2;

                } else if (s.contains("-")){

                    pos = s.indexOf("-");
                    num1 = Integer.valueOf(s.substring(0, pos - 1));
                    num2 = Integer.valueOf(s.substring(pos+1));

                    res = num1 - num2;

                }

                tv.setText(String.valueOf(res));




        }



    }







}