package com.example.androidproject.logic.demo.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.donkingliang.groupedadapter.layoutmanger.GroupedGridLayoutManager;
import com.donkingliang.varieditemdecoration.GridItemDecoration;
import com.example.androidproject.R;
import com.example.androidproject.base.activity.YLZTabBarActivity;
import com.example.androidproject.base.extent.MeasureExtent;
import com.example.androidproject.base.extent.VectorBarExtent;
import com.example.androidproject.logic.demo.HiGridItemDecoration;
import com.example.androidproject.logic.demo.adpter.HiRailwayAdapter;
import com.example.androidproject.logic.login.YLZLoginActivity;
import com.example.androidproject.logic.message.model.YLZStyleListModel;
import com.example.androidproject.logic.message.model.YLZStyleModel;
import com.example.androidproject.logic.mine.YLZMineActivity;
import com.example.androidproject.logic.mine.YLZMineFragment;
import com.example.androidproject.logic.mine.adapter.YLZMineActivityAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HiRailwayActivity extends AppCompatActivity implements View.OnClickListener {
    private HiRailwayAdapter mAdapter;
    public Context mContext;
    public LayoutInflater mInflater;
    public List<YLZStyleListModel> styleModels;

    @BindView(R.id.logoIMV)
    ImageView logoIMV;

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
        this.initClickListeners();

        this.styleModels = new ArrayList<>();
        initViews();
        initDatas();
    }

    public void initClickListeners() {
        this.logoIMV.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logoIMV:
                Intent intent = new Intent(HiRailwayActivity.this, YLZLoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initDatas() {
        for (int i = 0;i<4;i++) {
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
        GroupedGridLayoutManager gridLayoutManager = new GroupedGridLayoutManager(mContext, 12, this.mAdapter) {
            @Override
            public int getChildSpanSize(int groupPosition, int childPosition) {
                if (groupPosition == 0) {
                    return 12;
                } else if (groupPosition == 1) {
                    return 6;
                } else if (groupPosition == 2) {
                    return 4;
                } else {
                    return 3;
                }
            }
        };
//        thisrecyclerView.addItemDecoration(new HiGridItemDecoration(mContext));
        this.recyclerView.addItemDecoration(new HiRailwayActivity.SpaceItemDecoration(32));
        this.recyclerView.setLayoutManager(gridLayoutManager);
        this.recyclerView.setAdapter(this.mAdapter);
//        this.mAdapter.setOnHeaderClickListener((GroupedRecyclerViewAdapter adapter, BaseViewHolder holder, int groupPosition)-> {
//            Log.d(TAG, "initViews: "+ groupPosition);
//        });
        this.mAdapter.setOnHeaderClickListener(new YLZMineActivityAdapter.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder, int groupPosition) {
                Toast.makeText(mContext, "组头：groupPosition = " + groupPosition,
                        Toast.LENGTH_SHORT).show();
            }
        });
//        this.mAdapter.setOnItemClickListener((String str) -> {
//            Toast.makeText(mContext, "this.mAdapter.setOnItemClickListener:"+str,
//                    Toast.LENGTH_SHORT).show();
//        });
        this.mAdapter.setClickListener(new HiRailwayAdapter.OnItemClickListener() {
            @Override
            public void onExcuteHealth(String str) {
                Toast.makeText(mContext, "this.mAdapter.setOnItemClickListener:"+str,
                    Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onExcuteDetla(String str) {
                Toast.makeText(mContext, "this.mAdapter.setOnItemClickListener:"+str,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onExcuteShop(String str) {
                Toast.makeText(mContext, "this.mAdapter.setOnItemClickListener:"+str,
                        Toast.LENGTH_SHORT).show();
            }
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
            if (viewType == mAdapter.HEADER_COMMON) {
                outRect.left = 0;
            } else {
                outRect.top = space;
                outRect.left = space;
                outRect.right = space;
                outRect.bottom = space;
            }
        }
    }
}