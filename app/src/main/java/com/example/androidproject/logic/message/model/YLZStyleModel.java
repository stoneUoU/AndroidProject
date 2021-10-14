package com.example.androidproject.logic.message.model;

public class YLZStyleModel {

    private int id;

    private String name;

    private int style;

    public YLZStyleModel(int id,String name,int style){
        this.id = id;
        this.name = name;
        this.style = style;
    }

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

    public void setStyle(int id){
        this.style = style;
    }
    public int getStyle(){
        return this.style;
    }
}
