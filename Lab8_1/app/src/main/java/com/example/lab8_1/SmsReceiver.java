package com.example.lab8_1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive (Context context, Intent intent){
        Log.d("sms", "sms test");

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");

            if (pdus != null) {
                for (Object pdu:pdus){
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[])pdu);
                    String sender = smsMessage.getDisplayOriginatingAddress();
                    String message = smsMessage.getDisplayMessageBody();
                    Log.d("sms", "sms from" + sender + ": " + message);
                }
            }
        }
    }
}
