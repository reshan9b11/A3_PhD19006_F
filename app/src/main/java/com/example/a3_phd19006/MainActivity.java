package com.example.a3_phd19006;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final String LOG_TAG = MainActivity.class.getSimpleName();
    private String filepath;
    private TextView acc;

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
        Log.e(LOG_TAG, "Started service ");

    }

    public void onPressStopService(View v){
        stopService(new Intent(getApplicationContext(), BackgroundAccelerometerService.class));
    }


    public void onStopRecord(View v){
        stopService(new Intent(getApplicationContext(), BackgroundMicrophone.class));
    }
}
