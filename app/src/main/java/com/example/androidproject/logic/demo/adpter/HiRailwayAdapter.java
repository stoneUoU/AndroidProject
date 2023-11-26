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
import com.example.androidproject.logic.mine.adapter.YLZBannerAdapter;
import com.example.androidproject.logic.mine.model.YLZModel;
import com.youth.banner.Banner;
import com.youth.banner.config.BannerConfig;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.util.BannerUtils;

import java.util.ArrayList;
import java.util.List;

public class HiRailwayAdapter extends GroupedRecyclerViewAdapter {

    private List<YLZStyleListModel> mDatas;
    public List<YLZModel> carouselDatas;
    public LayoutInflater mInflater;
    public Context mContext;
    private static final String TAG = "HiRailwayAdapter";

    public final int ITEM_BANNER = 0;
    public final int ITEM_HEALTH = 1;
    public final int ITEM_SHOP = 2;
    public final int ITEM_DETLA = 4;
    public final int HEADER_COMMON = 3;

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
        this.carouselDatas = new ArrayList<>();
        this.initData();
    }

    public void initData() {
        YLZModel model;
        for (int i = 0;i<4;i++) {
            if (i == 0) {
                model = new YLZModel(i,"美食博主一","https://cdn3-banquan.ituchong.com/weili/smh/914019154323308646.jpeg");
            } else if (i == 1) {
                model = new YLZModel(i,"不屑的小坦克","https://cdn9-banquan.ituchong.com/weili/smh/914195600169762838.jpeg");
            } else if (i == 2) {
                model = new YLZModel(i,"国家医保服务平台","https://cdn3-banquan.ituchong.com/weili/smh/913401323278172163.jpeg");
            } else {
                model = new YLZModel(i,"哈哈","https://cdn3-banquan.ituchong.com/weili/smh/913445166303936520.jpeg");
            }
            this.carouselDatas.add(model);
        }
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
            return 5;
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
    public int getHeaderViewType(int groupPosition){
        return HEADER_COMMON;
    }
    @Override
    public int getHeaderLayout(int viewType) { return R.layout.item_group_header; }

    @Override
    public int getFooterLayout(int viewType) {
        return 0;
    }

    @Override
    public int getChildViewType(int groupPosition, int childPosition) {
        if (groupPosition == 0) {
            return ITEM_BANNER;
        } else if (groupPosition == 1) {
            if (childPosition == 0) {
                return ITEM_DETLA;
            }
            return ITEM_HEALTH;
        } else {
            return ITEM_SHOP;
        }
    }

    @Override
    public int getChildLayout(int viewType) {
        if (viewType == ITEM_BANNER) {
            return R.layout.item_railway_banner;
        } else if (viewType == ITEM_HEALTH) {
            return R.layout.item_railway_func;
        } else if (viewType == ITEM_DETLA) {
            return R.layout.item_railway_detla;
        } else {
            return R.layout.item_railway_shop;
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
            Banner mBanner = holder.get(R.id.mBanner);
            mBanner.setAdapter(new YLZBannerAdapter(this.carouselDatas));
            mBanner.setIndicator(new CircleIndicator(mContext));
            mBanner.setIndicatorGravity(IndicatorConfig.Direction.RIGHT);
            mBanner.setIndicatorMargins(new IndicatorConfig.Margins(0, 0,
                    BannerConfig.INDICATOR_MARGIN, BannerUtils.dp2px(12)));
            mBanner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(Object data, int position) {
                }
            });
        } else if (groupPosition == ITEM_HEALTH)  {
//            View showButton = (View) holder.get(R.id.bannner);
//            showButton.setOnClickListener(v -> {
//                if (this.healthCodeInfoClickListener != null) {
//                    this.healthCodeInfoClickListener.onExcute("ITEM_HEALTH");
//                }
//            });
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
