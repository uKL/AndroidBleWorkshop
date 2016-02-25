package com.polidea.androidbleworkshop.example3;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.polidea.androidbleworkshop.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Example3ReadingActivity extends AppCompatActivity {

    enum ConnectionState {
        DISCONNECTED, DISCONNECTING, CONNECTED, CONNECTING
    }

    @Bind(R.id.connect)
    Button connectButton;
    @Bind(R.id.disconnect)
    Button disconnectButton;
    @Bind(R.id.address)
    EditText addressView;
    @Bind(R.id.connection_state)
    TextView connectionStateView;
    private BluetoothAdapter bluetoothAdapter;
    private ConnectionState connectionState = ConnectionState.DISCONNECTED;
    private BluetoothGatt bluetoothGatt;
    private BluetoothGattCallback callback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {

            if (status == BluetoothGatt.GATT_SUCCESS) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    updateConnectionState(ConnectionState.CONNECTED);
                    runOnUiThread(() -> Toast.makeText(Example3ReadingActivity.this, "Connected!", Toast.LENGTH_LONG).show());
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    updateConnectionState(ConnectionState.DISCONNECTED);
                    // 2. Close GATT client
                    bluetoothGatt.close();
                    bluetoothGatt = null;
                }
            } else {
                onDisconnectRequested();
            }
        }
    };
    private BluetoothManager bluetoothManager;

    @OnClick(R.id.connect)
    public void onConnectClick() {
        /**
         * Must be a valid MAC address or expect java.lang.IllegalArgumentException
         */
        final BluetoothDevice remoteDevice = bluetoothAdapter.getRemoteDevice(getEnteredMacAddress());
        updateConnectionState(ConnectionState.CONNECTING);
        bluetoothGatt = remoteDevice.connectGatt(this, false, callback);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_example2);
        ButterKnife.bind(this);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
    }

    @OnClick(R.id.disconnect)
    public void onDisconnectRequested() {
        // 1. Request connection to be dropped first.
        updateConnectionState(ConnectionState.DISCONNECTING);
        final int connectionState = bluetoothManager.getConnectionState(bluetoothGatt.getDevice(), BluetoothProfile.GATT);

        if (connectionState == BluetoothProfile.STATE_CONNECTED
                || connectionState == BluetoothProfile.STATE_CONNECTING) {
            bluetoothGatt.disconnect();
        } else if (connectionState == BluetoothProfile.STATE_DISCONNECTED) {
            updateConnectionState(ConnectionState.DISCONNECTED);
            bluetoothGatt.close();
            bluetoothGatt = null;
        }
    }

    private String getEnteredMacAddress() {
        return addressView.getText().toString();
    }

    private void updateConnectionState(ConnectionState connectionState) {
        this.connectionState = connectionState;
        runOnUiThread(this::updateUI);
    }

    private void updateUI() {
        connectionStateView.setText(connectionState.name());
        connectButton.setEnabled(connectionState == ConnectionState.DISCONNECTED);
        disconnectButton.setEnabled(connectionState == ConnectionState.CONNECTED);
    }
}
