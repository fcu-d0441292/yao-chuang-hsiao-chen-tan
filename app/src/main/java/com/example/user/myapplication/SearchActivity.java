//搜尋頁面
package com.example.user.myapplication;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.app.AlertDialog.THEME_DEVICE_DEFAULT_DARK;
import static android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
import static android.app.AlertDialog.THEME_HOLO_LIGHT;

public class SearchActivity extends ListActivity {

    //全域變數 藥品:1  化妝品:2
    public static int classification = 2;

    //宣告UI
    private EditText input;
    private Button okbutton;
    private Button DelButton;
    private Button BackButton;
    private TextView historytext;

    //查詢記錄(藥品)
    private String[] RecordString = new String[]{"Record1","Record2","Record3","Record4","Record5","Record6","Record7","Record8","Record9","Record10"};
    int RecordNumber = 0;
    int NowNumber = 0;

    //查詢記錄(化妝品)
    private String[] RecordStringCos = new String[]{"RecordCos1","RecordCos2","RecordCos3","RecordCos4","RecordCos5","RecordCos6","RecordCos7","RecordCos","RecordCos9","RecordCos10"};
    int RecordNumberCos = 0;
    int NowNumberCos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //classification = 2;
        //UI綁定
        input = (EditText)findViewById(R.id.SerachInput);
        input.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/circle.otf"));
        okbutton = (Button)findViewById(R.id.ok_button);
        okbutton.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/circle.otf"));
        DelButton = (Button)findViewById(R.id.DelRecordButton);
        DelButton.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/circle.otf"));
        BackButton = (Button)findViewById(R.id.backbutton);
        BackButton.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/circle.otf"));
        historytext = (TextView)findViewById(R.id.historytextview);
        historytext.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/circle.otf"));

        //設定EditText的hint
        if(classification==1) input.setHint("輸入藥品名稱...");
        else input.setHint("輸入化妝品名稱...");

        //okbutton功能===========================================================================================================
        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = input.getText().toString();

                if(classification==1) {
                    //(藥品)
                    //有輸入名稱
                    if (Name.length() >= 1) {
                        //存入查詢記錄
                        SharedPreferences settings = getSharedPreferences("PREF", MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(RecordString[NowNumber], input.getText().toString());

                        RecordNumber = RecordNumber + 1;
                        if (RecordNumber >= 10) RecordNumber = 10;
                        NowNumber = NowNumber + 1;
                        if (NowNumber >= 10) NowNumber = 0;
                        editor.putInt("RecordNumber", RecordNumber);
                        editor.putInt("NowNumber", NowNumber);
                        editor.commit();//要記得加

                        //傳送出去的值
                        Intent intent = new Intent();
                        intent.setClass(SearchActivity.this, SearchList.class); //切換頁面(從SearchActivity 切換到 SearchList )

                        intent.putExtra("KEY_Input", Name);           //傳送到SearchList 的內容
                        input.setText("");                                     //設定輸入欄為空白，防止切換回SearchActivity時有前次搜尋
                        startActivity(intent);                                 //執行切換
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out); //切換動畫
                    }
                    //沒有輸入名稱
                    else Toast.makeText(SearchActivity.this, "請輸入藥品名稱。", Toast.LENGTH_SHORT).show();
                }

                else {
                    //(化妝品)
                    //有輸入名稱
                    if (Name.length() >= 1) {
                        //存入查詢記錄
                        SharedPreferences settings = getSharedPreferences("PREF", MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(RecordStringCos[NowNumberCos], input.getText().toString());

                        RecordNumberCos = RecordNumberCos + 1;
                        if (RecordNumberCos >= 10) RecordNumberCos = 10;
                        NowNumberCos = NowNumberCos + 1;
                        if (NowNumberCos >= 10) NowNumberCos = 0;
                        editor.putInt("RecordNumberCos", RecordNumberCos);
                        editor.putInt("NowNumberCos", NowNumberCos);
                        editor.commit();//要記得加

                        //傳送出去的值
                        Intent intent = new Intent();
                        intent.setClass(SearchActivity.this, SearchList.class); //切換頁面(從SearchActivity 切換到 SearchList )

                        intent.putExtra("KEY_Input", Name);           //傳送到SearchList 的內容
                        input.setText("");                                     //設定輸入欄為空白，防止切換回SearchActivity時有前次搜尋
                        startActivity(intent);                                 //執行切換
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out); //切換動畫
                    }
                    //沒有輸入名稱
                    else Toast.makeText(SearchActivity.this, "請輸入化妝品名稱。", Toast.LENGTH_SHORT).show();
                }

            }
        }); //okbutton功能===========================================================================================================

        //DelButton功能===========================================================================================================
        DelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences settings = getSharedPreferences("PREF",MODE_PRIVATE);
                final SharedPreferences.Editor editor = settings.edit();
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this,THEME_DEVICE_DEFAULT_LIGHT); //對話視窗


                if(classification==1) {
                    //(藥品)
                    if (RecordNumber != 0) {
                        //對話視窗
                        builder.setMessage("是否清除藥品歷史紀錄，確定要繼續嗎?").setPositiveButton("清除", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(SearchActivity.this, "藥品歷史記錄清除完畢。", Toast.LENGTH_SHORT).show();
                                RecordNumber = 0;
                                NowNumber = 0;
                                editor.putInt("RecordNumber", RecordNumber);
                                editor.putInt("NowNumber", NowNumber);
                                editor.commit();//要記得加
                                onResume();
                            }
                        })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                        AlertDialog about_dialog = builder.create();
                        about_dialog.show();
                    }

                    else Toast.makeText(SearchActivity.this, "沒有任何藥品歷史記錄!!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    //(化妝品)
                    if (RecordNumberCos != 0) {
                        //對話視窗
                        builder.setMessage("是否清除化妝品歷史紀錄，確定要繼續嗎?").setPositiveButton("清除", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(SearchActivity.this, "化妝品歷史記錄清除完畢。", Toast.LENGTH_SHORT).show();
                                RecordNumberCos = 0;
                                NowNumberCos = 0;
                                editor.putInt("RecordNumberCos", RecordNumberCos);
                                editor.putInt("NowNumberCos", NowNumberCos);
                                editor.commit();//要記得加
                                onResume();
                            }
                        })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                        AlertDialog about_dialog = builder.create();
                        about_dialog.show();
                    }
                    else
                        Toast.makeText(SearchActivity.this, "沒有任何化妝品歷史記錄!!!", Toast.LENGTH_SHORT).show();

                }


            }

        }); //DelButton功能============================================================================================================

        //BackButton功能===========================================================================================================
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //傳送出去的值
                Intent intent = new Intent();
                intent.setClass(SearchActivity.this,MainActivity.class); //切換頁面(從SearchActivity 切換到 MainActivity)
                startActivity(intent);                                 //執行切換
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out); //切換動畫
                finish();
            }

        }); //BackButton功能============================================================================================================



        //取得查詢記錄===========================================================================================================
        ArrayList<String> albumList = new ArrayList<String>();
        SharedPreferences settings = getSharedPreferences("PREF",MODE_PRIVATE);
        String getRecord;

        if(classification==1) {
            //(藥品)
            RecordNumber = settings.getInt("RecordNumber", 0);
            NowNumber = settings.getInt("NowNumber", 0);
            for (int i = 0; i < RecordNumber; i++) {
                getRecord = settings.getString(RecordString[i], "aaa");
                albumList.add(getRecord);
            }
        }
        else {
            //(化妝品)
            RecordNumberCos = settings.getInt("RecordNumberCos", 0);
            NowNumberCos = settings.getInt("NowNumberCos", 0);
            for (int j = 0; j < RecordNumberCos; j++) {
                getRecord = settings.getString(RecordStringCos[j], "aaa");
                albumList.add(getRecord);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, albumList);
        setListAdapter(adapter);
        //取得查詢資料===========================================================================================================


    }//onCreate****************************************************************************************************************************************

    //返回搜尋時*********************************************************************************************************************************
    protected void onResume() {
        super.onResume();

        ArrayList<String> albumList = new ArrayList<String>();
        SharedPreferences settings = getSharedPreferences("PREF",MODE_PRIVATE);
        String getRecord;

        if(classification==1) {
            //(藥品)
            RecordNumber = settings.getInt("RecordNumber", 0);
            for (int i = 0; i < RecordNumber; i++) {
                getRecord = settings.getString(RecordString[i], "aaa");
                albumList.add((i + 1) + ". " + getRecord);
            }
        }
        else {
            //(化妝品)
            RecordNumberCos = settings.getInt("RecordNumberCos", 0);
            for (int j = 0; j < RecordNumberCos; j++) {
                getRecord = settings.getString(RecordStringCos[j], "aaa");
                albumList.add((j + 1) + ". " + getRecord);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, albumList);
        setListAdapter(adapter);
    }//onResume******************************************************************************************************************************

    //按下搜尋記錄*************************************************************************************************************************
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        SharedPreferences settings = getSharedPreferences("PREF",MODE_PRIVATE);
        String Name;


        //(藥品)
        if(classification==1)Name = settings.getString(RecordString[position], "aaa");
            //(化妝品)
        else Name = settings.getString(RecordStringCos[position], "aaa");

        //傳送出去的值
        Intent intent = new Intent();
        intent.setClass(SearchActivity.this,SearchList.class); //切換頁面(從SearchActivity 切換到 SearchList )

        intent.putExtra("KEY_Input", Name);           //傳送到SearchList 的內容
        startActivity(intent);                                 //執行切換
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out); //切換動畫
    }//按下搜尋記錄*************************************************************************************************************************


    //實體返回健===========================================================================================================
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 攔截返回鍵
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //傳送出去的值
            Intent intent = new Intent();
            intent.setClass(SearchActivity.this,MainActivity.class); //切換頁面(從SearchActivity 切換到 MainActivity)
            startActivity(intent);                                      //執行切換
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out); //切換動畫
            finish();
        }
        return true;
    }
    //實體返回健===========================================================================================================

}//class結束*****************************************************************************************************************************************