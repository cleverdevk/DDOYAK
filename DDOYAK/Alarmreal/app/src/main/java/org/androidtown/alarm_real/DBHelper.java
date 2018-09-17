package org.androidtown.alarm_real;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    //public static final String DATABASE_NAME = "ALARMTIMES.db";
    //public static final int DATABASE_VERSION = 1;


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ALARMTIMES (_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id TEXT, time TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insert(String user_id, String time){
        Log.d("TAG", "insert: sqlite conncection start");
        SQLiteDatabase db = getWritableDatabase();
        //db.execSQL("CREATE TABLE ALARMTIMES (_id INTEGER PRIMARY KEY AUTOINCREMENT, userid TEXT, time TEXT);");
        db.execSQL("INSERT INTO ALARMTIMES VALUES(null,' " + user_id + "','" + time + "');");      //작은 따옴표로 안감싸면 오류남, 감싸면 해당 data가 아니라 user_id, time이 들어감 update로 해야해서 그런가?
        Log.d("TAG", "insert: alarm data success");
        db.close();
    }



    //update 기능 안씀
    public void update(String user_id, String time) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE ALARMTIMES SET time=" + time + " WHERE user_id='" + user_id + "';");
        db.close();
    }

    /**
     * public void update 미구현 - 원래는 insert는 첫 행이고 나머지는 update로 하는듯?? 알아보기
     * public void delete 미구현
     */

    public String getAllData(){
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        Cursor cursor = db.rawQuery("SELECT * FROM ALARMTIMES", null);
        while(cursor.moveToNext()){
            result += cursor.getString(0)
                    + " : "
                    + cursor.getString(1)
                    + " : "
                    + cursor.getString(2)
                    + "\n";
        }
        return result;
    }

}