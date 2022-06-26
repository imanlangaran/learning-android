package com.iman.broadcast;

import android.app.Application;

public class App extends Application {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static synchronized App getInstance(){
        return instance;
    }

    public void setConnectivityReceiverListener(ConnectivityReceiver.ConnectivityReceiverListener connectivityReceiverListener){
        ConnectivityReceiver.listener = connectivityReceiverListener;
    }

}
