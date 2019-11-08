package com.example.a3_phd19006;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String ACCE_TABLE_NAME = "acce";
    //public static final String CONTACTS_COLUMN_ID = "id";
    public static final String X = "x";
    public static final String Y = "y";
    public static final String Z = "z";

    private HashMap hp;

    public DBHelper(Context context) {

        super(context, DATABASE_NAME , null, 1);
        Log.d("sdfjh","databasecreated");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "CREATE TABLE "+ ACCE_TABLE_NAME +
                        " (" + X + " text, " + Y + " text, " + Z + " text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS "+ACCE_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertacce (String x, String y, String z) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(X, x);
        contentValues.put(Y, y);
        contentValues.put(Z, z);
//        contentValues.put("street", street);
//        contentValues.put("place", place);
       long flag= db.insert(ACCE_TABLE_NAME, null, contentValues);
        if(flag==-1){
            return false;
        }
        else {
            Log.d("shk","inserted");
            return true;
        }
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, ACCE_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, String x, String y, String z) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(X, x);
        contentValues.put(Y, y);
        contentValues.put(Z, z);
        db.update(ACCE_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("acce",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

//    public ArrayList<String> getAllCotacts() {
//        ArrayList<String> array_list = new ArrayList<String>();
//
//        //hp = new HashMap();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res =  db.rawQuery( "select * from contacts", null );
//        res.moveToFirst();
//
//        while(res.isAfterLast() == false){
//            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
//            res.moveToNext();
//        }
//        return array_list;
//    }
}