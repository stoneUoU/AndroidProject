package com.example.androidproject.base.httpHelper;

/**
 * Created By   : LiLinXiang
 * Created Date : 2017-3-28
 * Introduction : 重写网络请求回调生成Result类
 */
public interface ResultCallback {
    void onSuccess(Result result);

    void onError(Result result, String message);
}
