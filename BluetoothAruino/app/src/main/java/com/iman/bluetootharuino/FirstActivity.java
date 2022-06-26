package com.iman.bluetootharuino;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FirstActivity extends AppCompatActivity {

    private static final int BLUETOOTH_ENABLE_REQ = 1;

    private BluetoothAdapter bluetoothAdapter;

    private ListView listView;
    private ArrayAdapter<String> deviceArrayAdapter;
    private Set<BluetoothDevice> devices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        listView = findViewById(R.id.listView);


        if (!bluetoothAdapter.isEnabled()){
            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), BLUETOOTH_ENABLE_REQ);
        } else {
            devices = bluetoothAdapter.getBondedDevices();
        }


        final List<BluetoothDevice> bluetoothDeviceList = new ArrayList<>(devices);

        final List<String> list = new ArrayList<>();

        for (BluetoothDevice d : bluetoothDeviceList){
            list.add(d.getName());
        }

        deviceArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(deviceArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(FirstActivity.this, SecondActivity.class);
                i.putExtra("MAC", bluetoothDeviceList.get(position).getAddress());
                startActivity(i);
            }
        });
    }
}