package com.iman.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    Button btn1;
    EditText inputName, inputFamily;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv1);
        btn1 = findViewById(R.id.btn1);
        inputName = findViewById(R.id.inputName);
        inputFamily = findViewById(R.id.inputFamily);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text;
                text = inputName.getText().toString().trim() + "\n" + inputFamily.getText().toString().trim();
                tv.setText(text);
            }
        });






    }




}
