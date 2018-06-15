package com.example.user.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
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
    private ImageButton setbutton;
    private TextView MedText;
    private TextView CosText;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        medicine=findViewById(R.id.bt_medicine);
        cosmetic=findViewById(R.id.bt_cosmetic);
        setbutton=findViewById(R.id.set_button);
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

        View.OnClickListener set_button=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,Setting.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out); //切換動畫
            }
        } ;
        setbutton.setOnClickListener((set_button));


    }

}
