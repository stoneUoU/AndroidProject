package com.example.androidproject.logic.message.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.example.androidproject.R;
import com.example.androidproject.logic.message.model.YLZStyleListModel;
import com.example.androidproject.logic.message.model.YLZStyleModel;

import java.util.List;

public class YLZMessageFragmentAdapter extends GroupedRecyclerViewAdapter {
    private List<YLZStyleListModel> mDatas;
    public LayoutInflater mInflater;
    public Context mContext;

    public final int ITEM_HEALTH_INFO = 0;
    public final int ITEM_HEALTH = 1;
    public final int ITEM_HEALTH_CHECK = 2;
    public final int ITEM_HEALTH_SERVICE = 3;
    public final int ITEM_HEALTH_SOURCE = 4;

    public YLZMessageFragmentAdapter(Context context,List<YLZStyleListModel> mDatas) {
        super(context);
        this.mContext = context;
        this.mDatas = mDatas;
        this.mInflater = LayoutInflater.from(mContext);
    }
    @Override
    public int getGroupCount() {
        return this.mDatas.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public boolean hasHeader(int groupPosition) {
        return false;
    }

    @Override
    public int getHeaderLayout(int viewType) { return R.layout.item_group_header; }

    @Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition) {
    }

    //设置尾部：
    @Override
    public boolean hasFooter(int groupPosition) {
        return true;
    }
    @Override
    public int getFooterLayout(int viewType) {
        if (viewType == ITEM_HEALTH_SOURCE) {
            return R.layout.item_health_special_footer;
        } else {
            return R.layout.item_health_normal_footer;
        }
    }
    @Override
    public int getFooterViewType(int groupPosition) {
        if (groupPosition == 0) {
            return ITEM_HEALTH_INFO;
        } else if (groupPosition == 1) {
            return ITEM_HEALTH;
        } else if (groupPosition == 2) {
            return ITEM_HEALTH_CHECK;
        } else if (groupPosition == 3) {
            return ITEM_HEALTH_SERVICE;
        } else {
            return ITEM_HEALTH_SOURCE;
        }
    }
    @Override
    public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition) {
    }

    //设置内容：
    @Override
    public int getChildLayout(int viewType) {
        if (viewType == ITEM_HEALTH_INFO) {
            return R.layout.item_health_code_info;
        } else if (viewType == ITEM_HEALTH) {
            return R.layout.item_health_code;
        } else if (viewType == ITEM_HEALTH_CHECK) {
            return R.layout.item_health_code_check;
        } else if (viewType == ITEM_HEALTH_SERVICE) {
            return R.layout.item_health_code_service;
        } else {
            return R.layout.item_health_code_source;
        }
    }
    @Override
    public int getChildViewType(int groupPosition, int childPosition) {
        if (groupPosition == 0) {
            return ITEM_HEALTH_INFO;
        } else if (groupPosition == 1) {
            return ITEM_HEALTH;
        } else if (groupPosition == 2) {
            return ITEM_HEALTH_CHECK;
        } else if (groupPosition == 3) {
            return ITEM_HEALTH_SERVICE;
        } else {
            return ITEM_HEALTH_SOURCE;
        }
    }
    @Override
    public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {

    }

    public void setMDatas(List<YLZStyleListModel> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }
}
