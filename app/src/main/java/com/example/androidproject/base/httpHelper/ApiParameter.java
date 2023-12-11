package com.example.androidproject.base.httpHelper;

import com.alibaba.fastjson.JSONObject;

public class ApiParameter {
    /**
     * 参数不加密请求
     *
     * @param map
     * @return
     */
    public static String getParam(JSONObject map) {
        String param = map.toJSONString();
        return param;
    }
}
