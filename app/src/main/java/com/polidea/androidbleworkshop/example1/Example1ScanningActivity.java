package com.polidea.androidbleworkshop.example1;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.polidea.androidbleworkshop.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Example1ScanningActivity extends AppCompatActivity {

    @Bind(R.id.start_scan)
    Button startScanButton;
    @Bind(R.id.stop_scan)
    Button stopScanButton;
    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            /**
             * Process scan results.
             */
            Log.i(getClass().getSimpleName(), "Found device: " + device.getAddress());
        }
    };
    private BluetoothAdapter bluetoothAdapter;
    private boolean isScanning = false;

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
    }

    @OnClick(R.id.start_scan)
    public void onStartScanClick() {
        bluetoothAdapter.startLeScan(leScanCallback);
        isScanning = true;
        updateViews();
    }

    @OnClick(R.id.stop_scan)
    public void onStopScanClick() {
        bluetoothAdapter.stopLeScan(leScanCallback);
        isScanning = false;
        updateViews();
    }

    private void updateViews() {
        startScanButton.setEnabled(!isScanning);
        stopScanButton.setEnabled(isScanning);
    }
}
