package com.polidea.androidbleworkshop.example2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.polidea.androidbleworkshop.R;
import com.polidea.rxandroidble.RxBleClientImpl;
import com.polidea.rxandroidble.RxBleDevice;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class Example2RxConnectionActivity extends RxAppCompatActivity {

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
    private ConnectionState connectionState = ConnectionState.DISCONNECTED;
    private RxBleClientImpl rxBleClient;
    private Subscription connectionSubscription;

    @OnClick(R.id.connect)
    public void onConnectClick() {
        /**
         * Must be a valid MAC address or expect java.lang.IllegalArgumentException
         */
        final RxBleDevice bleDevice = rxBleClient.getBleDevice(getEnteredMacAddress());
        connectionSubscription = bleDevice
                .establishConnection(this, false)
                .subscribeOn(Schedulers.io())
                .subscribe(rxBleConnection -> {}, throwable -> {});
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_example2);
        ButterKnife.bind(this);
        rxBleClient = new RxBleClientImpl(this);
    }

    @OnClick(R.id.disconnect)
    public void onDisconnectClick() {
        connectionSubscription.unsubscribe();
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
