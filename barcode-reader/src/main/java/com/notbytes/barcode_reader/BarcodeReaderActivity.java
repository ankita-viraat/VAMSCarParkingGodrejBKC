package com.notbytes.barcode_reader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.SparseArray;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

public class BarcodeReaderActivity extends AppCompatActivity implements BarcodeReaderFragment.BarcodeReaderListener {
    public static String KEY_CAPTURED_BARCODE = "key_captured_barcode";
    public static String KEY_CAPTURED_RAW_BARCODE = "key_captured_raw_barcode";
    private static final String KEY_AUTO_FOCUS = "key_auto_focus";
    private static final String KEY_USE_FLASH = "key_use_flash";
    private boolean autoFocus = false;
    private boolean useFlash = false;
    private BarcodeReaderFragment mBarcodeReaderFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_reader);

        final Intent intent = getIntent();
        if (intent != null) {
            autoFocus = intent.getBooleanExtra(KEY_AUTO_FOCUS, true);
            useFlash = intent.getBooleanExtra(KEY_USE_FLASH, true);
        }
        mBarcodeReaderFragment = attachBarcodeReaderFragment();
    }

    private BarcodeReaderFragment attachBarcodeReaderFragment() {
        final FragmentManager supportFragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        BarcodeReaderFragment fragment = BarcodeReaderFragment.newInstance(autoFocus, useFlash);
        fragment.setListener(this);
        fragmentTransaction.replace(R.id.fm_container, fragment);
        fragmentTransaction.commitAllowingStateLoss();
        return fragment;
    }

    public static Intent getLaunchIntent(Context context, boolean autoFocus, boolean useFlash) {
        Intent intent = new Intent(context, BarcodeReaderActivity.class);
        intent.putExtra(KEY_AUTO_FOCUS, autoFocus);
        intent.putExtra(KEY_USE_FLASH, useFlash);
        return intent;
    }

    @Override
    public void onScanned(Barcode barcode) {

//        if (barcode != null && barcode.format == 12) {
//            if (mBarcodeReaderFragment != null) {
//                mBarcodeReaderFragment.pauseScanning();
//            }
            System.out.println("barcode.displayValue on scan : "+barcode.rawValue);
            Intent intent = new Intent();
            intent.putExtra(KEY_CAPTURED_BARCODE, barcode);
            intent.putExtra(KEY_CAPTURED_RAW_BARCODE, barcode.rawValue);
            setResult(RESULT_OK, intent);
            finish();
//        }
    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {
        for(int k = 0;k<barcodes.size();k++) {
            Barcode barcode = barcodes.get(k);
            System.out.println("barcode.displayValue onMultiple scans : " + barcode.rawValue);
//            if (barcode.format == 12) {
                if (mBarcodeReaderFragment != null) {
                    mBarcodeReaderFragment.pauseScanning();
                }
                Intent intent = new Intent();
                intent.putExtra(KEY_CAPTURED_BARCODE, barcode);
                intent.putExtra(KEY_CAPTURED_RAW_BARCODE, barcode.rawValue);
                setResult(RESULT_OK, intent);
                finish();
//            }
        }
    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {
        for(int k = 0;k<sparseArray.size();k++) {
            System.out.println("barcode.displayValue onBitmap scanned : " + sparseArray.get(k).rawValue);
        }
    }

    @Override
    public void onScanError(String errorMessage) {

    }

    @Override
    public void onCameraPermissionDenied() {

    }
}
