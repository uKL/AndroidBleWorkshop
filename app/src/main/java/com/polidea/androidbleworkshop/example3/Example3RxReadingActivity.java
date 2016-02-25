package com.polidea.androidbleworkshop.example3;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.polidea.androidbleworkshop.R;
import com.polidea.rxandroidble.RxBleClientImpl;
import com.polidea.rxandroidble.RxBleConnection.RxBleConnectionState;
import com.polidea.rxandroidble.RxBleDevice;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Example3RxReadingActivity extends RxAppCompatActivity {

    public static final String DEVICE_INFORMATION = "0000180A-0000-1000-8000-00805f9b34fb";
    public static final String VENDOR_INFORMATION = "00002A29-0000-1000-8000-00805f9b34fb";
    @Bind(R.id.connect)
    Button connectButton;
    @Bind(R.id.disconnect)
    Button disconnectButton;
    @Bind(R.id.address)
    EditText addressView;
    @Bind(R.id.connection_state)
    TextView connectionStateView;
    @Bind(R.id.characteristic_value)
    TextView characteristicValueView;
    private RxBleConnectionState connectionState = RxBleConnectionState.DISCONNECTED;
    private RxBleClientImpl rxBleClient;
    private Subscription connectionSubscription;

    @OnClick(R.id.connect)
    public void onConnectClick() {
        /**
         * Must be a valid MAC address or expect java.lang.IllegalArgumentException
         */
        final RxBleDevice bleDevice = rxBleClient.getBleDevice(getEnteredMacAddress());

        updateConnectionState(RxBleConnectionState.CONNECTING);
        connectionSubscription = bleDevice
                .establishConnection(this, false)
                .doOnNext(connection -> updateConnectionState(RxBleConnectionState.CONNECTED))
                .flatMap(connection -> connection.readCharacteristic(UUID.fromString(DEVICE_INFORMATION)))
                .doOnUnsubscribe(() -> updateConnectionState(RxBleConnectionState.DISCONNECTED))
                .map(String::new)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(characteristicValueView::setText, throwable -> {
                    // Connection broken
                });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_example3);
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


    private void updateConnectionState(RxBleConnectionState connectionState) {
        this.connectionState = connectionState;
        runOnUiThread(this::updateUI);
    }

    private void updateUI() {
        connectionStateView.setText(connectionState.toString());
        connectButton.setEnabled(connectionState == RxBleConnectionState.DISCONNECTED);
        disconnectButton.setEnabled(connectionState == RxBleConnectionState.CONNECTED);
    }
}
