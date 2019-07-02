package com.wanma.eichong.assets.config;

/**
 * 公共类
 */
public class Constants {
    /**
     * 登陆相关
     */
    public static final String ASSETS_CAPTCHA = "assetsCaptcha";
    public static final String COOKIE_ASSETS_USER_ID = "assetsUserId";
    public static final String USER_COOKIE_PATH = "/";
    public static final int DEFAULT_COOKIE_MAX_AGE = 7 * 24 * 60 * 60;
    public static final String SESSION_ASSETS_USER = "loginUser";
    /**
     * 登陆是否需要验证码 0 需要 1 不需要
     */
    public static final String CHECK_AUTH_CODE = "0";
    public static final String NOT_CHECK_AUTH_CODE = "1";

    /**
     * 电站阶段 所处阶段(0:新建，1：立项，2：实施，3：决算，4：运营)
     */
    public static final Integer STATION_STAGE_0 = 0;
    public static final Integer STATION_STAGE_1 = 1;
    public static final Integer STATION_STAGE_2 = 2;
    public static final Integer STATION_STAGE_3 = 3;
    public static final Integer STATION_STAGE_4 = 4;


    public static int TABLE_ORDER = 1;//订单表
    public static int TABLE_RECORD = 2;//订单记录表
    public static int TABLE_PUR = 3;//tbl_purchasehistory 流水记录表
    public static int TABLE_ORDER_NOT_OVER = 4;//未完成订单



    public static int FILETYPE_CODE1 = 1;//1	立项测算结果（2.1，2.2，2.3，2.4）
    public static int FILETYPE_CODE2 = 2;//2	实施预算汇总表
    public static int FILETYPE_CODE3 = 3;//3	实施预算分项明细
    public static int FILETYPE_CODE4 = 4;//4	总决算汇总表
    public static int FILETYPE_CODE5 = 5;//5	决算分项明细
    public static int FILETYPE_CODE6 = 6;//6	运营物料信息
    public static int FILETYPE_CODE7 = 7;//7	协议文件表

    /**
     * 导出
     */
    public static final String FILE_STATION_EXPORT = "充电站资产.xlsx";
    public static final String FILE_STATION_EXPORT_SHEET = "充电站资产信息";
}
