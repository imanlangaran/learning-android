package com.iman.smsapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class IncomingSmsActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_sms);
        tv = findViewById(R.id.tv);

        String phone = getIntent().getStringExtra("phone");
        String msg = getIntent().getStringExtra("msg");

        tv.setText("<" + phone + "> :\n" + msg);

    }



}
