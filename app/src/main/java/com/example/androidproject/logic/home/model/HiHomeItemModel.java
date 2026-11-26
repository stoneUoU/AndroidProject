package com.example.androidproject.logic.home.model;

import java.util.List;

/**
 * 首页(政务版)列表条目模型
 * 页面所有区块统一为列表条目,按itemType区分,配合GridLayoutManager(spanCount=10)通栏展示
 */
public class HiHomeItemModel {

    //四宫格快捷入口(标题+搜索行固定在页面顶部,不进列表;四宫格随内容滚动)
    public static final int TYPE_QUICK = 0;
    //猜你想办白卡
    public static final int TYPE_GUESS = 1;
    //主题服务(左大卡+2x2)
    public static final int TYPE_THEME = 2;
    //通知条
    public static final int TYPE_NOTICE = 3;
    //龙岩一卡通横幅
    public static final int TYPE_BANNER = 4;
    //特色专区2x2
    public static final int TYPE_ZONE = 5;
    //政务地图
    public static final int TYPE_MAP = 6;

    //条目类型
    public int itemType;
    //通用文本(猜你想办事项/通知条文案)
    public String title;
    //猜你想办事项列表(mock,待接口)
    public List<String> guessTitles;

    public HiHomeItemModel(int itemType) {
        this.itemType = itemType;
    }
}
