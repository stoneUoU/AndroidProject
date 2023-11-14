package com.example.androidproject.logic.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.androidproject.R;
import com.example.androidproject.base.activity.YLZTabBarActivity;
import com.example.androidproject.base.view.YLZTimerButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YLZLoginActivity extends AppCompatActivity implements View.OnClickListener {

    //关闭按钮
    @BindView(R.id.closeIMV)
    ImageView closeIMV;
    //密码登录
    @BindView(R.id.codeLoginBtn)
    Button codeLoginBtn;
    //验证码按钮
    @BindView(R.id.smsBtn)
    YLZTimerButton smsBtn;
    //密码登录
    @BindView(R.id.smsLoginBtn)
    Button smsLoginBtn;

    @BindView(R.id.user_name)
    EditText user_name;

    @BindView(R.id.sms_code)
    EditText sms_code;

    public Context mContext;

    private static final String TAG = "YLZLoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ylzlogin);
        ButterKnife.bind(this);
        mContext = this;
        this.initClickListeners();
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
        this.closeIMV.setOnClickListener(this);
        this.codeLoginBtn.setOnClickListener(this);
        this.smsBtn.setOnClickListener(this);
        this.smsLoginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.closeIMV:
                finishSelf();
                break;
            case R.id.codeLoginBtn:
                System.out.println("密码登录");
                break;
            case R.id.smsBtn:
                this.smsBtn.start();
                break;
            case R.id.smsLoginBtn:
                System.out.println("用户登录");
                //submit_LR(user_name.getText().toString(),sms_code.getText().toString());
                break;
        }
    }

    // 点击空白区域 自动隐藏软键盘
    public boolean onTouchEvent(MotionEvent event) {
        if(null != this.getCurrentFocus()){
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super .onTouchEvent(event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            return isCustumerBackKey();
        }
        return false;
    }

    private boolean isCustumerBackKey() {
        // 这儿做返回键的控制，如果自己处理返回键逻辑就返回true，如果返回false,代表继续向下传递back事件，由系统去控制
        Intent intent= new Intent(mContext, YLZTabBarActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        return true;
    }

    private void finishSelf(){
        finish();
    }

}