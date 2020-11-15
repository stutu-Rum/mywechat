package com.example.myapplication2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    MyReceiver myReceiver;

    public static final String CTL_ACTION=
            "org.crazyit.action.CTL_ACTION";
    public static final String UPDATE_ACTION=
            "org.crazyit.action.UPDATE_ACTION";
    TextView title,author;
    ImageButton play,stop,last,next;
    int status = 0x11;
    String[] titleStrs = new String[] { "Legends Never Die", "约定", "美丽新世界" };
    String[] authorStrs = new String[] { "英雄联盟", "周蕙", "伍佰" };


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            //获取来自receive中的intent的update消息，代表播放状态
            int update = intent.getIntExtra("update",-1);
            //获取来自receiver中intent的current消息，代表正在播放的歌曲
            int current = intent.getIntExtra("current",-1);
            //如果状态为正在播放歌曲或暂停
            if (current >= 0 && (update == 0x12 || update == 0x13)){
                title.setText(titleStrs[current]);
                author.setText(authorStrs[current]);
            }
            else
            {
                title.setText("未播放歌曲");
                author.setText("未播放歌曲");
            }
            switch (update)
            {
                //如果未播放歌曲，则播放图标为播放
                case 0x11:
                    play.setImageResource(R.drawable.play);
                    status=0x11;
                    break;
                //如果正在播放歌曲，则播放图标为暂停
                case 0x12:
                    play.setImageResource(R.drawable.pause);
                    status=0x12;
                    break;
                case 0x13:
                    play.setImageResource(R.drawable.play);
                    status=0x13;
                    break;
            }
        }
    }

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat, container, false);
        // Inflate the layout for this fragment
        // 在这里启动服务，将播放，暂停，停止，切换信号发送广播给service
        myReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();

        filter.addAction(UPDATE_ACTION);
        getActivity().registerReceiver(myReceiver,filter);
        Intent intent = new Intent(getContext(),MusicService.class);
        getActivity().startService(intent);


        return inflater.inflate(R.layout.chat, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        play = getActivity().findViewById(R.id.play);
        stop = getActivity().findViewById(R.id.stop);
        next = getActivity().findViewById(R.id.next);
        last = getActivity().findViewById(R.id.last);
        title = getActivity().findViewById(R.id.title);
        author = getActivity().findViewById(R.id.singer);
//        Toast.makeText(getContext(),title.getText(),Toast.LENGTH_SHORT).show();
        //添加监听
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("org.crazyit.action.CTL_ACTION");
//                Toast.makeText(getContext(),"dddddd",Toast.LENGTH_SHORT).show();
                intent.putExtra("control",1);
                getActivity().sendBroadcast(intent);
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("org.crazyit.action.CTL_ACTION");
//                Toast.makeText(getContext(),"dddddd",Toast.LENGTH_SHORT).show();
                intent.putExtra("control",2);
                getActivity().sendBroadcast(intent);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("org.crazyit.action.CTL_ACTION");
//                Toast.makeText(getContext(),"dddddd",Toast.LENGTH_SHORT).show();
                intent.putExtra("control",3);
                getActivity().sendBroadcast(intent);
            }
        });
        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("org.crazyit.action.CTL_ACTION");
//                Toast.makeText(getContext(),"dddddd",Toast.LENGTH_SHORT).show();
                intent.putExtra("control",4);
                getActivity().sendBroadcast(intent);
            }
        });
    }
}