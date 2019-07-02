package com.wanma.eichong.assets.mapper;

import com.wanma.eichong.assets.entity.BudgetFirstYearsCost;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.4 立项预算表-2.2首年运营成本表（导入数据） budget_first_years_cost
 */
@Mapper
@Component
public interface BudgetFirstYearsCostMapper {

    int insertBatchBudgetFirstYearsCostList(HashMap paramMap);

    int deleteBudgetFirstYearsCostByStationId(Long assetsStationId);

    List<BudgetFirstYearsCost> selectBudgetFirstYearsCostListByStationId(Long assetsStationId);
}
