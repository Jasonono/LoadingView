package com.jason.loadingview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LoadingView loadingView = findViewById(R.id.loading_view);
        loadingView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (GONE == loadingView.getVisibility()){
                    loadingView.setVisibility(VISIBLE);
                }else {
                    loadingView.setVisibility(GONE);
                }
                loadingView.postDelayed(this,4000);
            }
        },4000);
    }
}
