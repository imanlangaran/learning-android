package com.iman.smsapi;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class SmsSender extends IntentService {

    private static final String TAG = "SmsSender";

    public static final String EXTRA_PHONE = "extra.phone";
    public static final String EXTRA_MESSAGE = "extra.message";

    public static final String INTENT_SENT_MESSAGE = "smssender.message.sent";
    public static final String INTENT_DELIVERED_MESSAGE = "smssender.message.delivered";



    public SmsSender() {
        super("SmsSender");
    }

    static class IDGenerator{
        public static AtomicInteger counter = new AtomicInteger();
        public static int nextValue(){
            return counter.getAndIncrement();
        }
    }



    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null)    return;
        String phone = intent.getStringExtra(SmsSender.EXTRA_PHONE);
        String msg = intent.getStringExtra(SmsSender.EXTRA_MESSAGE);
        sendSms(phone, msg);
    }

    private void sendSms(String phone, String msg) {
        SmsManager sm = SmsManager.getDefault();
        ArrayList<String> parts = sm.divideMessage(msg);


        PendingIntent sentPI = PendingIntent.getBroadcast(this, IDGenerator.nextValue(), new Intent(SmsSender.INTENT_SENT_MESSAGE), PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, IDGenerator.nextValue(), new Intent(SmsSender.INTENT_DELIVERED_MESSAGE), PendingIntent.FLAG_CANCEL_CURRENT);

        if (parts.size() > 1) {
            ArrayList<PendingIntent> sentPIs = new ArrayList<>();
            ArrayList<PendingIntent> deliveredPIs = new ArrayList<>();
            for (String part : parts){
                sentPIs.add(sentPI);
                deliveredPIs.add(deliveredPI);
            }
            sm.sendMultipartTextMessage(phone, null, parts, null, null);
        }else {
            sm.sendTextMessage(phone, null, parts.get(0), sentPI, deliveredPI);
        }
    }

    public static void startSmsSender( Context context, String phone, String msg){
        Intent intent = new Intent(context, SmsSender.class);
        intent.putExtra(SmsSender.EXTRA_PHONE, phone);
        intent.putExtra(SmsSender.EXTRA_MESSAGE, msg);
        context.startService(intent);
    }


}
