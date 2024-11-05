package com.zimmer.carritov3;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "productos.db";
    private static final String TABLE_NAME = "productos";
    private static final String Col_1 = "ID";
    private static final String Col_2 = "nombre";
    private static final String Col_3 = "precio";

    // Constructor
    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                Col_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Col_2 + " TEXT, " +
                Col_3 + " REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertProduct(String nombre, double precio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_2, nombre);
        contentValues.put(Col_3, precio);
        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }

    public ArrayList<String> getAllProducts() {
        ArrayList<String> productos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (res.moveToFirst()) {
            do {
                productos.add("ID: " + res.getString(0) + " | Nombre: " + res.getString(1) +
                        " | Precio: " + res.getString(2));
            } while (res.moveToNext());
        }
        res.close();
        return productos;
    }
    public void deleteProduct(String nombre) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "nombre = ?", new String[]{nombre});
    }

    public void updateProduct(String oldName, String newName, double newPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_2, newName);
        contentValues.put(Col_3, newPrice);
        db.update(TABLE_NAME, contentValues, "nombre = ?", new String[]{oldName});
    }
}