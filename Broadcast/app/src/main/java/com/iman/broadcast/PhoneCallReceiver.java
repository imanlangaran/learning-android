package com.iman.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.Date;

public abstract class PhoneCallReceiver extends BroadcastReceiver {

    private static final String TAG = "hiiiiiii";

    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
    private static Date callStartTime;
    private static boolean isIncoming;
    private static String savedNumber;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.New_OUTGOING_CALL")){
            savedNumber = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");
            Log.i(TAG, "new outgoing call <" + savedNumber +">");
        }else {
            String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            Log.i(TAG, "stateStr = " + stateStr + "\nnumber = " + number);
            int state = 0;
            if (TelephonyManager.EXTRA_STATE_IDLE.equals(stateStr)){
                state = TelephonyManager.CALL_STATE_IDLE;
            }else if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(stateStr)){
                state = TelephonyManager.CALL_STATE_OFFHOOK;
            }else if (TelephonyManager.EXTRA_STATE_RINGING.equals(stateStr)){
                state = TelephonyManager.CALL_STATE_RINGING;
            }

            onCallStateChange(context, state, number);

        }
    }

    private void onCallStateChange(Context context, int state, String number) {
        if (lastState == state){
            return;
        }
        switch (state){
            case TelephonyManager.CALL_STATE_RINGING:
                isIncoming = true;
                callStartTime = new Date();
                savedNumber = number;
                onIncomingCallStarted(context, savedNumber, callStartTime);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                //ringing -> offhook : incomingCall
                if (lastState != TelephonyManager.CALL_STATE_RINGING){
                    isIncoming = false;
                    callStartTime = new Date();
                    savedNumber = number;
                    onOutGoingCallStarted(context, number, callStartTime);
                }else {
                    isIncoming = true;
                    callStartTime = new Date();
                    savedNumber = number;
                    onIncomingCallAnswered(context, savedNumber, callStartTime);
                }
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                if (lastState == TelephonyManager.CALL_STATE_RINGING){
                    onMissedCall(context, savedNumber, callStartTime);
                }else if (isIncoming){
                    onIncomingCallEnded(context, savedNumber, callStartTime);
                }else {
                    onOutgoingCallEnded(context, savedNumber, callStartTime);
                }

            default: break;
        }


        lastState = state;
    }



    //to override by
    protected abstract void onOutGoingCallStarted(Context ctx, String number, Date start);
    protected abstract void onIncomingCallStarted(Context ctx, String number, Date start);
    protected abstract void onIncomingCallAnswered(Context ctx, String number, Date start);
    protected abstract void onMissedCall(Context ctx, String number, Date start);
    protected abstract void onIncomingCallEnded(Context ctx, String number, Date start);
    protected abstract void onOutgoingCallEnded(Context ctx, String number, Date start);
}
