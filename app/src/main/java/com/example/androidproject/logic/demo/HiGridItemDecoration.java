package com.example.androidproject.logic.demo;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.donkingliang.varieditemdecoration.GridVariedItemDecoration;
import com.example.androidproject.R;

public class HiGridItemDecoration extends GridVariedItemDecoration {

    private Drawable[] rowDrawables = new Drawable[5];

    private Drawable[] columnDrawables = new Drawable[5];

    public HiGridItemDecoration(Context context) {

        rowDrawables[0] = context.getResources().getDrawable(R.drawable.row_divider_1);
        rowDrawables[1] = context.getResources().getDrawable(R.drawable.row_divider_2);
        rowDrawables[2] = context.getResources().getDrawable(R.drawable.row_divider_3);
        rowDrawables[3] = context.getResources().getDrawable(R.drawable.row_divider_4);
        rowDrawables[4] = context.getResources().getDrawable(R.drawable.row_divider_5);

        columnDrawables[0] = context.getResources().getDrawable(R.drawable.row_divider_5);
        columnDrawables[1] = context.getResources().getDrawable(R.drawable.row_divider_4);
        columnDrawables[2] = context.getResources().getDrawable(R.drawable.row_divider_3);
        columnDrawables[3] = context.getResources().getDrawable(R.drawable.row_divider_2);
        columnDrawables[4] = context.getResources().getDrawable(R.drawable.row_divider_1);
    }


    @Override
    public int getRowDividerSize(int position) {
        // 根据position返回间隔的大小,Grid不推荐这样做，但是垂直和水平间隔可以设置不同大小
        return 32;
    }

    @Override
    public Drawable getRowDivider(int position) {
        // 根据position返回Drawable  可以为null
        return null;
    }

    @Override
    public int getColumnDividerSize(int position) {
        // 根据position返回间隔的大小,Grid不推荐这样做，但是垂直和水平间隔可以设置不同大小
        return 32;
    }

    @Override
    public Drawable getColumnDivider(int position) {
        // 根据position返回Drawable 可以为null
        return null;
    }
}
