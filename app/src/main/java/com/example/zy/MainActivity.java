package com.example.zy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private List<Fragment> fragments;
    private MenuItem menuItem;
    private ListView messageList;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        //viewPager绑定适配器内容
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
        messageList = (ListView) viewPager.findViewById(R.id.messageList);
        //通过Intent获取用户名
        Intent intent = getIntent();
        String  username = intent.getStringExtra("username");
        //获取navigationView的头部文件
        View headerView =navigationView.getHeaderView(0);
        //获取头部文件的view控件
        TextView headUsername = headerView.findViewById(R.id.head_username);
        //给用户名设置值
        headUsername.setText(username);
        //底部导航item点击事件
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //根据底部导航的item的id选择当前对应的viewPager
                switch (item.getItemId()){
                    case R.id.message:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.contacts:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.me:
                        viewPager.setCurrentItem(2);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //当滑动viewPager发生改变时，相应的导航栏item被选中
                if (menuItem == null){
                    menuItem = bottomNavigationView.getMenu().getItem(0);
                }
                //下一个被选中时上一个变为false
                 menuItem.setChecked(false);
                 menuItem = bottomNavigationView.getMenu().getItem(position);
                 menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    //定义一个fragment适配器类
    private class MyAdapter extends FragmentPagerAdapter{
        private List<Fragment> fragments;
        public MyAdapter(@NonNull FragmentManager fm,List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
    //初始化控件
    private void init(){
        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fragments = new ArrayList<>();
        fragments.add(new MessageFragment());
        fragments.add(new ContactsFragment());
        fragments.add(new MeFragment());
        navigationView = findViewById(R.id.navigationView);
    }
}
