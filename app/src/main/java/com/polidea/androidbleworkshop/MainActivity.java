package com.polidea.androidbleworkshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.polidea.androidbleworkshop.example1.Example1ScanningActivity;
import com.polidea.androidbleworkshop.example2.Example2ConnectionActivity;
import com.polidea.androidbleworkshop.example2.Example2RxConnectionActivity;
import com.polidea.androidbleworkshop.example3.Example3ReadingActivity;
import com.polidea.androidbleworkshop.example3.Example3RxReadingActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @OnClick(R.id.example_1)
    public void launchExample1() {
        startActivity(new Intent(this, Example1ScanningActivity.class));
    }

    @OnClick(R.id.example_2)
    public void launchExample2() {
        startActivity(new Intent(this, Example2ConnectionActivity.class));
    }

    @OnClick(R.id.example_2_rx)
    public void launchExample2Rx() {
        startActivity(new Intent(this, Example2RxConnectionActivity.class));
    }

    @OnClick(R.id.example_3)
    public void launchExample3() {
        startActivity(new Intent(this, Example3RxReadingActivity.class));
    }

    @OnClick(R.id.example_3_rx)
    public void launchExample3Rx() {
        startActivity(new Intent(this, Example3ReadingActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
