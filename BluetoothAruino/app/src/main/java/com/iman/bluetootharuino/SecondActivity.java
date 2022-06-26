package com.iman.bluetootharuino;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class SecondActivity extends AppCompatActivity {


    private static final int REQUEST_ENABLE_BT = 1;
    BluetoothAdapter bta;                 //bluetooth stuff
    BluetoothSocket mmSocket;             //bluetooth stuff
    BluetoothDevice mmDevice;             //bluetooth stuff
    //    Button switchLight, switchRelay;      //UI stuff
//    TextView response;                    //UI stuff
    boolean lightFlag = false;            //flags to determ. if ON/OFF
    boolean relayFlag = true;             //flags to determ. if ON/OFF
    ConnectedThread btt = null;           //Our custom thread
    public Handler mHandler;              //this receives messages from thread

    private TextView textView;

//    private UUID uuid = null;

//    private static final UUID uuid = UUID.fromString("52fa83a6-fdfa-479e-bd02-2d44900c0271");


//    private static final int BLUETOOTH_ENABLE_REQ = 1;
//    private static final int PERM_REQ = 2;

//    private BluetoothAdapter bluetoothAdapter;
//    private BluetoothDevice device;

//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            byte[] writeBuf = (byte[]) msg.obj;
//            int begin = msg.arg1;
//            int end = msg.arg2;
//
//            switch (msg.what){
//                case 1:
//                    String writeMessage = new String(writeBuf);
//                    writeMessage = writeMessage.substring(begin, end);
//                    textView.append("\n" + writeMessage);
//                    break;
//            }
//        }
//    };


//    private ConnectThread connectThread;
//    private ConnectedThread connectedThread;

//    private Button btn;

//    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textView = findViewById(R.id.tv);
        textView.setMovementMethod(new ScrollingMovementMethod());


        bta = BluetoothAdapter.getDefaultAdapter();
        //bluetooth is not enabled then create Intent for user to turn it onif
        if (!bta.isEnabled()) {
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT);
        } else {
            initiateBluetoothProcess();
        }


//        actionBar = getSupportActionBar();

//        settitle(isConnected);

//        btn = findViewById(R.id.btn);


//        textView.append("\n" + bluetoothDevice.getName() + "\n" + bluetoothDevice.getAddress());

//        btn.setOnClickListener(this);

//        canWork = checkPerms();

//        device = (BluetoothDevice) getIntent().getExtras().get("device");
//        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        if (bluetoothAdapter == null) {
//            Toast.makeText(this, "Your Device Doesn't Support Bluetooth!", Toast.LENGTH_LONG).show();
//            finish();
//        }

//        canWork = enableBluetooth();

//        connectThread = new ConnectThread(device);
//        connectThread.start();
//        Toast.makeText(this, "new ConnectThread", Toast.LENGTH_SHORT).show();

//        if (bluetoothAdapter.isEnabled()){
////            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
////            if (pairedDevices.size() == 1){
////                for (BluetoothDevice t : pairedDevices) {
//
////                }
//
////            } else {
////                Toast.makeText(this, "Disconnect from other devices...", Toast.LENGTH_LONG).show();
////            }
//        } else {
//            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), BLUETOOTH_ENABLE_REQ);
//            Toast.makeText(this, "Restart App...", Toast.LENGTH_LONG).show();
//        }


        ///////null nabashe bluetooth device

//        if (bluetoothDevice != null) {
//
//        }
//        else {
//            Toast.makeText(this, "ERROR!!!", Toast.LENGTH_SHORT).show();
//            finish();
//        }


//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                if (connectedThread != null){
//                    boolean t = connectedThread.isConnected();
//                    if (t != isConnected){
//                        settitle(t);
//                        isConnected = t;
//                    }
//                    // -----
//
//                    // -----
//                }
//            }
//        }, 0, 1000);

    }

//    private void settitle(boolean t) {
//        String title = (t)? "Connected" : "Not Connected";
//        if (actionBar != null){
//            actionBar.setTitle(title);
//        } else Toast.makeText(SecondActivity.this, title, Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    public void onClick(View v) {
//        if (v.getId() == R.id.btn){
//            checkPerms();
//            if (bluetoothAdapter.isEnabled()) {
//                if (connectedThread != null) {
//                    if (connectedThread.isConnected()) {
//                        connectedThread.write("*".getBytes());
//                    } else Toast.makeText(this, "Not Connected!", Toast.LENGTH_SHORT).show();
//                } else Toast.makeText(this, "Not Connected!", Toast.LENGTH_SHORT).show();
//            } else {
//                startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), BLUETOOTH_ENABLE_REQ);
//            }
//        }
//    }


//    private class ConnectThread extends Thread {
//        private final BluetoothSocket bluetoothSocket;
//
//        //        private final BluetoothDevice bluetoothDevice;
//        public ConnectThread(BluetoothDevice device) {
////            this.bluetoothDevice = device;
//            BluetoothSocket tmp = null;
//            try {
//                tmp = device.createRfcommSocketToServiceRecord(uuid);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            bluetoothSocket = tmp;
//        }
//
//        @Override
//        public void run() {
//            if (bluetoothAdapter.isDiscovering()) {
//                bluetoothAdapter.cancelDiscovery();
//            }
//            try {
//                bluetoothSocket.connect();
//            } catch (IOException e) {
//                try {
//                    bluetoothSocket.close();
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//                return;
//            }
//
//            new ConnectedThread(bluetoothSocket).start();
//
//            Toast.makeText(SecondActivity.this, "new ConnectedThread", Toast.LENGTH_SHORT).show();
//        }
//
//        public void cancel() {
//            try {
//                bluetoothSocket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }


//    private class ConnectedThread extends Thread {
//        private final BluetoothSocket bluetoothSocket;
//        private final InputStream inputStream;
//        private final OutputStream outputStream;
//
//        public ConnectedThread(BluetoothSocket socket) {
//            bluetoothSocket = socket;
//            InputStream tmpIn = null;
//            OutputStream tmpOut = null;
//            try {
//                tmpIn = socket.getInputStream();
//                tmpOut = socket.getOutputStream();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            inputStream = tmpIn;
//            outputStream = tmpOut;
//
////            runOnUiThread(new Runnable() {
////                @Override
////                public void run() {
////                    isConnected = true;
////                }
////            });
//        }
//
//        @Override
//        public void run() {
//            byte[] buffer = new byte[1024];
//            int begin = 0;
//            int bytes = 0;
//            while (true) {
//                try {
//                    bytes += inputStream.read(buffer, bytes, buffer.length - bytes);
//                    for (int i = begin; i < bytes; i++) {
//                        if (buffer[i] == "#".getBytes()[0]) {
//                            handler.obtainMessage(1, begin, i, buffer).sendToTarget();
//                            begin = i + 1;
//                            if (i == bytes - 1) {
//                                bytes = 0;
//                                begin = 0;
//                            }
//                        }
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    break;
//                }
//            }
//        }
//
//
//        public void write(byte[] bytes) {
//            try {
//                outputStream.write(bytes);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        public void cancel() {
//            try {
//                bluetoothSocket.close();
//                outputStream.close();
//                inputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
////        public boolean isConnected(){
////            return bluetoothSocket.isConnected();
////        }
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("1").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
//                if (connectedThread.isAlive()){
//                    connectedThread.write("LightSwitch".getBytes());
//                }

                if (mmSocket.isConnected() && btt != null) {
//                    if (!lightFlag) {
//                        String sendtxt = "LY";
//                        btt.write(sendtxt.getBytes());
//                        lightFlag = true;
//                    } else {
//                        String sendtxt = "LN";
//                        btt.write(sendtxt.getBytes());
//                        lightFlag = false;
//                    }
                    btt.write("1".getBytes());
                } else {
                    Toast.makeText(SecondActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }


                return false;
            }
        });

        menu.add("2").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
//                if (connectedThread.isAlive()){
//                    connectedThread.write("PowerOutlet".getBytes());
//                }

                if (mmSocket.isConnected() && btt != null) {

                    btt.write("2".getBytes());


//                    if (relayFlag) {
//                        String sendtxt = "RY";
//                        btt.write(sendtxt.getBytes());
//                        relayFlag = false;
//                    } else {
//                        String sendtxt = "RN";
//                        btt.write(sendtxt.getBytes());
//                        relayFlag = true;
//                    }

                    //disable the button and wait for 4 seconds to enable it again
//                    switchRelay.setEnabled(false);
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try{
//                                Thread.sleep(4000);
//                            }catch(InterruptedException e){
//                                return;
//                            }
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    switchRelay.setEnabled(true);
//                                }
//                            });
//                        }
//                    }).start();


                } else {
                    Toast.makeText(SecondActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }


                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

//    private boolean checkPerms() {
//        if (checkSelfPermission(Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED ||
//                checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
//                checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
//                checkSelfPermission(Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(new String[]{Manifest.permission.BLUETOOTH,
//                            Manifest.permission.ACCESS_FINE_LOCATION,
//                            Manifest.permission.ACCESS_COARSE_LOCATION,
//                            Manifest.permission.BLUETOOTH_ADMIN},
//                    PERM_REQ);
//            return false;
//        } else return true;
//    }

//    private boolean enableBluetooth() {
//        if (bluetoothAdapter.isEnabled()) {
//            return true;
//        } else {
//            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), BLUETOOTH_ENABLE_REQ);
//            return false;
//        }
//    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (requestCode == BLUETOOTH_ENABLE_REQ) {
//            if (resultCode == RESULT_OK) {
//                Toast.makeText(this, "Bluetooth Enabled", Toast.LENGTH_SHORT).show();
////                canWork = true;
//            } else {
//                Toast.makeText(this, "Bluetooth Didn't Enabled", Toast.LENGTH_SHORT).show();
////                canWork = false;
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == PERM_REQ) {
//            for (int i : grantResults) {
//                if (i != PackageManager.PERMISSION_GRANTED){
//                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
//                    canWork = false;
//                    break;
//                } else canWork = true;
//            }
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }


    public void initiateBluetoothProcess() {
        if (bta.isEnabled()) {
            //attempt to connect to bluetooth module
            BluetoothSocket tmp = null;
            mmDevice = bta.getRemoteDevice(getIntent().getStringExtra("MAC"));
            Log.i("[BLUETOOTHHHH]", "Connecting to: " + mmDevice.getAddress());

            if (bta.isDiscovering()) {
                bta.cancelDiscovery();
            }

            //create socket
            try {
                tmp = mmDevice.createRfcommSocketToServiceRecord(mmDevice.getUuids()[0].getUuid());
                mmSocket = tmp;
                mmSocket.connect();
                Log.i("[BLUETOOTHHHH]", "Connected to: " + mmDevice.getName());
            } catch (IOException e) {
                Log.i("[BLUETOOTHHHH]", "Not Connected to: " + mmDevice.getName());
                Log.i("[BLUETOOTHHHH]", "reason : " + e.getMessage());
                try {
                    mmSocket.close();
                } catch (IOException c) {
                    return;
                }
            }
//        Log.i("[BLUETOOTH]", "Creating handler");
            mHandler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (msg.what == ConnectedThread.RESPONSE_MESSAGE) {
                        String txt = (String) msg.obj;
                        textView.append("\n" + txt);
                        Toast.makeText(SecondActivity.this, txt, Toast.LENGTH_SHORT).show();
                    }
                }
            };
        }
//        Log.i("[BLUETOOTH]", "Creating and running Thread");
        btt = new ConnectedThread(mmSocket, mHandler);
        btt.start();
    }


    public static class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        public static final int RESPONSE_MESSAGE = 10;
        Handler uih;

        public ConnectedThread(BluetoothSocket socket, Handler uih) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            this.uih = uih;
            Log.i("[THREAD-CT]", "Creating thread");
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();

            } catch (IOException e) {
                Log.e("[THREAD-CT]", "Error:" + e.getMessage());
            }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
            try {
                mmOutStream.flush();
            } catch (IOException e) {
                return;
            }
            Log.i("[THREAD-CT]", "IO's obtained");
        }


        public void run() {
            BufferedReader br;
            br = new BufferedReader(new InputStreamReader(mmInStream));
            while (true) {
                try {
                    String resp = br.readLine();
                    Message msg = new Message();
                    msg.what = RESPONSE_MESSAGE;
                    msg.obj = resp;
                    uih.sendMessage(msg);
                } catch (IOException e) {
                    break;
                }
            }
            Log.i("[THREAD-CT]", "While loop ended");
        }

        public void write(byte[] bytes) {
            try {
                Log.i("[THREAD-CT]", "Writting bytes");
                mmOutStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_ENABLE_BT) {
            initiateBluetoothProcess();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
//        if (connectThread != null && connectThread.isAlive()) {
//            connectThread.cancel();
//        }
//        if (connectedThread != null && connectedThread.isAlive()) {
//            connectedThread.cancel();
//        }

        if (btt != null) {
            if (btt.isAlive()) {
                btt.cancel();
            }
        }
        super.onDestroy();
    }
}