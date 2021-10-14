package com.example.androidproject.logic.home;

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
import com.example.androidproject.base.layoutManage.AutoSizeLayoutManager;
import com.example.androidproject.logic.home.adapter.YLZHomeFragmentAdapter;
import com.example.androidproject.logic.home.model.YLZTopListModel;
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

    private final String HotSearchAPI = "/provide/news";
    private YLZHomeFragment.YLZHandler mHandler = new YLZHomeFragment.YLZHandler(this);
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
        this.initDatas();

        return view;
    }

    public void initViews(View view) {

        mToolBarRL.setBackgroundColor(getResources().getColor(R.color.ylzLightBlueView));
        mToolBarTitle.setText(R.string.app_home_title);
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
            Intent intent = new Intent(mContext,YLZHomeRecycleActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(mContext, YLZMineActivity.class);
            startActivity(intent);
        }
    }
    @Override
    public void onItemLongClick(View view, int posi,int ids) {
        Toast.makeText(mContext,"LongClick"+posi,Toast.LENGTH_SHORT).show();
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

    private void initDatas() {
        try {
            StringBuilder realPath = new StringBuilder();
            realPath.append(APIServices.API_PATH)
                    .append(HotSearchAPI)
                    .append("?page=1&limit=8");
            APIServices.sendGetRequest(realPath.toString(), "", new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e(TAG, "onFailure" + e.getMessage());
                        }
                    });
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseStr = response.body().string();
                    Message msg = mHandler.obtainMessage();
                    msg.what = 0;
                    msg.obj = responseStr;
                    mHandler.sendMessage(msg);
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
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
                    try {
                        String responseStr = (String) msg.obj;
                        Log.d(TAG, responseStr);
                        JSONObject responseJSON = new JSONObject(responseStr);
                        int code = responseJSON.getInt("code");
                        JSONObject dict = responseJSON.getJSONObject("data");
                        if (code == APIServices.CODE_SUCCESS) {
                            mReference.get().mDatas.removeAll(mReference.get().mDatas);
                            JSONArray data = dict.getJSONArray("list");
                            Gson gson = new Gson();
                            YLZTopListModel model = null;
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject itemJSON = data.getJSONObject(i);
                                model = gson.fromJson(itemJSON.toString(), YLZTopListModel.class);
                                mReference.get().mDatas.add(model);
                            }
                            mReference.get().mAdapter.setMDatas(mReference.get().mDatas);
                        }
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