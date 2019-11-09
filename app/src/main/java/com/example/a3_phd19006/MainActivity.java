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
//        DBHelper dh = new DBHelper(this.getApplication());
//        SQLiteDatabase db = dh.getWritableDatabase();
//
//        Cursor c = db.rawQuery("SELECT x, y, z FROM acce",null);
//        String xxx = c.getString(c.getColumnIndex("x"));
//        String yyy = c.getString(c.getColumnIndex("y"));
//        String zzz = c.getString(c.getColumnIndex("z"));

       // filepath = this.getString(R.string.default_file_path);
        //EditText fp = (EditText) findViewById(R.id.filePathText);
//        Log.e(LOG_TAG, filepath);
//        acc=(TextView)findViewById(R.id.acc_show);
//        acc.setText("X= "+xxx+"Y= "+yyy+"z= "+zzz);
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
        // get user chosen filepath
      //  EditText fp = (EditText) findViewById(R.id.filePathText);
     //   String userFilepath = fp.getText().toString();
        // store in sharedPrefs
//        SharedPreferences prefs = this.getSharedPreferences(
//                "com.", Context.MODE_PRIVATE);
//      //  prefs.edit().putString("filepath",userFilepath).apply();
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

            for (ScanResult scanResult : result) {
                arrayList.add(scanResult.SSID + " - " + scanResult.capabilities);
                Log.d("kx",scanResult.SSID);

                adapter.notifyDataSetChanged();
            }
        }
    };


}
