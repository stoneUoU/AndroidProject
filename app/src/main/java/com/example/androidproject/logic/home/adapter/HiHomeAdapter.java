package com.example.androidproject.logic.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.logic.home.model.HiHomeItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页(政务版)列表Adapter
 * 标题+搜索行固定在页面顶部(不进列表);四宫格及以下区块统一为列表条目:
 * 四宫格/猜你想办/主题服务/通知条/龙岩一卡通横幅/特色专区/政务地图
 * 配合GridLayoutManager(spanCount=10):当前全部通栏占10格,后续按需拆格(如特色专区分页)
 */
public class HiHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public LayoutInflater mInflater;
    public Context mContext;
    //页面条目数据源
    private final ArrayList<HiHomeItemModel> mItems = new ArrayList<>();

    /**
     * 区块内元素点击回调(回传控件id,由页面统一处理跳转)
     */
    public interface OnHiHomeItemClickListener {
        void onHiHomeItemClick(int viewId);
    }

    private OnHiHomeItemClickListener mOnItemClickListener;

    public void setOnHiHomeItemClickListener(OnHiHomeItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public HiHomeAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    /**
     * 装载页面条目数据
     */
    public void setItems(ArrayList<HiHomeItemModel> items) {
        this.mItems.clear();
        if (items != null) {
            this.mItems.addAll(items);
        }
        notifyDataSetChanged();
    }

    /**
     * 条目占据的格数:当前全部区块通栏占10格
     */
    public int getSpanSize(int position) {
        return 10;
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
            case HiHomeItemModel.TYPE_GUESS:
                return new GuessViewHolder(this.mInflater.inflate(R.layout.item_hi_home_guess, parent, false), this);
            case HiHomeItemModel.TYPE_THEME:
                return new ThemeViewHolder(this.mInflater.inflate(R.layout.item_hi_home_theme, parent, false), this);
            case HiHomeItemModel.TYPE_NOTICE:
                return new NoticeViewHolder(this.mInflater.inflate(R.layout.item_hi_home_notice, parent, false), this);
            case HiHomeItemModel.TYPE_BANNER:
                return new BannerViewHolder(this.mInflater.inflate(R.layout.item_hi_home_banner, parent, false), this);
            case HiHomeItemModel.TYPE_ZONE:
                return new ZoneViewHolder(this.mInflater.inflate(R.layout.item_hi_home_zone, parent, false), this);
            case HiHomeItemModel.TYPE_MAP:
                return new MapViewHolder(this.mInflater.inflate(R.layout.item_hi_home_map, parent, false), this);
            case HiHomeItemModel.TYPE_QUICK:
            default:
                return new QuickViewHolder(this.mInflater.inflate(R.layout.item_hi_home_quick, parent, false), this);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final HiHomeItemModel itemModel = mItems.get(position);
        switch (itemModel.itemType) {
            case HiHomeItemModel.TYPE_GUESS:
                bindGuess((GuessViewHolder) holder, itemModel);
                break;
            case HiHomeItemModel.TYPE_NOTICE:
                ((NoticeViewHolder) holder).noticeTextTv.setText(itemModel.title);
                break;
            default:
                //四宫格/主题服务/横幅/特色专区/政务地图为固定展示区块,无需绑定数据
                break;
        }
    }

    /**
     * 绑定猜你想办事项(mock数据,待接口)
     */
    private void bindGuess(GuessViewHolder holder, HiHomeItemModel itemModel) {
        List<String> titles = itemModel.guessTitles;
        if (titles == null) {
            return;
        }
        if (titles.size() > 0) {
            holder.guessItem1Tv.setText(titles.get(0));
        }
        if (titles.size() > 1) {
            holder.guessItem2Tv.setText(titles.get(1));
        }
    }

    /**
     * 条目内可点击控件统一挂接回调(构造时注册,绑定数据不影响)
     */
    private void bindClicks(View itemView, int... viewIds) {
        for (int viewId : viewIds) {
            View view = itemView.findViewById(viewId);
            if (view != null) {
                view.setOnClickListener(v -> {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onHiHomeItemClick(v.getId());
                    }
                });
            }
        }
    }

    static class QuickViewHolder extends RecyclerView.ViewHolder {

        QuickViewHolder(View itemView, HiHomeAdapter adapter) {
            super(itemView);
            //四宫格快捷入口(搜索/通知/老年版已上移到页面固定导航,由页面直接处理)
            adapter.bindClicks(itemView, R.id.scanItem, R.id.busCodeItem, R.id.payCodeItem, R.id.myCardItem);
        }
    }

    static class GuessViewHolder extends RecyclerView.ViewHolder {

        TextView guessItem1Tv;
        TextView guessItem2Tv;

        GuessViewHolder(View itemView, HiHomeAdapter adapter) {
            super(itemView);
            guessItem1Tv = (TextView) itemView.findViewById(R.id.guessItem1Tv);
            guessItem2Tv = (TextView) itemView.findViewById(R.id.guessItem2Tv);
            adapter.bindClicks(itemView, R.id.moreGuess, R.id.guessItem1, R.id.guessItem2);
        }
    }

    static class ThemeViewHolder extends RecyclerView.ViewHolder {

        ThemeViewHolder(View itemView, HiHomeAdapter adapter) {
            super(itemView);
            adapter.bindClicks(itemView, R.id.themeMainCard, R.id.themeSocial, R.id.themeJob,
                    R.id.themePerson, R.id.themeRelation);
        }
    }

    static class NoticeViewHolder extends RecyclerView.ViewHolder {

        TextView noticeTextTv;

        NoticeViewHolder(View itemView, HiHomeAdapter adapter) {
            super(itemView);
            noticeTextTv = (TextView) itemView.findViewById(R.id.noticeTextTv);
            adapter.bindClicks(itemView, R.id.noticeBar);
        }
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {

        BannerViewHolder(View itemView, HiHomeAdapter adapter) {
            super(itemView);
            adapter.bindClicks(itemView, R.id.bannerBox);
        }
    }

    static class ZoneViewHolder extends RecyclerView.ViewHolder {

        ZoneViewHolder(View itemView, HiHomeAdapter adapter) {
            super(itemView);
            adapter.bindClicks(itemView, R.id.zoneRights, R.id.zoneHr, R.id.zoneArbitrate, R.id.zoneGong);
        }
    }

    static class MapViewHolder extends RecyclerView.ViewHolder {

        MapViewHolder(View itemView, HiHomeAdapter adapter) {
            super(itemView);
            adapter.bindClicks(itemView, R.id.mapBox, R.id.mapLook);
        }
    }
}
