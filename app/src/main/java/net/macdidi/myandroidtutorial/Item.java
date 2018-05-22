package net.macdidi.myandroidtutorial;

import java.util.Date;
import java.util.Locale;

public class Item implements java.io.Serializable {

    // 編號、日期時間、產品名、公司名、違規情結、處分法條、處罰日期
    private long id;
    private long datetime;
    private String title;
    private String CompanyName;
    private String Detail;
    private String Law;
    private String LawDate;

    //建構子
    public Item() {
        title = "";
        CompanyName = "";
        Detail = "";
        Law = "";
        LawDate = "";
    }

    //建構子
    public Item(long id, long datetime, String title,String CompanyName,String Detail ,String Law,String LawDate) {
        this.id = id;
        this.datetime = datetime;
        this.title = title;
        this.CompanyName = CompanyName;
        this.Detail = Detail;
        this.Law = Law;
        this.LawDate = LawDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDatetime() {
        return datetime;
    }

    // 裝置區域的日期時間
    public String getLocaleDatetime() {
        return String.format(Locale.getDefault(), "%tF  %<tR", new Date(datetime));
    }

    // 裝置區域的日期
    public String getLocaleDate() {
        return String.format(Locale.getDefault(), "%tF", new Date(datetime));
    }

    // 裝置區域的時間
    public String getLocaleTime() {
        return String.format(Locale.getDefault(), "%tR", new Date(datetime));
    }

    //**********************************************************************************
    //各個基本資料的Get And Set
    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public String getLaw() {
        return Law;
    }

    public void setLaw(String law) {
        Law = law;
    }

    public String getLawDate() {
        return LawDate;
    }

    public void setLawDate(String lawDate) {
        LawDate = lawDate;
    }

}//Item結束********************************************************************************************