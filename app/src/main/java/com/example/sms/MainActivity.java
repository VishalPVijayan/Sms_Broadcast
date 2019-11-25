package com.example.sms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private static final String SMS_RECIEIVED = "android.provider.Telephony.SMS_RECEIVED";

    private static final int MY_PERMISSION_REQUEST_RECIEVE_MESSAGE = 0;
    TextView txtMessageReciever,txtNumberReciever;

    MyReciever reciever = new MyReciever(){
        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            txtMessageReciever.setText(msg);
            txtNumberReciever.setText(phoneNo);


        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(reciever, new IntentFilter(SMS_RECIEIVED));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(reciever);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtMessageReciever = (TextView)findViewById(R.id.txtMessageReciever);
        txtNumberReciever = (TextView)findViewById(R.id.txtNumberReciever);




        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.RECEIVE_SMS))
            {
                //do  nothing
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS},MY_PERMISSION_REQUEST_RECIEVE_MESSAGE);
            }

        }//onCreate



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch(requestCode)
        {
            case MY_PERMISSION_REQUEST_RECIEVE_MESSAGE:
            {
                // check whether the length of grantResult is greater than 0 or equal
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "Thanks for permitting", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "Please permit", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
