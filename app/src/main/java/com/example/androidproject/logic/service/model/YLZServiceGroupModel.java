package com.example.androidproject.logic.service.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务页-服务分组(右侧列表中的一组,如:企业职工养老保险)
 */
public class YLZServiceGroupModel {

    //分组名称
    public String groupName;
    //分组下的服务项
    public ArrayList<YLZServiceItemModel> items;

    public YLZServiceGroupModel(String groupName, ArrayList<YLZServiceItemModel> items) {
        this.groupName = groupName;
        this.items = items;
    }
}
