package com.example.zy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.zy.dao.UserDao;
import com.example.zy.pojo.User;

public class LoginActivity extends AppCompatActivity {
    private UserDao dao;
    private EditText account;
    private EditText password;
    private TextView to_register;
    private Intent intent;
    private CheckBox agree;
    private Button loginBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);
        init();
        //创建数据库
        DatabaseHelper helper = new DatabaseHelper(this);
        helper.getWritableDatabase();
        //跳转注册页面
        to_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    //登录
    public void login(View view){
        //如果没有同意服务条款，则不能登录
        if (!agree.isChecked()){
            Toast.makeText(LoginActivity.this,"请先阅读并同意服务条款",Toast.LENGTH_SHORT).show();
            return;
        }
        User user = dao.findByUser(account.getText().toString(), password.getText().toString());
        System.out.println(user);
        if (user.getId() != null) {
            intent.putExtra("username",user.getName());
            //调用setClass方法指定某一个Activity
            intent.setClass(LoginActivity.this,MainActivity.class);
            //调用startActivity
            startActivity(intent);
        }else {
            Toast.makeText(this,"账号或者密码错误",Toast.LENGTH_SHORT).show();
        }

    }
    //初始化控件
    public void init(){
        account = findViewById(R.id.account);
        password = findViewById(R.id.password);
        to_register = findViewById(R.id.to_register);
        dao = new UserDao(this);
        intent = new Intent();
        agree = findViewById(R.id.login_agree);
        loginBtn = findViewById(R.id.loginBtn);
    }
}
