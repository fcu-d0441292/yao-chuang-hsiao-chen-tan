package com.example.user.myapplication;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import net.macdidi.myandroidtutorial.ItemDAO;

public class Setting extends AppCompatActivity {

    private TextView tv_version;
    private TextView tv_datasource;
    private TextView tv_engineer;
    private TextView tv_thank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //UI綁定=
        tv_version = (TextView)findViewById(R.id.tv_version);
        tv_datasource = (TextView)findViewById(R.id.tv_datasource);
        tv_engineer = (TextView)findViewById(R.id.tv_engineer);
        tv_thank = (TextView)findViewById(R.id.tv_thank);

        //設定字形
        tv_version.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/wt021.ttf"));
        tv_datasource.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/wt021.ttf"));
        tv_engineer.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/wt021.ttf"));
        tv_thank.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/wt021.ttf"));

    }
}
