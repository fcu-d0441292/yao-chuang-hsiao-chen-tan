package com.example.user.myapplication;
/*
* 查詢結果的畫面
* */
import android.app.ListActivity;
import android.content.Intent;
import org.json.*;
import org.w3c.dom.Text;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.macdidi.myandroidtutorial.Item;
import net.macdidi.myandroidtutorial.ItemDAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchList extends ListActivity {

    JSONObject data;
    List<Item> item = new  ArrayList<Item>();
    int [] AllNumber = new int[10000];
    int Count = 0;

    private boolean FindSomething = false; //有無找到資料 true:有找到 false:沒找到
    private TextView NotFindText;
    private ImageView NotFind;
    private Button BackButton;


    //按下查詢結果**************************************************************************
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String  title = null,CompanyName = null,Detail = null,Law = null,LawDate = null;


        //打入資料的內容(產品名稱  , 公司名稱 , 違反情節 , 處分法條 , 處罰日期 )
        title = item.get( position + AllNumber[position]).getTitle();
        CompanyName = item.get(position + AllNumber[position]).getCompanyName();
        Detail = item.get(position + AllNumber[position]).getDetail();
        Law = item.get(position + AllNumber[position]).getLaw();
        LawDate = item.get(position + AllNumber[position]).getLawDate();

        Intent intent = new Intent();
        intent.setClass(SearchList.this,Detail.class); //切換頁面(從SearchList 切換到 Detail)
        intent.putExtra("KEY_title", title);           //傳送到Detail的內容
        intent.putExtra("KEY_CompanyName", CompanyName); //傳送到Detail的內容
        intent.putExtra("KEY_Detail", Detail);         //傳送到Detail的內容
        intent.putExtra("KEY_Law", Law);           //傳送到Detail的內容
        intent.putExtra("KEY_LawDate", LawDate);       //傳送到Detail的內容
        startActivity(intent);//執行切換
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out); //切換動畫
    }
  //按下查詢結果結束******************************************************************************************/

    public  void getdata(){
        try {
            InputStream is = this.getAssets().open("156_3.json");
            BufferedReader bufr = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line ;
            StringBuilder builder = new StringBuilder();
            while((line = bufr.readLine()) != null){
                builder.append(line);
            }
            is.close();
            bufr.close();

            try {
                JSONArray resultArray  =new JSONArray(builder.toString());
                JSONArray Array=resultArray.getJSONArray(0);
                data = Array.getJSONObject(0);
                System.out.println("違規產品名稱="+ data.getString("違規產品名稱") );
               /* System.out.println("cat="+ data.getString("cat") );
                JSONArray array = data.getJSONArray("languages");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject lan = array.getJSONObject(i);
                    System.out.println("-----------------------");
                    System.out.println("id="+lan.getInt("id"));
                    System.out.println("ide="+lan.getString("ide"));
                    System.out.println("name="+lan.getString("name"));
                }*/
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);


        NotFindText  = (TextView)findViewById(R.id.NotFindText);
        BackButton = (Button)findViewById(R.id.BackButton_list);

        //BackButton功能===========================================================================================================
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //傳送出去的值
                Intent intent = new Intent();
                intent.setClass(SearchList.this,SearchActivity.class); //切換頁面(從SearchActivity 切換到 MainActivity)
                startActivity(intent);                                 //執行切換
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out); //切換動畫
                finish();
            }

        }); //BackButton功能============================================================================================================


        getdata();




            //(藥品)===============================================================================================================================================
            // 如果資料庫是空的，就建立一些範例資料
            // 這是為了方便測試用的，完成應用程式以後可以拿掉
            ItemDAO itemDAO = new ItemDAO(getApplicationContext());

            if (itemDAO.getCount() == 0) {
                itemDAO.sample();
            }


            //(化妝品)===============================================================================================================================================
            ItemDAO itemDAOCos = new ItemDAO(getApplicationContext());

            if (itemDAOCos.getCount() == 0) {
                itemDAOCos.sample();
            }


        ArrayList<String> albumList = new ArrayList<String>();


        //在這裡將JSON的資料放進資料庫(藥品)
        /*
                     for(int i=5;  i<JSON資料數; i++){
                      Item item = new Item(i, new Date().getTime(), "潤膚霜" , "東海公司" , "成分標示不清" , "消保法" , "2018/5/14");
                     itemDAO.insert(item);
                     }
               */


        //在這裡將JSON的資料放進資料庫(化妝品)
        /*
                     for(int j=5;  j<JSON資料數; j++){
                      Item item = new Item(j, new Date().getTime(), "潤膚霜" , "東海公司" , "成分標示不清" , "消保法" , "2018/5/14");
                     itemDAOCos.insert(item);
                     }
                */

        //獲得DataBase所有資料(藥品)
        item = itemDAO.getAll();

        //獲得DataBase所有資料(化妝品)
        item = itemDAOCos.getAll();

        //(需完成)找出符合資料
        boolean Find = false;      //是否包含 true:包含 false:未包含
        //獲取SearchActivity傳送的值
        Intent intent = getIntent();
        String TitleName = intent.getStringExtra("KEY_Input");    //包含的字

        if(SearchActivity.classification==1) {
            //開始搜尋(藥品)
            for (int i = 0; i < itemDAO.getCount(); i++) {
                if (item.get(i).getTitle().indexOf(TitleName) != -1) Find = true; //有找出是否包含字
                else Find = false; //沒有找出是否包含字

                //依照上面結果放入ListView
                if (Find) {
                    //String.valueOf(itemDAO.getCount())  + item.get(0).getTitle() + " " +  item.get(1).getTitle() + " " + item.get(2).getTitle() + " " + item.get(3).getTitle() + itemDAO.getCount()
                    albumList.add(item.get(i).getTitle());
                    Find = false;
                    FindSomething = true;
                    AllNumber[Count] = i - Count;
                    Count = Count + 1;
                }

            }
        }
        else {
            //開始搜尋(化妝品)
            for (int i = 0; i < itemDAOCos.getCount(); i++) {
                if (item.get(i).getTitle().indexOf(TitleName) != -1) Find = true; //有找出是否包含字
                else Find = false; //沒有找出是否包含字

                //依照上面結果放入ListView
                if (Find) {
                    //String.valueOf(itemDAO.getCount())  + item.get(0).getTitle() + " " +  item.get(1).getTitle() + " " + item.get(2).getTitle() + " " + item.get(3).getTitle() + itemDAO.getCount()
                    albumList.add(item.get(i).getTitle());
                    Find = false;
                    FindSomething = true;
                    AllNumber[Count] = i - Count;
                    Count = Count + 1;
                }
            }

        }



        //NotFindText.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/wt021.ttf"));
        if(FindSomething){
            NotFindText.setText("⊙以下為符合的資料⊙");
        }
        else NotFindText.setText("⊙沒找到任何資料⊙");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, albumList);
        setListAdapter(adapter);



    }

    //實體返回健===========================================================================================================
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 攔截返回鍵
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //傳送出去的值
            Intent intent = new Intent();
            intent.setClass(SearchList.this,SearchActivity.class); //切換頁面(從SearchActivity 切換到 MainActivity)
            startActivity(intent);                                      //執行切換
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out); //切換動畫
            finish();
        }
        return true;
    }
    //實體返回健===========================================================================================================



}
