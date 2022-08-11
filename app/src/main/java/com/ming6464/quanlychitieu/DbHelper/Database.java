package com.ming6464.quanlychitieu.DbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public static final String NAME_DB = "ASSG.DB", TABLE_LOAI = "TABLE_LOAI",TEN_LOAI = "TEN_LOAI",MA_LOAI = "MA_LOAI",
            TABLE_KHOAN = "TABLE_KHOAN",KHOAN_TIEN = "KHOAN_TIEN",STT = "STT",KH_THU = "#",KH_CHI = "@";
    public Database(@Nullable Context context) {
        super(context, NAME_DB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String table_loai = "CREATE TABLE IF NOT EXISTS " + TABLE_LOAI + "(" + MA_LOAI + " TEXT PRIMARY KEY,"
        + TEN_LOAI + " TEXT NOT NULL)";

        db.execSQL(table_loai);

        String table_khoan =
                "CREATE TABLE IF NOT EXISTS " + TABLE_KHOAN + "(" + STT + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + KHOAN_TIEN + " INTEGER NOT NULL," +
                MA_LOAI + " TEXT NOT NULL REFERENCES " +
                        TABLE_LOAI + "(" + MA_LOAI +"))";

        db.execSQL(table_khoan);
    }

    private static String dropTable(String table){
        return "DROP TABLE IF EXISTS " + table;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(dropTable(TABLE_KHOAN));
        db.execSQL(dropTable(TABLE_LOAI));
        onCreate(db);
    }
}
