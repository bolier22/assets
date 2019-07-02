package com.wanma.eichong.assets.mapper;

import com.wanma.eichong.assets.entity.BudgetCompletePeriodQuota;
import com.wanma.eichong.assets.entity.BudgetFirstYearsCost;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.6 立项预算表-2.4全周期运营指标表（导入数据） budget_complete_period_quota
 */
@Mapper
@Component
public interface BudgetCompletePeriodQuotaMapper {

    int insertBatchBudgetCompletePeriodQuotaList(HashMap paramMap);

    int deleteBudgetCompletePeriodQuotaByStationId(Long assetsStationId);

    List<BudgetCompletePeriodQuota> selectBudgetCompletePeriodQuotaListByStationId(Long assetsStationId);
}