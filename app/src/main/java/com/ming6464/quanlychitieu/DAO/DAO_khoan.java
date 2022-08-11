package com.ming6464.quanlychitieu.DAO;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.ming6464.quanlychitieu.DTO.Khoan;
import com.ming6464.quanlychitieu.DbHelper.Database;

import java.util.ArrayList;

public class DAO_khoan {
    private SQLiteDatabase db;
    private Database database;
    private String table,kyHieu,ma;
    private int index,tong;
    public DAO_khoan(Context context,String kyHieu){
        database = new Database(context);
        db = database.getWritableDatabase();
        table = Database.TABLE_KHOAN;
        this.kyHieu = kyHieu;
    }

    public void changeKH(String KH){
     this.kyHieu = KH;
    }

    private ContentValues getValues(Object ... objs) {
        ContentValues values = new ContentValues();
        String name = "";
        Object obj;
        for(int i = 0; i < objs.length; i +=2){
            name = (String)objs[i];
            obj = objs[i+1];
            switch (obj.getClass().getSimpleName()){
                case "String":
                    values.put(name,(String)obj);
                    break;
                case "Integer":
                    values.put(name,(Integer)obj);
                    break;
            }
        }
        return values;
    }

    private Cursor getAllColunm(){
        Cursor cursor = db.rawQuery("SELECT * FROM " + table,null);
        return cursor;
    }

    public void create(Khoan obj){
        db.insert(table,null,getValues(Database.MA_LOAI,
                obj.getMaLoai() + kyHieu,Database.KHOAN_TIEN,obj.getKhoanTien()));
    }

    public int getSTT(){
        Cursor cursor = getAllColunm();
        if(cursor.moveToLast())
            return cursor.getInt(0) + 1;
        return 0;
    }

    public ArrayList<Khoan> read(){
        ArrayList<Khoan> list  = new ArrayList<>();
        Cursor cursor = getAllColunm();
        if(cursor.moveToFirst()){
            do{
                ma = cursor.getString(2);
                index = ma.indexOf(kyHieu);
                if(index > 0)
                    list.add(new Khoan(cursor.getInt(0),cursor.getInt(1),ma.substring(0,ma.indexOf(kyHieu))));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public ArrayList<Integer> readTienWithMa(String ma_loai){
        ArrayList<Integer> list = new ArrayList<>();
        for(Khoan x : read()){
            if(x.getMaLoai().equals(ma_loai))
                list.add(x.getKhoanTien());
        }
        return list;
    }

    public int readTongTienWithMa(String ma_loai){
        tong = 0;
        for(int x : readTienWithMa(ma_loai)){
            tong += x;
        }
        return tong;
    }

    public int readTongTien(){
        tong = 0;
        for(Khoan x : read()){
            tong += readTongTienWithMa(x.getMaLoai());
        }
        return tong;
    }

    public void update(int STT, Khoan obj){
        db.update(table,getValues(Database.MA_LOAI,obj.getMaLoai() + kyHieu,Database.KHOAN_TIEN,obj.getKhoanTien())
                ,Database.STT + " = ?", new String[]{String.valueOf(STT)});
    }
    public void updateMa(String ma_loai_cu,String ma_loai){
        db.update(table,getValues(Database.MA_LOAI,ma_loai + kyHieu),Database.MA_LOAI + " = ?",
                new String[]{ma_loai_cu + kyHieu});
    }

    public void deleteWithMa(String ma_loai){
        db.delete(table,Database.MA_LOAI + " = ?",new String[]{ma_loai + kyHieu});
    }

    public void delete(int STT){
        db.delete(table,Database.STT + " = ?", new String[]{String.valueOf(STT)});
    }


}

