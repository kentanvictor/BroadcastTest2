package com.example.dell.broadcasttest2.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by DELL on 2017/5/6.
 */

public class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
