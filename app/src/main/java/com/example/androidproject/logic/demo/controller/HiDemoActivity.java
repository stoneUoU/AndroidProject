package com.example.androidproject.logic.demo.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidproject.R;
import com.example.androidproject.base.extent.VectorBarExtent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HiDemoActivity extends AppCompatActivity implements View.OnClickListener {
    public Context mContext;
    public LayoutInflater mInflater;
    private static final String TAG = "HiDemoActivity";
    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.more)
    Button moreButton;

    @BindView(R.id.shut)
    Button shutButton;

    @BindView(R.id.scanImageView)
    ImageView scanImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mInflater = LayoutInflater.from(this);
        View view = mInflater.inflate(R.layout.activity_hi_demo, null);
        setContentView(view);
        VectorBarExtent.setToolStatusBarColor(this,view,R.color.colorF3F6FD,true);
        ButterKnife.bind(this);

        title.setText("厦门健康码");
        title.setTextColor(getResources().getColor(R.color.appOrange));
        this.scanImageView.setImageDrawable(getResources().getDrawable(R.mipmap.app_login_pic));

        this.moreButton.setOnClickListener(this);
        this.shutButton.setOnClickListener(this);
        this.scanImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.more:
                Log.d(TAG, "onClick: 哈哈哈----more");
                break;
            case R.id.shut:
                Log.d(TAG, "onClick: 哈哈哈----shut");
                break;
            case R.id.scanImageView:
                Log.d(TAG, "onClick: 哈哈哈----scanImageView");
                break;
        }
    }
}