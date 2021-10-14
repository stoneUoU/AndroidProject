package com.example.androidproject.logic.healthcode.controller;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.example.androidproject.R;
import com.example.androidproject.base.extent.MeasureExtent;
import com.example.androidproject.base.extent.VectorBarExtent;
import com.example.androidproject.base.layoutManage.AutoSizeLayoutManager;
import com.example.androidproject.logic.healthcode.adapter.YLZHealthCodeAdapter;
import com.example.androidproject.logic.message.model.YLZStyleListModel;
import com.example.androidproject.logic.message.model.YLZStyleModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YLZHealthCodeActivity extends AppCompatActivity implements View.OnClickListener {

    private YLZHealthCodeAdapter mAdapter;
    public Context mContext;
    public LayoutInflater mInflater;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.more)
    Button moreButton;

    @BindView(R.id.shut)
    Button shutButton;

    public List<YLZStyleListModel> styleModels;
    private static final String TAG = "YLZHealthCodeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.activity_ylzhealthcode, null);
        setContentView(view);
        VectorBarExtent.setToolStatusBarColor(this,view,R.color.appWhite,true);
        ButterKnife.bind(this);

        this.styleModels = new ArrayList<>();
        this.initViews();
        this.initDatas();
    }

    private void initDatas() {
        for (int i = 0;i<5;i++) {
            YLZStyleListModel model;
            List<YLZStyleModel> list = new ArrayList<YLZStyleModel>();;
            model = new YLZStyleListModel(i,"i"+i,1,list);
            this.styleModels.add(model);
        }
        this.mAdapter.setMDatas(this.styleModels);
    }

    public void initViews() {
        this.mAdapter = new YLZHealthCodeAdapter(this.mContext,this.styleModels);
        final AutoSizeLayoutManager layoutManager = new AutoSizeLayoutManager(this.mContext, LinearLayoutManager.VERTICAL,false);
        //设置RecyclerView布局
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.addItemDecoration(new YLZHealthCodeActivity.SpaceItemDecoration(30));
        this.recyclerView.setAdapter(this.mAdapter);
        this.mAdapter.setOnItemClickListener(() -> {
            mAdapter.clickNum = mAdapter.clickNum+1;
            if (mAdapter.clickNum>2) {
                mAdapter.clickNum = 0;
            }
            mAdapter.setMDatas(styleModels);
        });

        this.moreButton.setOnClickListener(this);
        this.shutButton.setOnClickListener(this);
    }

    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        private int space;
        public SpaceItemDecoration(int space) {
            this.space = space;
        }
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int viewType =  parent.getAdapter().getItemViewType(parent.getChildLayoutPosition(view));
            outRect.top = MeasureExtent.px2dip(mContext,15);
            outRect.bottom = MeasureExtent.px2dip(mContext,30);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.more:
                Log.d(TAG, "onClick: 哈哈哈----more");
                break;
            case R.id.shut:
                Log.d(TAG, "onClick: 哈哈哈----shut");
                break;
        }
    }
}