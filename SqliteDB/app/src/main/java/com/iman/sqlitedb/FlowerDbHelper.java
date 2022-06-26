package com.iman.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class FlowerDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "flower-db";
    private static final int VERSION_NUMBER = 1;

    public static final String TABLE_FLOWERS = "tb_flowers";
    private static final String CMD = "CREATE TABLE IF NOT EXISTS '" + TABLE_FLOWERS + "' ('" +
            Flower.KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '" +
            Flower.KEY_NAME + "' TEXT, '" +
            Flower.KEY_CAT + "' TEXT, '" +
            Flower.KEY_INST + "' TEXT, '" +
            Flower.KEY_PRICE + "'NUMERIC, '" +
            Flower.KEY_PHOTO + "' TEXT" +
            ")";

    private static final String[] allColumns = {Flower.KEY_ID, Flower.KEY_NAME, Flower.KEY_CAT, Flower.KEY_INST, Flower.KEY_PRICE, Flower.KEY_PHOTO};


    public FlowerDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION_NUMBER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CMD);
        Log.i("database", "table created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLOWERS);
        Log.i("database", "table dropped");
        onCreate(db);
    }


    public void insertFlower(Flower flower) {

        if (getFlower(Flower.KEY_ID + " = " + flower.getProductId(), null).isEmpty()) {
            SQLiteDatabase db = getWritableDatabase();
            long insertId = db.insert(TABLE_FLOWERS, null, flower.getContentValuesForDb());
            Log.i("database", "data inserted with id : " + insertId);
            if (db.isOpen()) db.close();
        }
    }


    public List<Flower> getAllFlowers() {
        SQLiteDatabase db = getReadableDatabase();
        List<Flower> flowerList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM '" + TABLE_FLOWERS + "'", null);
        if (cursor.moveToFirst()) {
            do {
                Flower flower = Flower.cursorToFlower(cursor);
                flowerList.add(flower);
            } while (cursor.moveToNext());
        }


        cursor.close();
        if (db.isOpen()) db.close();
        return flowerList;
    }

    public void deleteFlower(long productId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_FLOWERS, Flower.KEY_ID + " = ?", new String[]{String.valueOf(productId)});
        if (db.isOpen()) db.close();
    }

    public void update(long id, ContentValues contentValues) {
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_FLOWERS, contentValues, Flower.KEY_ID + " = " + id, null);
        if (db.isOpen()) db.close();
    }

    public List<Flower> getFlower(String selection, String[] selArgs) {
        SQLiteDatabase db = getReadableDatabase();
        List<Flower> flowers = new ArrayList<>();

        Cursor cursor = db.query(TABLE_FLOWERS, allColumns, selection, selArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Flower flower = Flower.cursorToFlower(cursor);
                flowers.add(flower);
            } while (cursor.moveToNext());
        }

        if (db.isOpen()) db.close();
        return flowers;
    }

    public void getFlower(List<Flower> flowerList, String selection, String[] selArgs) {
        SQLiteDatabase db = getReadableDatabase();
        flowerList.clear();
        Cursor cursor = db.query(TABLE_FLOWERS, allColumns, selection, selArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Flower flower = Flower.cursorToFlower(cursor);
                flowerList.add(flower);
            } while (cursor.moveToNext());
        }

        if (db.isOpen()) db.close();
    }

    public List<Flower> getFlower(String selection, String[] selArgs, String orderBy) {
        SQLiteDatabase db = getReadableDatabase();
        List<Flower> flowers = new ArrayList<>();

        Cursor cursor = db.query(TABLE_FLOWERS, allColumns, selection, selArgs, null, null, orderBy);
        if (cursor.moveToFirst()) {
            do {
                Flower flower = Flower.cursorToFlower(cursor);
                flowers.add(flower);
            } while (cursor.moveToNext());
        }

        if (db.isOpen()) db.close();
        return flowers;
    }


}
