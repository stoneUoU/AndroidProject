package com.example.androidproject.logic.home;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.base.extent.VectorBarExtent;
import com.example.androidproject.base.extent.YLZToast;
import com.example.androidproject.logic.home.adapter.HiHomeAdapter;
import com.example.androidproject.logic.home.model.HiHomeItemModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页(政务版)
 * 顶部导航(「首页」标题+小程序胶囊+搜索行)固定不随列表滚动;
 * 四宫格快捷入口及以下区块统一由RecyclerView实现:四宫格/猜你想办/主题服务/通知条/龙岩一卡通横幅/特色专区/政务地图
 */
public class HiHomeActivity extends AppCompatActivity {

    public Context mContext;

    @BindView(R.id.homeRecycler)
    RecyclerView homeRecycler;

    private HiHomeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_hi_home);
        //状态栏:蓝色+白色图标,与头部渐变顶部一致
        VectorBarExtent.setStatusBarStyle(this, getWindow().getDecorView(), R.color.hi_blue, false);
        ButterKnife.bind(this);
        this.initRecyclerView();
    }

    /**
     * 列表初始化:spanCount=10,当前区块全部通栏,后续按需拆格
     */
    private void initRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 10);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mAdapter == null ? 10 : mAdapter.getSpanSize(position);
            }
        });
        this.homeRecycler.setLayoutManager(layoutManager);
        this.homeRecycler.setOverScrollMode(View.OVER_SCROLL_NEVER);
        this.mAdapter = new HiHomeAdapter(mContext);
        this.mAdapter.setOnHiHomeItemClickListener(this::onHiHomeItemClick);
        this.homeRecycler.setAdapter(this.mAdapter);
        this.buildMockDatas();
    }

    /**
     * 页面条目数据(mock,待接口)
     * TODO 数据接口待接:猜你想办列表/主题服务角标/通知条轮播/横幅轮播/特色专区分页/政务地图
     */
    private void buildMockDatas() {
        ArrayList<HiHomeItemModel> items = new ArrayList<>();
        //四宫格快捷入口(随内容滚动)
        items.add(new HiHomeItemModel(HiHomeItemModel.TYPE_QUICK));
        //猜你想办白卡
        HiHomeItemModel guessModel = new HiHomeItemModel(HiHomeItemModel.TYPE_GUESS);
        ArrayList<String> guessTitles = new ArrayList<>();
        guessTitles.add("企业养老保险缴费证明报表");
        guessTitles.add("待遇资格认证");
        guessModel.guessTitles = guessTitles;
        items.add(guessModel);
        //主题服务
        items.add(new HiHomeItemModel(HiHomeItemModel.TYPE_THEME));
        //通知条
        HiHomeItemModel noticeModel = new HiHomeItemModel(HiHomeItemModel.TYPE_NOTICE);
        noticeModel.title = "“福在闽西”只等你来，红土筑梦，乐业龙岩";
        items.add(noticeModel);
        //龙岩一卡通横幅
        items.add(new HiHomeItemModel(HiHomeItemModel.TYPE_BANNER));
        //特色专区
        items.add(new HiHomeItemModel(HiHomeItemModel.TYPE_ZONE));
        //政务地图
        items.add(new HiHomeItemModel(HiHomeItemModel.TYPE_MAP));
        this.mAdapter.setItems(items);
    }

    /**
     * 固定顶部导航点击(搜索/通知/老年版,统一走区块点击分发)
     */
    @OnClick({R.id.searchBox, R.id.bellBtn, R.id.elderBtn})
    public void onTopBarClick(View view) {
        this.onHiHomeItemClick(view.getId());
    }

    /**
     * 区块内元素点击(回传控件id)
     */
    private void onHiHomeItemClick(int viewId) {
        switch (viewId) {
            case R.id.searchBox:
                YLZToast.showShortToast(mContext, "TODO: 搜索");
                break;
            case R.id.bellBtn:
                YLZToast.showShortToast(mContext, "TODO: 消息通知");
                break;
            case R.id.elderBtn:
                YLZToast.showShortToast(mContext, "TODO: 老年版切换");
                break;
            case R.id.scanItem:
                YLZToast.showShortToast(mContext, "TODO: 扫一扫");
                break;
            case R.id.busCodeItem:
                YLZToast.showShortToast(mContext, "TODO: 乘车码");
                break;
            case R.id.payCodeItem:
                YLZToast.showShortToast(mContext, "TODO: 付款码");
                break;
            case R.id.myCardItem:
                YLZToast.showShortToast(mContext, "TODO: 我的卡证");
                break;
            case R.id.moreGuess:
                YLZToast.showShortToast(mContext, "TODO: 猜你想办-更多");
                break;
            case R.id.guessItem1:
                YLZToast.showShortToast(mContext, "TODO: 企业养老保险缴费证明报表");
                break;
            case R.id.guessItem2:
                YLZToast.showShortToast(mContext, "TODO: 待遇资格认证");
                break;
            case R.id.themeMainCard:
                YLZToast.showShortToast(mContext, "TODO: 社会保障卡");
                break;
            case R.id.themeSocial:
                YLZToast.showShortToast(mContext, "TODO: 社会保险");
                break;
            case R.id.themeJob:
                YLZToast.showShortToast(mContext, "TODO: 就业创业");
                break;
            case R.id.themePerson:
                YLZToast.showShortToast(mContext, "TODO: 人事人才");
                break;
            case R.id.themeRelation:
                YLZToast.showShortToast(mContext, "TODO: 劳动关系");
                break;
            case R.id.noticeBar:
                YLZToast.showShortToast(mContext, "TODO: 通知列表");
                break;
            case R.id.bannerBox:
                YLZToast.showShortToast(mContext, "TODO: 龙岩一卡通横幅");
                break;
            case R.id.zoneRights:
                YLZToast.showShortToast(mContext, "TODO: 劳动维权超市");
                break;
            case R.id.zoneHr:
                YLZToast.showShortToast(mContext, "TODO: 人力资源超市");
                break;
            case R.id.zoneArbitrate:
                YLZToast.showShortToast(mContext, "TODO: 智慧仲裁");
                break;
            case R.id.zoneGong:
                YLZToast.showShortToast(mContext, "TODO: 龙零工");
                break;
            case R.id.mapBox:
            case R.id.mapLook:
                YLZToast.showShortToast(mContext, "TODO: 政务地图");
                break;
            default:
                break;
        }
    }
}
