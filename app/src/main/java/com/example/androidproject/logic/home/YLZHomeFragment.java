package com.example.androidproject.logic.home;

import android.app.Activity;
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
import android.widget.TextView;

import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.donkingliang.groupedadapter.layoutmanger.GroupedGridLayoutManager;
import com.example.androidproject.R;
import com.example.androidproject.base.extent.YLZToast;
import com.example.androidproject.base.extent.VectorBarExtent;
import com.example.androidproject.logic.login.YLZQuickLoginActivity;
import com.example.androidproject.logic.home.adapter.YLZHomeFragmentAdapter;
import com.example.androidproject.logic.mine.model.YLZModel;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

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
 * Use the {@link YLZHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YLZHomeFragment extends Fragment {

    public Context mContext;

    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.toolBarTitle)
    TextView toolBarTitle;

    private YLZHomeFragmentAdapter mAdapter;
    public List<YLZModel> brustDatas;
    public List<YLZModel> preSellDatas;
    public List<YLZModel> carouselDatas;

    private YLZHomeFragment.YLZHandler mH = new YLZHomeFragment.YLZHandler(this);

    public YLZHomeFragment() {
    }

    public static YLZHomeFragment newInstance(String param1, String param2) {
        YLZHomeFragment fragment = new YLZHomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);
        mContext = getContext();

        this.initViews(view);
        this.initData();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //Tab切换为当前页时刷新状态栏(ViewPager模式下切换时回调;view可能尚未创建,需判空)
        if (isVisibleToUser && isAdded() && getView() != null) {
            this.setupStatusBar();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //首次创建/从其他页面返回时,只有当前可见页刷新状态栏,避免多Tab互相覆盖
        if (isAdded() && getUserVisibleHint()) {
            this.setupStatusBar();
        }
    }

    /**
     * 状态栏颜色与页面白色标题栏一致,黑色状态栏字体(需通过宿主Activity设置)
     */
    private void setupStatusBar() {
        Activity activity = getActivity();
        if (activity != null) {
            VectorBarExtent.setStatusBarStyle(activity, getView(), R.color.appWhite, true);
        }
    }

    public void initData() {
        YLZModel model;
        for (int i = 0;i<4;i++) {
            if (i == 0) {
                model = new YLZModel(i,"用户界面的基础结构。理解和使用不同的布局类型对于创建响应式和用户友好的界面至关重要","https://js.app.meitudata.com/corp-new/image/talents/earth.@b2485c95.png");
            } else if (i == 1) {
                model = new YLZModel(i,"不屑的小坦克","https://js.app.meitudata.com/corp-new/image/talents/real.@d03582dd.png");
            } else if (i == 2) {
                model = new YLZModel(i,"国家医保服务平台","https://js.app.meitudata.com/corp-new/image/talents/fire.@048f4693.png");
            } else {
                model = new YLZModel(i,"用户界面的基础结构。理解和使用不同的布局类型对于创建响应式和用户友好的界面至关重要","https://js.app.meitudata.com/corp-new/image/talents/rocket.@e5538365.png");
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

        mAdapter = new YLZHomeFragmentAdapter(mContext, brustDatas , preSellDatas,carouselDatas);
        GroupedGridLayoutManager gridLayoutManager = new GroupedGridLayoutManager(mContext, 2, mAdapter){
            @Override
            public int getChildSpanSize(int groupPosition, int childPosition) {
                return super.getChildSpanSize(groupPosition, childPosition);
            }
        };
        //设置RecyclerView布局
        recyclerView.addItemDecoration(new SpaceItemDecoration(24));
        recyclerView.setLayoutManager(gridLayoutManager);
        mAdapter.setOnHeaderClickListener(new YLZHomeFragmentAdapter.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder, int groupPosition) {
                YLZToast.showShortToast(mContext,"Header：groupPosition = " + groupPosition);
            }
        });

        mAdapter.setOnChildClickListener(new YLZHomeFragmentAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder, int groupPosition, int childPosition) {
                YLZToast.showShortToast(mContext,"Child：groupPosition = " + groupPosition + " childPosition = " + childPosition);
                start(mContext);
            }
        });
        mAdapter.setCarouselCallBack(new YLZHomeFragmentAdapter.CarouselCallBack() {
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

    public static void start(Context context) {
        Intent intent = new Intent(context, YLZQuickLoginActivity.class);
        context.startActivity(intent);
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
        private WeakReference<YLZHomeFragment> mReference;
        public YLZHandler(YLZHomeFragment fragment) {
            mReference = new WeakReference<YLZHomeFragment>(fragment);
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