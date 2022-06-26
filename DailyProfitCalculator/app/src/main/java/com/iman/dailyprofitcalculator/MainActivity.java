package com.iman.dailyprofitcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Objects;
import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences mp;
    private EditText input_mojudi;
    private EditText input_darsad;
    private Button cal;
    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mp = getPreferences(MODE_PRIVATE);

        input_mojudi = findViewById(R.id.mojudi);
        input_darsad = findViewById(R.id.darsad);
        tv_result = findViewById(R.id.result);
        cal = findViewById(R.id.calculator);

        input_darsad.setText(mp.getString("darsad", "0").equals("") ? "0" : mp.getString("darsad", "0"));



        input_darsad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double darsad;
                if (s.toString().equals("")){
                    darsad = 0;
                } else {
                    darsad = Double.valueOf(s.toString());
                }
                if (darsad <= 0){
                    input_darsad.setError("invalid percent", null);
                }
            }
        });

        input_mojudi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double mojudi;
                if (s.toString().equals("")){
                    mojudi = 0;
                } else {
                    mojudi = Double.valueOf(s.toString());
                }
                if (mojudi <= 0){
                    input_mojudi.setError("invalid Margin", null);
                }



            }
        });

        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String margin = input_mojudi.getText().toString();
                if (margin.equals("")){
                    input_mojudi.setError("invalid Margin", null);
                    input_mojudi.requestFocus();
                    return;
                }
                String darsad = input_darsad.getText().toString();
                if (darsad.equals("") || darsad.equals("0")){
                    input_darsad.setError("invalid percent", null);
                    input_darsad.requestFocus();
                    return;
                }

                double marginN = Double.valueOf(margin);
                double darsadN = Double.valueOf(darsad);
                double result = marginN * darsadN / 100;

                tv_result.setText("+ " + result + " $");
//                Objects.requireNonNull(getCurrentFocus()).clearFocus();

                //margin = 0
            }
        });

    }

    @Override
    protected void onPause() {
        mp.edit().putString("darsad", input_darsad.getText().toString().equals("") ? "0" : input_darsad.getText().toString()).apply();
        super.onPause();
    }

}
