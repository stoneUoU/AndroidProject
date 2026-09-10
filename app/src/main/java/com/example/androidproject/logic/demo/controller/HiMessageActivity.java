package com.example.androidproject.logic.demo.controller;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.donkingliang.groupedadapter.layoutmanger.GroupedGridLayoutManager;
import com.example.androidproject.R;
import com.example.androidproject.base.extent.VectorBarExtent;
import com.example.androidproject.logic.demo.adpter.HiMessageAdapter;
import com.example.androidproject.logic.message.model.YLZStyleListModel;
import com.example.androidproject.logic.message.model.YLZStyleModel;
import com.example.androidproject.logic.mine.adapter.YLZMineActivityAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HiMessageActivity extends AppCompatActivity {
    private HiMessageAdapter mAdapter;
    public Context mContext;
    public LayoutInflater mInflater;
    public List<YLZStyleListModel> styleModels;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private static final String TAG = "HiMessageActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mInflater = LayoutInflater.from(this);
        View view = mInflater.inflate(R.layout.activity_hi_message, null);
        setContentView(view);
        VectorBarExtent.setToolStatusBarColor(this,view,R.color.appWhite,true);
        ButterKnife.bind(this);

        this.styleModels = new ArrayList<>();
        initViews();
        initDatas();
    }

    private void initDatas() {
        for (int i = 0;i<7;i++) {
            YLZStyleListModel model;
            List<YLZStyleModel> list = new ArrayList();;
            model = new YLZStyleListModel(i,"i"+i,1,list);
            this.styleModels.add(model);
        }
        this.mAdapter.setMDatas(this.styleModels);
    }

    public void initViews() {
        this.mAdapter = new HiMessageAdapter(this.mContext,this.styleModels);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.mContext, LinearLayoutManager.VERTICAL,false);
        //设置RecyclerView布局
        GroupedGridLayoutManager gridLayoutManager = new GroupedGridLayoutManager(mContext, 1, this.mAdapter) {
            @Override
            public int getChildSpanSize(int groupPosition, int childPosition) {
                return 1;
            }
        };
//        thisrecyclerView.addItemDecoration(new HiGridItemDecoration(mContext));
        this.recyclerView.addItemDecoration(new HiMessageActivity.SpaceItemDecoration(32));
        this.recyclerView.setLayoutManager(gridLayoutManager);
        this.recyclerView.setAdapter(this.mAdapter);
        this.mAdapter.setOnHeaderClickListener(new YLZMineActivityAdapter.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder, int groupPosition) {
                Toast.makeText(mContext, "组头：groupPosition = " + groupPosition,
                        Toast.LENGTH_SHORT).show();
            }
        });
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