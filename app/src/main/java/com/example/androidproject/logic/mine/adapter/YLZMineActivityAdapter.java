package com.example.androidproject.logic.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.example.androidproject.R;
import com.example.androidproject.logic.home.model.RecyclerBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YLZMineActivityAdapter extends GroupedRecyclerViewAdapter {
    private List<RecyclerBean> mDatas;
    public LayoutInflater mInflater;
    public Context mContext;
    public YLZMineActivityAdapter(Context context,List<RecyclerBean> mDatas) {
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
        return 2;
    }

    @Override
    public boolean hasHeader(int groupPosition) {
        return true;
    }

    @Override
    public boolean hasFooter(int groupPosition) {
        return true;
    }

    @Override
    public int getHeaderLayout(int viewType) { return R.layout.item_group_header; }

    @Override
    public int getFooterLayout(int viewType) {
        return R.layout.item_group_footer;
    }

    @Override
    public int getChildLayout(int viewType) {
        return R.layout.item_right_recycler_view;
    }

    @Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition) {
        holder.setText(R.id.emptyTV, "我是头视图（headerView）");
    }

    @Override
    public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition) {
        holder.setText(R.id.emptyTV, "我是尾视图（headerView）");
    }

    @Override
    public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {
        holder.setText(R.id.text1, "我是子视图（childView）");
    }
    public void setMDatas(List<RecyclerBean> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }
}
