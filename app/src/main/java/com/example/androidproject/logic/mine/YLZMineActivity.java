package com.example.androidproject.logic.mine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.example.androidproject.R;
import com.example.androidproject.base.extent.VectorBarExtent;
import com.example.androidproject.base.layoutManage.AutoSizeLayoutManager;
import com.example.androidproject.logic.home.model.RecyclerBean;
import com.example.androidproject.logic.mine.adapter.YLZMineActivityAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class YLZMineActivity extends AppCompatActivity {
    public Context mContext;
    private YLZMineActivityAdapter mAdapter;
    private RecyclerView recyclerView;
    private List<RecyclerBean> mDatas;
    private static final String TAG = "GroupedRecyclerViewActivity";
    @BindView(R.id.toolbarLeftImg)
    ImageView mToolbarLeftImg;
    @BindView(R.id.toolBarTitle)
    TextView mToolBarTitle;
    @BindView(R.id.toolBarRL)
    RelativeLayout mToolBarRL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_ylzmine,
                null);
        setContentView(view);
        ButterKnife.bind(this);

        VectorBarExtent.setToolStatusBarColor(this, view, R.color.ylzLightBlueView, false);
        VectorBarExtent.MIUISetStatusBarLightMode(this, false);
        VectorBarExtent.FlymeSetStatusBarLightMode(getWindow(), false);

        mToolBarRL.setBackgroundColor(getResources().getColor(R.color.ylzLightBlueView));
        mToolBarTitle.setText(R.string.mine_infos);
        mToolBarTitle.setTextColor(getResources().getColor(R.color.appWhite));
        mToolbarLeftImg.setImageDrawable(getResources().getDrawable(R.mipmap.icon_return_h32));
        mToolbarLeftImg.setBackground(getResources().getDrawable(R.drawable.selector_orangepress));

        this.initView();
        this.initDatas();
    }

    private void initView() {
        this.mDatas = new ArrayList<RecyclerBean>();
        mAdapter = new YLZMineActivityAdapter(mContext, this.mDatas);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        final AutoSizeLayoutManager layoutManager = new AutoSizeLayoutManager(mContext, LinearLayoutManager.VERTICAL,false);
        //设置RecyclerView布局
        recyclerView.setLayoutManager(layoutManager);
        mAdapter.setOnHeaderClickListener(new YLZMineActivityAdapter.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder, int groupPosition) {
                Toast.makeText(YLZMineActivity.this, "组头：groupPosition = " + groupPosition,
                        Toast.LENGTH_SHORT).show();
            }
        });
        mAdapter.setOnFooterClickListener(new YLZMineActivityAdapter.OnFooterClickListener() {
            @Override
            public void onFooterClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder, int groupPosition) {
                Toast.makeText(YLZMineActivity.this, "组尾：groupPosition = " + groupPosition,
                        Toast.LENGTH_SHORT).show();
            }
        });
        mAdapter.setOnChildClickListener(new YLZMineActivityAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder, int groupPosition, int childPosition) {
                Toast.makeText(YLZMineActivity.this, "子项：groupPosition = " + groupPosition
                                + ", childPosition = " + childPosition,
                        Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    private void initDatas() {
        RecyclerBean recyclerBean;
        for(int i = 0;i<2;i++){
            recyclerBean = new RecyclerBean(6,"我是描述========"+i,"我是电话号码"+i,"15717914505","oooooo");
            mDatas.add(recyclerBean);
        }
        mAdapter.setMDatas(mDatas);
    }

    @OnClick({R.id.toolbarLeftImg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbarLeftImg:
                finish();
                break;
            default:
                break;
        }
    }
}