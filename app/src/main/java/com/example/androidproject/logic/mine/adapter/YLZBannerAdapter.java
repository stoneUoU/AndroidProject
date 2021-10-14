package com.example.androidproject.logic.mine.adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.androidproject.R;
import com.example.androidproject.logic.mine.model.YLZModel;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.util.BannerUtils;

import java.util.List;
public class YLZBannerAdapter extends BannerAdapter<YLZModel, YLZBannerAdapter.ImageHolder> {

    public YLZBannerAdapter(List<YLZModel> mDatas) {
        super(mDatas);
    }

    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new ImageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_image, parent, false));
    }

    @Override
    public void onBindView(ImageHolder holder, YLZModel data, int position, int size) {
        //通过图片加载器实现圆角，你也可以自己使用圆角的imageview，实现圆角的方法很多，自己尝试哈
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BannerUtils.setBannerRound(holder.imageView, 20);
        }
        Glide.with(holder.itemView)
                .load(data.getPic_path())
                .thumbnail(Glide.with(holder.itemView).load(R.drawable.loading))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.imageView);
        holder.titleView.setText(data.getTitle());
    }

    public class ImageHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView titleView;

        public ImageHolder(@NonNull View view) {
            super(view);
            imageView = view.findViewById(R.id.image);
            titleView = view.findViewById(R.id.bannerTitle);
        }
    }
}