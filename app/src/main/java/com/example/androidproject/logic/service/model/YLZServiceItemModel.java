package com.example.androidproject.logic.service.model;

/**
 * 服务页-服务项(右侧列表中的一条服务)
 */
public class YLZServiceItemModel {

    //图标类型:养老保险类(蓝色老人图标)
    public static final int ICON_TYPE_ELDERLY = 0;
    //图标类型:单位保险类(橙色楼宇)
    public static final int ICON_TYPE_BUILDING = 1;

    //服务名称
    public String title;
    //图标类型
    public int iconType;

    public YLZServiceItemModel(String title, int iconType) {
        this.title = title;
        this.iconType = iconType;
    }
}
