package org.d3ifcool.hutang;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.d3ifcool.hutang.database.Contract;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FormHutang extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_HUTANG_LOADER = 0;

    private final Calendar calendar = Calendar.getInstance();
    private Uri mCurrentHutangUri;

    private EditText mTanggalCreateEditText;
    private EditText mTanggalDeadlineEditText;
    private EditText mNamaEditText;
    private EditText mJumlahEditText;
    private RadioGroup mKategoriRadioGroup;
    private RadioButton mDipinjamkanRadioButton;
    private RadioButton mMeminjamRadioButton;
    private Button mAddButton;
    private Button mUpdateButton;
    private Button mDeleteButton;

    private int mKategori = Contract.HutangEntry.HUTANG_KATEGORI_UNKNOW;

    private boolean mHutangHasChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mHutangHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_hutang);

        mTanggalCreateEditText = (EditText) findViewById(R.id.form_tanggal_create);
        mTanggalDeadlineEditText = (EditText) findViewById(R.id.form_tanggal_deadline);
        mNamaEditText = (EditText) findViewById(R.id.form_nama);
        mJumlahEditText = (EditText) findViewById(R.id.form_jumlah);
        mDipinjamkanRadioButton = (RadioButton) findViewById(R.id.radioButton_dipinjamkan);
        mMeminjamRadioButton = (RadioButton) findViewById(R.id.radioButton_meminjam);
        mKategoriRadioGroup = (RadioGroup) findViewById(R.id.form_kategori);
        mAddButton = findViewById(R.id.button_add);
        mUpdateButton = findViewById(R.id.button_update);
        mDeleteButton = findViewById(R.id.button_remove);

        mTanggalCreateEditText.setOnTouchListener(mTouchListener);
        mTanggalDeadlineEditText.setOnTouchListener(mTouchListener);
        mNamaEditText.setOnTouchListener(mTouchListener);
        mJumlahEditText.setOnTouchListener(mTouchListener);
        mDipinjamkanRadioButton.setOnTouchListener(mTouchListener);
        mMeminjamRadioButton.setOnTouchListener(mTouchListener);

        Intent intent = getIntent();
        mCurrentHutangUri = intent.getData();

        if (mCurrentHutangUri == null) {
            invalidateOptionsMenu();
        } else {
            mAddButton.setVisibility(View.GONE);
            mUpdateButton.setVisibility(View.VISIBLE);
            mDeleteButton.setVisibility(View.VISIBLE);
            LoaderManager.getInstance(this).initLoader(EXISTING_HUTANG_LOADER, null, this);
        }

        datePicker();

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FormHutang.this, "Kosong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void datePicker() {
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        mTanggalCreateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(FormHutang.this, date,  calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mTanggalDeadlineEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(FormHutang.this, date,  calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd MMM yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mTanggalCreateEditText.setText(sdf.format(calendar.getTime()));
        mTanggalDeadlineEditText.setText(sdf.format(calendar.getTime()));
    }

    private void saveHutang(){
        String tanggalCreateString = mTanggalCreateEditText.getText().toString().trim();
        String tanggalDeadlineString = mTanggalDeadlineEditText.getText().toString().trim();
        String namaString = mTanggalCreateEditText.getText().toString().trim();
//        String kategoriString =

        if (mCurrentHutangUri == null
                && TextUtils.isEmpty(tanggalCreateString) && TextUtils.isEmpty(tanggalDeadlineString)
                && TextUtils.isEmpty(namaString) && mKategori == Contract.HutangEntry.HUTANG_KATEGORI_UNKNOW){
            return;
        }

//        int jumlahTotal = 0;
//        int kategori = 0;
//        int status = 0;

//        if (!TextUtils.isEmpty())
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
