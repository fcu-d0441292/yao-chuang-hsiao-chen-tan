package com.example.user.myapplication;
/*
* 產品細節
* */
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.macdidi.myandroidtutorial.Item;
import net.macdidi.myandroidtutorial.ItemDAO;

import java.util.ArrayList;
import java.util.List;

public class Detail extends AppCompatActivity {


    private Button BackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().hide();//隱藏標題題

        //獲取SearchList傳送的值
        Intent intent = getIntent();
        String titlestring = intent.getStringExtra("KEY_title");
        String CompanyNamestring = intent.getStringExtra("KEY_CompanyName");
        String Detailstring = intent.getStringExtra("KEY_Detail");
        String Lawstring = intent.getStringExtra("KEY_Law");
        String LawDatestring = intent.getStringExtra("KEY_LawDate");

        //綁定UI
        TextView Title = (TextView)findViewById(R.id.TitleText);
        TextView CompanyName = (TextView)findViewById(R.id.ComNameText);
        TextView Detail = (TextView)findViewById(R.id.DetailText);
        TextView Law = (TextView)findViewById(R.id.LawText);
        TextView LawDate = (TextView)findViewById(R.id.LawDateText);
        BackButton = (Button)findViewById(R.id.BackButton_Detail);
        BackButton.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/circle.otf"));

       //設定字型
        Title.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/circle.otf"));
        CompanyName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/circle.otf"));
        Detail.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/circle.otf"));
        Law.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/circle.otf"));
        LawDate.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/circle.otf"));

        //將值放入UI
        Title.setText(titlestring);
        CompanyName.setText(CompanyNamestring);
        Detail.setText(Detailstring);
        Law.setText(Lawstring);
        LawDate.setText(LawDatestring);

        //BackButton功能===========================================================================================================
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out); //切換動畫
                finish();
            }

        }); //BackButton功能============================================================================================================



     /*
         Name = (TextView)findViewById(R.id.NametextView);

        // 建立資料庫物件
        ItemDAO itemDAO = new ItemDAO(getApplicationContext());

        // 如果資料庫是空的，就建立一些範例資料
        // 這是為了方便測試用的，完成應用程式以後可以拿掉
        if (itemDAO.getCount() == 0) {
            itemDAO.sample();
        }
        //新增資料
        Item item5 = new Item(5, new Date().getTime(), "保濕乳" , "海倫公司" , "效果誇大" , "消保法" , "2018/5/20");
        itemDAO.insert(item5);
        // 取得所有記事資料
        Item item = new Item();
        item = itemDAO.get(1);

        Name.setText(item.getCompanyName());

       */

   }

    //實體返回健===========================================================================================================
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 攔截返回鍵
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out); //切換動畫
            finish();
        }
        return true;
    }
    //實體返回健===========================================================================================================


}
