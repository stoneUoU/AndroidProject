package com.example.androidproject.logic.message;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.androidproject.R;
import com.example.androidproject.base.activity.YLZTabBarActivity;
import com.example.androidproject.base.extent.APIServices;
import com.example.androidproject.base.extent.MeasureExtent;
import com.example.androidproject.base.extent.YLZToast;
import com.example.androidproject.base.layoutManage.AutoSizeLayoutManager;
import com.example.androidproject.logic.home.YLZHomeActivity;
import com.example.androidproject.logic.home.YLZHomeFragment;
import com.example.androidproject.logic.home.YLZHomeRecycleActivity;
import com.example.androidproject.logic.home.adapter.YLZHomeFragmentAdapter;
import com.example.androidproject.logic.message.adapter.YLZMessageFragmentAdapter;
import com.example.androidproject.logic.message.model.YLZHotSearchModel;
import com.example.androidproject.logic.message.model.YLZStyleListModel;
import com.example.androidproject.logic.message.model.YLZStyleModel;
import com.example.androidproject.logic.mine.YLZMineActivity;
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
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class YLZMessageFragment extends Fragment {

    private YLZMessageFragmentAdapter mAdapter;
    public Context mContext;
    public LayoutInflater mInflater;

    private final String HotSearchAPI = "/provide/hot_search";
//    private YLZMessageFragment.YLZHandler mHandler = new YLZMessageFragment.YLZHandler(this);
    private static final String TAG = "YLZMessageFragment";

//    public List<YLZHotSearchModel> searchModels;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    public List<YLZStyleListModel> styleModels;


    public YLZMessageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_message, null);
        mContext = getContext();
        mInflater = LayoutInflater.from(mContext);
        ButterKnife.bind(this,view);
        this.styleModels = new ArrayList<>();

        this.initViews(view);
        this.initDatas();
        return view;
    }

    private void initDatas() {
        for (int i = 0;i<5;i++) {
            YLZStyleListModel model;
            List<YLZStyleModel> list = new ArrayList<YLZStyleModel>();;
            for (int j = 0;j<4;j++) {
                YLZStyleModel styleModel;
                styleModel = new YLZStyleModel(i,"美食博主"+j,1);
                list.add(styleModel);
            }
            model = new YLZStyleListModel(i,"美食博主"+i,1,list);
            this.styleModels.add(model);
        }
        this.mAdapter.setMDatas(this.styleModels);
    }

    public void initViews(View view) {

        this.mAdapter = new YLZMessageFragmentAdapter(this.mContext,this.styleModels);
        final AutoSizeLayoutManager layoutManager = new AutoSizeLayoutManager(this.mContext, LinearLayoutManager.VERTICAL,false);
        //设置RecyclerView布局
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.addItemDecoration(new YLZMessageFragment.SpaceItemDecoration(30));
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

    private void alert() {
        AlertDialog dialog = new AlertDialog.Builder(((YLZTabBarActivity) getActivity()))
                .setTitle("退出登录")
                .setMessage("确定退出登录？")
                .setCancelable(true)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton("取消", null)
                .create();
        dialog.show();
    }

//    private void initDatas() {
//
//        this.searchModels = new ArrayList<YLZHotSearchModel>();
//        try {
//            StringBuilder realPath = new StringBuilder();
//            realPath.append(APIServices.API_PATH)
//                    .append(HotSearchAPI)
//                    .append("");
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
//        private WeakReference<YLZMessageFragment> mReference;
//        public YLZHandler(YLZMessageFragment fragment) {
//            mReference = new WeakReference<YLZMessageFragment>(fragment);
//        }
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 0:
//                    try {
//                        String responseStr = (String) msg.obj;
//                        JSONObject responseJSON = new JSONObject(responseStr);
//                        int code = responseJSON.getInt("code");
//                        JSONObject dict = responseJSON.getJSONObject("result");
//                        if (code == APIServices.CODE_SUCCESS) {
//                            mReference.get().searchModels.removeAll(mReference.get().searchModels);
//                            JSONArray data = dict.getJSONArray("hot_search");
//                            Gson gson = new Gson();
//                            YLZHotSearchModel model = null;
//                            for (int i = 0; i < data.length(); i++) {
//                                JSONObject itemJSON = data.getJSONObject(i);
//                                model = gson.fromJson(itemJSON.toString(), YLZHotSearchModel.class);
//                                mReference.get().searchModels.add(model);
//                            }
//                            Log.d(TAG, mReference.get().searchModels.toString());
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