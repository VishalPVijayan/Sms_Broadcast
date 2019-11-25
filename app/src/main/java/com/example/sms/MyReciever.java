package com.example.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class MyReciever extends BroadcastReceiver {
    private static final String SMS_RECIEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SmsBroadcastReceiver";
    String msg, phoneNo = "";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"Intent Received: " +intent.getAction());
        if(intent.getAction()==SMS_RECIEIVED){
            Bundle dataBundle = intent.getExtras();
            if(dataBundle!= null)
            {
                Object[] mypdu = (Object[]) dataBundle.get("pdus");
                final SmsMessage[] message = new SmsMessage[mypdu.length];

                for (int i = 0; i<mypdu.length; i++){
                    //FOR BUILD VERSION ABOVE API LEVEL 23

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        String  format = dataBundle.getString("format");
                        message[i] = SmsMessage.createFromPdu((byte[]) mypdu[i], format);
                    }
                    else{
                        message[i] = SmsMessage.createFromPdu((byte[]) mypdu[i]);
                    }
                    msg = message[i].getMessageBody();
                    phoneNo = message[i].getOriginatingAddress();
                }
                Log.d("xyz",msg);
                Toast.makeText(context, "Message:" +msg +"\nNumber:" +phoneNo, Toast.LENGTH_LONG).show();
            }
        }
    }
}
