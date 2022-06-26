package com.iman.smsapi;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

public class SentMessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        switch (getResultCode()) {
            case Activity.RESULT_OK:
                onSent();
                Toast.makeText(context, "Sms Sent.", Toast.LENGTH_SHORT).show();
                break;
            case SmsManager.RESULT_ERROR_NO_SERVICE:
                Toast.makeText(context, "Failed : no service", Toast.LENGTH_SHORT).show();
                onFailure();
                break;
            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                onFailure();
                Toast.makeText(context, "Failed : generic failure", Toast.LENGTH_SHORT).show();
                break;
            case SmsManager.RESULT_ERROR_RADIO_OFF:
                onFailure();
                Toast.makeText(context, "Failed : radio off", Toast.LENGTH_SHORT).show();
                break;
            case SmsManager.RESULT_ERROR_NULL_PDU:
                onFailure();
                Toast.makeText(context, "Failed : null pdu", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }

    }

    protected void onSent() {

    }

    protected void onFailure() {

    }
}
