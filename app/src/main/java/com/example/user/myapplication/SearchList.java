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
import android.view.ViewDebug;
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
    JSONArray resultMedicineArray;
    JSONArray resultCosmeticArray;
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

    public  void GetMedicineData(){
        Log.v("MedicineDAta","ss");
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
                resultMedicineArray  =new JSONArray(builder.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public  void GetCosmeticData(){
        Log.v("CosmetDAta","as");
        try {
            InputStream is = this.getAssets().open("158_3.json");
            BufferedReader bufr = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line ;
            StringBuilder builder = new StringBuilder();
            while((line = bufr.readLine()) != null){
                builder.append(line);
            }
            is.close();
            bufr.close();

            try {
                resultCosmeticArray  =new JSONArray(builder.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void StoreData(ItemDAO itemDAO,JSONArray resultArray,JSONArray resultArray2){
        if (itemDAO.getCount() == 0) {
            itemDAO.sample();
            JSONObject data;
            JSONArray medicineArray;
            String title="查無資料";
            String CompanyName="查無資料";
            String Detail="查無資料";
            String Law="查無資料";
            String LawData="查無資料";
            //Log.v("ssss","fafsodfjsd");
            //int a=resultArray.length();
            //Log.v("sabe",Integer.toString(a) );
            for(int i=5;  i<resultArray.length()+5; i++){
                try{
                    medicineArray=resultArray.getJSONArray(i-5);
                    data=medicineArray.getJSONObject(0);
                    if(data.getString("違規產品名稱").length()!=0)title=data.getString("違規產品名稱");
                    else title = "查無資料";
                    //Log.v("title",title);

                    data=medicineArray.getJSONObject(1);
                    if(data.getString("違規廠商名稱或負責人").length()!=0)CompanyName=data.getString("違規廠商名稱或負責人");
                    else CompanyName = "查無資料";
                    //Log.v("CompanyName",CompanyName);

                    data=medicineArray.getJSONObject(5);
                    if(data.getString("違規情節").length()!=0) Detail=data.getString("違規情節");
                    else Detail = "查無資料";
                    //Log.v("Detail",Detail);

                    data=medicineArray.getJSONObject(9);
                    if(data.getString("查處情形").length()!=0) Law=data.getString("查處情形");
                    else Law = "查無資料";
                    //Log.v("Law",Law);

                    data=medicineArray.getJSONObject(3);
                    if(data.getString("處分日期").length()!=0) LawData = data.getString("處分日期");
                    else LawData = "查無資料";
                    //Log.v("LawData",LawData);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Item item = new Item(i, new Date().getTime(), title , CompanyName , Detail , Law , LawData);
                itemDAO.insert(item);
            }
            for(int i=resultArray.length()+5;  i<resultArray2.length()+resultArray.length()+5; i++){
                try{
                    medicineArray=resultArray2.getJSONArray(i-5);
                    data=medicineArray.getJSONObject(0);
                    if(data.getString("違規產品名稱").length()!=0)title=data.getString("違規產品名稱");
                    else title = "查無資料";
                    //Log.v("title",title);

                    data=medicineArray.getJSONObject(1);
                    if(data.getString("違規廠商名稱或負責人").length()!=0)CompanyName=data.getString("違規廠商名稱或負責人");
                    else CompanyName = "查無資料";
                    //Log.v("CompanyName",CompanyName);

                    data=medicineArray.getJSONObject(5);
                    if(data.getString("違規情節").length()!=0) Detail=data.getString("違規情節");
                    else Detail = "查無資料";
                    //Log.v("Detail",Detail);

                    data=medicineArray.getJSONObject(9);
                    if(data.getString("查處情形").length()!=0) Law=data.getString("查處情形");
                    else Law = "查無資料";
                    //Log.v("Law",Law);

                    data=medicineArray.getJSONObject(3);
                    if(data.getString("處分日期").length()!=0) LawData = data.getString("處分日期");
                    else LawData = "查無資料";
                    //Log.v("LawData",LawData);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Item item = new Item(i, new Date().getTime(), title , CompanyName , Detail , Law , LawData);
                itemDAO.insert(item);
            }

        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);


        NotFindText  = (TextView)findViewById(R.id.NotFindText);
        BackButton = (Button)findViewById(R.id.BackButton_list);
        BackButton.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/circle.otf"));

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






        //(藥品)===============================================================================================================================================
        // 如果資料庫是空的，就建立一些範例資料
        // 這是為了方便測試用的，完成應用程式以後可以拿掉
        GetMedicineData();
        GetCosmeticData();
        ItemDAO itemDAO = new ItemDAO(getApplicationContext());
        StoreData(itemDAO, resultMedicineArray,resultCosmeticArray);

        //(化妝品)===============================================================================================================================================

        ItemDAO itemDAOCos = new ItemDAO(getApplicationContext());

        ArrayList<String> albumList = new ArrayList<String>();



        //獲得DataBase所有資料(藥品)
        if(SearchActivity.classification==1) item = itemDAO.getAll();
        else  item = itemDAOCos.getAll();

        //(需完成)找出符合資料
        boolean Find = false;      //是否包含 true:包含 false:未包含
        //獲取SearchActivity傳送的值
        Intent intent = getIntent();
        String TitleName = intent.getStringExtra("KEY_Input");    //包含的字
        //int d= itemDAOCos.getCount();
        //Log.v("CC",Integer.toString(d));
        /*for (int i = 0; i < d; i++) {
           // if (item.get(i).getTitle().indexOf(TitleName) != -1) Find = true; //有找出是否包含字
            //else Find = false; //沒有找出是否包含
           // if (Find) {
                itemDAOCos.delete(i);
            //}
        }*/
        //String a=Integer.toString(itemDAO.getCount());
        if(SearchActivity.classification==1) {
            //開始搜尋(藥品)
            int len=itemDAO.getCount()-resultCosmeticArray.length();
            for (int i = 0; i < len; i++) {
                //Log.v("caca",Integer.toString(i));
                if (item.get(i).getTitle().indexOf(TitleName) != -1) Find = true; //有找出是否包含字
                else Find = false; //沒有找出是否包含字

                //依照上面結果放入ListView
                if (Find) {
                    //String.valueOf(itemDAO.getCount())  + item.get(0).getTitle() + " " +  item.get(1).getTitle() + " " + item.get(2).getTitle() + " " + item.get(3).getTitle() + itemDAO.getCount()
                    albumList.add("⊙"+item.get(i).getTitle());
                    Find = false;
                    FindSomething = true;
                    AllNumber[Count] = i - Count;
                    Count = Count + 1;
                }

            }
        }
        else {
            //開始搜尋(化妝品)
            int len=itemDAOCos.getCount();
            int startlen =  resultMedicineArray.length()+5;

            for (int i = startlen; i < len; i++) {
                if (item.get(i).getTitle().indexOf(TitleName) != -1) Find = true; //有找出是否包含字
                else Find = false; //沒有找出是否包含字

                //依照上面結果放入ListView
                if (Find) {
                    //String.valueOf(itemDAO.getCount())  + item.get(0).getTitle() + " " +  item.get(1).getTitle() + " " + item.get(2).getTitle() + " " + item.get(3).getTitle() + itemDAO.getCount()
                    albumList.add("⊙"+item.get(i).getTitle());
                    Find = false;
                    FindSomething = true;
                    AllNumber[Count] = i - Count;
                    Count = Count + 1;
                }
            }

        }



        NotFindText.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/circle.otf"));
        if(FindSomething){
            NotFindText.setText("⊙以下為符合的資料，共"+Count+"筆⊙");
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
