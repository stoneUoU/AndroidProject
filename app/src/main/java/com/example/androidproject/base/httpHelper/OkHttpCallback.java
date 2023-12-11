package com.example.androidproject.base.httpHelper;

import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created By   : LiLinXiang
 * Created Date : 2017-3-28
 * Introduction : 重写网络请求回调生成Result类
 */
public abstract class OkHttpCallback extends Callback<Result> {
    @Override
    public Result parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        JSONObject jsonObject = JSONObject.parseObject(string);
        Result result = new Result();
        result.setCode(jsonObject.getInteger("code"));
        result.setMessage(jsonObject.getString("message"));
        result.setData(jsonObject.getString("data"));
        result.setType(jsonObject.getString("type"));
        return result;
    }
}
