package com.polidea.androidbleworkshop.example1;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.polidea.androidbleworkshop.R;
import com.polidea.androidbleworkshop.toolbox.UUIDParser;

import java.util.List;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Example1ScanningActivity extends AppCompatActivity {

    public static final UUID EXPECTED_UUID = UUID.fromString("D0611E78-BBB4-4591-A5F8-487910AE4366");
    @Bind(R.id.start_scan)
    Button startScanButton;
    @Bind(R.id.stop_scan)
    Button stopScanButton;
    @Bind(R.id.scan_results)
    ListView scanResults;
    private BluetoothAdapter bluetoothAdapter;
    private boolean isScanning = false;
    private ArrayAdapter<String> resultAdapter;
    private UUIDParser uuidParser = new UUIDParser();
    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            final List<UUID> uuids = uuidParser.extractUUIDs(scanRecord);

            if (uuids.contains(EXPECTED_UUID)) {
                Log.i(getClass().getSimpleName(), "Found device: " + device.getAddress());
                resultAdapter.add(String.format("%s:%s", device.getAddress(), device.getName())); // <-- Get name requires BLUETOOTH permission
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_example1);
        ButterKnife.bind(this);
        resultAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        scanResults.setAdapter(resultAdapter);
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
        bluetoothAdapter.startLeScan(leScanCallback); // <---------
        isScanning = true;
        updateViews();
    }

    @OnClick(R.id.stop_scan)
    public void onStopScanClick() {
        bluetoothAdapter.stopLeScan(leScanCallback); // <---------
        isScanning = false;
        updateViews();
    }

    @Override
    protected void onPause() {
        super.onPause();
        onStopScanClick();
    }

    private void updateViews() {
        startScanButton.setEnabled(!isScanning);
        stopScanButton.setEnabled(isScanning);
    }
}
