package com.example.androidproject.base.httpHelper;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
//import com.lilinxiang.baseandroiddevlibrary.user.UserController;
import com.example.androidproject.base.toast.ToastUtils;
import com.orhanobut.hawk.Hawk;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.builder.PostStringBuilder;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public class MyHttpUtil {
    /**
     * 初始化网络请求配置
     */
    public static void init(long connectTimeout, long readTimeout) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("qhyb", true))
                .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }


    /**
     * json请求
     */
    public static void httpPost(final String serviceName, final JSONObject jsonObject, final ResultCallback resultCallback) {

        PostStringBuilder PostBuilder = null;
        String accessToken = Hawk.get("ACCESS_TOKEN");
        if (!TextUtils.isEmpty(accessToken)) {
//            Logutil.e("curToken", "url：" + serviceName + "请求时使用的token\n" + accessToken);
            PostBuilder = OkHttpUtils
                    .postString()
                    .addHeader("accessToken", accessToken);
        } else {
            PostBuilder = OkHttpUtils
                    .postString();
        }
        PostBuilder
                .url(ApiConfig.BASE_SERVICE_URL + serviceName)
                .content(ApiParameter.getParam(jsonObject))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new OkHttpCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        resultCallback.onError(null, e.toString());
                    }

                    @Override
                    public void onResponse(Result response, int id) {
//                        Logutil.e("curToken", "url：" + serviceName + "返回的code:" + response.getCode());
//                        if (response.getCode() == Result.RESULT_SERVER_SUCCESS_CODE && Result.SUCC_TYPE.equals(response.getType())) {
                        if (response.getCode() == Result.RESULT_SERVER_SUCCESS_CODE) {
                            resultCallback.onSuccess(response);
                        } else {
                            if (response.getCode() == Result.TOKEN_TIME_OUT) {//token过期，刷新token
                                EventBus.getDefault().postSticky("token_time_out");//通知首页弹框，或者直接跳转到登录页
                            } else if (response.getCode() == Result.OTHER_DEVICE_LOGIN) {//被抢登，需要重新登录
                                EventBus.getDefault().postSticky("login_suc_event");//通知刷新需要刷新的页面
                                ToastUtils.showLongToast(response.getMessage());
                            } else {//其余情况由各自接口处理
                                resultCallback.onError(response, response.getMessage());
                            }
                        }
                    }
                });
    }

    /**
     * form请求，文件上传功能
     */
    public static void httpPostFile(String serviceName, Map<String, String> map, final ResultCallback resultCallback, List<File> files) {
        PostFormBuilder postFormBuilder = OkHttpUtils.post();
        for (int i = 0; i < files.size(); i++) {
            postFormBuilder.addFile(files.get(i).getName(), files.get(i).getName(), files.get(i));
        }
        postFormBuilder
                .url(ApiConfig.BASE_SERVICE_URL)
                .params(map).build()
                .execute(new OkHttpCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(Result response, int id) {

                    }
                });
    }
}
