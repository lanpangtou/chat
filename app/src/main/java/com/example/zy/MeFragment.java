package com.example.zy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeFragment extends Fragment {
    private ListView meList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me, container, false);
        meList = view.findViewById(R.id.meList);
        meList();
        return view;
    }

    //设置消息viewPager的内容
    public void meList(){
        //数据
        int[]  Icons = new int[]{R.drawable.qqkongjian,R.drawable.fujing,R.drawable.ziliao};
        int[]  arrows = new int[]{R.drawable.dayu,R.drawable.dayu,R.drawable.dayu};
        String[]  desc = new String[]{"好友动态","附近","个人资料"};


        List<Map<String,Object>> list = new ArrayList<>();
        for (int i = 0; i < desc.length; i++) {
            Map<String,Object> map = new HashMap<>();
            map.put("me_navIcon",Icons[i]);
            map.put("me_navDesc",desc[i]);
            map.put("me_arrow",arrows[i]);
            list.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(getContext(),list,R.layout.me_list,new String[]{"me_navIcon","me_navDesc","me_arrow"},new int[]{R.id.me_navIcon,R.id.me_navDesc,R.id.me_arrow});
        meList.setAdapter(adapter);
    }
}
