package com.polidea.androidbleworkshop.example2;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothProfile;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.polidea.androidbleworkshop.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Example2ConnectionActivity extends AppCompatActivity {

    @Bind(R.id.address)
    EditText addressView;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothGattCallback callback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {

            if (status == BluetoothGatt.GATT_SUCCESS && newState == BluetoothProfile.STATE_CONNECTED) {
                runOnUiThread(() -> Toast.makeText(Example2ConnectionActivity.this, "Connected!", Toast.LENGTH_LONG).show());
                // Hooray!
            }
        }
    };

    @OnClick(R.id.connect)
    public void onConnectClick() {
        /**
         * Must be a valid MAC address or expect java.lang.IllegalArgumentException
         */
        final BluetoothDevice remoteDevice = bluetoothAdapter.getRemoteDevice(getEnteredMacAddress());
        remoteDevice.connectGatt(this, false, callback);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_example2);
        ButterKnife.bind(this);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    private String getEnteredMacAddress() {
        return addressView.getText().toString();
    }
}
