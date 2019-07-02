package com.wanma.eichong.assets.service.impl;

import com.wanma.eichong.assets.entity.Imp7tionBudgetCollectList;
import com.wanma.eichong.assets.mapper.Imp7tionBudgetCollectListMapper;
import com.wanma.eichong.assets.service.Imp7tionBudgetCollectListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.8 实施预算汇总表（条目表-导入数据） imp7tion_budget_collect_list
 */
@Service
public class Imp7tionBudgetCollectListServiceImpl implements Imp7tionBudgetCollectListService {
    private final Imp7tionBudgetCollectListMapper imp7tionBudgetCollectListMapper;

    @Autowired
    public Imp7tionBudgetCollectListServiceImpl(Imp7tionBudgetCollectListMapper imp7tionBudgetCollectListMapper) {
        this.imp7tionBudgetCollectListMapper = imp7tionBudgetCollectListMapper;
    }

    @Override
    public List<Imp7tionBudgetCollectList> selectImp7tionBudgetCollectListByStationId(Long budgetCollectId) {
        return imp7tionBudgetCollectListMapper.selectImp7tionBudgetCollectListByStationId(budgetCollectId);
    }
}
