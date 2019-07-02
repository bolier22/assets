package com.wanma.eichong.assets.service;

import com.wanma.eichong.assets.entity.Imp7tionBudgetCollectList;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.8 实施预算汇总表（条目表-导入数据） imp7tion_budget_collect_list
 */
public interface Imp7tionBudgetCollectListService {
    List<Imp7tionBudgetCollectList> selectImp7tionBudgetCollectListByStationId(Long budgetCollectId);
}
