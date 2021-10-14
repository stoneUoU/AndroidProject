package com.example.androidproject.logic.message.model;

import java.util.ArrayList;
import java.util.List;

public class YLZStyleListModel  {
    private int id;

    private String name;

    private int style;

    private List<YLZStyleModel> list;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setStyle(int style){
        this.style = style;
    }
    public int getStyle(){
        return this.style;
    }
    public void setList(List<YLZStyleModel> list){
        this.list = list;
    }
    public List<YLZStyleModel> getList(){
        return this.list;
    }

    public YLZStyleListModel(int id,String name,int style,List<YLZStyleModel> list){
        this.id = id;
        this.name = name;
        this.style = style;
        this.list = list;
    }
}
