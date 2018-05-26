package com.example.user.myapplication;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchActivity extends ListActivity {


    private EditText input;
    private Button okbutton;

    //查詢記錄
    private String[] RecordString = new String[]{"Record1","Record2","Record3","Record4","Record5","Record6","Record7","Record8","Record9","Record10"};
    int RecordNumber = 0;
    int NowNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //UI綁定
        input = (EditText)findViewById(R.id.SerachInput);
        okbutton = (Button)findViewById(R.id.ok_button);

        //okbutton功能===========================================================================================================
        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = input.getText().toString();

                //存入查詢記錄
                SharedPreferences settings = getSharedPreferences("PREF",MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(RecordString[NowNumber], input.getText().toString());

                RecordNumber = RecordNumber + 1;
                if(RecordNumber>=10) RecordNumber = 10;
                NowNumber = NowNumber + 1;
                if(NowNumber>=10) NowNumber = 0;
                editor.putInt("RecordNumber" , RecordNumber);
                editor.putInt("NowNumber" , NowNumber);
                editor.commit();//要記得加

                //傳送出去的值
                Intent intent = new Intent();
                intent.setClass(SearchActivity.this,SearchList.class); //切換頁面(從SearchActivity 切換到 SearchList )

                intent.putExtra("KEY_Input", Name);           //傳送到SearchList 的內容
                input.setText("");                                     //設定輸入欄為空白，防止切換回SearchActivity時有前次搜尋
                startActivity(intent);                                 //執行切換

            }
        }); //okbutton功能===========================================================================================================




        //取得查詢資料===========================================================================================================
        ArrayList<String> albumList = new ArrayList<String>();
        SharedPreferences settings = getSharedPreferences("PREF",MODE_PRIVATE);
        RecordNumber =  settings.getInt("RecordNumber",0);
        NowNumber = settings.getInt("NowNumber",0);
        String getRecord ;
        for(int i=0; i<RecordNumber; i++) {
            getRecord = settings.getString(RecordString[i], "aaa");
            albumList.add(getRecord);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, albumList);
        setListAdapter(adapter);
        //取得查詢資料===========================================================================================================


    }//onCreate****************************************************************************************************************************************

    //返回搜尋時********************************************************************************************************************************
    protected void onResume() {
        super.onResume();


        ArrayList<String> albumList = new ArrayList<String>();
        SharedPreferences settings = getSharedPreferences("PREF",MODE_PRIVATE);
        RecordNumber =  settings.getInt("RecordNumber",0);
        String getRecord ;
        for(int i=0; i<RecordNumber; i++) {
            getRecord = settings.getString(RecordString[i], "aaa");
            albumList.add( (i+1) + ". " + getRecord);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, albumList);
        setListAdapter(adapter);
    }//onResume******************************************************************************************************************************

    //按下搜尋記錄*************************************************************************************************************************
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        SharedPreferences settings = getSharedPreferences("PREF",MODE_PRIVATE);
        String Name = settings.getString(RecordString[position], "aaa");

        //傳送出去的值
        Intent intent = new Intent();
        intent.setClass(SearchActivity.this,SearchList.class); //切換頁面(從SearchActivity 切換到 SearchList )

        intent.putExtra("KEY_Input", Name);           //傳送到SearchList 的內容
        startActivity(intent);                                 //執行切換

    }//按下搜尋記錄*************************************************************************************************************************


}//class結束*****************************************************************************************************************************************
