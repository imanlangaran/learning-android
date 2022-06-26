package com.iman.bluetoothtest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 10;
    private static final int PERM_REQ_CODE = 11;
    private BluetoothAdapter bluetoothAdapter;

    private TextView textView;
    private EditText editText;
    private Button discover;
    private Button server;
    private Button client;
    private ImageButton send;
    private BroadcastReceiver receiver;


    private AcceptThread acceptThread;
    private ConnectThread connectThread;
    private ConnectedThread connectedThread;

    private boolean lastStateConnected = false;
    private boolean currentStateConnected = false;
    private String RemoteDeviceName = "";


    private ActionBar actionBar;


    private static final String uuid = "e4faf5e0-c141-11ea-b3de-0242ac130004";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();

        connectedThread = null;

        textView = findViewById(R.id.mytext);
        editText = findViewById(R.id.editttext);
        discover = findViewById(R.id.discover);
        server = findViewById(R.id.server);
        client = findViewById(R.id.client);
        send = findViewById(R.id.send);

        textView.setMovementMethod(new ScrollingMovementMethod());

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Your Device Doesnot Support Bluetooth", Toast.LENGTH_SHORT).show();
            finish();
        }

        if (!bluetoothAdapter.isEnabled()) {
            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_ENABLE_BT);
            Toast.makeText(this, "Bluetooth is enable", Toast.LENGTH_SHORT).show();
        }

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                textView.append("\n" + deviceName + " - " + deviceHardwareAddress);
            }
        }

        discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothAdapter.isDiscovering())
                    bluetoothAdapter.cancelDiscovery();

                if (!bluetoothAdapter.startDiscovery()) {
                    Toast.makeText(MainActivity.this, "failed!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "discovering ...", Toast.LENGTH_SHORT).show();
                }
            }
        });


        server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptThread = new AcceptThread();
                acceptThread.start();
            }
        });


        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<BluetoothDevice> paredDevices = bluetoothAdapter.getBondedDevices();
                if (paredDevices.size() > 0) {
                    BluetoothDevice device = (BluetoothDevice) paredDevices.toArray()[0];
                    connectThread = new ConnectThread(device);
                    connectThread.start();

                }
            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectedThread == null) {
                    Toast.makeText(MainActivity.this, "not connected", Toast.LENGTH_SHORT).show();
                } else {
                    byte[] bytes = editText.getText().toString().trim().getBytes(Charset.defaultCharset());
                    connectedThread.write(bytes);
                }
            }
        });


        initReceiver();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(receiver, filter);


//        Log.i("HIIIIIIIIII", "onCreate");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERM_REQ_CODE);
            }
        }


        createTimer();


    }

    private void createTimer() {
//        Handler handler = new Handler();
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                textView.append("\n1");
//            }
//        };
//
//        handler.postDelayed(runnable, 1000);

//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    sleep(1000);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            textView.append("\n1");
//                        }
//                    });
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        thread.start();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (connectedThread == null) {
//                            textView.append("\n" + false);
                            currentStateConnected = false;
                            if (lastStateConnected != currentStateConnected) {
                                lastStateConnected = currentStateConnected;
//                                textView.append("\n" + false);
                                stateChanged(lastStateConnected);
                            }
                        } else {
                            currentStateConnected = connectedThread.isConnected();
                            if (lastStateConnected != currentStateConnected) {
                                lastStateConnected = currentStateConnected;
//                                textView.append("\n" + lastStateConnected);
                                stateChanged(lastStateConnected);

                            }
                        }
                    }
                });
            }
        }, 0, 1000);

    }

    private void initReceiver() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
//                        Log.i("HIIIIIIIIII", "onReceive");
                String action = intent.getAction();
//                Toast.makeText(MainActivity.this, "action : " + action, Toast.LENGTH_SHORT).show();
                textView.append("\n" + action);
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    textView.append("\n" + "--------------");
                    textView.append("\n" + "name : " + device.getName() + "\naddress : " + device.getAddress()
                            + "\nBluetoothClass : " + device.getBluetoothClass() + "\nBondState : " + device.getBondState()
                            + "\nUuids : " + device.getType() + Arrays.toString(device.getUuids()));
                    textView.append("\n" + "--------------");
                } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                    Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
                    if (pairedDevices.size() > 0) {
                        for (BluetoothDevice device : pairedDevices) {
                            String deviceName = device.getName();
                            String deviceHardwareAddress = device.getAddress(); // MAC address
                            textView.append("\n" + deviceName + " - " + deviceHardwareAddress);
                        }
                    }
                }
            }
        };
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Bluetooth is OFF!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Bluetooth is turning on ...", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERM_REQ_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied, closing application", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private class AcceptThread extends Thread {

        private final BluetoothServerSocket serverSocket;

        public AcceptThread() {
            BluetoothServerSocket tmp = null;
            try {
                tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord("NAME", UUID.fromString(uuid));
            } catch (IOException e) {
                e.printStackTrace();
            }

            serverSocket = tmp;
        }


        @Override
        public void run() {
            BluetoothSocket socket = null;
            while (true) {
                try {
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }

                if (socket != null) {
                    try {
                        manageMyConnectedSocket(socket);
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }


        public void cancel() {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private class ConnectThread extends Thread {

        private final BluetoothSocket socket;
        private final BluetoothDevice device;

        public ConnectThread(BluetoothDevice device) {
            BluetoothSocket tmp = null;
            this.device = device;

            try {
                tmp = device.createRfcommSocketToServiceRecord(UUID.fromString(uuid));
            } catch (IOException e) {
                e.printStackTrace();
            }

            socket = tmp;
        }


        @Override
        public void run() {
            if (bluetoothAdapter.isDiscovering()) {
                bluetoothAdapter.cancelDiscovery();
            }

            try {
                socket.connect();
            } catch (IOException e) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return;
            }
            manageMyConnectedSocket(socket);
        }


        public void cancel() {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private void manageMyConnectedSocket(BluetoothSocket socket) {
        connectedThread = new ConnectedThread(socket);
        connectedThread.start();
//        final boolean isConnected = socket.isConnected();
        RemoteDeviceName = socket.getRemoteDevice().getName();
        stateChanged(true);

//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                textView.append("\n(manage)isConnected : " + isConnected);
//                textView.append("\n(manage)RemoteDeviceName : " + RemoteDeviceName);
//            }
//        });
    }

    private void stateChanged(boolean state) {
        final String t = RemoteDeviceName + "\n" + ((state) ? "online" : "offline");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                titleSetter(t);
            }
        });
    }

//    public class MyBluetoothService extends Thread{
//        private final static String TAG = "MY_APP_DEBUG_TAG";
//        private Handler handler;
//
//        private interface MessageContents {
//            public static final int MESSAGE_READ = 0;
//            public static final int MESSAGE_WRITE = 1;
//            public static final int MESSAGE_TOAST = 2;
//        }
//
//        private class ConnectedThread extends Thread{
//            private final BluetoothSocket socket;
//            private final InputStream inputStream;
//            private final OutputStream outputStream;
//            private byte[] buffer;
//
//
//            public ConnectedThread(BluetoothSocket socket){
//                this.socket = socket;
//                InputStream tmpIn = null;
//                OutputStream tmpOut = null;
//
//                try {
//                    tmpIn = socket.getInputStream();
//                    tmpOut = socket.getOutputStream();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                inputStream = tmpIn;
//                outputStream = tmpOut;
//
//            }
//
//
//            @Override
//            public void run() {
//                buffer = new byte[1024];
//                int numBytes;
//
//                while (true){
//                    try {
//                        numBytes = inputStream.read(buffer);
////                        Message readMsg = handler.obtainMessage(MessageContents.MESSAGE_READ, numBytes, -1 ,buffer);
////                        readMsg.sendToTarget();
//
//                        final String meeeesage = new String(buffer, 0, numBytes);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
////                                Toast.makeText(MainActivity.this, meeeesage, Toast.LENGTH_SHORT).show();
//                                textView.append("\n" + meeeesage);
//                            }
//                        });
//
//
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        break;
//                    }
//                }
//            }
//
//
//            public void write(byte[] bytes){
//                try {
//                    outputStream.write(bytes);
//
//                    Message writtenMsg = handler.obtainMessage(MessageContents.MESSAGE_WRITE, -1, -1, buffer);
//                    writtenMsg.sendToTarget();
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//
//                    Message writeErrorMsg = handler.obtainMessage(MessageContents.MESSAGE_TOAST);
//
//                    Bundle bundle = new Bundle();
//                    bundle.putString("toast", "Couldn't send data to the other device");
//                    writeErrorMsg.setData(bundle);
//                    handler.sendMessage(writeErrorMsg);
//                }
//
//
//
//            }
//
//
//            public void cancel(){
//                try {
//                    socket.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
//
//    }


    private class ConnectedThread extends Thread {
        private final BluetoothSocket socket;
        private final InputStream inputStream;
        private final OutputStream outputStream;
        private byte[] buffer;


        public ConnectedThread(BluetoothSocket socket) {
            this.socket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            inputStream = tmpIn;
            outputStream = tmpOut;

        }


        @Override
        public void run() {

//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    textView.append("" + socket.isConnected());
//                }
//            });

//            if (lastState = socket.isConnected()){
//
//            }


            buffer = new byte[1024];
            int numBytes;

            while (true) {
//                textView.append("1-" + socket.isConnected());

                try {
                    numBytes = inputStream.read(buffer);
//                        Message readMsg = handler.obtainMessage(MessageContents.MESSAGE_READ, numBytes, -1 ,buffer);
//                        readMsg.sendToTarget();

                    final String meeeesage = new String(buffer, 0, numBytes);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                                Toast.makeText(MainActivity.this, meeeesage, Toast.LENGTH_SHORT).show();
                            textView.append("\n" + meeeesage);
//                            textView.append("2-" + socket.isConnected());

                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }


        }


        public void write(byte[] bytes) {

            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            try {
//                outputStream.write(bytes);
//
//                Message writtenMsg = handler.obtainMessage(MessageContents.MESSAGE_WRITE, -1, -1, buffer);
//                writtenMsg.sendToTarget();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//
//                Message writeErrorMsg = handler.obtainMessage(MessageContents.MESSAGE_TOAST);
//
//                Bundle bundle = new Bundle();
//                bundle.putString("toast", "Couldn't send data to the other device");
//                writeErrorMsg.setData(bundle);
//                handler.sendMessage(writeErrorMsg);
//            }


        }


        public void cancel() {
            try {
                inputStream.close();
                outputStream.close();
                socket.close();
//                Toast.makeText(MainActivity.this, "closed", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public boolean isConnected() {
            if (socket == null) {
                Toast.makeText(MainActivity.this, "socket is null", Toast.LENGTH_SHORT).show();
                return false;
            }
            return socket.isConnected();
        }

        public boolean disconnect() {
            if (socket == null) {
                Toast.makeText(MainActivity.this, "socket is null", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (socket.isConnected()) {
                try {
                    inputStream.close();
                    outputStream.close();
                    socket.close();
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            } else {
                Toast.makeText(MainActivity.this, "socket is not connected", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

//        public boolean isClosed(){
//            if (socket == null){
//                Toast.makeText(MainActivity.this, "socket is null", Toast.LENGTH_SHORT).show();
//                return false;
//            }

//        }

    }


    public void titleSetter(String title) {
        if (actionBar == null) {
            Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
        } else {
            actionBar.setTitle(title);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("is connected").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                boolean isConnected;
                if (connectedThread == null) {
                    isConnected = false;
                } else
                    isConnected = connectedThread.isConnected();
                Toast.makeText(MainActivity.this, "" + isConnected, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        menu.add("disconnect").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                boolean disconnected = connectedThread.disconnect();
                Toast.makeText(MainActivity.this, "disconnected succesfully", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (acceptThread != null && acceptThread.isAlive()) {
            acceptThread.cancel();
        }
        if (connectThread != null && connectThread.isAlive()) {
            connectThread.cancel();
        }
        if (connectedThread != null) {
            connectedThread.cancel();
        }
        unregisterReceiver(receiver);
    }
}
