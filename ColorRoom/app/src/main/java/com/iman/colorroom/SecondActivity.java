package com.iman.colorroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.usage.UsageEvents;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.EventLog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.skydoves.colorpickerpreference.ColorEnvelope;
import com.skydoves.colorpickerpreference.ColorListener;
import com.skydoves.colorpickerpreference.ColorPickerView;
import com.skydoves.colorpickerpreference.FlagMode;
import com.skydoves.colorpickerpreference.FlagView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Timer;
import java.util.TimerTask;

public class SecondActivity extends AppCompatActivity {

    //    private int color;
    private ConstraintLayout rootLayout;
    private ColorPickerView colorPicker;
    //    private ImageButton btn_send;
    private boolean isDark = false;


    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice bluetoothDevice;
    private BluetoothSocket bluetoothSocket;
    public Handler handler;
    private ConnectedThread connectedThread = null;

    private boolean received = true;
    private int lastColor = 1;
    private int[] lastColorInt = new int[3];

    private String currentColor;

    private Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        statusBarMode();

        ((TextView) findViewById(R.id.tv)).setMovementMethod(new ScrollingMovementMethod());

        rootLayout = findViewById(R.id.secondRoot_layout);
        colorPicker = findViewById(R.id.clrPicker);
//        btn_send = findViewById(R.id.btn_send);

//        final Timer t = new Timer();
//        t.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                if (received){
//
//                    t.cancel();
//                }
//            }
//        }, 0, 100);


        colorPicker.setColorListener(new ColorListener() {
            @Override
            public void onColorSelected(ColorEnvelope colorEnvelope) {
                int[] colors = colorEnvelope.getColorRGB();
                setColor(Color.rgb(colors[0], colors[1], colors[2]));
                int sum = 0;
                for (int i : colors) {
                    sum += i;
                }
                if (sum > 450) {
                    if (!isDark) {
                        setDarkMode();
                    }
                } else {
                    if (isDark) {
                        setWhiteMode();
                    }
                }

                if (bluetoothSocket.isConnected() && connectedThread != null) {


                    int val = (colors[0] << 16) | (colors[1] << 8) | colors[2];
                    String strVal = String.valueOf(val) + "E";
//                    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
//                    buffer.putLong(val);
                    connectedThread.write(strVal.getBytes());

//                    ByteBuffer buffer1 = ByteBuffer.allocate(Long.BYTES);
//                    buffer1.put(buffer.array());
//                    buffer1.flip();


                    ((TextView) findViewById(R.id.tv)).append("\n" + strVal);



                }
//                btn_send.setImageTintList(new ColorStateList(new int[][]{{0}},new int[]{}));


//                if (lastColor == 1){
//                    lastColor = colorEnvelope.getColor();
//                    //send now
//                }


//                if (bluetoothSocket.isConnected() && connectedThread != null) {
//                    if (lastColor != colorEnvelope.getColor()) {
//                        lastColor = colorEnvelope.getColor();
//                        lastColorInt = colorEnvelope.getColorRGB();
//                        if (received) {
//                            received = false;
//                            //send now
//                            String t = "";
//                            for (int i : lastColorInt) {
//                                t += "-" + i;
//                            }
//                            connectedThread.write(t.getBytes());
////                            Log.i("MYTAGTTNG", "sent");
//                        } else {
//                            // send after now
//                            if (timer == null){
//                                timer = new Timer();
//                            }
//                            timer.schedule(new TimerTask() {
//                                @Override
//                                public void run() {
//                                        if (received) {
//                                            received = false;
//                                            String t = "";
//                                            for (int i : lastColorInt) {
//                                                t += "-" + i;
//                                            }
//                                            if (!currentColor.equals(t)) {
//                                                connectedThread.write(t.getBytes());
////                                                Log.i("MYTAGTTNG", "sent");
//                                                timer.cancel();
//                                                timer = null;
//                                            }
//                                        }
//                                }
//                            }, 0, 10);
//
//                        }
//                    }
//                }

                }
            });


        colorPicker.setPreferenceName("lastColor");


            bluetoothAdapter =BluetoothAdapter.getDefaultAdapter();

            initiateBluetoothProcess();


        }

        private void initiateBluetoothProcess () {

            if (bluetoothAdapter.isEnabled()) {
                BluetoothSocket tmp = null;
                bluetoothDevice = bluetoothAdapter.getRemoteDevice(getIntent().getStringExtra("MAC"));

                if (bluetoothAdapter.isDiscovering()) {
                    bluetoothAdapter.cancelDiscovery();
                }

                try {
                    tmp = bluetoothDevice.createRfcommSocketToServiceRecord(bluetoothDevice.getUuids()[0].getUuid());
                    bluetoothSocket = tmp;
                    bluetoothSocket.connect();
                    Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    try {
                        bluetoothSocket.close();
                    } catch (IOException c) {
                        Toast.makeText(this, "not Connected!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SecondActivity.this, FirstActivity.class));
                        finish();
                        return;
                    }
                    Toast.makeText(this, "not Connected!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SecondActivity.this, FirstActivity.class));
                    finish();
                }
                handler = new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        if (msg.what == ConnectedThread.RESPONSE_MESSAGE) {
                            String response = (String) msg.obj;
//                        Toast.makeText(SecondActivity.this, txt, Toast.LENGTH_SHORT).show();
//                        if (Boolean.valueOf(response)) {
//                            received = true;
//                        }

                            received = true;
                            if (response.length() > 1) {
                                currentColor = response.substring(1);
                            }
//                            Log.i("MTAGAT", "" + response );
                        }
                    }
                };
            }
//        Log.i("[BLUETOOTH]", "Creating and running Thread");
            connectedThread = new ConnectedThread(bluetoothSocket, handler);
            connectedThread.start();

        }

        private void setWhiteMode () {
            isDark = false;
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(v.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        }

        private void setDarkMode () {
            isDark = true;
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }


        private void setColor ( int color){
            setStatusBarColor(color);
            rootLayout.setBackgroundColor(color);
        }

        private void statusBarMode () {
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(v.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }


        public void setStatusBarColor ( int color){
            Window w = this.getWindow();
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.setStatusBarColor(color);

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
                try {
                    tmpIn = socket.getInputStream();
                    tmpOut = socket.getOutputStream();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                mmInStream = tmpIn;
                mmOutStream = tmpOut;
                try {
                    mmOutStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
                        e.printStackTrace();
                        break;
                    }
                }
            }

            public void write(byte[] bytes) {
                try {
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
        protected void onDestroy () {
            colorPicker.saveData();
            if (connectedThread != null) {
                if (connectedThread.isAlive()) {
                    connectedThread.cancel();
                }
            }
            super.onDestroy();
        }
    }