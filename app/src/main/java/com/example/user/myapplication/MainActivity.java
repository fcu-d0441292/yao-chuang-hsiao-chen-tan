package com.example.user.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import net.macdidi.myandroidtutorial.Item;
import net.macdidi.myandroidtutorial.ItemDAO;

import org.w3c.dom.Text;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ImageButton medicine;
    private ImageButton cosmetic;
    //private ImageButton setbutton;
    private TextView MedText;
    private TextView CosText;

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        switch (id){
            case  R.id.about:
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,Setting.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.navigation,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTextMessage = (TextView) findViewById(R.id.message);
        medicine=findViewById(R.id.bt_medicine);
        cosmetic=findViewById(R.id.bt_cosmetic);
        //setbutton=findViewById(R.id.set_button);
        MedText = (TextView) findViewById(R.id.MedtextView);
        CosText = (TextView) findViewById(R.id.CostextView);
        MedText.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/circle.otf"));
        CosText.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/circle.otf"));

        View.OnClickListener bt_medicine=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,SearchActivity.class);
                SearchActivity.classification = 1;
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out); //切換動畫
            }
        } ;
        medicine.setOnClickListener((bt_medicine));

        View.OnClickListener bt_cosmetic=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,SearchActivity.class);
                SearchActivity.classification = 2;
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out); //切換動畫
            }
        } ;
        cosmetic.setOnClickListener((bt_cosmetic));

        /*View.OnClickListener set_button=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,Setting.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out); //切換動畫
            }
        } ;
        setbutton.setOnClickListener((set_button));*/


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
