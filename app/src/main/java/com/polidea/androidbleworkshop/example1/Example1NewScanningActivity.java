package com.polidea.androidbleworkshop.example1;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.polidea.androidbleworkshop.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Example1NewScanningActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter;
    private ScanCallback leScanCallback = new ScanCallback() {
        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            // Batched result, in a power optimized scan.
        }

        @Override
        public void onScanFailed(int errorCode) {
            // Callback when scan could not be started.
        }

        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            // Single scan result.
        }
    };
    private BluetoothLeScanner bluetoothLeScanner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_example1);
        ButterKnife.bind(this);
        /**
         * Currently Android only supports one Bluetooth adapter, but the API could be extended to support more.
         */
        // 1. Use static method
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // or 2. Get system service
        // bluetoothAdapter = (BluetoothAdapter) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
    }

    @OnClick(R.id.start_scan)
    public void onStartScanClick() {
        bluetoothLeScanner.startScan(leScanCallback);
    }

    @OnClick(R.id.stop_scan)
    public void onStopScanClick() {
        bluetoothLeScanner.stopScan(leScanCallback);
    }
}
