package com.example.androidproject.base.httpHelper;

import com.alibaba.fastjson.JSONObject;


/**
 * Created by Qin on 2015/11/30.
 */
public class Result {

    public static final int RESULT_SERVER_SUCCESS_CODE = 0;//成功
    public static final int TOKEN_TIME_OUT = 600003;//token过期
    public static final int OTHER_DEVICE_LOGIN = 600016;//被抢登


    public static final String SUCC_TYPE = "success";
    public static final String FAIL_TYPE = "error";

    private int code;
    private String type;
    private String message;
    private String data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
