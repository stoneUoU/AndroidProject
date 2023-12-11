package com.example.androidproject.base.httpHelper;

import com.alibaba.fastjson.JSONObject;

public class RequestParam {
    String appId;
    String serviceName;
    String encData;
    String signData;
    String timestamp;
    String reqNo;
    JSONObject data;
    String openEnc;

    public String getOpenEnc() {
        return openEnc;
    }

    public void setOpenEnc(String openEnc) {
        this.openEnc = openEnc;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getEncData() {
        return encData;
    }

    public void setEncData(String encData) {
        this.encData = encData;
    }

    public String getSignData() {
        return signData;
    }

    public void setSignData(String signData) {
        this.signData = signData;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getReqNo() {
        return reqNo;
    }

    public void setReqNo(String reqNo) {
        this.reqNo = reqNo;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }
}
