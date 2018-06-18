package com.example.user.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.macdidi.myandroidtutorial.ItemDAO;

public class Setting extends AppCompatActivity {

    private TextView tv_version;
    private TextView tv_datasource;
    private TextView tv_engineer;
    private TextView tv_thank;
    private TextView tv_setting;
    private Button BackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().hide();//隱藏標題題

        //UI綁定
        tv_version = (TextView)findViewById(R.id.tv_version);
        tv_datasource = (TextView)findViewById(R.id.tv_datasource);
        tv_engineer = (TextView)findViewById(R.id.tv_engineer);
        tv_thank = (TextView)findViewById(R.id.tv_thank);
        tv_setting = (TextView)findViewById(R.id.settingtext);
        BackButton = (Button)findViewById(R.id.backbutton);

        //設定字形
        tv_version.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/circle.otf"));
        tv_datasource.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/circle.otf"));
        tv_engineer.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/circle.otf"));
        tv_thank.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/circle.otf"));
        tv_setting.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/circle.otf"));
        //BackButton功能===========================================================================================================
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //傳送出去的值
                Intent intent = new Intent();
                intent.setClass(Setting.this,MainActivity.class); //切換頁面(從SearchActivity 切換到 MainActivity)
                //startActivity(intent);                                 //執行切換
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out); //切換動畫
                finish();
            }

        }); //BackButton功能============================================================================================================

    }
}
