package com.example.androidproject.logic.demo.adpter;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.example.androidproject.R;
import com.example.androidproject.base.extent.MeasureExtent;
import com.example.androidproject.logic.healthcode.adapter.YLZHealthCodeAdapter;
import com.example.androidproject.logic.message.model.YLZStyleListModel;

import java.util.List;

public class HiRailwayAdapter extends GroupedRecyclerViewAdapter {

    private List<YLZStyleListModel> mDatas;
    public LayoutInflater mInflater;
    public Context mContext;
    private static final String TAG = "HiRailwayAdapter";

    public final int ITEM_BANNER = 0;
    public final int ITEM_HEALTH = 1;
    public final int ITEM_SHOP = 2;

    public  interface OnItemHealthCodeInfoClickListener {
        void onExcute(String str);
    }

    private HiRailwayAdapter.OnItemHealthCodeInfoClickListener healthCodeInfoClickListener;

    public  void setOnItemClickListener(HiRailwayAdapter.OnItemHealthCodeInfoClickListener listener){
        this.healthCodeInfoClickListener = listener;
    }

    public HiRailwayAdapter(Context context,List<YLZStyleListModel> mDatas) {
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
        if (groupPosition == ITEM_BANNER) {
            return 1;
        } if (groupPosition == ITEM_HEALTH)  {
            return 2;
        } else {
            return 3;
        }
    }

    @Override
    public boolean hasHeader(int groupPosition) {
        return true;
    }

    @Override
    public boolean hasFooter(int groupPosition) {
        return false;
    }

    @Override
    public int getHeaderLayout(int viewType) { return R.layout.item_group_header; }

    @Override
    public int getFooterLayout(int viewType) {
        return 0;
    }

    @Override
    public int getChildLayout(int viewType) {
        if (viewType == ITEM_BANNER) {
            return R.layout.item_railway_banner;
        } else if (viewType == ITEM_HEALTH) {
            return R.layout.item_railway_health;
        } else {
            return R.layout.item_railway_shop;
        }
    }

    @Override
    public int getChildViewType(int groupPosition, int childPosition) {
        if (groupPosition == 0) {
            return ITEM_BANNER;
        } else if (groupPosition == 1) {
            return ITEM_HEALTH;
        } else {
            return ITEM_SHOP;
        }
    }

    @Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition) {

    }

    @Override
    public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition) {

    }

    @Override
    public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {
        if (groupPosition == ITEM_BANNER) {
            View showButton = (View) holder.get(R.id.bannner);
            showButton.setOnClickListener(v -> {
                if (this.healthCodeInfoClickListener != null) {
                    this.healthCodeInfoClickListener.onExcute("ITEM_BANNER");
                }
            });
        } else if (groupPosition == ITEM_HEALTH)  {
            View showButton = (View) holder.get(R.id.bannner);
            showButton.setOnClickListener(v -> {
                if (this.healthCodeInfoClickListener != null) {
                    this.healthCodeInfoClickListener.onExcute("ITEM_HEALTH");
                }
            });
        } else{
            View showButton = (View) holder.get(R.id.bannner);
            showButton.setOnClickListener(v -> {
                if (this.healthCodeInfoClickListener != null) {
                    this.healthCodeInfoClickListener.onExcute("ITEM_SHOP");
                }
            });
        }
    }

    public void setMDatas(List<YLZStyleListModel> datas) {
        mDatas = datas;
        Log.d(TAG, "setMDatas: "+ datas);
        notifyDataSetChanged();
    }
}
