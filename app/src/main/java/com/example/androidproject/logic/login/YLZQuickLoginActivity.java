package com.example.androidproject.logic.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidproject.R;
import com.example.androidproject.base.extent.YLZToast;
import com.example.androidproject.base.view.YLZTimerButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 龙岩一卡通-快捷登录页(个人登录/单位登录)
 */
public class YLZQuickLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "YLZQuickLoginActivity";
    //登录方式下标
    private static final int TAB_PERSONAL = 0;
    private static final int TAB_UNIT = 1;

    //个人登录选项卡
    @BindView(R.id.tabPersonalLl)
    LinearLayout tabPersonalLl;
    //单位登录选项卡
    @BindView(R.id.tabUnitLl)
    LinearLayout tabUnitLl;
    @BindView(R.id.tabPersonalTv)
    TextView tabPersonalTv;
    @BindView(R.id.tabUnitTv)
    TextView tabUnitTv;
    //选项卡下方蓝色小横条
    @BindView(R.id.tabPersonalIndicator)
    View tabPersonalIndicator;
    @BindView(R.id.tabUnitIndicator)
    View tabUnitIndicator;
    //手机号
    @BindView(R.id.phoneEt)
    EditText phoneEt;
    //短信验证码
    @BindView(R.id.smsEt)
    EditText smsEt;
    //获取验证码按钮
    @BindView(R.id.smsBtn)
    YLZTimerButton smsBtn;
    //登录按钮
    @BindView(R.id.loginBtn)
    Button loginBtn;
    //闽政通图标
    @BindView(R.id.mztImv)
    ImageView mztImv;

    public Context mContext;
    //当前选中的登录方式
    private int currentTab = TAB_PERSONAL;

    /**
     * 页面入口
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, YLZQuickLoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ylzquick_login);
        ButterKnife.bind(this);
        mContext = this;
        this.initStatusBar();
        this.switchTab(TAB_PERSONAL);
        this.initClickListeners();
    }

    private void initStatusBar() {
        //Android5.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public void initClickListeners() {
        this.tabPersonalLl.setOnClickListener(this);
        this.tabUnitLl.setOnClickListener(this);
        this.smsBtn.setOnClickListener(this);
        this.loginBtn.setOnClickListener(this);
        this.mztImv.setOnClickListener(this);
    }

    /**
     * 切换登录方式
     */
    private void switchTab(int tab) {
        this.currentTab = tab;
        this.tabPersonalLl.setSelected(tab == TAB_PERSONAL);
        this.tabUnitLl.setSelected(tab == TAB_UNIT);
        this.tabPersonalTv.setTextColor(ContextCompat.getColor(mContext,
                tab == TAB_PERSONAL ? R.color.ylzColorTitleOne : R.color.ylzColorTitleThree));
        this.tabPersonalTv.setTypeface(null, tab == TAB_PERSONAL ? Typeface.BOLD : Typeface.NORMAL);
        this.tabUnitTv.setTextColor(ContextCompat.getColor(mContext,
                tab == TAB_UNIT ? R.color.ylzColorTitleOne : R.color.ylzColorTitleThree));
        this.tabUnitTv.setTypeface(null, tab == TAB_UNIT ? Typeface.BOLD : Typeface.NORMAL);
        this.tabPersonalIndicator.setVisibility(tab == TAB_PERSONAL ? View.VISIBLE : View.GONE);
        this.tabUnitIndicator.setVisibility(tab == TAB_UNIT ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tabPersonalLl:
                this.switchTab(TAB_PERSONAL);
                break;
            case R.id.tabUnitLl:
                this.switchTab(TAB_UNIT);
                break;
            case R.id.smsBtn:
                this.requestSmsCode();
                break;
            case R.id.loginBtn:
                this.submitLogin();
                break;
            case R.id.mztImv:
                //TODO 跳转闽政通授权登录
                YLZToast.showShortToast(mContext, "闽政通登录");
                break;
            default:
                break;
        }
    }

    /**
     * 获取短信验证码
     */
    private void requestSmsCode() {
        String phone = phoneEt.getText().toString().trim();
        if (!checkPhone(phone)) {
            return;
        }
        //TODO 调用发送验证码接口
        this.smsBtn.start();
        YLZToast.showShortToast(mContext, "验证码已发送至" + phone);
    }

    /**
     * 登录
     */
    private void submitLogin() {
        String phone = phoneEt.getText().toString().trim();
        String smsCode = smsEt.getText().toString().trim();
        if (!checkPhone(phone)) {
            return;
        }
        if (TextUtils.isEmpty(smsCode)) {
            YLZToast.showShortToast(mContext, "请输入短信验证码");
            return;
        }
        //TODO 调用登录接口,currentTab区分个人登录/单位登录
        YLZToast.showShortToast(mContext, currentTab == TAB_PERSONAL ? "个人登录" : "单位登录");
    }

    private boolean checkPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            YLZToast.showShortToast(mContext, "请输入手机号");
            return false;
        }
        if (phone.length() != 11) {
            YLZToast.showShortToast(mContext, "请输入正确的手机号");
            return false;
        }
        return true;
    }

    // 点击空白区域 自动隐藏软键盘
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }
}
