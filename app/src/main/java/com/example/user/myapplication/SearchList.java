package com.example.user.myapplication;
/*
* 查詢結果的畫面
* */
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import net.macdidi.myandroidtutorial.Item;
import net.macdidi.myandroidtutorial.ItemDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchList extends ListActivity {

    List<Item> item = new  ArrayList<Item>();

    //按下查詢結果**************************************************************************
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String  title = null,CompanyName = null,Detail = null,Law = null,LawDate = null;


        //打入資料的內容(產品名稱  , 公司名稱 , 違反情節 , 處分法條 , 處罰日期 )
        switch(position) {
            case 0:

               // item =  Setting.itemDAO.get(1);
                title = item.get(0).getTitle();
                CompanyName = item.get(0).getCompanyName();
                Detail = item.get(0).getDetail();
                Law = item.get(0).getLaw();
                LawDate = item.get(0).getLawDate();
                break;
            case 1:

                //item =  itemDAO.get(1);
                title = item.get(1).getTitle();
                CompanyName = item.get(1).getCompanyName();
                Detail = item.get(1).getDetail();
                Law = item.get(1).getLaw();
                LawDate = item.get(1).getLawDate();
                break;
        }



        Intent intent = new Intent();
        intent.setClass(SearchList.this,Detail.class); //切換頁面(從SearchList 切換到 Detail)
        intent.putExtra("KEY_title", title);           //傳送到Detail的內容
        intent.putExtra("KEY_CompanyName", CompanyName); //傳送到Detail的內容
        intent.putExtra("KEY_Detail", Detail);         //傳送到Detail的內容
        intent.putExtra("KEY_Law", Law);           //傳送到Detail的內容
        intent.putExtra("KEY_LawDate", LawDate);       //傳送到Detail的內容
        startActivity(intent);//執行切換
    }
  //按下查詢結果結束******************************************************************************************/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);


        // 如果資料庫是空的，就建立一些範例資料
        // 這是為了方便測試用的，完成應用程式以後可以拿掉
        ItemDAO itemDAO = new ItemDAO(getApplicationContext());

        if (itemDAO.getCount() == 0) {
           itemDAO.sample();
        }
        ArrayList<String> albumList = new ArrayList<String>();


        //獲得DataBase所有資料
        item = itemDAO.getAll();
        //System.out.print(item.getCompanyName());
        // System.out.print(1);
        albumList.add("SK-II保濕霜");
        albumList.add("ARJ防曬乳");




        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, albumList);

        setListAdapter(adapter);



    }
}
