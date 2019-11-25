package com.example.a3_phd19006;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.os.Bundle;
import android.widget.Toast;

public class GetGPS extends AppCompatActivity {

    private Button btn_start, btn_stop;
    private TextView textView;
    private BroadcastReceiver broadcastReceiver;
    private String CHANNEL_ID="MC_ASSIGNMENT";
    private String CHANNEL_NAME="MC_ASSIGNMENT";
    private String CHANNEL_DESC="MC_ASSIGNMENT Notification";
    private String xxx;

    @Override
    protected void onResume() {
        super.onResume();
        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    textView.append("\n" +intent.getExtras().get("coordinates"));
                    xxx=intent.getExtras().get("coordinates").toString();
                }
            };
        }
        registerReceiver(broadcastReceiver,new IntentFilter("location_update"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_gps);

        btn_start = (Button) findViewById(R.id.button_gps_start);
        btn_stop = (Button) findViewById(R.id.button_gps_stop);
        textView = (TextView) findViewById(R.id.textView_gps);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }




    }


    public void gps_start(View view) {
                Toast.makeText(getApplicationContext(),"Service Start",Toast.LENGTH_SHORT).show();
                Intent i =new Intent(getApplicationContext(),BackgroundGPSService.class);
                startService(i);
    }



    public void gps_stop(View view) {
        Toast.makeText(getApplicationContext(), "Service Stop", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(getApplicationContext(), BackgroundGPSService.class);
        stopService(i);

    }

    public void notifyy(View view){
        Intent i =new Intent(getApplicationContext(),BackgroundGPSService.class);
        startService(i);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_gps)
                .setContentTitle("GPS Location")
                .setContentText(xxx)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1,builder.build());
    }



}