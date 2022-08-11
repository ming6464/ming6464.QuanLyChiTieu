package com.ming6464.quanlychitieu.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ming6464.quanlychitieu.DTO.Loai;
import com.ming6464.quanlychitieu.DbHelper.Database;

import java.util.ArrayList;

public class DAO_loai {
    private SQLiteDatabase db;
    private Database database;
    private String table;
    private DAO_khoan db_khoan;
    private String kyHieu,ma;
    private int index;

    public DAO_loai(Context context,String kyHieu){
        database = new Database(context);
        db = database.getWritableDatabase();
        table = Database.TABLE_LOAI;
        db_khoan = new DAO_khoan(context,kyHieu);
        this.kyHieu = kyHieu;
    }
    public void changeKH(String KH){
        this.kyHieu = KH;
        db_khoan.changeKH(KH);
    }

    private Cursor getAllColunm(){
        Cursor cursor = db.rawQuery("SELECT * FROM " + table,null);
        return cursor;
    }

    private ContentValues getValues(Object ... objs){
        ContentValues values = new ContentValues();
        String colunm = "";
        Object obj;
        for(int i = 0; i < objs.length;i += 2){
            colunm = (String)objs[i];
            obj = objs[i + 1];
            switch (obj.getClass().getSimpleName()){
                case "String":
                    values.put(colunm,(String) obj);
                    break;
                case "Integer":
                    values.put(colunm,(Integer) obj);
                    break;
            }
        }
        return values;
    }

    public void create(Loai obj){
        db.insert(table,null,getValues(Database.MA_LOAI,obj.getMaLoai() + kyHieu,Database.TEN_LOAI,obj.getTenLoai()));
    }

    public ArrayList<Loai> read(){
        ArrayList<Loai> list = new ArrayList<>();
        Cursor cursor = getAllColunm();
        if(cursor.moveToFirst()){
            do{
                ma = cursor.getString(0);
                index = ma.indexOf(kyHieu);
                if(index > 0)
                    list.add(new Loai(ma.substring(0,index),cursor.getString(1)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public Loai readWithMa(String ma_loai){
        for(Loai x : read()){
            if(x.getMaLoai().equals(ma_loai))
                return x;
        }
        return null;
    }

    public void update(String ma_loai_cu, Loai obj){
        db.update(table,getValues(Database.TEN_LOAI,obj.getTenLoai(),Database.MA_LOAI,obj.getMaLoai() + kyHieu),
                Database.MA_LOAI  + " = ?", new String[]{ma_loai_cu + kyHieu});
        if(!ma_loai_cu.equals(obj.getMaLoai())){
            db_khoan.updateMa(ma_loai_cu,obj.getMaLoai());
        }
    }

    public void delete(String ma_loai){
        db_khoan.deleteWithMa(ma_loai);
        db.delete(table,Database.MA_LOAI + " = ?", new String[]{ma_loai + kyHieu});
    }

    public ArrayList<String> readMaVsLoai() {
        ArrayList<String> list = new ArrayList<>();
        for(Loai x : read()){
            list.add(x.getMaLoai() + "-" + x.getTenLoai());
        }
        return list;
    }

    public int getIndex(String maThuChi) {
        int index = 0;
        for(Loai x : read()){
            if(x.getMaLoai().equals(maThuChi))
                return index;
            index ++;
        }
        return -1;
    }

    public ArrayList<String> readTen() {
        ArrayList<String> list = new ArrayList<>();
        for(Loai x : read()){
            list.add(x.getTenLoai());
        }
        return list;
    }
    public ArrayList<String> readMa(){
        ArrayList<String> list = new ArrayList<>();
        for(Loai x : read()){
            list.add(x.getMaLoai());
        }
        return list;
    }
}

