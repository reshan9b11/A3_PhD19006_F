package com.example.a3_phd19006;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static final String LOG_TAG = MainActivity.class.getSimpleName();
    private String filepath;
    private TextView acc;
    private ListView wifilist;
    private List<ScanResult> result;
    private ArrayList<String> arrayList=new ArrayList<>();
    private ArrayAdapter adapter;
    private WifiManager wifiManager;
    Button buttonScan;

   // private DBHelper mydb


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonScan = findViewById(R.id.wifi_scan);
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanWifi();
            }
        });


        wifilist=findViewById(R.id.wifi_list);
        wifiManager=(WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if(!wifiManager.isWifiEnabled()){
            Toast.makeText(this ,"WIFI is disable, Enable it", Toast.LENGTH_SHORT).show();
            wifiManager.setWifiEnabled(true);
        }
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrayList);
       // Log.d("g",arrayList.get(1));
        wifilist.setAdapter(adapter);











    }

    public void onPressStartService(View v){

        Intent intent = new Intent(this, BackgroundAccelerometerService.class);
        //Log.e(LOG_TAG, "in onPressStartService, userFilePath is "+userFilepath);
        startService(intent);
        Log.e(LOG_TAG, "Started service through onPressStartService");

    }



    public void onStartRecord(View v){

        Intent intent = new Intent(this, BackgroundMicrophone.class);
        //Log.e(LOG_TAG, "in onPressStartService, userFilePath is "+userFilepath);
        startService(intent);
        Toast.makeText(getApplicationContext(),"Service Started",Toast.LENGTH_SHORT).show();
        Log.e(LOG_TAG, "Started service ");

    }

    public void onPressStopService(View v){
        Toast.makeText(getApplicationContext(),"Service Stop",Toast.LENGTH_SHORT).show();
        stopService(new Intent(getApplicationContext(), BackgroundAccelerometerService.class));
    }


    public void onStopRecord(View v){
        Toast.makeText(getApplicationContext(),"Service Stop",Toast.LENGTH_SHORT).show();
        stopService(new Intent(getApplicationContext(), BackgroundMicrophone.class));
    }



    public void fun(View v){
        Intent x=new Intent(this,GetGPS.class);
        startActivity(x);

    }





    public void scanWifi(){
        arrayList.clear();
        registerReceiver(wifiReceiver,new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        Log.d("dfsfsf","ScanningWifif");
        wifiManager.startScan();
    }


    BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            result = wifiManager.getScanResults();
            unregisterReceiver(this);
            Log.d("k","Broadcasr");
            int max=5;
            WifiInfo wifiInfo=wifiManager.getConnectionInfo();
           // int rssi=WifiManager.calculateSignalLevel(wifiInfo.getRssi(),max);

            for (ScanResult scanResult : result) {
//                int r=WifiManager.calculateSignalLevel(wifiInfo.getRssi(),max);
//                int cc=wifiInfo.getLinkSpeed();
//                int ccc=wifiInfo.getRssi();
//               // float c=scanResult.level;
//                r=(r*100)/max;

                arrayList.add(scanResult.SSID +"  Strength in dBm= "+scanResult.level);
                Log.d("kx",scanResult.SSID);

                adapter.notifyDataSetChanged();
            }
        }
    };


     public void show_accel(View v){
           // getSupportFragmentManager().beginTransaction().replace(R.id.f_c,new Show_acc_Frag()).commit();
            Intent cccc=new Intent(this,Show_acc_Frag.class);
            startActivity(cccc);
     }

}
