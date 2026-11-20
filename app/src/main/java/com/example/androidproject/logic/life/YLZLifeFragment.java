package com.example.androidproject.logic.life;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.androidproject.R;

/**
 * 惠民生活页(占位,待设计稿补充)
 */
public class YLZLifeFragment extends Fragment {

    private static final String TAG = "YLZLifeFragment";

    public Context mContext;

    public YLZLifeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_life, null);
        mContext = getContext();
        return view;
    }
}
