package com.example.androidproject.logic.life.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.base.extent.MeasureExtent;
import com.example.androidproject.logic.life.model.YLZLifeItemModel;

import java.util.ArrayList;

/**
 * 惠民生活页列表Adapter
 * 页面所有区块统一为列表条目:标题栏/消费banner/快捷图标/分组标题/享优惠/金融专区/三栏导航/银行卡片
 * 配合GridLayoutManager(spanCount=10):图标每条占2格(每行5个),银行卡占5格(每行2张),其余通栏占10格
 */
public class YLZLifeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public LayoutInflater mInflater;
    public Context mContext;
    //页面条目数据源
    private final ArrayList<YLZLifeItemModel> mItems = new ArrayList<>();

    /**
     * 条目点击回调
     */
    public interface OnLifeItemClickListener {
        void onLifeItemClick(YLZLifeItemModel itemModel, int position);
    }

    private OnLifeItemClickListener mOnItemClickListener;

    public void setOnLifeItemClickListener(OnLifeItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public YLZLifeAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    /**
     * 装载页面条目数据
     */
    public void setItems(ArrayList<YLZLifeItemModel> items) {
        this.mItems.clear();
        if (items != null) {
            this.mItems.addAll(items);
        }
        notifyDataSetChanged();
    }

    /**
     * 条目占据的格数:快捷图标占2格(每行5个),银行卡占5格(每行2张),其余通栏
     */
    public int getSpanSize(int position) {
        if (position < 0 || position >= mItems.size()) {
            return 10;
        }
        int itemType = mItems.get(position).itemType;
        if (itemType == YLZLifeItemModel.TYPE_ICON) {
            return 2;
        }
        if (itemType == YLZLifeItemModel.TYPE_BANK) {
            return 5;
        }
        return 10;
    }

    /**
     * 当前位置前面已排过的银行卡数量,用于判断卡片在行内的左右位置
     */
    private int getBankIndex(int position) {
        int bankIndex = 0;
        for (int i = 0; i < position; i++) {
            if (mItems.get(i).itemType == YLZLifeItemModel.TYPE_BANK) {
                bankIndex++;
            }
        }
        return bankIndex;
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).itemType;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case YLZLifeItemModel.TYPE_TITLE_BAR:
                return new SimpleViewHolder(this.mInflater.inflate(R.layout.item_life_title, parent, false));
            case YLZLifeItemModel.TYPE_BANNER:
                return new SimpleViewHolder(this.mInflater.inflate(R.layout.item_life_banner, parent, false));
            case YLZLifeItemModel.TYPE_ICON:
                return new IconViewHolder(this.mInflater.inflate(R.layout.item_life_icon, parent, false));
            case YLZLifeItemModel.TYPE_SECTION_TITLE:
                return new SectionTitleViewHolder(this.mInflater.inflate(R.layout.item_life_section_title, parent, false));
            case YLZLifeItemModel.TYPE_PROMO:
                return new SimpleViewHolder(this.mInflater.inflate(R.layout.item_life_promo, parent, false));
            case YLZLifeItemModel.TYPE_BANK_ZONE:
                return new SimpleViewHolder(this.mInflater.inflate(R.layout.item_life_bank_zone, parent, false));
            case YLZLifeItemModel.TYPE_FINANCE_NAV:
                return new SimpleViewHolder(this.mInflater.inflate(R.layout.item_life_finance_nav, parent, false));
            case YLZLifeItemModel.TYPE_BANK:
            default:
                return new BankViewHolder(this.mInflater.inflate(R.layout.item_life_bank_card, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final YLZLifeItemModel itemModel = mItems.get(position);
        switch (itemModel.itemType) {
            case YLZLifeItemModel.TYPE_ICON:
                IconViewHolder iconHolder = (IconViewHolder) holder;
                iconHolder.lifeIconIv.setImageResource(itemModel.iconRes);
                iconHolder.lifeIconNameTv.setText(itemModel.title);
                break;
            case YLZLifeItemModel.TYPE_SECTION_TITLE:
                ((SectionTitleViewHolder) holder).lifeSectionTitleTv.setText(itemModel.title);
                break;
            case YLZLifeItemModel.TYPE_BANK:
                BankViewHolder bankHolder = (BankViewHolder) holder;
                bankHolder.lifeBankLogoTv.setBackgroundResource(itemModel.bankLogoBgRes);
                bankHolder.lifeBankLogoTv.setText(itemModel.bankLogoChar);
                bankHolder.lifeBankNameTv.setText(itemModel.title);
                bankHolder.lifeBankDescTv.setText(itemModel.bankDesc);
                //按行内左右位置设置边距:左卡(左边距16/右边距5),右卡(左边距5/右边距16),卡间距10
                boolean isLeftCard = this.getBankIndex(position) % 2 == 0;
                ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) layoutParams;
                    marginParams.leftMargin = MeasureExtent.dip2px(mContext, isLeftCard ? 16 : 5);
                    marginParams.rightMargin = MeasureExtent.dip2px(mContext, isLeftCard ? 5 : 16);
                    marginParams.topMargin = MeasureExtent.dip2px(mContext, 10);
                }
                break;
            default:
                //标题栏/banner/享优惠/金融专区/三栏导航为纯展示区块,无需绑定数据
                break;
        }

        //条目点击
        if (this.mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(v ->
                    this.mOnItemClickListener.onLifeItemClick(itemModel, holder.getLayoutPosition()));
        }
    }

    static class SimpleViewHolder extends RecyclerView.ViewHolder {

        SimpleViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class IconViewHolder extends RecyclerView.ViewHolder {

        ImageView lifeIconIv;
        TextView lifeIconNameTv;

        IconViewHolder(View itemView) {
            super(itemView);
            lifeIconIv = (ImageView) itemView.findViewById(R.id.lifeIconIv);
            lifeIconNameTv = (TextView) itemView.findViewById(R.id.lifeIconNameTv);
        }
    }

    static class SectionTitleViewHolder extends RecyclerView.ViewHolder {

        TextView lifeSectionTitleTv;

        SectionTitleViewHolder(View itemView) {
            super(itemView);
            lifeSectionTitleTv = (TextView) itemView.findViewById(R.id.lifeSectionTitleTv);
        }
    }

    static class BankViewHolder extends RecyclerView.ViewHolder {

        TextView lifeBankLogoTv;
        TextView lifeBankNameTv;
        TextView lifeBankDescTv;

        BankViewHolder(View itemView) {
            super(itemView);
            lifeBankLogoTv = (TextView) itemView.findViewById(R.id.lifeBankLogoTv);
            lifeBankNameTv = (TextView) itemView.findViewById(R.id.lifeBankNameTv);
            lifeBankDescTv = (TextView) itemView.findViewById(R.id.lifeBankDescTv);
        }
    }
}
