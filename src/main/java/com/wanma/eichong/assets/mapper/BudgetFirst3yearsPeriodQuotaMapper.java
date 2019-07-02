package com.wanma.eichong.assets.mapper;

import com.wanma.eichong.assets.entity.BudgetFirst3yearsPeriodQuota;
import com.wanma.eichong.assets.entity.BudgetFirstYearsCost;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.5 立项预算表-2.3前三年运营指标表（导入数据） budget_first_3years_period_quota
 */
@Mapper
@Component
public interface BudgetFirst3yearsPeriodQuotaMapper {

    int insertBatchBudgetFirst3yearsPeriodQuotaList(HashMap paramMap);

    int deleteBudgetFirst3yearsPeriodQuotaByStationId(Long assetsStationId);

    List<BudgetFirst3yearsPeriodQuota> selectBudgetFirst3yearsPeriodQuotaListByStationId(Long assetsStationId);
}