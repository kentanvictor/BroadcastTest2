package com.example.dell.broadcasttest2.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dell.broadcasttest2.base.BaseActivity;
import com.example.dell.broadcasttest2.R;

public class LoginActivity extends BaseActivity
{
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private EditText accountEdit;
    private EditText passwordEdit;
    private Button login;
    private CheckBox rememberPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pref = PreferenceManager.getDefaultSharedPreferences(this);//获取SharedPreferences的对象
        accountEdit = (EditText) findViewById(R.id.account);
        passwordEdit = (EditText) findViewById(R.id.password);
        rememberPass = (CheckBox) findViewById(R.id.remeber_pass);
        login = (Button) findViewById(R.id.login);
        boolean isRemember = pref.getBoolean("remember_password",false);//获取remember_password这个键对应的值
        //一开始不存在，就用false作为初始化

        if(isRemember)//当inRemember为1时
        {
            //将账号与密码都设置到文本框中
            String account = pref.getString("account","");
            String password = pref.getString("password","");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);//将复选框设置为选中
        }

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                //如果账号是admin并且密码是123456，就认为登陆成功
                if(account.equals("admin")&&password.equals("123456"))
                {
                    editor = pref.edit();
                    if(rememberPass.isChecked())//登录成功之后调用isChecked()方法来检查复选框是否被选中
                    {
                        //检查复选框是否被选中
                        editor.putBoolean("remember_password",true);//将remember_password设置为true
                        editor.putString("account",account);//把account值存到SharedPreferences文件中
                        editor.putString("password",password);//把password值存到SharedPreferences文件中
                    }else
                    {
                        editor.clear();//没有选中，清空SharedPreferences文件
                    }
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "count or password is invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
/**
 * 因为数据是以明文的形式存储在SharedPreferences文件中的，所以很容易被人盗取
 * 因此
 * 在正式项目里还需要结合一定的加密算法来对密码进行保护才行
* */
