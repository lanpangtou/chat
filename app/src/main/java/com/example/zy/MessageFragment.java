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

public class MessageFragment extends Fragment {

    ListView messageList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message, container, false);
        messageList = view.findViewById(R.id.messageList);
        messageList();
        return view;
    }

    //设置消息viewPager的内容
    public void messageList(){
        //数据
        String[]  names = new String[]{"张三","李四","王五","杀马特","流星雨","如初","喵喵","你是人间四月天","诗与远方","晚风",};
        String[]  contents = new String[]{
                "你真好看，哈哈","你知道当你需要个夏天 我会拼了命努力",
                "少年别嫌弃那个用青春跟你赌未来的姑娘好吗",
                "我若在你心上，情敌三千又何妨","我若在你心上，情敌三千又何妨","你的每一个动作 都能把我迷得魂牵梦绕","等我战平天下，你是否等我归家",
                "左右逢源和逞强其实都是坏事前者让你疲惫后者让你流泪","自作多情和认怂其实都是好事前者教你长大后者教你务实.",
                "自作多情和认怂其实都是好事前者教你长大后者教你务实."};
        int[]  headImgs = new int[]{R.drawable.pig,R.drawable.touxiang2,R.drawable.touxiang3,R.drawable.touxiang4,R.drawable.pig,
                R.drawable.pig,R.drawable.touxiang2,R.drawable.pig,R.drawable.touxiang4,R.drawable.touxiang3
        };
        List<Map<String,Object>> list = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            Map<String,Object> map = new HashMap<>();
            map.put("headImg",headImgs[i]);
            map.put("name",names[i]);
            map.put("content",contents[i]);
            list.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(getContext(),list,R.layout.message_list,new String[]{"headImg","name","content"},new int[]{R.id.headImg,R.id.name,R.id.content});
        messageList.setAdapter(adapter);
    }
}
