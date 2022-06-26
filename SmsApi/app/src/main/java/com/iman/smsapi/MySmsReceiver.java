package com.iman.smsapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class MySmsReceiver extends BroadcastReceiver {

    private static final String TAG = "SMS_receiver";

    @Override
    public void onReceive(Context context, Intent intent) {
//        Log.i(TAG, "sms received");
        Map<String, String> smsMap = getMessages(intent);
        for (String phone : smsMap.keySet()){
            String msg = smsMap.get(phone);
            Log.i(TAG, "<" + phone + "> :\n" + msg + "\n");

            Intent i = new Intent(context, IncomingSmsActivity.class);
            i.putExtra("phone", phone);
            i.putExtra("msg", msg);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(i);
        }
    }

    private void getSmsFromIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle == null) return;
        Object[] pdus = (Object[]) bundle.get("pdus");
        if (pdus == null) return;

        SmsMessage[] message = new SmsMessage[pdus.length];
        for (int i = 0; i < pdus.length; i++) {
            String format = bundle.getString("format");
            message[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);

            String text = "sms from : " + message[i].getDisplayOriginatingAddress() + " :  " + message[i].getMessageBody() + "\n";

            Log.i(TAG, text);
        }
    }

    private Map<String, String> getMessages(Intent intent) {
        Map<String, String> map = new HashMap<>();
        Bundle bundle = intent.getExtras();
        if (bundle == null) return map;
        Object[] pdus = (Object[]) bundle.get("pdus");
        if (pdus == null) return map;

        SmsMessage[] message = new SmsMessage[pdus.length];
        for (int i = 0; i < pdus.length; i++) {
            String format = bundle.getString("format");
            message[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);

            if (map.containsKey(message[i].getDisplayOriginatingAddress())){
                String body = map.get(message[i].getDisplayOriginatingAddress());
                body += message[i].getDisplayMessageBody();
                map.put(message[i].getDisplayOriginatingAddress(), body);
            }else {
                map.put(message[i].getDisplayOriginatingAddress(), message[i].getDisplayMessageBody());
            }
        }


        return map;
    }


}
