package com.example.mywechat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout chatLayout;
    private LinearLayout friendsLayout;
    private LinearLayout bookLayout;
    private LinearLayout settingLayout;

    private ImageButton chatImageButton;
    private ImageButton friendsImageButton;
    private ImageButton bookImageButton;
    private ImageButton settingImageButton;

    private TextView chatText;
    private TextView friendsText;
    private TextView bookText;
    private TextView settingText;

    private Fragment chatFragment = new ChatFragment();
    private Fragment friendsFragment = new FriendsFragment();
    private Fragment bookFragment = new BookFragment();
    private Fragment settingFragment = new SettingFragment();

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
        initFragment();
        selectFragment(0);//默认首页是聊天界面
    }

    private void initFragment(){
//        fragmentManager = getFragmentManager();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.content,chatFragment);
        transaction.add(R.id.content,friendsFragment);
        transaction.add(R.id.content,bookFragment);
        transaction.add(R.id.content,settingFragment);
        transaction.commit();
    }

    private void initView(){
        chatLayout = findViewById(R.id.chatLayout);
        friendsLayout = findViewById(R.id.friendsLayout);
        bookLayout = findViewById(R.id.bookLayout);
        settingLayout = findViewById(R.id.settingLayout);

        chatImageButton = findViewById(R.id.chatImageButton);
        friendsImageButton = findViewById(R.id.friendsImageButton);
        bookImageButton = findViewById(R.id.bookImageButton);
        settingImageButton = findViewById(R.id.settingImageButton);

        chatText = findViewById(R.id.chatText);
        friendsText = findViewById(R.id.friendsText);
        bookText = findViewById(R.id.bookText);
        settingText = findViewById(R.id.settingText);
    }

    private void hideFragment(FragmentTransaction transaction){
        transaction.hide(chatFragment);
        transaction.hide(friendsFragment);
        transaction.hide(bookFragment);
        transaction.hide(settingFragment);
    }

    @SuppressLint("ResourceAsColor")
    private void selectFragment(int i){//i是一个选择器，用于选择显示哪个界面
         FragmentTransaction transaction = fragmentManager.beginTransaction();
         hideFragment(transaction);//首先隐藏所有页面

         switch (i){
             case 0:
                 transaction.show(chatFragment);
                 chatImageButton.setImageResource(R.drawable.chat_pick);
                 chatText.setTextColor(this.getResources().getColor(R.color.pickText));
                 break;
             case 1:
                 transaction.show(friendsFragment);
                 friendsImageButton.setImageResource(R.drawable.friends_pick);
                 friendsText.setTextColor(this.getResources().getColor(R.color.pickText));
                 break;
             case 2:
                 transaction.show(bookFragment);
                 bookImageButton.setImageResource(R.drawable.book_pick);
                 bookText.setTextColor(this.getResources().getColor(R.color.pickText));
                 break;
             case 3:
                 transaction.show(settingFragment);
                 settingImageButton.setImageResource(R.drawable.setting_pick);
                 settingText.setTextColor(this.getResources().getColor(R.color.pickText));
                 break;
             default:
                 break;
         }
         transaction.commit();
    }

    @Override
    public void onClick(View view) {
        resetBtn();
        switch (view.getId()){
            case R.id.chatLayout:
                selectFragment(0);
                break;
            case R.id.friendsLayout:
                selectFragment(1);
                break;
            case R.id.bookLayout:
                selectFragment(2);
                break;
            case R.id.settingLayout:
                selectFragment(3);
                break;
            default:
                break;
        }
    }

    @SuppressLint("ResourceAsColor")
    private void resetBtn(){
        chatImageButton.setImageResource(R.drawable.chat);
        friendsImageButton.setImageResource(R.drawable.friends);
        bookImageButton.setImageResource(R.drawable.book);
        settingImageButton.setImageResource(R.drawable.setting);

        chatText.setTextColor(this.getResources().getColor(R.color.nopickText));
        friendsText.setTextColor(this.getResources().getColor(R.color.nopickText));
        bookText.setTextColor(this.getResources().getColor(R.color.nopickText));
        settingText.setTextColor(this.getResources().getColor(R.color.nopickText));
    }

    //仅仅对bottom的四个linerlayout监听
    private void initEvent(){
        chatLayout.setOnClickListener(this);
        friendsLayout.setOnClickListener(this);
        bookLayout.setOnClickListener(this);
        settingLayout.setOnClickListener(this);
    }
}
