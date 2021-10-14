package com.example.androidproject.logic.healthcode.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.example.androidproject.R;
import com.example.androidproject.logic.healthcode.controller.YLZHealthCodeActivity;
import com.example.androidproject.logic.message.model.YLZStyleListModel;

import java.lang.ref.WeakReference;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class YLZHealthCodeServiceAdapter extends GroupedRecyclerViewAdapter {

    private List<String> mDatas;
    public LayoutInflater mInflater;
    public Context mContext;
    private static final String TAG = "YLZHealthCodeServiceAdapter";

    public YLZHealthCodeServiceAdapter(Context context, List<String> mDatas) {
        super(context);
        this.mContext = context;
        this.mDatas = mDatas;
        this.mInflater = LayoutInflater.from(mContext);
    }
    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.mDatas.size();
    }

    @Override
    public boolean hasHeader(int groupPosition) {
        return false;
    }

    @Override
    public int getHeaderLayout(int viewType) { return 0; }

    @Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition) {
    }

    @Override
    public boolean hasFooter(int groupPosition) {
        return false;
    }
    @Override
    public int getFooterLayout(int viewType) {
        return 0;
    }
    @Override
    public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition) {
    }

    //设置内容：
    @Override
    public int getChildLayout(int viewType) {
        return R.layout.item_health_code_service_project;
    }
    @Override
    public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {
        TextView serviceTv = holder.get(R.id.serviceTv);
        serviceTv.setText(this.mDatas.get(childPosition));
    }

    public void setMDatas(List<String> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }
}
