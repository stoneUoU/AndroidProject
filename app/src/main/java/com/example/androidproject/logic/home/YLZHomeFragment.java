package com.example.androidproject.logic.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidproject.R;
import com.example.androidproject.base.extent.APIServices;
import com.example.androidproject.base.extent.MeasureExtent;
import com.example.androidproject.base.extent.VectorBarExtent;
import com.example.androidproject.base.layoutManage.AutoSizeLayoutManager;
import com.example.androidproject.logic.home.adapter.YLZHomeFragmentAdapter;
import com.example.androidproject.logic.home.model.YLZTopListModel;
import com.example.androidproject.logic.login.YLZQuickLoginActivity;
import com.example.androidproject.logic.mine.YLZMineActivity;
import com.example.androidproject.logic.mine.YLZMineFragment;
import com.google.gson.Gson;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YLZHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YLZHomeFragment extends Fragment implements YLZHomeFragmentAdapter.OnItemClickListener {

    private YLZHomeFragmentAdapter mAdapter;
    public Context mContext;
    private List<YLZTopListModel> mDatas;

//    private final String HotSearchAPI = "/provide/news";
//    private YLZHomeFragment.YLZHandler mHandler = new YLZHomeFragment.YLZHandler(this);
    private static final String TAG = "YLZHomeFragment";

    @BindView(R.id.toolBarTitle)
    TextView mToolBarTitle;
    @BindView(R.id.toolBarRL)
    RelativeLayout mToolBarRL;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    public YLZHomeFragment() {
    }
    // TODO: Rename and change types and number of parameters
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
        View view = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this,view);
        mContext = getContext();
        this.mDatas =  new ArrayList<YLZTopListModel>();

        this.initViews(view);
        this.initJson();

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
     * 状态栏颜色与ToolBar一致,并适配MIUI/Flyme状态栏字体颜色(需通过宿主Activity设置)
     */
    private void setupStatusBar() {
        Activity activity = getActivity();
        if (activity != null) {
            VectorBarExtent.setStatusBarStyle(activity, getView(), R.color.ylzLightBlueView, false);
        }
    }

    public void initViews(View view) {

        mToolBarRL.setBackgroundColor(getResources().getColor(R.color.ylzLightBlueView));
        mToolBarTitle.setText(R.string.app_news_title);
        mToolBarTitle.setTextColor(getResources().getColor(R.color.appWhite));

        this.mAdapter = new YLZHomeFragmentAdapter(this.mContext,this.mDatas);
        this.mAdapter.setOnItemClickListener(this);
        final AutoSizeLayoutManager layoutManager = new AutoSizeLayoutManager(this.mContext, LinearLayoutManager.VERTICAL,false);
        //设置RecyclerView布局
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.addItemDecoration(new YLZHomeFragment.SpaceItemDecoration(30));
        this.recyclerView.setAdapter(this.mAdapter);

        //设置 Header 为 贝塞尔雷达 样式
        refreshLayout.setRefreshHeader(new BezierRadarHeader(mContext).setEnableHorizontalDrag(true));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshLayout.finishRefresh(2000);
            }
        });
    }

    @Override
    public void onItemClick(View view, int posi,int ids) {
        if (posi == 0) {
            Intent intent = new Intent(mContext,YLZHomeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("name","iOS开发工程师");
            bundle.putString("age","19");
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (posi == 1) {
            Intent intent = new Intent(mContext, YLZQuickLoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(mContext, YLZMineActivity.class);
            startActivity(intent);
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
            outRect.top = MeasureExtent.px2dip(mContext,15);
            outRect.bottom = MeasureExtent.px2dip(mContext,30);
        }
    }

    void initJson() {
        String responseStr = "{\"code\":1,\"data\":{\"list\":[{\"title\":\"11月24日，塞尔维亚前总统武契奇在电视节目中表示，其于11月22日收到极其严重的死亡威胁。\",\"add_time\":\"2026-11-18 09:00:00\",\"imgs\":[\"https://js.app.meitudata.com/corp-new/image/talents/heart.@a83509f7.png\",\"https://js.app.meitudata.com/corp-new/image/talents/rocket.@e5538365.png\",\"https://js.app.meitudata.com/corp-new/image/talents/earth.@b2485c95.png\"]},{\"title\":\"特朗普:期待同习主席、普京总统会晤\",\"add_time\":\"2026-11-18 09:00:00\",\"imgs\":[\"https://js.app.meitudata.com/corp-new/image/talents/fire.@048f4693.png\",\"https://js.app.meitudata.com/corp-new/image/talents/turn.@5240bf8d.png\",\"https://js.app.meitudata.com/corp-new/image/talents/real.@d03582dd.png\"]},{\"title\":\"我们探求本质、敢说真话，不弄虚作假；同时知行合一、说到做到，不回避问题。\",\"add_time\":\"2026-11-18 09:00:00\",\"imgs\":[\"https://js.app.meitudata.com/corp-new/image/talents/heart.@a83509f7.png\",\"https://js.app.meitudata.com/corp-new/image/talents/rocket.@e5538365.png\",\"https://js.app.meitudata.com/corp-new/image/talents/earth.@b2485c95.png\"]}]}}";
        try {
            Log.d(TAG, responseStr);
            JSONObject responseJSON = new JSONObject(responseStr);
            int code = responseJSON.getInt("code");
            JSONObject dict = responseJSON.getJSONObject("data");
            if (code == APIServices.CODE_SUCCESS) {
                this.mDatas.removeAll(this.mDatas);
                JSONArray data = dict.getJSONArray("list");
                Gson gson = new Gson();
                YLZTopListModel model = null;
                for (int i = 0; i < data.length(); i++) {
                    JSONObject itemJSON = data.getJSONObject(i);
                    model = gson.fromJson(itemJSON.toString(), YLZTopListModel.class);
                    this.mDatas.add(model);
                }
                this.mAdapter.setMDatas(this.mDatas);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    private void initDatas() {
//        try {
//            StringBuilder realPath = new StringBuilder();
//            realPath.append(APIServices.API_PATH)
//                    .append(HotSearchAPI)
//                    .append("?page=1&limit=8");
//            APIServices.sendGetRequest(realPath.toString(), "", new Callback() {
//                @Override
//                public void onFailure(Call call, final IOException e) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Log.e(TAG, "onFailure" + e.getMessage());
//                        }
//                    });
//                }
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    String responseStr = response.body().string();
//                    Message msg = mHandler.obtainMessage();
//                    msg.what = 0;
//                    msg.obj = responseStr;
//                    mHandler.sendMessage(msg);
//                }
//            });
//        } catch (Exception e) {
//            e.getMessage();
//        }
//    }
//
//    private static class YLZHandler extends Handler {
//        private WeakReference<YLZHomeFragment> mReference;
//        public YLZHandler(YLZHomeFragment fragment) {
//            mReference = new WeakReference<YLZHomeFragment>(fragment);
//        }
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 0:
//                    try {
//                        String responseStr = (String) msg.obj;
//                        Log.d(TAG, responseStr);
//                        JSONObject responseJSON = new JSONObject(responseStr);
//                        int code = responseJSON.getInt("code");
//                        JSONObject dict = responseJSON.getJSONObject("data");
//                        if (code == APIServices.CODE_SUCCESS) {
//                            mReference.get().mDatas.removeAll(mReference.get().mDatas);
//                            JSONArray data = dict.getJSONArray("list");
//                            Gson gson = new Gson();
//                            YLZTopListModel model = null;
//                            for (int i = 0; i < data.length(); i++) {
//                                JSONObject itemJSON = data.getJSONObject(i);
//                                model = gson.fromJson(itemJSON.toString(), YLZTopListModel.class);
//                                mReference.get().mDatas.add(model);
//                            }
//                            mReference.get().mAdapter.setMDatas(mReference.get().mDatas);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
}