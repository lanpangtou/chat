package com.example.zy;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.zy.dao.UserDao;
import com.example.zy.pojo.User;

public class RegisterActivity extends AppCompatActivity {
    private UserDao dao;
    private EditText account;
    private EditText password;
    private RadioGroup radioGroup;
    //省市区控件
    private Spinner provinceSpinner,citySpinner,areaSpinner;
    //省市区适配器
    ArrayAdapter<String> provinceAdapter;
    ArrayAdapter<String> cityAdapter;
    ArrayAdapter<String> areaAdapter;
    //省
    private String[] province = new String[]{"四川","广东"};
    //市
    private String[][] city = new String[][]{
            {"成都", "巴中"},
            {"广州","深圳"}
    };
    //区
    private String[][][] area = new String[][][]{
            {
                    {"郫都区","武侯区"},
                    {"巴州区","恩阳区"}
            },
            {
                    {"海珠区","白云区"},
                    {"龙岗区","罗湖区"}
            }
    };
    private int provincePosition;//记录省索引
    User user = new User();//封装注册信息的user
    private String addressStr = "";//拼接address
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.register);
        init();
        areaSelect();
    }
    //地区选择三级联动
    public void areaSelect(){
        //初始化
        provinceAdapter = new ArrayAdapter<String>(RegisterActivity.this,R.layout.area_item,province);
        provinceSpinner.setAdapter(provinceAdapter);//初始化省级

        //省级下拉监听
        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //动态改变对应的市级
                cityAdapter = new ArrayAdapter<String>(RegisterActivity.this,R.layout.area_item,city[position]);
                citySpinner.setAdapter(cityAdapter);
                provincePosition = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        //市级下拉监听
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //动态改变对应的区级
                areaAdapter = new ArrayAdapter<>(RegisterActivity.this,R.layout.area_item,area[provincePosition][position]);
                areaSpinner.setAdapter(areaAdapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    //注册
        //添加用户名
        public void register(View view){
        user.setName(account.getText().toString());
        //添加密码
        user.setPassword(password.getText().toString());
        //添加地址
        addressStr+=provinceSpinner.getSelectedItem();
        addressStr+=citySpinner.getSelectedItem();
        addressStr+=areaSpinner.getSelectedItem();
        user.setAddress(addressStr);
        //添加性别
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
            if (radioButton.isChecked()){
                user.setSex(radioButton.getText().toString());
                break;
            }
        }
        long count = dao.insert(user);
        if (count >= 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setTitle("提示");
            builder.setMessage("注册成功！");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent();
                    intent.setClass(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            });
            builder.show();

        }else {
            Toast.makeText(this,"注册失败",Toast.LENGTH_SHORT).show();
        }
    }

    //初始化控件
    public void init(){
        account = findViewById(R.id.register_name);
        password = findViewById(R.id.register_password);
        dao = new UserDao(this);
        provinceSpinner = findViewById(R.id.provinceSpinner);
        citySpinner = findViewById(R.id.citySpinner);
        areaSpinner = findViewById(R.id.areaSpinner);
        radioGroup = findViewById(R.id.radioGroupSex);
    }
}
