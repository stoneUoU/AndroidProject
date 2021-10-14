package com.example.androidproject.logic.mine.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.example.androidproject.R;
import com.example.androidproject.base.extent.YLZToast;
import com.example.androidproject.base.view.YLZRoundImageView;
import com.example.androidproject.logic.mine.model.YLZModel;
import com.youth.banner.Banner;
import com.youth.banner.config.BannerConfig;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.indicator.RectangleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.util.BannerUtils;

import java.util.ArrayList;
import java.util.List;

public class YLZMineFragmentAdapter extends GroupedRecyclerViewAdapter {

    public List<YLZModel> brustDatas;
    public List<YLZModel> preSellDatas;
    public List<YLZModel> carouselDatas;
    private static final String TAG = "YLZMineFragmentAdapter";

    private Context mContext;

    public final int ITEM_PRESELL = 0;
    public final int ITEM_BRUST = 1;

    public final int HEADER_BANNER = 2;
    public final int HEADER_COMMON = 3;

    public CarouselCallBack carouselCallBack;//回调接口类

    public interface CarouselCallBack {
        public void clickIdx(int pos);
    }

    public void setCarouselCallBack(CarouselCallBack callBackListener) {
        this.carouselCallBack = callBackListener;
    }

    public YLZMineFragmentAdapter(Context context,List<YLZModel> brustDatas,List<YLZModel> preSellDatas,List<YLZModel> carouselDatas) {
        super(context);
        this.mContext = context;
        this.brustDatas = brustDatas;
        this.preSellDatas = preSellDatas;
        this.carouselDatas = carouselDatas;
    }

    @Override
    public int getGroupCount() {
        return 2;
    }

    @Override
    public int getChildrenCount(int groupPosi) {
        if(groupPosi == 0){
            return preSellDatas.size();
        }else {
            return brustDatas.size();
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
    public int getHeaderLayout(int viewType) {
        if(viewType == HEADER_BANNER){
            return R.layout.header_for_banner_market;
        }else{
            return R.layout.header_for_common_market;
        }
    }

    @Override
    public int getFooterLayout(int viewType) {
        return R.layout.footer_for_market;
    }

    @Override
    public int getChildLayout(int viewType) {
        if(viewType == ITEM_PRESELL){
            return R.layout.item_for_presell_market;
        }else{
            return R.layout.item_for_brust_market;
        }
    }
    @Override
    public int getHeaderViewType(int groupPosition){
        if(groupPosition == 0){
            return HEADER_BANNER;
        }else{
            return HEADER_COMMON;
        }
    }
    @Override
    public int getChildViewType(int groupPosition, int childPosition) {
        if(groupPosition == 0){
            return ITEM_PRESELL;
        }else{
            return ITEM_BRUST;
        }
    }
    @Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition) {
        if(groupPosition == 0){
            Banner mBanner = holder.get(R.id.mBanner);
            mBanner.setAdapter(new YLZBannerAdapter(this.carouselDatas));
            mBanner.setIndicator(new CircleIndicator(mContext));
            mBanner.setIndicatorGravity(IndicatorConfig.Direction.RIGHT);
            mBanner.setIndicatorMargins(new IndicatorConfig.Margins(0, 0,
                    BannerConfig.INDICATOR_MARGIN, BannerUtils.dp2px(12)));
            mBanner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(Object data, int position) {
                    if(carouselCallBack!=null) {
                        carouselCallBack.clickIdx(position);
                    }
                }
            });
            holder.setText(R.id.headerTv, "预售清单");
        }else{
            holder.setText(R.id.headerTv, "当日爆款");
        }
    }

    @Override
    public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition) {
        holder.setText(R.id.emptyTV, "我是尾视图（headerView）");
    }

    @Override
    public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {
        final YLZModel brustItem;
        final YLZModel preSellItem;
        if (groupPosition == 0) {
            preSellItem = preSellDatas.get(childPosition);
            holder.setText(R.id.nameTv, preSellItem.getTitle()).setText(R.id.presellTv, "预售").setText(R.id.endTimeTv, preSellItem.getTitle()).setText(R.id.priceTv, "￥ "+"30.00");
            YLZRoundImageView IMV = (YLZRoundImageView) holder.get(R.id.imageView);
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.NONE);
            Glide.with(mContext).load(preSellItem.getPic_path()).apply(options).into(IMV);
        } else {
            brustItem = brustDatas.get(childPosition);
            holder.setText(R.id.nameTv, brustItem.getTitle()).setText(R.id.priceTv, "￥ "+"30.00");
            YLZRoundImageView IMV = (YLZRoundImageView) holder.get(R.id.imageView);
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.NONE);
            Glide.with(mContext).load(brustItem.getPic_path()).apply(options).into(IMV);
        }
    }

    public void setMDatas(List<YLZModel> preSellDatas,List<YLZModel> brustDatas,List<YLZModel> carouselDatas) {
        this.carouselDatas = carouselDatas;
        this.brustDatas = brustDatas;
        this.preSellDatas = preSellDatas;
        notifyDataSetChanged();
    }
}
