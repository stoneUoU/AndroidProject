package com.example.androidproject.logic.healthcode.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.donkingliang.groupedadapter.layoutmanger.GroupedGridLayoutManager;
import com.example.androidproject.R;
import com.example.androidproject.base.extent.MeasureExtent;
import com.example.androidproject.base.extent.YLZQRCodeExtent;
import com.example.androidproject.base.layoutManage.AutoSizeLayoutManager;
import com.example.androidproject.logic.healthcode.controller.YLZHealthCodeActivity;
import com.example.androidproject.logic.message.model.YLZStyleListModel;
import com.example.androidproject.logic.message.model.YLZStyleModel;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class YLZHealthCodeAdapter extends GroupedRecyclerViewAdapter {
    private List<YLZStyleListModel> mDatas;
    public LayoutInflater mInflater;
    public Context mContext;
    /**
     * 0 : 绿码
     * 1 : 橙码
     * 2 : 红码
     */
    public int clickNum = 0;

    //相关服务的Adapter:
    private YLZHealthCodeServiceAdapter serviceAdapter;
    public List<String> stringModels;

    public Boolean isOn = false;

    private static final String TAG = "YLZHealthCodeAdapter";
    public final int ITEM_HEALTH_INFO = 0;
    public final int ITEM_HEALTH = 1;
    public final int ITEM_HEALTH_CHECK = 2;
    public final int ITEM_HEALTH_SERVICE = 3;
    public final int ITEM_HEALTH_SOURCE = 4;

    public TextView secondWrapperTv;

    public  interface OnItemHealthCodeInfoClickListener {
        void onExcute();
    }

    private YLZHealthCodeAdapter.OnItemHealthCodeInfoClickListener healthCodeInfoClickListener;

    public  void setOnItemClickListener(YLZHealthCodeAdapter.OnItemHealthCodeInfoClickListener listener){
        this.healthCodeInfoClickListener = listener;
    }

    public YLZHealthCodeAdapter(Context context,List<YLZStyleListModel> mDatas) {
        super(context);
        this.mContext = context;
        this.mDatas = mDatas;
        this.mInflater = LayoutInflater.from(mContext);
    }
    @Override
    public int getGroupCount() {
        return this.mDatas.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public boolean hasHeader(int groupPosition) {
        return false;
    }

    @Override
    public int getHeaderLayout(int viewType) { return R.layout.item_group_header; }

    @Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition) {
    }

    //设置尾部：
    @Override
    public boolean hasFooter(int groupPosition) {
        return true;
    }
    @Override
    public int getFooterLayout(int viewType) {
        if (viewType == ITEM_HEALTH_SOURCE) {
            return R.layout.item_health_special_footer;
        } else {
            return R.layout.item_health_normal_footer;
        }
    }
    @Override
    public int getFooterViewType(int groupPosition) {
        if (groupPosition == 0) {
            return ITEM_HEALTH_INFO;
        } else if (groupPosition == 1) {
            return ITEM_HEALTH;
        } else if (groupPosition == 2) {
            return ITEM_HEALTH_CHECK;
        } else if (groupPosition == 3) {
            return ITEM_HEALTH_SERVICE;
        } else {
            return ITEM_HEALTH_SOURCE;
        }
    }
    @Override
    public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition) {
    }

    //设置内容：
    @Override
    public int getChildLayout(int viewType) {
        if (viewType == ITEM_HEALTH_INFO) {
            return R.layout.item_health_code_info;
        } else if (viewType == ITEM_HEALTH) {
            return R.layout.item_health_code;
        } else if (viewType == ITEM_HEALTH_CHECK) {
            return R.layout.item_health_code_check;
        } else if (viewType == ITEM_HEALTH_SERVICE) {
            return R.layout.item_health_code_service;
        } else {
            return R.layout.item_health_code_source;
        }
    }
    @Override
    public int getChildViewType(int groupPosition, int childPosition) {
        if (groupPosition == 0) {
            return ITEM_HEALTH_INFO;
        } else if (groupPosition == 1) {
            return ITEM_HEALTH;
        } else if (groupPosition == 2) {
            return ITEM_HEALTH_CHECK;
        } else if (groupPosition == 3) {
            return ITEM_HEALTH_SERVICE;
        } else {
            return ITEM_HEALTH_SOURCE;
        }
    }
    @Override
    public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {
        if (groupPosition == ITEM_HEALTH_INFO) {
            Button showButton = (Button) holder.get(R.id.showButton);
            Button senderButton = (Button) holder.get(R.id.senderButton);
            TextView nameTv = (TextView) holder.get(R.id.nameTv);
            TextView certTv = (TextView) holder.get(R.id.certTv);
            showButton.setOnClickListener(v -> {
                isOn = !isOn;
                nameTv.setText(isOn ? "姓名： 彭于晏":"姓名： 彭*晏");
                certTv.setText(isOn ? "身份证号： 362324199509185186" : "身份证号： 362324********5186");
                v.setBackgroundResource(isOn ? R.mipmap.ylz_eye_show:R.mipmap.ylz_eye_hide);
            });
            senderButton.setOnClickListener(v -> {
                if (this.healthCodeInfoClickListener != null) {
                    this.healthCodeInfoClickListener.onExcute();
                }
            });
        } else if (groupPosition == ITEM_HEALTH) {
            TextView stateTv = holder.get(R.id.stateTv);
            TextView secondTv = holder.get(R.id.secondTv);
            ImageView codeImageView = holder.get(R.id.codeImageView);
            //健康状况核验 强制隔离【绿码】
            //健康状况核验 建议隔离【橙码】
            //健康状况核验 强制隔离【红码】
            if (this.clickNum == 0) {
                this.formatDatas(stateTv,"健康状况核验 未见异常【绿码】",7,R.color.YLZColorMZTGreenView);
                codeImageView.setImageBitmap(YLZQRCodeExtent.createQRCodeBitmap("Hello world",MeasureExtent.dip2px(mContext,230), ContextCompat.getColor(mContext,R.color.YLZColorMZTGreenView),Color.WHITE));
            } else if (this.clickNum == 1) {
                this.formatDatas(stateTv,"健康状况核验 建议隔离【橙码】",7,R.color.YLZColorMZTOrangeView);
                codeImageView.setImageBitmap(YLZQRCodeExtent.createQRCodeBitmap("Hello world",MeasureExtent.dip2px(mContext,230), ContextCompat.getColor(mContext,R.color.YLZColorMZTOrangeView),Color.WHITE));
            } else {
                this.formatDatas(stateTv,"健康状况核验 强制隔离【红码】",7,R.color.YLZColorMZTRedView);
                codeImageView.setImageBitmap(YLZQRCodeExtent.createQRCodeBitmap("Hello world",MeasureExtent.dip2px(mContext,230), ContextCompat.getColor(mContext,R.color.YLZColorMZTRedView),Color.WHITE));
            }
            //设置时间倒计时
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            secondTv.setText(simpleDateFormat.format(date));
            this.secondWrapperTv = secondTv;
            this.timer.schedule(timerTask,0,1000);
        } else if (groupPosition == ITEM_HEALTH_CHECK) {

        } else if (groupPosition == ITEM_HEALTH_SERVICE) {
            this.stringModels = new ArrayList<>();
            RecyclerView recyclerView =  holder.get(R.id.recyclerView);
            this.serviceAdapter = new YLZHealthCodeServiceAdapter(this.mContext,this.stringModels);
            GroupedGridLayoutManager gridLayoutManager = new GroupedGridLayoutManager(mContext, 2, this.serviceAdapter){
                //重写这个方法 改变子项的SpanSize。
                //这个跟重写SpanSizeLookup的getSpanSize方法的使用是一样的。
                @Override
                public int getChildSpanSize(int groupPosition, int childPosition) {
                    return super.getChildSpanSize(groupPosition, childPosition);
                }
            };
            //设置RecyclerView布局
            recyclerView.addItemDecoration(new SpaceItemDecoration(30));
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(this.serviceAdapter);
            this.initServiceDatas();
        } else {
        }
    }

    private void formatDatas(TextView stateTv, String textString, int location, int color) {
        SpannableString spannableString = new SpannableString(textString);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(mContext,color));
        spannableString.setSpan(colorSpan, location, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        StyleSpan styleSpan_B  = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(styleSpan_B, location, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        stateTv.setText(spannableString);
    }

    private void initServiceDatas() {
        List<String> datas = Arrays.asList("健康报告","为家人申领","扫张贴码","疫苗接种预约","疫苗接种查询","医保电子凭证","申领张贴码","申领机构张贴码","通信行程卡","疫情防控服务");
        for (int i = 0;i<datas.size();i++) {
            this.stringModels.add(datas.get(i));
        }
        this.serviceAdapter.setMDatas(this.stringModels);
    }

    public void setMDatas(List<YLZStyleListModel> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1){
                Log.d(TAG,"View  View  excute");
                //设置时间倒计时
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                secondWrapperTv.setText(simpleDateFormat.format(date));
            }
            super.handleMessage(msg);
        }
    };
    Timer timer = new Timer();
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

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
}
