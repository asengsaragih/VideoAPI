package org.d3ifcool.hutang.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class Provider extends ContentProvider {
    public static final String LOG_TAG = Provider.class.getSimpleName();

    private static final int HUTANG = 100;
    private static final int HUTANG_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.PATH_HUTANG, HUTANG);

        sUriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.PATH_HUTANG + "/#", HUTANG_ID);
    }

    private Helper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new Helper(getContext());
//        sebelumnya returnnya false. kalau error database tambah di sini
        return true;
    }

    
    @Override
    public Cursor query(Uri uri,  String[] projection,  String selection,  String[] selectionArgs,  String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case HUTANG:
                cursor = database.query(Contract.HutangEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case HUTANG_ID:
                selection = Contract.HutangEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                cursor = database.query(Contract.HutangEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case HUTANG:
                return Contract.HutangEntry.CONTENT_LIST_TYPE;
            case  HUTANG_ID:
                return Contract.HutangEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }


    @Override
    public Uri insert(Uri uri,  ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case HUTANG:
                return insertHutang(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertHutang(Uri uri, ContentValues values) {
        String tanggal_create = values.getAsString(Contract.HutangEntry.COLUMN_HUTANG_TANGGAL_CREATE);
        if (tanggal_create == null){
            throw new IllegalArgumentException("Masukkan Data Yang Valid");
        }

        String tanggal_deadline = values.getAsString(Contract.HutangEntry.COLUMN_HUTANG_TANGGAL_DEADLINE);
        if (tanggal_deadline == null){
            throw new IllegalArgumentException("Masukkan Data Yang Valid");
        }

        String nama = values.getAsString(Contract.HutangEntry.COLUMN_HUTANG_NAMA);
        if (nama == null){
            throw new IllegalArgumentException("Masukkan Nama Yang Valid");
        }

        Integer jumlah = values.getAsInteger(Contract.HutangEntry.COLUMN_HUTANG_JUMLAH);
        if (jumlah != null && jumlah < 0){
            throw new IllegalArgumentException("Masukkan Jumlah Yang Valid");
        }

        Integer status = values.getAsInteger(Contract.HutangEntry.COLUMN_HUTANG_STATUS);
        if (status == null ||!Contract.HutangEntry.isValidStatusHutang(status)){
            throw new IllegalArgumentException("Masukkan Status Yang Valid");
        }

        Integer kategori = values.getAsInteger(Contract.HutangEntry.COLUMN_HUTANG_KATEGORI);
        if (kategori == null ||!Contract.HutangEntry.isValidKategoriHutang(kategori)){
            throw new IllegalArgumentException("Masukkan Kategori Yang Valid");
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(Contract.HutangEntry.TABLE_NAME, null, values);

        if (id == -1){
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri,  String selection,  String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsDeleted;
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case HUTANG:
                rowsDeleted = database.delete(Contract.HutangEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case HUTANG_ID:
                selection = Contract.HutangEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(Contract.HutangEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if (rowsDeleted !=0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri,  ContentValues values,  String selection,  String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case HUTANG:
                return  updateHutang(uri, values, selection, selectionArgs);
            case HUTANG_ID:
                selection = Contract.HutangEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateHutang(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateHutang(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.containsKey(Contract.HutangEntry.COLUMN_HUTANG_TANGGAL_CREATE)){
            String tanggal_create = values.getAsString(Contract.HutangEntry.COLUMN_HUTANG_TANGGAL_CREATE);
            if (tanggal_create == null){
                throw new IllegalArgumentException("Masukkan Tanggal create Yang Valid");
            }
        }

        if (values.containsKey(Contract.HutangEntry.COLUMN_HUTANG_TANGGAL_DEADLINE)){
            String tanggal_deadline = values.getAsString(Contract.HutangEntry.COLUMN_HUTANG_TANGGAL_DEADLINE);
            if (tanggal_deadline == null){
                throw new IllegalArgumentException("Masukkan Tanggal deadline Yang Valid");
            }
        }

        if (values.containsKey(Contract.HutangEntry.COLUMN_HUTANG_NAMA)){
            String nama = values.getAsString(Contract.HutangEntry.COLUMN_HUTANG_NAMA);
            if (nama == null){
                throw new IllegalArgumentException("Masukkan Nama Yang Valid");
            }
        }

        if (values.containsKey(Contract.HutangEntry.COLUMN_HUTANG_JUMLAH)){
            Integer jumlah = values.getAsInteger(Contract.HutangEntry.COLUMN_HUTANG_JUMLAH);
            if (jumlah != null && jumlah < 0){
                throw new IllegalArgumentException("Masukkan Jumlah Yang Valid");
            }
        }

        if (values.containsKey(Contract.HutangEntry.COLUMN_HUTANG_KATEGORI)){
            Integer kategori = values.getAsInteger(Contract.HutangEntry.COLUMN_HUTANG_KATEGORI);
            if (kategori == null || !Contract.HutangEntry.isValidStatusHutang(kategori)){
                throw new IllegalArgumentException("Masukkan kategori Yang Valid");
            }
        }

        if (values.containsKey(Contract.HutangEntry.COLUMN_HUTANG_STATUS)){
            Integer status = values.getAsInteger(Contract.HutangEntry.COLUMN_HUTANG_STATUS);
            if (status == null || !Contract.HutangEntry.isValidStatusHutang(status)){
                throw new IllegalArgumentException("Masukkan Status Yang Valid");
            }
        }

        if (values.size() == 0){
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsUpdated = database.update(Contract.HutangEntry.TABLE_NAME, values, selection, selectionArgs);

        if (rowsUpdated != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }
}
