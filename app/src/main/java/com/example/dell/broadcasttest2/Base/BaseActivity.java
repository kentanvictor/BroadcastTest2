package com.example.dell.broadcasttest2.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.dell.broadcasttest2.activity.ActivityCollector;
import com.example.dell.broadcasttest2.activity.LoginActivity;

/*
 * Created by DELL on 2017/5/6.
 */

public class BaseActivity extends AppCompatActivity{
    private ForceOffLineReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);//addActivity需要写到onCreate()方法中才能够被实现
        //不可以在onCreate()方法中注册广播，因为这样会使得在后台的处于onStop()的activity也接收到
    }
    /*
    * 注册ForeceOffLineReceiver广播接收器，重写onCreate()和onResume()这两个生命周期函数
    * 分别在这两个方法里面注册和取消注册了ForeceOffLineReceiver。
    * 这里为什么不在onCreate()和onDestory()方法中注册是因为：
    * 因为需要保证只有处于栈顶的activity才能够接收到这条强制下线的广播，非栈顶的活动不应该也没有必要去接收这条广播
    * 所以写在onResume()和onPause()方法里面能够更好的解决问题
    * 当一个Activity失去栈顶位置的时候就会自动取消对广播接收器的注册。
    * */
    @Override
    protected void onResume()
    {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.dell.broadcasttest2.FORCEOFFLINE");
        receiver = new ForceOffLineReceiver();
        registerReceiver(receiver,intentFilter);
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        if(receiver!=null)
        {
            unregisterReceiver(receiver);
            receiver = null;
        }
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ActivityCollector.removeActivity(this);//removeActivity方法也需要写到OnDestroy里面才能够被实现
    }
}
class ForceOffLineReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(final Context context, Intent intent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);//使用AlertDialog.Builder构建对话框
        builder.setTitle("Warning");
        builder.setMessage("You are forced to be offline.Please try to login again.");
        builder.setCancelable(false);//用于设置对话框是否能够取消，这里设置的是对话框不可以取消(指的是用户按back是无法关闭对话框的)
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            //使用setPositiveButton方法给对话框注册确定按钮
            //当用户点击确定按钮，就调用ActivityCollector的finishAll()方法
            //销毁所有的活动，并且重新启动LoginActivity这个活动
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCollector.finishAll();//销毁所有的活动
                Intent intent = new Intent(context,LoginActivity.class);
                context.startActivity(intent);//重新启动LoginActivity
            }
        });
        builder.show();
    }
}