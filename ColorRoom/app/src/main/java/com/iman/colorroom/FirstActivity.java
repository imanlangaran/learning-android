package com.iman.colorroom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.StateListAnimator;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FirstActivity extends AppCompatActivity {

    private static final int ENABLE_BLUETOOTH_REQ = 1;
    private BluetoothAdapter bluetoothAdapter;
    private List<BluetoothDevice> deviceList;
    private ListView mListView;
    private ConstraintLayout rootLayout;
    int color = 0xfff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mListView = findViewById(R.id.mList);
        rootLayout = findViewById(R.id.firstRootLayout);

        color = getResources().getColor(android.R.color.holo_blue_bright);

        rootLayout.setBackgroundColor(color);
        setStatusBarColor(color);


        if (!bluetoothAdapter.isEnabled()){
            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), ENABLE_BLUETOOTH_REQ);
        } else {
            refreshList();
        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                {
//                    Toast.makeText(FirstActivity.this, "Connecting ...", Toast.LENGTH_SHORT).show();
//                }
                Intent i = new Intent(FirstActivity.this, SecondActivity.class);
                i.putExtra("MAC", deviceList.get(position).getAddress());
                startActivity(i);
                finish();
            }
        });


//        testToast();

    }

//    private void testToast() {
//        int r = 255;
//        int g = 255;
//        int b = 255;
//        long val = (r << 16) | (g <<  8) | b;
//        Toast.makeText(this, "" + val, Toast.LENGTH_LONG).show();
//    }

    private void refreshList() {
        deviceList = new ArrayList<>(bluetoothAdapter.getBondedDevices());
        List<String> deviceNames = new ArrayList<>();
        for (BluetoothDevice d : deviceList){
            deviceNames.add(d.getName());
        }
        mListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, deviceNames));
        mListView.setAlpha(0f);
        mListView.setScaleX(0.5f);
        mListView.animate().alpha(1f).setDuration(600).scaleX(1f).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ENABLE_BLUETOOTH_REQ){
            if (resultCode == RESULT_OK) {
                refreshList();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onClick(View v){
        if (!bluetoothAdapter.isEnabled()){
            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), ENABLE_BLUETOOTH_REQ);
        } else {
            refreshList();
        }
    }

    public void setStatusBarColor(int color){
        Window w = this.getWindow();
        w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        w.setStatusBarColor(color);
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    }
}