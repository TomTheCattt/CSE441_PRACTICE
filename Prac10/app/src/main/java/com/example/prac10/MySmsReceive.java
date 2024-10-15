package com.example.prac10;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MySmsReceive extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void processReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        String msg = "";
        String body = "";
        String address = "";
        if (extras != null) {
            Object[] smsExtra = (Object[]) extras.get("pdus");
            for (int i = 0; i < smsExtra.length; i++) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) smsExtra[i]);
                body = smsMessage.getMessageBody();
                address = smsMessage.getOriginatingAddress();
                msg += "New message from " + address + "" +
                        "\n" + body + "just sent";
            }
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }
}