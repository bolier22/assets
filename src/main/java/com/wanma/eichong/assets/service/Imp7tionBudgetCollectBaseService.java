package com.wanma.eichong.assets.service;

import com.wanma.eichong.assets.entity.Imp7tionBudgetCollectBase;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.7 实施预算汇总表（基表-导入数据） imp7tion_budget_collect_base
 */

public interface Imp7tionBudgetCollectBaseService {
    int insertImp7tionBudgetCollect(List<Object> obj,Integer fileType);

    int deleteImp7tionBudgetCollectByStationId(Long assetsStationId,Integer fileType);

    Imp7tionBudgetCollectBase selectImp7tionBudgetCollectBaseByStationId(Long assetsStationId);
}