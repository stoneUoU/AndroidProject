package com.example.androidproject.base.httpHelper;

public class Api {

    public static final String BASE_LOGIN_FORWARD = "/hsa-app-service/app/login/forward/";//用户信息相关
    public static final String BASE_MANAGER = "/hsa-app-service/app/manage/";//功能相关
    public static final String BASE_YBESSCARD = "/hsa-app-service/evoucher/";//医保凭证相关
    public static final String BASE_CMSCONT = "/hsa-app-service/cmscont/";//动态资讯
    public static final String BASE_FILE = "/hsa-app-service/app/file/";//文件相关

    public static final String LOGIN = BASE_LOGIN_FORWARD + "acct/loginNew";
    //头像上传
    public static final String UPLOAD_AVATAR="/hsa-app-service/app/develop/uploadUserImage";
    //头像获取
    public static final String GET_AVATAR="/hsa-app-service/app/develop/getUserImage";
    //参数加密接口
    public static final String SM4_PARAM="/hsa-app-service/app/sm4/encryptSm4";
    //公告
    public static final String GET_NOTICE="/hsa-app-service/app/picture/selectMsgBoard";
}
