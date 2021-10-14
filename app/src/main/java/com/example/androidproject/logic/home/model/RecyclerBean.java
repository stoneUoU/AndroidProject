package com.example.androidproject.logic.home.model;

/**
 * Created by test on 2018/7/23.
 */

public class RecyclerBean {
    private int id;
    private String title;
    private String desc;
    private String phone;
    private String pic_url;

    public RecyclerBean(int id,String title,String desc,String phone,String pic_url){
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.phone = phone;
        this.pic_url = pic_url;
    }
    public int getId() {
        return id;
    }

    public void setId(int title) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

}
