package com.iman.smsapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int PER_REQ_CODE = 1234;
    EditText input_phone;
    EditText input_Msg;
    Button btnSend;

    SentMessageReceiver sent;
    DeliveredMessageReceiver delivered;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input_phone = findViewById(R.id.input_phone);
        input_Msg = findViewById(R.id.input_message);
        btnSend = findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = input_phone.getText().toString().trim();
                String msg = input_Msg.getText().toString().trim();
                if (phone.isEmpty()){
                    input_phone.setError("enter phone number");
                    input_phone.requestFocus();
                    return;
                }
                if (msg.isEmpty()){
                    input_Msg.setError("no message... !");
                    input_Msg.requestFocus();
                    return;
                }

                SmsSender.startSmsSender(MainActivity.this, phone, msg);

            }
        });


        checkPermission();
        register();

    }

    private void register() {
        sent = new SentMessageReceiver(){
            @Override
            protected void onSent() {
                super.onSent();
            }

            @Override
            protected void onFailure() {
                super.onFailure();
            }
        };


        delivered = new DeliveredMessageReceiver(){
            @Override
            protected void onFailure() {
                super.onFailure();
            }

            @Override
            protected void onDelivered() {
                super.onDelivered();
            }
        };


        registerReceiver(sent, new IntentFilter(SmsSender.INTENT_SENT_MESSAGE));
        registerReceiver(delivered, new IntentFilter(SmsSender.INTENT_DELIVERED_MESSAGE));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(sent);
        unregisterReceiver(delivered);
    }

    private void checkPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS}, PER_REQ_CODE);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PER_REQ_CODE){
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED || grantResults[2] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Closing Application", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
