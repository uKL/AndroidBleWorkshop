package com.polidea.androidbleworkshop.example1;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.polidea.androidbleworkshop.R;

public class Example1ScanningActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_example1);
        /**
         * Currently Android only supports one Bluetooth adapter, but the API could be extended to support more.
         */
        // 1. Use static method
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // or 2. Get system service
        // bluetoothAdapter = (BluetoothAdapter) getSystemService(Context.BLUETOOTH_SERVICE);
    }
}
