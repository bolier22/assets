package com.wanma.eichong.assets.mapper;

import com.wanma.eichong.assets.entity.Imp7tionBudgetCollectBase;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.7 实施预算汇总表（基表-导入数据） imp7tion_budget_collect_base
 */
@Mapper
@Component
public interface Imp7tionBudgetCollectBaseMapper {
    Long insertImp7tionBudgetCollectBase(Imp7tionBudgetCollectBase imp7tionBudgetCollectBase);

    int deleteImp7tionBudgetCollectBaseByStationId(Long assetsStationId);

    Imp7tionBudgetCollectBase selectImp7tionBudgetCollectBaseByStationId(Long assetsStationId);


}