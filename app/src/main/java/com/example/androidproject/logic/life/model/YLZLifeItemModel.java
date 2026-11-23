package com.example.androidproject.logic.life.model;

/**
 * 惠民生活页列表条目模型
 * 页面所有区块(标题栏/消费banner/快捷图标/分组标题/享优惠/金融服务/银行卡)统一为列表条目,按itemType区分
 */
public class YLZLifeItemModel {

    //标题栏
    public static final int TYPE_TITLE_BAR = 0;
    //社保卡金融账户消费banner
    public static final int TYPE_BANNER = 1;
    //快捷图标
    public static final int TYPE_ICON = 2;
    //分组标题(享优惠/金融服务/社保卡权益)
    public static final int TYPE_SECTION_TITLE = 3;
    //享优惠横滑卡片
    public static final int TYPE_PROMO = 4;
    //兴业银行金融惠民专区banner
    public static final int TYPE_BANK_ZONE = 5;
    //金融知识/信用卡申请/远程银行三栏
    public static final int TYPE_FINANCE_NAV = 6;
    //社保卡权益银行卡片
    public static final int TYPE_BANK = 7;

    //条目类型
    public int itemType;
    //标题(标题栏文字/分组标题/图标名称/银行名称)
    public String title;
    //图标资源(快捷图标)
    public int iconRes;
    //银行logo圆底资源(银行卡)
    public int bankLogoBgRes;
    //银行logo占位字(工/建/中/农/邮/兴)
    public String bankLogoChar;
    //银行描述小字(银行卡)
    public String bankDesc;

    public YLZLifeItemModel(int itemType) {
        this.itemType = itemType;
    }
}
