package com.example.a3_phd19006;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Show_acc_Frag extends AppCompatActivity {

    TextView xvalue,yvalue,zvalue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_accelerometer);
        DBHelper dh = new DBHelper(this.getApplication());
        SQLiteDatabase db = dh.getWritableDatabase();
        xvalue=(TextView)findViewById(R.id.xval);
        yvalue=(TextView)findViewById(R.id.yval);
        zvalue=(TextView)findViewById(R.id.zval);
        ListView lst=(ListView)findViewById(R.id.accel_list);
        ArrayList<String> list=new ArrayList<>();
        ArrayAdapter<String> aadapter;

        Cursor c = db.rawQuery("SELECT x, y, z FROM acce",null);
        if(c.moveToFirst()) {
            while (!c.isAfterLast()) {

                final String xxx = c.getString(c.getColumnIndex("x"));
               final  String yyy = c.getString(c.getColumnIndex("y"));
                final String zzz = c.getString(c.getColumnIndex("z"));
                list.add(xxx+" "+yyy+" "+zzz);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        xvalue.setText(xxx);
                        yvalue.setText(yyy);
                        zvalue.setText(zzz);
                    }
                });

                c.moveToNext();



            }
        }
        aadapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        lst.setAdapter(aadapter);


    }
}
