package com.example.caucse.ddoyak;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.caucse.ddoyak.AlarmSetting.MediRef;

public class DBHelper extends SQLiteOpenHelper {

    private static final String KEY_ID = "id";
    private static final String KEY_USERID = "user_id";
    private static final String KEY_TIME = "time";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ALARMTIMES (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id TEXT, time TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + "ALARMTIMES");

        // Create tables again
        onCreate(db);
    }

    public void insert(String user_id, String time){
        Log.d("TAG", "insert: sqlite conncection start");
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO ALARMTIMES VALUES(null,' " + user_id + "','" + time + "');");      //작은 따옴표로 안감싸면 오류남, 감싸면 해당 data가 아니라 user_id, time이 들어감 update로 해야해서 그런가?
        Log.d("TAG", "insert: alarm data success");
        db.close();
    }

    public void update(String user_id, String time, int sqlite_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE ALARMTIMES SET time = '" + time + "', user_id = '" + user_id + "' WHERE id ='" + sqlite_id + "';");
        db.close();
    }

    public void delete(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM ALARMTIMES;");
        db.execSQL("DELETE FROM 'sqlite_sequence' WHERE name = 'ALARMTIMES';");
        db.close();
    }

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

    public void writeNewData(String info) {
        SQLiteDatabase db = this.getReadableDatabase();
        int i=0;
        Cursor cursor = db.rawQuery("SELECT * FROM ALARMTIMES", null);
        while(cursor.moveToNext()){
            MediRef.child(info).child(String.valueOf(i++)).setValue(cursor.getString(2));
        }
    }
}