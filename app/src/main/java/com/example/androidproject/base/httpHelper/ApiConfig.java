package com.example.androidproject.base.httpHelper;

/**
 * 接口环境配置
 */
public class ApiConfig {


    public static String BASE_SERVICE_URL;//生产环境
    public static String BASE_SERVICE_H5_URL;//生产环境
    public static boolean DEBUG_SERVER = true;


    public static void init(String serviceUrl, String serviceH5Url, boolean isDebug) {
        BASE_SERVICE_URL = serviceUrl;
        BASE_SERVICE_H5_URL = serviceH5Url;
        DEBUG_SERVER = isDebug;
    }


}
