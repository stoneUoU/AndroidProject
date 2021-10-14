package com.example.androidproject.logic.mine.model;


public class YLZModel {

    /**
     * redirect_url :
     * id : 10
     * vendor_id : 4
     * title : 轮播图2
     * pic_path : /static_file/upload/3b248a8c88d511e8a28e021e2a6028ad.png
     * sn : 1
     * is_deleted : false
     * description : 轮播图2
     */
    private int vendor_id;
    private String title;
    private String pic_path;

    public YLZModel(int vendor_id,String title,String pic_path) {
        this.vendor_id = vendor_id;
        this.title = title;
        this.pic_path = pic_path;
    }

    public int getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(int vendor_id) {
        this.vendor_id = vendor_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic_path() {
        return pic_path;
    }

    public void setPic_path(String pic_path) {
        this.pic_path = pic_path;
    }

}
