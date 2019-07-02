package com.wanma.eichong.assets.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
        String re0 = "零|一|二|三|四|五|六|七|八|九|十|百|千|万|亿";
//        String re1 = "\\d+-\\d+-?\\d*-?\\d*|" + re0 + "+-" + re0 + "+-?" + re0 + "*-?" + re0 + "*";//存在 3-3(3-3-3、3-3-3-3)
//        String re2 = "\\d+号|" + re0 + "+号";//存在3/三号
//        String re3 = "\\d+-\\d+-?\\d*-?\\d*号|" + re0 + "+-" + re0 + "+-?" + re0 + "*-?" + re0 + "*号";//存在3-33-3(3-3-3、3-3-3-3)号
//        String re4 = "\\d+号?[栋弄幢楼座]|" + re0 + "+号?[栋弄幢楼座]";
//        String re10 = "[省市乡县村屯区巷街路道]$";//以村等结尾
//        String str1 = "撒点发送的十五10000";
//        System.out.println(re1);

//        String str = "五十百";
//        Pattern pattern = Pattern.compile(re0);
//        for(int i=0;i<str.length();i++){
//            char c = str.charAt(i);
//            String s = String.valueOf(c); //效率最高的方法
//            Matcher matcher = pattern.matcher(s);
//            System.out.println(matcher.find());
//        }

//
//        Matcher matcher = pattern.matcher(str1);
//        boolean rs = matcher.find();
//        System.out.println(rs);


        toChinese("15");

    }

    private static String toChinese(String str) {
        String[] s1 = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String[] s2 = {"十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千"};
        String result = "";
        int n = str.length();
        for (int i = 0; i < n; i++) {
            int num = str.charAt(i) - '0';
            if (i != n - 1 && num != 0) {
                result += s1[num] + s2[n - 2 - i];
            } else {
                result += s1[num];
            }
            System.out.println("  " + result);
        }
        System.out.println(result);
        return result;
    }



}