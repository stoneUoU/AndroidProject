package com.example.androidproject.logic.home.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.androidproject.R;
import com.example.androidproject.logic.home.model.YLZTopListModel;

import java.util.List;

import butterknife.BindView;

public class YLZHomeFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<YLZTopListModel> mDatas;
    public LayoutInflater mInflater;
    public Context mContext;

    private static final String TAG = "YLZHomeFragmentAdapter";

    public interface OnItemClickListener {
        void onItemClick(View view, int posi, int ids);
        void onItemLongClick(View view,int posi,int ids);
    }
    private YLZHomeFragmentAdapter.OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(YLZHomeFragmentAdapter.OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }
    public YLZHomeFragmentAdapter(Context context,List<YLZTopListModel> mDatas) {
        this.mContext = context;
        this.mDatas = mDatas;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this.mInflater.inflate(R.layout.item_top_list_view, parent, false);
        return new YLZHomeFragmentAdapter.CustomerViewHolder(view, mContext);
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final YLZTopListModel item;
        item = mDatas.get(position);
        YLZHomeFragmentAdapter.CustomerViewHolder vh = (YLZHomeFragmentAdapter.CustomerViewHolder)holder;
        vh.titleView.setText(item.getTitle());
        vh.timeView.setText(String.valueOf(item.getAdd_time()));
        vh.readView.setText("阅读："+String.valueOf(item.getReadCount()));
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(mContext).load(item.getImgs().get(0)).apply(options).into(vh.imageView0);
        Glide.with(mContext).load(item.getImgs().get(1)).apply(options).into(vh.imageView1);
        Glide.with(mContext).load(item.getImgs().get(2)).apply(options).into(vh.imageView2);

        if(mOnItemClickListener != null){
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int layoutPosi = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(((YLZHomeFragmentAdapter.CustomerViewHolder) holder).itemView,layoutPosi,item.getId());
                }
            });
            vh.itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    int layoutPosi = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(((YLZHomeFragmentAdapter.CustomerViewHolder) holder).itemView,layoutPosi,item.getId());
                    return false;
                }
            });
        }
    }
    static class CustomerViewHolder extends RecyclerView.ViewHolder {
        TextView titleView;
        TextView timeView;
        TextView readView;

        ImageView imageView0;
        ImageView imageView1;
        ImageView imageView2;
        Context mContext;
        public CustomerViewHolder(View view, Context context) {
            super(view);
            mContext = context;
            imageView0 = (ImageView) view.findViewById(R.id.image0);
            imageView1 = (ImageView) view.findViewById(R.id.image1);
            imageView2 = (ImageView) view.findViewById(R.id.image2);

            titleView = (TextView) view.findViewById(R.id.titleView);
            timeView = (TextView) view.findViewById(R.id.timeView);
            readView = (TextView) view.findViewById(R.id.readView);
        }
    }
    public void setMDatas(List<YLZTopListModel> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }
}