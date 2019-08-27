package org.d3ifcool.hutang.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Helper extends SQLiteOpenHelper {
    public static final String LOG_TAG = Helper.class.getSimpleName();
    private static final String DATABASE_NAME = "Hutang.db";
    private static final int DATABASE_VERSION = 1;

    public Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_HUTANG_TABLE = "CREATE TABLE " + Contract.HutangEntry.TABLE_NAME + " ("
                + Contract.HutangEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Contract.HutangEntry.COLUMN_HUTANG_TANGGAL_CREATE + " TEXT NOT NULL, "
                + Contract.HutangEntry.COLUMN_HUTANG_TANGGAL_DEADLINE + " TEXT NOT NULL, "
                + Contract.HutangEntry.COLUMN_HUTANG_NAMA + " TEXT NOT NULL, "
                + Contract.HutangEntry.COLUMN_HUTANG_JUMLAH + " INTEGER NOT NULL DEFAULT 0, "
                + Contract.HutangEntry.COLUMN_HUTANG_KATEGORI + " INTEGER NOT NULL, "
                + Contract.HutangEntry.COLUMN_HUTANG_STATUS + " INTEGER NOT NULL);";

        db.execSQL(SQL_CREATE_HUTANG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
