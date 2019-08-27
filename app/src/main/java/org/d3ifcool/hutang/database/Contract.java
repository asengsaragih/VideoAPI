package org.d3ifcool.hutang.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class Contract {
    private Contract() {}

    public static final String CONTENT_AUTHORITY = "org.d3ifcool.hutang";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_HUTANG = "hutang";

    public static final class HutangEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_HUTANG);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_HUTANG;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_HUTANG;

        public final static String TABLE_NAME = "hutang";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_HUTANG_TANGGAL_CREATE = "hutang_tanggal_create";
        public final static String COLUMN_HUTANG_TANGGAL_DEADLINE = "hutang_tanggal_deadline";
        public final static String COLUMN_HUTANG_NAMA = "hutang_nama";
        public final static String COLUMN_HUTANG_JUMLAH = "hutang_jumlah";
        public final static String COLUMN_HUTANG_KATEGORI = "hutang_kategori";
        public final static String COLUMN_HUTANG_STATUS = "hutang_status";

        public static final int HUTANG_KATEGORI_UNKNOW = 0;
        public static final int HUTANG_KATEGORI_DIPINJAMKAN = 1;
        public static final int HUTANG_KATEGORI_MEMINJAM = 2;

        public static final int HUTANG_STATUS_UNKNOW = 0;
        public static final int HUTANG_STATUS_BELUMLUNAS = 1;
        public static final int HUTANG_STATUS_LUNAS = 2;

        public static boolean isValidStatusHutang(int statusHutang){
            if (statusHutang == HUTANG_STATUS_UNKNOW || statusHutang == HUTANG_STATUS_BELUMLUNAS || statusHutang == HUTANG_STATUS_LUNAS){
                return true;
            }
            return false;
        }

        public static boolean isValidKategoriHutang(int kategorihutang){
            if (kategorihutang == HUTANG_KATEGORI_UNKNOW || kategorihutang == HUTANG_KATEGORI_DIPINJAMKAN || kategorihutang == HUTANG_KATEGORI_MEMINJAM){
                return true;
            }
            return false;
        }
    }
}
