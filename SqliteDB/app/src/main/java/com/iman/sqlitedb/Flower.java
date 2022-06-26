package com.iman.sqlitedb;

import android.content.ContentValues;
import android.database.Cursor;

import androidx.annotation.NonNull;

public class Flower {
    public static final String KEY_ID = "productId";
    public static final String KEY_NAME = "name";
    public static final String KEY_CAT = "category";
    public static final String KEY_INST = "instructions";
    public static final String KEY_PRICE = "price";
    public static final String KEY_PHOTO = "photo";

    private long productId;
    private String category;
    private String name;
    private String instructions;
    private double price;
    private String photo;

    public Flower(){}

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public ContentValues getContentValuesForDb(){
        ContentValues values = new ContentValues();
        values.put(Flower.KEY_ID, productId);
        values.put(Flower.KEY_NAME, name);
        values.put(Flower.KEY_CAT, category);
        values.put(Flower.KEY_INST, instructions);
        values.put(Flower.KEY_PRICE, price);
        values.put(Flower.KEY_PHOTO, photo);
        return values;
    }

    public static Flower cursorToFlower(Cursor cursor){
        Flower flower = new Flower();
        flower.setProductId(cursor.getLong(cursor.getColumnIndex(Flower.KEY_ID)));
        flower.setName(cursor.getString(cursor.getColumnIndex(Flower.KEY_NAME)));
        flower.setInstructions(cursor.getString(cursor.getColumnIndex(Flower.KEY_INST)));
        flower.setCategory(cursor.getString(cursor.getColumnIndex(Flower.KEY_CAT)));
        flower.setPrice(cursor.getDouble(cursor.getColumnIndex(Flower.KEY_PRICE)));
        flower.setPhoto(cursor.getString(cursor.getColumnIndex(Flower.KEY_PHOTO)));
        return flower;
    }


    @NonNull
    @Override
    public String toString() {
        return getName() + "\n$ " + getPrice();
    }
}
