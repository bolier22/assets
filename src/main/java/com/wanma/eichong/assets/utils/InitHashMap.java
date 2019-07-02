package com.wanma.eichong.assets.utils;

import com.wanma.eichong.assets.service.impl.BudgetBaseServiceImpl;

import java.util.HashMap;

public class InitHashMap {
    private static HashMap paramMap = new HashMap<>();
    public static HashMap getInstanceParamMap() {
        if (paramMap == null) {
            synchronized (InitHashMap.class) {
                if (paramMap == null) {
                    paramMap = new HashMap();
                }
            }
        }
        paramMap.clear();
        return paramMap;
    }

}
