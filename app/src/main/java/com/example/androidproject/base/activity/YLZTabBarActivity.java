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
import com.example.androidproject.logic.mine.YLZMineFragment;
import com.example.androidproject.logic.service.YLZServiceFragment;
import com.next.easynavigation.view.EasyNavigationBar;

import java.util.ArrayList;
import java.util.List;

public class YLZTabBarActivity extends AppCompatActivity {

    private EasyNavigationBar navigationBar;

    private String[] tabText = {"首页", "服务", "惠民生活", "我的"};
    //未选中icon
    private int[] selectIcon = {R.drawable.ic_tab_home_blue, R.drawable.ic_tab_service_blue, R.drawable.ic_tab_life_blue, R.drawable.ic_tab_mine_blue};
    //选中时icon
    private int[] normalIcon = {R.drawable.ic_tab_home_gray, R.drawable.ic_tab_service_gray, R.drawable.ic_tab_life_gray, R.drawable.ic_tab_mine_gray};

    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = LayoutInflater.from(this).inflate(R.layout.activity_ylztab_bar, null);
        setContentView(view);

        VectorBarExtent.setToolStatusBarColor(this, view, R.color.ylzLightBlueView, false);
        VectorBarExtent.MIUISetStatusBarLightMode(this, false);
        VectorBarExtent.FlymeSetStatusBarLightMode(getWindow(), false);

        navigationBar = findViewById(R.id.navigationBar);

        fragments.add(new YLZHomeFragment());
        fragments.add(new YLZServiceFragment());
        fragments.add(new YLZLifeFragment());
        fragments.add(new YLZMineFragment());

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
        .build();
    }
    public EasyNavigationBar getNavigationBar() {
        return navigationBar;
    }
}
