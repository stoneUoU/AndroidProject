package com.example.androidproject.logic.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.androidproject.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

public class YLZHomeRecycleActivity extends AppCompatActivity {

    public Context mContext;
    public LayoutInflater mInflater;
    private YLZHomeRecycleActivity.YLZHandler mHandler = new YLZHomeRecycleActivity.YLZHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        mInflater = LayoutInflater.from(mContext);
        View v = mInflater.inflate(R.layout.activity_ylzhome_recycle, null);
        setContentView(v);
        ButterKnife.bind(this);
    }

    private static class YLZHandler extends Handler {
        private WeakReference<YLZHomeRecycleActivity> mReference;
        public YLZHandler(YLZHomeRecycleActivity activity) {
            mReference = new WeakReference<YLZHomeRecycleActivity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    try {
                        String responseStr = (String) msg.obj;
                        JSONObject responseJSON = new JSONObject(responseStr);
                        int code = responseJSON.getInt("code");
                        String message = responseJSON.getString("msg");
                        int totalMount = responseJSON.getInt("total");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    }

}