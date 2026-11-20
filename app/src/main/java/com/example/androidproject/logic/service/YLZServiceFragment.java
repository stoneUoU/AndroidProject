package com.example.androidproject.logic.service;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.example.androidproject.R;
import com.example.androidproject.base.extent.YLZToast;
import com.example.androidproject.logic.service.adapter.YLZServiceCategoryAdapter;
import com.example.androidproject.logic.service.adapter.YLZServiceContentAdapter;
import com.example.androidproject.logic.service.model.YLZServiceCategoryModel;
import com.example.androidproject.logic.service.model.YLZServiceGroupModel;
import com.example.androidproject.logic.service.model.YLZServiceItemModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 服务页(个人服务/单位服务),左侧大类联动右侧服务列表
 */
public class YLZServiceFragment extends Fragment {

    private static final String TAG = "YLZServiceFragment";
    //服务类型下标
    private static final int TAB_PERSONAL = 0;
    private static final int TAB_UNIT = 1;

    //个人服务选项卡
    @BindView(R.id.tabPersonalLl)
    LinearLayout tabPersonalLl;
    //单位服务选项卡
    @BindView(R.id.tabUnitLl)
    LinearLayout tabUnitLl;
    @BindView(R.id.tabPersonalTv)
    TextView tabPersonalTv;
    @BindView(R.id.tabUnitTv)
    TextView tabUnitTv;
    //选项卡下方蓝色小横条
    @BindView(R.id.tabPersonalIndicator)
    View tabPersonalIndicator;
    @BindView(R.id.tabUnitIndicator)
    View tabUnitIndicator;
    //搜索框
    @BindView(R.id.searchEt)
    EditText searchEt;
    //左侧大类列表
    @BindView(R.id.cateRecyclerView)
    RecyclerView cateRecyclerView;
    //右侧服务列表
    @BindView(R.id.serviceRecyclerView)
    RecyclerView serviceRecyclerView;

    public Context mContext;
    //当前选中的服务页签
    private int currentTab = TAB_PERSONAL;
    //个人服务/单位服务数据源
    private final ArrayList<YLZServiceCategoryModel> personalDatas = new ArrayList<>();
    private final ArrayList<YLZServiceCategoryModel> unitDatas = new ArrayList<>();
    private YLZServiceCategoryAdapter cateAdapter;
    private YLZServiceContentAdapter contentAdapter;

    public YLZServiceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service, null);
        mContext = getContext();
        ButterKnife.bind(this, view);
        this.buildMockDatas();
        this.initViews(view);
        this.switchTab(TAB_PERSONAL);
        return view;
    }

    private void initViews(View view) {
        //左侧大类列表
        this.cateAdapter = new YLZServiceCategoryAdapter(mContext, personalDatas);
        this.cateAdapter.setOnCategoryClickListener(position -> {
            //左侧大类切换,把右侧滚动到对应分组的顶部
            this.scrollToCategory(position);
        });
        this.cateRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        this.cateRecyclerView.setAdapter(this.cateAdapter);

        //右侧服务列表:一次装载当前页签下所有大类的分组,点击左侧大类滚动定位到对应分组
        this.contentAdapter = new YLZServiceContentAdapter(mContext, personalDatas);
        this.contentAdapter.setOnChildClickListener(new GroupedRecyclerViewAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder, int groupPosition, int childPosition) {
                YLZServiceItemModel itemModel = contentAdapter.getItem(groupPosition, childPosition);
                //TODO 跳转对应服务详情页
                YLZToast.showShortToast(mContext, itemModel.title);
            }
        });
        this.serviceRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        this.serviceRecyclerView.setAdapter(this.contentAdapter);

        //右侧滚动监听:手动滚动右侧时,左侧大类高亮自动跟随
        this.serviceRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy != 0) {
                    YLZServiceFragment.this.syncCategoryOnScroll();
                }
            }
        });

        //搜索框:软键盘搜索键
        this.searchEt.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String keyword = searchEt.getText().toString().trim();
                if (TextUtils.isEmpty(keyword)) {
                    YLZToast.showShortToast(mContext, "请输入您想搜索的内容");
                } else {
                    //TODO 调用服务搜索接口
                    YLZToast.showShortToast(mContext, "搜索:" + keyword);
                }
                return true;
            }
            return false;
        });
    }

    /**
     * 当前页签的数据源
     */
    private ArrayList<YLZServiceCategoryModel> currentDatas() {
        return currentTab == TAB_PERSONAL ? personalDatas : unitDatas;
    }

    /**
     * 切换个人服务/单位服务
     */
    private void switchTab(int tab) {
        this.currentTab = tab;
        this.tabPersonalLl.setSelected(tab == TAB_PERSONAL);
        this.tabUnitLl.setSelected(tab == TAB_UNIT);
        this.tabPersonalTv.setTextColor(ContextCompat.getColor(mContext,
                tab == TAB_PERSONAL ? R.color.ylzColorTitleOne : R.color.ylzColorTitleThree));
        this.tabPersonalTv.setTypeface(null, tab == TAB_PERSONAL ? Typeface.BOLD : Typeface.NORMAL);
        this.tabUnitTv.setTextColor(ContextCompat.getColor(mContext,
                tab == TAB_UNIT ? R.color.ylzColorTitleOne : R.color.ylzColorTitleThree));
        this.tabUnitTv.setTypeface(null, tab == TAB_UNIT ? Typeface.BOLD : Typeface.NORMAL);
        this.tabPersonalIndicator.setVisibility(tab == TAB_PERSONAL ? View.VISIBLE : View.GONE);
        this.tabUnitIndicator.setVisibility(tab == TAB_UNIT ? View.VISIBLE : View.GONE);
        //切换页签后刷新左右数据,左侧选中的大类下标保持不变
        this.cateAdapter.setDatas(currentDatas());
        this.contentAdapter.setCategories(currentDatas());
        int selectedIndex = Math.min(this.cateAdapter.getSelectedIndex(), currentDatas().size() - 1);
        //右侧装载的是当前页签的全部大类,把左侧选中的大类滚动到顶部
        this.scrollToCategory(selectedIndex);
    }

    /**
     * 把右侧列表滚动到指定大类第一个分组的顶部
     */
    private void scrollToCategory(int categoryIndex) {
        int position = this.contentAdapter.getCategoryAnchorPosition(categoryIndex);
        RecyclerView.LayoutManager layoutManager = this.serviceRecyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(position, 0);
        } else {
            this.serviceRecyclerView.scrollToPosition(position);
        }
    }

    /**
     * 右侧手动滚动时,根据第一个完全可见项反查所属大类,联动左侧高亮
     */
    private void syncCategoryOnScroll() {
        RecyclerView.LayoutManager layoutManager = this.serviceRecyclerView.getLayoutManager();
        if (!(layoutManager instanceof LinearLayoutManager)) {
            return;
        }
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
        //用第一个"完全可见"的条目判定,避免上一个分类的条目还剩一点在顶部时高亮滞后
        int anchor = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
        if (anchor < 0) {
            anchor = linearLayoutManager.findFirstVisibleItemPosition();
        }
        int categoryIndex = this.contentAdapter.findCategoryIndexByPosition(anchor);
        this.cateAdapter.updateSelectedIndex(categoryIndex);
    }

    @OnClick({R.id.tabPersonalLl, R.id.tabUnitLl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tabPersonalLl:
                this.switchTab(TAB_PERSONAL);
                break;
            case R.id.tabUnitLl:
                this.switchTab(TAB_UNIT);
                break;
            default:
                break;
        }
    }

    /**
     * 组装演示数据,TODO 接入服务列表接口
     */
    private void buildMockDatas() {
        //个人服务
        ArrayList<YLZServiceGroupModel> personalSocialGroups = new ArrayList<>();
        personalSocialGroups.add(buildGroup("企业职工养老保险", YLZServiceItemModel.ICON_TYPE_ELDERLY,
                "企业养老保险缴费证明报表", "职工社保缴费证明报表", "职工社保缴费证明报表企业养老保险",
                "待遇领取资格认证业务进度反馈", "企业养老个人当年缴费凭证", "企业养老保险缴费证明报表"));
        personalSocialGroups.add(buildGroup("城乡居民养老保险", YLZServiceItemModel.ICON_TYPE_BUILDING,
                "城乡居民基本养老保险个人权益记录", "城乡居民养老保险参保登记",
                "城乡居民缴费证明报表", "待遇领取资格认证业务进度反馈"));
        personalDatas.add(new YLZServiceCategoryModel("社会保险", personalSocialGroups));

        ArrayList<YLZServiceGroupModel> personalEmploymentGroups = new ArrayList<>();
        personalEmploymentGroups.add(buildGroup("就业创业服务", YLZServiceItemModel.ICON_TYPE_ELDERLY,
                "创业担保贷款申请", "就业困难人员认定", "求职就业登记"));
        personalDatas.add(new YLZServiceCategoryModel("就业创业", personalEmploymentGroups));

        ArrayList<YLZServiceGroupModel> personalLaborGroups = new ArrayList<>();
        personalLaborGroups.add(buildGroup("劳动关系服务", YLZServiceItemModel.ICON_TYPE_ELDERLY,
                "劳动用工备案", "劳动人事争议调解申请", "劳动能力鉴定申请"));
        personalDatas.add(new YLZServiceCategoryModel("劳动关系", personalLaborGroups));

        ArrayList<YLZServiceGroupModel> personalTalentGroups = new ArrayList<>();
        personalTalentGroups.add(buildGroup("人事人才服务", YLZServiceItemModel.ICON_TYPE_BUILDING,
                "人事档案接收", "职称评审申报", "高校毕业生报到"));
        personalDatas.add(new YLZServiceCategoryModel("人事人才", personalTalentGroups));

        //单位服务
        ArrayList<YLZServiceGroupModel> unitSocialGroups = new ArrayList<>();
        unitSocialGroups.add(buildGroup("单位社保服务", YLZServiceItemModel.ICON_TYPE_BUILDING,
                "单位社会保险参保登记", "单位社保缴费证明报表", "单位职工参保信息维护", "单位社保费缴费明细"));
        unitSocialGroups.add(buildGroup("单位养老经办", YLZServiceItemModel.ICON_TYPE_ELDERLY,
                "单位养老保险缴费申报", "职工退休待遇核算", "单位年度缴费工资申报"));
        unitDatas.add(new YLZServiceCategoryModel("社会保险", unitSocialGroups));

        ArrayList<YLZServiceGroupModel> unitEmploymentGroups = new ArrayList<>();
        unitEmploymentGroups.add(buildGroup("单位用工服务", YLZServiceItemModel.ICON_TYPE_BUILDING,
                "单位用工登记", "就业补贴申领", "见习基地申报"));
        unitDatas.add(new YLZServiceCategoryModel("就业创业", unitEmploymentGroups));

        ArrayList<YLZServiceGroupModel> unitLaborGroups = new ArrayList<>();
        unitLaborGroups.add(buildGroup("劳动关系备案", YLZServiceItemModel.ICON_TYPE_BUILDING,
                "劳动用工备案(单位)", "集体合同审查"));
        unitDatas.add(new YLZServiceCategoryModel("劳动关系", unitLaborGroups));

        ArrayList<YLZServiceGroupModel> unitTalentGroups = new ArrayList<>();
        unitTalentGroups.add(buildGroup("单位人才服务", YLZServiceItemModel.ICON_TYPE_BUILDING,
                "单位招聘信息发布", "人才引进申报", "技工院校办学许可"));
        unitDatas.add(new YLZServiceCategoryModel("人事人才", unitTalentGroups));
    }

    /**
     * 构建一个服务分组
     */
    private YLZServiceGroupModel buildGroup(String groupName, int iconType, String... titles) {
        ArrayList<YLZServiceItemModel> items = new ArrayList<>();
        for (String title : titles) {
            items.add(new YLZServiceItemModel(title, iconType));
        }
        return new YLZServiceGroupModel(groupName, items);
    }
}
