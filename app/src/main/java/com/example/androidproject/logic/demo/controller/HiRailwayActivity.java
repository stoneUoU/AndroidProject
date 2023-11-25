package com.example.androidproject.logic.demo.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.donkingliang.groupedadapter.layoutmanger.GroupedGridLayoutManager;
import com.example.androidproject.R;
import com.example.androidproject.base.extent.MeasureExtent;
import com.example.androidproject.base.extent.VectorBarExtent;
import com.example.androidproject.logic.demo.adpter.HiRailwayAdapter;
import com.example.androidproject.logic.message.model.YLZStyleListModel;
import com.example.androidproject.logic.message.model.YLZStyleModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HiRailwayActivity extends AppCompatActivity {
    private HiRailwayAdapter mAdapter;
    public Context mContext;
    public LayoutInflater mInflater;
    public List<YLZStyleListModel> styleModels;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private static final String TAG = "HiRailwayActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mInflater = LayoutInflater.from(this);
        View view = mInflater.inflate(R.layout.activity_hi_railway, null);
        setContentView(view);
        VectorBarExtent.setToolStatusBarColor(this,view,R.color.appWhite,true);
        ButterKnife.bind(this);

        this.styleModels = new ArrayList<>();
        initViews();
        initDatas();
    }

    private void initDatas() {
        for (int i = 0;i<3;i++) {
            YLZStyleListModel model;
            List<YLZStyleModel> list = new ArrayList();;
            model = new YLZStyleListModel(i,"i"+i,1,list);
            this.styleModels.add(model);
        }
        this.mAdapter.setMDatas(this.styleModels);
    }

    public void initViews() {
        this.mAdapter = new HiRailwayAdapter(this.mContext,this.styleModels);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.mContext, LinearLayoutManager.VERTICAL,false);
        //设置RecyclerView布局
        GroupedGridLayoutManager gridLayoutManager = new GroupedGridLayoutManager(mContext, 3, this.mAdapter) {
            @Override
            public int getChildSpanSize(int groupPosition, int childPosition) {
                if(groupPosition == 0){
                    return 3;
                }
                return super.getChildSpanSize(groupPosition, childPosition);
            }
        };
        this.recyclerView.setLayoutManager(gridLayoutManager);
        this.recyclerView.addItemDecoration(new HiRailwayActivity.SpaceItemDecoration(10));
        this.recyclerView.setAdapter(this.mAdapter);

        this.mAdapter.setOnItemClickListener((String str) -> {
            Log.d(TAG, "this.mAdapter.setOnItemClickListener:"+str);
        });
    }

    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        private int space;
        public SpaceItemDecoration(int space) {
            this.space = space;
        }
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int viewType =  parent.getAdapter().getItemViewType(parent.getChildLayoutPosition(view));
            outRect.top = MeasureExtent.px2dip(mContext,0);
            outRect.bottom = MeasureExtent.px2dip(mContext,0);
        }
    }
}