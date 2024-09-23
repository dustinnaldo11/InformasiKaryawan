package com.example.informasikaryawan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "karyawan.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_KARYAWAN = "karyawan";


    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAMA = "nama";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_GAJI = "gaji";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_KARYAWAN + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAMA + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_GAJI + " REAL)";
        db.execSQL(createTable);

        addDummyData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KARYAWAN);
        onCreate(db);
    }

    public boolean addKaryawan(String nama, String password, double gaji) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAMA, nama);
        contentValues.put(COLUMN_PASSWORD, password);
        contentValues.put(COLUMN_GAJI, gaji);

        long result = db.insert(TABLE_KARYAWAN, null, contentValues);
        return result != -1;
    }

    public ArrayList<Karyawan> getAllKaryawan() {
        ArrayList<Karyawan> karyawanList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_KARYAWAN, null);

        if (cursor.moveToFirst()) {
            do {
                Karyawan karyawan = new Karyawan();
                karyawan.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                karyawan.setNama(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMA)));
                karyawan.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)));
                karyawan.setGaji(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_GAJI)));
                karyawanList.add(karyawan);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return karyawanList;
    }

    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_KARYAWAN, new String[]{COLUMN_ID},
                COLUMN_NAMA + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);

        boolean isValid = (cursor.getCount() > 0);
        cursor.close();
        return isValid;
    }

    private void addDummyData(SQLiteDatabase db) {
        addKaryawan("admin", "admin", 5000.0);
        addKaryawan("user1", "password1", 3000.0);
        addKaryawan("user2", "password2", 3500.0);
    }

    public boolean updateKaryawan(int id, String nama, String password, double gaji) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAMA, nama);
        contentValues.put(COLUMN_PASSWORD, password);
        contentValues.put(COLUMN_GAJI, gaji);

        int result = db.update(TABLE_KARYAWAN, contentValues, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    public boolean deleteKaryawan(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_KARYAWAN, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    public Karyawan getKaryawanById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_KARYAWAN, null, COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor.moveToFirst()) {
            Karyawan karyawan = new Karyawan();
            karyawan.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            karyawan.setNama(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMA)));
            karyawan.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)));
            karyawan.setGaji(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_GAJI)));
            cursor.close();
            return karyawan;
        } else {
            cursor.close();
            return null;
        }
    }
}
