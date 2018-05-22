package com.example.user.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {


    private EditText input;
    private Button okbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //UI綁定
        input = (EditText)findViewById(R.id.SerachInput);
        okbutton = (Button)findViewById(R.id.ok_button);

        //okbutton功能
        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = input.getText().toString();

                Intent intent = new Intent();
                intent.setClass(SearchActivity.this,SearchList.class); //切換頁面(從SearchActivity 切換到 SearchList )

                intent.putExtra("KEY_Input", Name);           //傳送到SearchList 的內容
                input.setText("");                                     //設定輸入欄為空白，防止切換回SearchActivity時有前次搜尋
                startActivity(intent);                                 //執行切換

            }
        });



    }


}
