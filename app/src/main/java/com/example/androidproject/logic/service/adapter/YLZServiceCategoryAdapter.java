package com.example.androidproject.logic.service.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.logic.service.model.YLZServiceCategoryModel;

import java.util.ArrayList;

/**
 * 服务页-左侧服务大类的Adapter,点击大类联动右侧数据
 */
public class YLZServiceCategoryAdapter extends RecyclerView.Adapter<YLZServiceCategoryAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<YLZServiceCategoryModel> mDatas;
    //当前选中的大类下标
    private int mSelectedIndex = 0;

    public interface OnCategoryClickListener {
        void onCategoryClick(int position);
    }

    private OnCategoryClickListener onCategoryClickListener;

    public YLZServiceCategoryAdapter(Context context, ArrayList<YLZServiceCategoryModel> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    public void setDatas(ArrayList<YLZServiceCategoryModel> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    public int getSelectedIndex() {
        return mSelectedIndex;
    }

    public void setSelectedIndex(int index) {
        this.mSelectedIndex = index;
    }

    /**
     * 更新选中下标并局部刷新(点击切换、右侧滚动反向联动时使用)
     */
    public void updateSelectedIndex(int index) {
        if (index < 0 || index >= getItemCount() || index == mSelectedIndex) {
            return;
        }
        int oldIndex = mSelectedIndex;
        mSelectedIndex = index;
        notifyItemChanged(oldIndex);
        notifyItemChanged(index);
    }

    public void setOnCategoryClickListener(OnCategoryClickListener listener) {
        this.onCategoryClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_service_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        YLZServiceCategoryModel model = mDatas.get(position);
        boolean isSelected = position == mSelectedIndex;
        holder.cateNameTv.setText(model.categoryName);
        holder.cateNameTv.setTextColor(ContextCompat.getColor(mContext,
                isSelected ? R.color.ylzQuickLoginBlue : R.color.ylzColorTitleOne));
        holder.cateNameTv.setTypeface(null, isSelected ? Typeface.BOLD : Typeface.NORMAL);
        holder.itemView.setSelected(isSelected);
        holder.itemView.setOnClickListener(v -> {
            //重复点击已选中的大类时也回调,用于把右侧重新滚动到该大类顶部
            updateSelectedIndex(position);
            if (onCategoryClickListener != null) {
                onCategoryClickListener.onCategoryClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView cateNameTv;

        ViewHolder(View itemView) {
            super(itemView);
            cateNameTv = (TextView) itemView.findViewById(R.id.cateNameTv);
        }
    }
}
