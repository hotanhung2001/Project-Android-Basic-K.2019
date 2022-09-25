package com.example.radioonline_hotanhung_ngothithanhngan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.radioonline_hotanhung_ngothithanhngan.Ultilities.ImageUltilities;

import java.net.IDN;
import java.util.ArrayList;

public class FavDB {
    Context context;
    String dbName = "ChannelFavDB1.db";


    public FavDB(Context context) {
        this.context = context;


    }

    private SQLiteDatabase openDB() {
        return context.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
    }

    private void closeDB(SQLiteDatabase db) {
        db.close();
    }

    public void createTable() {
        SQLiteDatabase db = openDB();
        String sqlFavorite = "CREATE TABLE IF NOT EXISTS tblFavorite (" +
                " ID INTEGER NOT NULL PRIMARY KEY," +
                " Name TEXT," +
                " Image STRING," +
                " Url String," +
                " ChannelID INTEGER)";

        db.execSQL(sqlFavorite);
        closeDB(db);
    }

    public ArrayList<Channel> getAllFavorite(){
        SQLiteDatabase db = openDB();
        ArrayList<Channel> arr = new ArrayList<>();
        String sql = "select * from tblFavorite";
        Cursor csr = db.rawQuery(sql, null);

        if (csr != null) {
            if (csr.moveToFirst()) {
                do {
                    Channel channel = new Channel();
                    channel.setIdChannel(csr.getInt(0));
                    channel.setName(csr.getString(1));
                    Bitmap image = ImageUltilities.loadImageFromStorage(context,csr.getString(2));
                    channel.setLinkImage(csr.getString(2));
                    channel.setImage(image);
                    channel.setUrlChannel(csr.getString(3));
                    arr.add(channel);

                } while (csr.moveToNext());
                closeDB(db);
                return arr;
            }
        }
        closeDB(db);
        return arr;
    }

    public void removeFavorite(int id){
        SQLiteDatabase db = openDB();
        db.execSQL("DELETE FROM tblFavorite where ID="+id);
        closeDB(db);
    }


    public void insertChannelFavorite(Channel channel) {
        SQLiteDatabase db = openDB();
        Cursor cursor = db.rawQuery("select count(ID) from tblFavorite where ID="+ channel.getIdChannel(),null);
        if (cursor!=null)
            if (cursor.moveToFirst())
            {
                if (cursor.getInt(0)==0){
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("ID",channel.getIdChannel());
                    contentValues.put("Name", channel.name);
                    contentValues.put("Image", channel.linkImage);
                    ImageUltilities.saveToInternalStorage(context, channel.getImage(), channel.linkImage);
                    contentValues.put("Url", channel.urlChannel);
                    contentValues.put("ChannelID", channel.idChannel);
                    db.insert("tblFavorite", null, contentValues);
                    closeDB(db);
                }
            }

    }

    public boolean CheckFavorite(int id){
        SQLiteDatabase db = openDB();
        Cursor cursor = db.rawQuery("select count(ID) from tblFavorite where ID="+id,null);
        if (cursor.moveToFirst()){
            if (cursor.getInt(0)==1){
                closeDB(db);
                return true;
            }

        }
        closeDB(db);
        return false;
    }
}
