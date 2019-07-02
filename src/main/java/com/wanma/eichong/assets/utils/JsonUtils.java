package com.wanma.eichong.assets.utils;

import com.alibaba.fastjson.JSONObject;

public class JsonUtils {
    //根据key获取 int类型值
    public static Integer getIntegerValue(JSONObject jsonObject,String key){
        if(jsonObject.containsKey(key) && !jsonObject.get(key).equals("")) {
            return Integer.valueOf(jsonObject.get(key).toString());
        }
        return -1;
    }
    //默认分页 页数
    public static Integer getPage(JSONObject jsonObject,String key){
        if(jsonObject.containsKey(key)) {
            return Integer.valueOf(jsonObject.get(key).toString());
        }
        return 1;
    }
    //默认分页 显示条数
    public static Integer getSize(JSONObject jsonObject,String key){
        if(jsonObject.containsKey(key)) {
            return Integer.valueOf(jsonObject.get(key).toString());
        }
        return 10;
    }
    //根据key获取 String类型值
    public static String getStrValue(JSONObject jsonObject,String key){
        if(jsonObject.containsKey(key)){
            if(jsonObject.get(key) == null){
            return "";
            }else{
                return jsonObject.get(key).toString();
            }
        }
        return "";
    }

    //根据key获取 String数组类型值
    public static String[] getStrArrValue(JSONObject jsonObject,String key){
        if(jsonObject.containsKey(key)) {
            return jsonObject.get(key).toString().split(",");
        }
        return new String[0];
    }
    //根据key获取 int数组类型值
    public static int[] getIntegerArrValue(JSONObject jsonObject,String key){
        if(jsonObject.containsKey(key)) {
            return StringToInt(jsonObject.get(key).toString().split(","));
        }
        return new int[0];
    }
    public static int[] StringToInt(String[] arrs){
        int[] ints = new int[arrs.length];
        for(int i=0;i<arrs.length;i++){
            ints[i] = Integer.parseInt(arrs[i]);
        }
        return ints;
    }
}
