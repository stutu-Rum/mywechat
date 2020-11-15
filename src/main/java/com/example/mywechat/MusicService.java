package com.example.mywechat;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import java.io.IOException;

public class MusicService extends Service {
    MyReceiver serviceReceiver;
    AssetManager am;
    String[] music = new String[]{"legendsneverdie.mp3", "promise.mp3", "beautiful.mp3"};
    MediaPlayer mPlayer;

    //0x11 表示没有播放，0x12表示正在播放，0x13表示暂停
    int status = 0x11;
    int current = 0;
    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        am=getAssets();
        //创建BroadcastReceiver
        serviceReceiver=new MyReceiver();
        //创建IntentFilter
        IntentFilter filter=new IntentFilter();
        filter.addAction(ChatFragment.CTL_ACTION);
        registerReceiver(serviceReceiver,filter);
        mPlayer=new MediaPlayer();
        //为MediaPlayer播放完成事件绑定监听器
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                current++;
                if (current>=4)
                {
                    current=0;
                }
                Intent sendIntent = new Intent(ChatFragment.UPDATE_ACTION);
                sendIntent.putExtra("current",current);
                //发送广播，将被Activity组件中的BroadcastReceiver接收
                sendBroadcast(sendIntent);
                //准备播放音乐
                prepareAndPlay(music[current]);
            }
        });
    }

    public class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getApplicationContext(),"收到消息",Toast.LENGTH_SHORT);
            int control = intent.getIntExtra("control",-1);
            switch (control){
                //播放或暂停
                case 1:
                    //原来处于没有播放状态
                    if (status == 0x11){
                        //准备并播放音乐
                        prepareAndPlay(music[current]);
                        status = 0x12;
                    }
                    //原来处于播放状态
                    else if (status == 0x12){
                        //暂停
                        mPlayer.start();
                        //改变为暂停状态
                        status = 0x13;
                    }
                    //原来处于暂停状态
                    else if (status == 0x13){
                        //播放
                        mPlayer.start();
                        status = 0x12;
                    }
                    break;
                //停止声音
                case 2:
                    if (status == 0x12 || status == 0x13){
                        //停止播放
                        mPlayer.stop();
                        status = 0x11;
                    }
                    break;
                case 3:
                    //原来处于没有播放或暂停状态
                    if (status == 0x11 || status == 0x13){
                        if (current == 0){
                            current = 3;
                            prepareAndPlay(music[current]);
                        }
                        //准备并播放音乐
                        else{
                            current = current - 1;
                            prepareAndPlay(music[current]);
                        }
                        status = 0x12;
                    }
                    //原来处于播放状态
                    else if (status == 0x12){
                        if (current == 0){
                            current = 3;
                            prepareAndPlay(music[current]);
                        }else{
                            current = current-1;
                            prepareAndPlay(music[current]);
                        }
                    }
                    break;
                case 4:
                    //原来没有处于播放状态或暂停状态
                    if (status == 0x11 || status == 0x13){
                        if(current==3) {
                            current=0;
                            prepareAndPlay(music[current]);
                        }   //准备并播放音乐
                        else {
                            current=current+1;
                            prepareAndPlay(music[current]);
                        }
                        status=0x12;
                    }
                    else if (status==0x12)
                    {
                        if(current==3) {
                            current=0;
                            prepareAndPlay(music[current]);
                        }
                        else {
                            current=current+1;
                            prepareAndPlay(music[current]);
                        }
                    }
                    break;
            }
            //广播通知Activity更改图标、文本框
            Intent sendIntent=new Intent(ChatFragment.UPDATE_ACTION);
            sendIntent.putExtra("update",status);
            sendIntent.putExtra("current",current);
            //发送广播，将被Activity组件中的BroadcastReceiver接收
            sendBroadcast(sendIntent);
        }
    }
    private void prepareAndPlay(String music){
        try{
            //打开指定音乐文件
            AssetFileDescriptor afd = am.openFd(music);
            mPlayer.reset();
            //使用MediaPlayer加载指定的音乐文件
            mPlayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            //准备声音
            mPlayer.prepare();
            //播放
            mPlayer.start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
