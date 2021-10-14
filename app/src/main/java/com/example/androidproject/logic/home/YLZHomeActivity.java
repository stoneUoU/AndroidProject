package com.example.androidproject.logic.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.androidproject.R;

import com.example.androidproject.base.extent.VectorBarExtent;
import com.example.androidproject.base.extent.YLZToast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class YLZHomeActivity extends AppCompatActivity {
    //关闭按钮
    @BindView(R.id.tv1)
    TextView textView1;
    //关闭按钮
    @BindView(R.id.tv2)
    TextView textView2;
    //关闭按钮
    @BindView(R.id.smsLoginBtn)
    Button smsLoginBtn;
    //协议信息：
    @BindView(R.id.protocolLabel)
    TextView protocolLabel;

    @BindView(R.id.toolbarLeftImg)
    ImageView mToolbarLeftImg;
    @BindView(R.id.toolBarTitle)
    TextView mToolBarTitle;
    @BindView(R.id.toolBarRL)
    RelativeLayout mToolBarRL;

    public Context mContext;
    private static final String TAG = "YLZHomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_ylzhome, null);
        setContentView(view);
        ButterKnife.bind(this);

        this.initDatas(view);
    }

    private void initDatas(View view) {

        VectorBarExtent.setToolStatusBarColor(this, view, R.color.ylzLightBlueView, false);
        VectorBarExtent.MIUISetStatusBarLightMode(this, false);
        VectorBarExtent.FlymeSetStatusBarLightMode(getWindow(), false);

        mToolBarRL.setBackgroundColor(getResources().getColor(R.color.ylzLightBlueView));
        mToolBarTitle.setText(R.string.mine_infos);
        mToolBarTitle.setTextColor(getResources().getColor(R.color.appWhite));
        mToolbarLeftImg.setImageDrawable(getResources().getDrawable(R.mipmap.icon_return_h32));
        mToolbarLeftImg.setBackground(getResources().getDrawable(R.drawable.selector_orangepress));

        //设置Hello World前三个字符有点击事件
        SpannableStringBuilder procotolSpannableString = new SpannableStringBuilder("登录即代表您已阅读并同意《用户协议》和《隐私政策》");
        ClickableSpan procotolSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                YLZToast.showShortToast(YLZHomeActivity.this, "protocolLabel");
            }
        };
        procotolSpannableString.setSpan(procotolSpan,
                12, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        procotolSpannableString.setSpan(new ForegroundColorSpan(Color.RED),
                12, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ClickableSpan policySpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                YLZToast.showShortToast(YLZHomeActivity.this, "policySpan");
            }
        };
        procotolSpannableString.setSpan(new ForegroundColorSpan(Color.RED),
                19, 25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        procotolSpannableString.setSpan(policySpan,
                19, 25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        protocolLabel.setMovementMethod(LinkMovementMethod.getInstance());
        protocolLabel.setText(procotolSpannableString);

//        String stringExtra = getIntent().getStringExtra("param");
//        textView2.setText(stringExtra);

        Bundle bundle = getIntent().getExtras();
        String name = (String) bundle.get("name");
        String age = (String) bundle.get("age");
        textView2.setText(name + age);
    }

    @OnClick({R.id.waitingButton,R.id.toolbarLeftImg,R.id.smsLoginBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.waitingButton:
                YLZToast.showShortToast(YLZHomeActivity.this, "被点击了");
                break;
            case R.id.toolbarLeftImg:
                finish();
                break;
            case R.id.smsLoginBtn:
                textView2.setText("AAAAAAAAAAA");
                break;
            default:
                break;
        }
    }

}