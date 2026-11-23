package com.example.androidproject.base.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.androidproject.R;
import com.example.androidproject.base.extent.VectorBarExtent;
import com.example.androidproject.logic.home.YLZHomeFragment;
import com.example.androidproject.logic.home.YLZHomeRecycleActivity;
import com.example.androidproject.logic.life.YLZLifeFragment;
import com.example.androidproject.logic.login.YLZLoginActivity;
import com.example.androidproject.logic.message.YLZMessageFragment;
import com.example.androidproject.logic.mine.YLZMineFragment;
import com.example.androidproject.logic.service.YLZServiceFragment;
import com.next.easynavigation.view.EasyNavigationBar;

import java.util.ArrayList;
import java.util.List;

public class YLZTabBarActivity extends AppCompatActivity {

    private EasyNavigationBar navigationBar;

    private String[] tabText = {"首页", "发现", "消息", "我的"};
    //未选中icon
    private int[] selectIcon = {R.mipmap.mine_icon_store_click, R.mipmap.icon_shoppingcar_click,  R.mipmap.icon_shoppingcar_click, R.mipmap.mine_icon_mine_click};
    //选中时icon
    private int[] normalIcon = {R.mipmap.mine_icon_store_onclick, R.mipmap.icon_shoppingcar_onclick, R.mipmap.icon_shoppingcar_onclick, R.mipmap.mine_icon_mine_onclick};

    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = LayoutInflater.from(this).inflate(R.layout.activity_ylztab_bar, null);
        setContentView(view);

        navigationBar = findViewById(R.id.navigationBar);

        fragments.add(new YLZMineFragment());
        fragments.add(new YLZServiceFragment());
        fragments.add(new YLZLifeFragment());
        fragments.add(new YLZHomeFragment());

        navigationBar.titleItems(tabText)
        .normalIconItems(normalIcon)
        .selectIconItems(selectIcon)
        .centerImageRes(R.mipmap.ylz_tabbar_middle_cert)
        .fragmentList(fragments)
        .fragmentManager(getSupportFragmentManager())
        .centerLayoutRule(EasyNavigationBar.RULE_CENTER)
        .setOnTabClickListener(new EasyNavigationBar.OnTabClickListener() {
            @Override
            public boolean onTabSelectEvent(View view, int position) {
//                if (position == 3) {
//                    Toast.makeText(YLZTabBarActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
                return false;
            }

            @Override
            public boolean onTabReSelectEvent(View view, int position) {
                return false;
            }

        })
        .setOnCenterTabClickListener(new EasyNavigationBar.OnCenterTabSelectListener() {
            @Override
            public boolean onCenterTabSelectEvent(View view) {
                Intent intent = new Intent(YLZTabBarActivity.this, YLZLoginActivity.class);
                startActivity(intent);
//                Toast.makeText(YLZTabBarActivity.this, "点击中间按钮", Toast.LENGTH_SHORT).show();
                return false;
            }
        })
        .mode(EasyNavigationBar.NavigationMode.MODE_ADD)
        //EasyNavigation 2.0.5的Easy_tabTextSize属性有bug(解析时误用默认值12换算,恒为3dp),
        //这里用builder接口直接设置:Tab文字14sp
        .textSizeType(EasyNavigationBar.TextSizeType.TYPE_SP)
        .tabTextSize(14)
        .build();
    }

    public EasyNavigationBar getNavigationBar() {
        return navigationBar;
    }
}
