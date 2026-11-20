package com.example.androidproject.logic.service.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.example.androidproject.R;
import com.example.androidproject.logic.service.model.YLZServiceCategoryModel;
import com.example.androidproject.logic.service.model.YLZServiceGroupModel;
import com.example.androidproject.logic.service.model.YLZServiceItemModel;

import java.util.ArrayList;

/**
 * 服务页-右侧分组服务列表的Adapter
 * 右侧一次装载当前页签下所有大类的分组,点击左侧大类时滚动定位到对应分组顶部
 */
public class YLZServiceContentAdapter extends GroupedRecyclerViewAdapter {

    //当前页签下的所有大类
    private ArrayList<YLZServiceCategoryModel> mCategories;
    //展平后的全部分组(各大类的分组依次排列)
    private final ArrayList<YLZServiceGroupModel> mGroups = new ArrayList<>();
    public LayoutInflater mInflater;
    public Context mContext;

    public YLZServiceContentAdapter(Context context, ArrayList<YLZServiceCategoryModel> categories) {
        super(context);
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.setCategories(categories);
    }

    /**
     * 装载当前页签下所有大类的分组数据
     */
    public void setCategories(ArrayList<YLZServiceCategoryModel> categories) {
        this.mCategories = categories;
        this.mGroups.clear();
        if (categories != null) {
            for (YLZServiceCategoryModel category : categories) {
                if (category.groups != null) {
                    this.mGroups.addAll(category.groups);
                }
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 计算某个大类第一个分组头在整个列表中的位置,用于点击左侧大类时把右侧滚动到对应分组顶部
     */
    public int getCategoryAnchorPosition(int categoryIndex) {
        if (mCategories == null || categoryIndex < 0 || categoryIndex >= mCategories.size()) {
            return 0;
        }
        int position = 0;
        for (int i = 0; i < categoryIndex; i++) {
            ArrayList<YLZServiceGroupModel> groups = mCategories.get(i).groups;
            if (groups == null) {
                continue;
            }
            for (YLZServiceGroupModel group : groups) {
                //每组在列表中占:1个组头 + 全部子项
                position += 1 + (group.items == null ? 0 : group.items.size());
            }
        }
        return position;
    }

    /**
     * 获取某个分组下的服务项(供点击回调取数据)
     */
    public YLZServiceItemModel getItem(int groupPosition, int childPosition) {
        return mGroups.get(groupPosition).items.get(childPosition);
    }

    /**
     * 根据列表位置(组头或子项)反查所属大类的下标,用于右侧手动滚动时联动左侧高亮
     */
    public int findCategoryIndexByPosition(int position) {
        if (mCategories == null || position < 0) {
            return 0;
        }
        int consumed = 0;
        for (int i = 0; i < mCategories.size(); i++) {
            ArrayList<YLZServiceGroupModel> groups = mCategories.get(i).groups;
            int count = 0;
            if (groups != null) {
                for (YLZServiceGroupModel group : groups) {
                    //每组在列表中占:1个组头 + 全部子项
                    count += 1 + (group.items == null ? 0 : group.items.size());
                }
            }
            if (position < consumed + count) {
                return i;
            }
            consumed += count;
        }
        return mCategories.size() - 1;
    }

    @Override
    public int getGroupCount() {
        return mGroups == null ? 0 : mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroups.get(groupPosition).items.size();
    }

    @Override
    public boolean hasHeader(int groupPosition) {
        return true;
    }

    @Override
    public int getHeaderLayout(int viewType) {
        return R.layout.item_service_group_header;
    }

    @Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition) {
        TextView groupNameTv = holder.get(R.id.groupNameTv);
        groupNameTv.setText(mGroups.get(groupPosition).groupName);
    }

    @Override
    public boolean hasFooter(int groupPosition) {
        return false;
    }

    @Override
    public int getFooterLayout(int viewType) {
        return 0;
    }

    @Override
    public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition) {
    }

    @Override
    public int getChildLayout(int viewType) {
        return R.layout.item_service_group_child;
    }

    @Override
    public int getChildViewType(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public void onBindChildViewHolder(BaseViewHolder holder, final int groupPosition, final int childPosition) {
        YLZServiceItemModel itemModel = mGroups.get(groupPosition).items.get(childPosition);
        TextView titleTv = holder.get(R.id.serviceTitleTv);
        ImageView iconIv = holder.get(R.id.serviceIconIv);
        titleTv.setText(itemModel.title);
        iconIv.setImageResource(itemModel.iconType == YLZServiceItemModel.ICON_TYPE_BUILDING
                ? R.drawable.ic_service_type_building : R.drawable.ic_service_type_elderly);
    }
}
