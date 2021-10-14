package com.example.androidproject.logic.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.donkingliang.groupedadapter.layoutmanger.GroupedGridLayoutManager;
import com.example.androidproject.R;
import com.example.androidproject.base.extent.YLZToast;
import com.example.androidproject.logic.home.YLZHomeActivity;
import com.example.androidproject.logic.home.model.RecyclerBean;
import com.example.androidproject.logic.mine.adapter.YLZMineFragmentAdapter;
import com.example.androidproject.logic.mine.model.YLZModel;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YLZMineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YLZMineFragment extends Fragment {

    public Context mContext;

    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.toolBarTitle)
    TextView toolBarTitle;

    private YLZMineFragmentAdapter mAdapter;
    public List<YLZModel> brustDatas;
    public List<YLZModel> preSellDatas;
    public List<YLZModel> carouselDatas;

    private YLZMineFragment.YLZHandler mH = new YLZMineFragment.YLZHandler(this);

    public YLZMineFragment() {
    }

    public static YLZMineFragment newInstance(String param1, String param2) {
        YLZMineFragment fragment = new YLZMineFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this,view);
        mContext = getContext();

        this.initViews(view);
        this.initData();
        return view;
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

            this.preSellDatas.add(model);
            this.brustDatas.add(model);
            this.carouselDatas.add(model);
        }
        this.mAdapter.setMDatas(this.preSellDatas, this.brustDatas,this.carouselDatas);
    }

    public void initViews(View view) {

        brustDatas = new ArrayList<>();
        preSellDatas = new ArrayList<>();
        carouselDatas = new ArrayList<>();

        mAdapter = new YLZMineFragmentAdapter(mContext, brustDatas , preSellDatas,carouselDatas);
        GroupedGridLayoutManager gridLayoutManager = new GroupedGridLayoutManager(mContext, 2, mAdapter){
            @Override
            public int getChildSpanSize(int groupPosition, int childPosition) {
                return super.getChildSpanSize(groupPosition, childPosition);
            }
        };
        //设置RecyclerView布局
        recyclerView.addItemDecoration(new SpaceItemDecoration(30));
        recyclerView.setLayoutManager(gridLayoutManager);
        mAdapter.setOnHeaderClickListener(new YLZMineFragmentAdapter.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder, int groupPosition) {
                YLZToast.showShortToast(mContext,"Header：groupPosition = " + groupPosition);
            }
        });

        mAdapter.setOnChildClickListener(new YLZMineFragmentAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder, int groupPosition, int childPosition) {
                YLZToast.showShortToast(mContext,"Child：groupPosition = " + groupPosition);
            }
        });
        mAdapter.setCarouselCallBack(new YLZMineFragmentAdapter.CarouselCallBack() {
            @Override
            public void clickIdx(int pos) {
                final YLZModel carouselItem;
                carouselItem = carouselDatas.get(pos);
                YLZToast.showShortToast(mContext,"Banner = " + carouselItem.getPic_path());
            }
        });
        recyclerView.setAdapter(mAdapter);
        //设置 Header 为 贝塞尔雷达 样式
        refreshLayout.setRefreshHeader(new BezierRadarHeader(mContext).setEnableHorizontalDrag(true));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshLayout.finishRefresh(2000);
            }
        });
    }

    @OnClick({R.id.toolBarTitle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolBarTitle:
                YLZToast.showShortToast(mContext, "被点击了");
                break;
        }
    }

    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        private int space;
        public SpaceItemDecoration(int space) {
            this.space = space;
        }
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int viewType =  parent.getAdapter().getItemViewType(parent.getChildLayoutPosition(view));
            if(viewType==mAdapter.HEADER_BANNER || viewType==mAdapter.HEADER_COMMON ){
                outRect.left = 0;
            }else{
                outRect.left = space;
                outRect.right = space;
            }
        }
    }
    @Override
    public void onAttach(Context context) {super.onAttach(context); }
    @Override
    public void onDetach() {
        super.onDetach();
    }
    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public void onStop() {
        super.onStop();
    }

    private static class YLZHandler extends Handler {
        private WeakReference<YLZMineFragment> mReference;
        public YLZHandler(YLZMineFragment fragment) {
            mReference = new WeakReference<YLZMineFragment>(fragment);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    try {
                        String responseStr = (String) msg.obj;
                        JSONObject responseJSON = new JSONObject(responseStr);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    }
}