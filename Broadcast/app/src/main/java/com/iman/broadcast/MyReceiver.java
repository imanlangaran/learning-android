package com.iman.broadcast;

import android.content.Context;
import android.util.Log;

import java.util.Date;

public class MyReceiver extends PhoneCallReceiver {

    private static final String TAG = MyReceiver.class.getSimpleName();


    @Override
    protected void onOutGoingCallStarted(Context ctx, String number, Date start) {
        Log.i(TAG, "outgoing : started. <" + number + ">");
    }

    @Override
    protected void onIncomingCallStarted(Context ctx, String number, Date start) {
        Log.i(TAG, "incoming : started. <" + number + ">");
    }

    @Override
    protected void onIncomingCallAnswered(Context ctx, String number, Date start) {
        Log.i(TAG, "incoming : answered. <" + number + ">");
    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start) {
        Log.i(TAG, "missed call <" + number + ">");
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start) {
        Log.i(TAG, "incoming : ended. <" + number + ">");
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start) {
        Log.i(TAG, "outgoing : ended. <" + number + ">");
    }
}
