package com.example.contact;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class ContactHelper extends SQLiteOpenHelper {

    public static final String CREATE_Contact = "create table Contact("
            +"id integer primary key autoincrement,"
            +"name text,"
            +"number integer,"
            +"sex text)";

    private Context mContext;

    public ContactHelper(Context context,String name,
                         SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_Contact);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists Contact");
        onCreate(db);
    }
}
