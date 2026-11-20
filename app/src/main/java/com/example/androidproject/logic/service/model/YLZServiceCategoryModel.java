package com.example.androidproject.logic.service.model;

import java.util.ArrayList;

/**
 * 服务页-左侧服务大类(如:社会保险),联动右侧分组数据
 */
public class YLZServiceCategoryModel {

    //大类名称
    public String categoryName;
    //该大类下的服务分组
    public ArrayList<YLZServiceGroupModel> groups;

    public YLZServiceCategoryModel(String categoryName, ArrayList<YLZServiceGroupModel> groups) {
        this.categoryName = categoryName;
        this.groups = groups;
    }
}
