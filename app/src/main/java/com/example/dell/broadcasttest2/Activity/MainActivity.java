package com.example.dell.broadcasttest2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dell.broadcasttest2.base.BaseActivity;
import com.example.dell.broadcasttest2.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button forceOffLIne = (Button) findViewById(R.id.force_offline);
        forceOffLIne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.dell.broadcasttest2.FORCEOFFLINE");
                sendBroadcast(intent);
            }
        });
    }
}
