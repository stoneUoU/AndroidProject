package com.example.androidproject.logic.home.model;

import java.util.List;

public class YLZTopListModel {

    private int id;

    private String title;

    private int readCount;

    private String add_time;

    private List<String> imgs;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setReadCount(int readCount){
        this.readCount = readCount;
    }
    public int getReadCount(){
        return this.readCount;
    }
    public void setAdd_time(String add_time){
        this.add_time = add_time;
    }
    public String getAdd_time(){
        return this.add_time;
    }
    public void setImgs(List<String> imgs){
        this.imgs = imgs;
    }
    public List<String> getImgs(){
        return this.imgs;
    }
}
