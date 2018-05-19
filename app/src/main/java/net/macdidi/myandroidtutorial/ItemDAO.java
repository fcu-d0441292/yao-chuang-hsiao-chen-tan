package net.macdidi.myandroidtutorial;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

// 資料功能類別
public class ItemDAO {
    // 表格名稱
    public static final String TABLE_NAME = "item";

    // 編號表格欄位名稱，固定不變
    public static final String KEY_ID = "_id";

    // 其它表格欄位名稱
    public static final String DATETIME_COLUMN = "datetime";
    public static final String TITLE_COLUMN = "title";
    public static final String COMPANYNAME_COLUMN = "CompanyName";
    public static final String DETAIL_COLUMN = "Detail";
    public static final String LAW_COLUMN = "Law";
    public static final String LAWDATE_COLUMN = "LawDate";

    // 使用上面宣告的變數建立表格的SQL指令(EX. 不可為NULL 或是 只能是整數或字串)
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DATETIME_COLUMN + " INTEGER NOT NULL, " +
                    TITLE_COLUMN + " TEXT NOT NULL, " +
                    COMPANYNAME_COLUMN +  " TEXT NOT NULL, " +
                    DETAIL_COLUMN + " TEXT NOT NULL, " +
                    LAW_COLUMN + " TEXT NOT NULL, " +
                    LAWDATE_COLUMN + " TEXT NOT NULL)";

    // 資料庫物件
    private SQLiteDatabase db;

    // 建構子，一般的應用都不需要修改
    public ItemDAO(Context context) {
        db = MyDBHelper.getDatabase(context);
    }

    // 關閉資料庫，一般的應用都不需要修改
    public void close() {
        db.close();
    }

    // 新增參數指定的物件************************************************************************************************************************
    public Item insert(Item item) {
        // 建立準備新增資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的新增資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(DATETIME_COLUMN, item.getDatetime());
        cv.put(TITLE_COLUMN, item.getTitle());
        cv.put(COMPANYNAME_COLUMN ,item.getCompanyName());
        cv.put(DETAIL_COLUMN ,item.getDetail());
        cv.put(LAW_COLUMN ,item.getLaw());
        cv.put(LAWDATE_COLUMN ,item.getLawDate());
        // 新增一筆資料並取得編號
        // 第一個參數是表格名稱
        // 第二個參數是沒有指定欄位值的預設值
        // 第三個參數是包裝新增資料的ContentValues物件
        long id = db.insert(TABLE_NAME, null, cv);

        // 設定編號
        item.setId(id);
        // 回傳結果
        return item;
    }

    // 修改參數指定的物件************************************************************************************************************************
    public boolean update(Item item) {
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的修改資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(DATETIME_COLUMN, item.getDatetime());
        cv.put(TITLE_COLUMN, item.getTitle());
        cv.put(COMPANYNAME_COLUMN ,item.getCompanyName());
        cv.put(DETAIL_COLUMN ,item.getDetail());
        cv.put(LAW_COLUMN ,item.getLaw());
        cv.put(LAWDATE_COLUMN ,item.getLawDate());

        // 設定修改資料的條件為編號
        // 格式為「欄位名稱＝資料」
        String where = KEY_ID + "=" + item.getId();

        // 執行修改資料並回傳修改的資料數量是否成功
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    // 刪除參數指定編號的資料************************************************************************************************************************
    public boolean delete(long id){
        // 設定條件為編號，格式為「欄位名稱=資料」
        String where = KEY_ID + "=" + id;
        // 刪除指定編號資料並回傳刪除是否成功
        return db.delete(TABLE_NAME, where , null) > 0;
    }

    // 讀取所有記事資料************************************************************************************************************************
    public List<Item> getAll() {
        List<Item> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    // 取得指定編號的資料物件
    public Item get(long id) {
        // 準備回傳結果用的物件
        Item item = null;
        // 使用編號為查詢條件
        String where = KEY_ID + "=" + id;
        // 執行查詢
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        // 如果有查詢結果
        if (result.moveToFirst()) {
            // 讀取包裝一筆資料的物件
            item = getRecord(result);
        }

        // 關閉Cursor物件
        result.close();
        // 回傳結果
        return item;
    }

    // 把Cursor目前的資料包裝為物件
    public Item getRecord(Cursor cursor) {
        // 準備回傳結果用的物件
        Item result = new Item();

        result.setId(cursor.getLong(0));
        result.setDatetime(cursor.getLong(1));
        result.setTitle(cursor.getString(2));
        result.setCompanyName(cursor.getString(3));
        result.setDetail(cursor.getString(4));
        result.setLaw(cursor.getString(5));
        result.setLawDate(cursor.getString(6));
        // 回傳結果
        return result;
    }

    // 取得資料數量
    public int getCount() {
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);

        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }

        return result;
    }

    // 建立範例資料
    public void sample() {
        Item item = new Item(0, new Date().getTime(), "潤膚霜" , "東海公司" , "成分標示不清" , "消保法" , "2018/5/14");
        Item item1 = new Item(1, new Date().getTime(), "BB霜" , "羅素公司" , "成分標示不清" , "消保法" , "2018/5/14");
        Item item2 = new Item(2, new Date().getTime(), "彩妝" , "海迪公司" , "成分標示不清" , "消保法" , "2018/5/14");
        Item item3 = new Item(3, new Date().getTime(), "保濕乳" , "逢甲公司" , "成分標示不清" , "消保法" , "2018/5/14");

        insert(item);
        insert(item1);
        insert(item2);
        insert(item3);
    }

}