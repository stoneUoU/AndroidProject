package com.example.androidproject.logic.life;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.base.extent.YLZToast;
import com.example.androidproject.base.extent.VectorBarExtent;
import com.example.androidproject.logic.life.adapter.YLZLifeAdapter;
import com.example.androidproject.logic.life.model.YLZLifeItemModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 惠民生活页(社保卡消费banner/快捷入口/享优惠/金融服务/社保卡权益)
 * 标题栏固定在列表外,其余区块统一为RecyclerView条目,由YLZLifeAdapter按条目类型渲染
 */
public class YLZLifeFragment extends Fragment {

    private static final String TAG = "YLZLifeFragment";

    //页面列表
    @BindView(R.id.lifeRecyclerView)
    RecyclerView lifeRecyclerView;

    public Context mContext;
    //页面条目数据源
    private final ArrayList<YLZLifeItemModel> mDatas = new ArrayList<>();
    private YLZLifeAdapter lifeAdapter;

    public YLZLifeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_life, null);
        mContext = getContext();
        ButterKnife.bind(this, view);
        this.buildMockDatas();
        this.initViews(view);
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

    private void initViews(View view) {
        //10列网格:图标占2格(每行5个),银行卡占5格(每行2张),其余区块通栏
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 10);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return lifeAdapter.getSpanSize(position);
            }
        });
        this.lifeRecyclerView.setLayoutManager(layoutManager);

        this.lifeAdapter = new YLZLifeAdapter(mContext);
        this.lifeAdapter.setOnLifeItemClickListener((itemModel, position) -> {
            if (itemModel.title == null) {
                //纯展示区块(banner/金融专区等),暂无跳转
                return;
            }
            //TODO 跳转对应功能页面
            YLZToast.showShortToast(mContext, itemModel.title);
        });
        this.lifeRecyclerView.setAdapter(this.lifeAdapter);
        this.lifeAdapter.setItems(this.mDatas);
    }

    /**
     * 组装页面条目数据,TODO 接入惠民生活配置接口
     */
    private void buildMockDatas() {
        //社保卡金融账户消费banner(标题栏固定在列表外,不作为列表条目)
        this.mDatas.add(new YLZLifeItemModel(YLZLifeItemModel.TYPE_BANNER));

        //快捷图标 两行五列
        int[] iconRes = {R.drawable.ic_life_pay, R.drawable.ic_life_ride, R.drawable.ic_life_payfee,
                R.drawable.ic_life_elderly, R.drawable.ic_life_busstop, R.drawable.ic_life_weather,
                R.drawable.ic_life_typhoon, R.drawable.ic_life_seniorcard, R.drawable.ic_life_restroom,
                R.drawable.ic_life_almanac};
        String[] iconNames = {"刷码付款", "刷码乘车", "生活缴费", "惠老服务", "查公交",
                "查天气", "台风路径", "敬老卡", "查公厕", "查黄历"};
        for (int i = 0; i < iconRes.length; i++) {
            YLZLifeItemModel itemModel = new YLZLifeItemModel(YLZLifeItemModel.TYPE_ICON);
            itemModel.iconRes = iconRes[i];
            itemModel.title = iconNames[i];
            this.mDatas.add(itemModel);
        }

        //享优惠
        this.mDatas.add(this.buildSectionTitle("享优惠"));
        this.mDatas.add(new YLZLifeItemModel(YLZLifeItemModel.TYPE_PROMO));

        //金融服务
        this.mDatas.add(this.buildSectionTitle("金融服务"));
        this.mDatas.add(new YLZLifeItemModel(YLZLifeItemModel.TYPE_BANK_ZONE));
        this.mDatas.add(new YLZLifeItemModel(YLZLifeItemModel.TYPE_FINANCE_NAV));

        //社保卡权益
        this.mDatas.add(this.buildSectionTitle("社保卡权益"));
        this.mDatas.add(this.buildBank("工", R.drawable.bg_life_bank_icbc, "中国工商银行", "享受加油返现、购车福利"));
        this.mDatas.add(this.buildBank("建", R.drawable.bg_life_bank_ccb, "中国建设银行", "激活有好礼"));
        this.mDatas.add(this.buildBank("中", R.drawable.bg_life_bank_boc, "中国银行", "激活有好礼"));
        this.mDatas.add(this.buildBank("农", R.drawable.bg_life_bank_abc, "中国农业银行", "激活有好礼"));
        this.mDatas.add(this.buildBank("邮", R.drawable.bg_life_bank_psbc, "中国邮政储蓄银行", "激活有好礼"));
        this.mDatas.add(this.buildBank("兴", R.drawable.bg_life_bank_cib, "兴业银行", "激活有好礼"));
    }

    /**
     * 构建一个分组标题条目
     */
    private YLZLifeItemModel buildSectionTitle(String title) {
        YLZLifeItemModel itemModel = new YLZLifeItemModel(YLZLifeItemModel.TYPE_SECTION_TITLE);
        itemModel.title = title;
        return itemModel;
    }

    /**
     * 构建一个银行卡片条目
     */
    private YLZLifeItemModel buildBank(String logoChar, int logoBgRes, String bankName, String bankDesc) {
        YLZLifeItemModel itemModel = new YLZLifeItemModel(YLZLifeItemModel.TYPE_BANK);
        itemModel.bankLogoChar = logoChar;
        itemModel.bankLogoBgRes = logoBgRes;
        itemModel.title = bankName;
        itemModel.bankDesc = bankDesc;
        return itemModel;
    }
}
