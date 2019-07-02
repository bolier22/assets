package com.wanma.eichong.assets.mapper;

import com.wanma.eichong.assets.entity.BudgetBase;
import com.wanma.eichong.assets.entity.BudgetFirstYearsCost;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.3 立项预算表-2.1基表信息表（导入数据）
 */
@Mapper
@Component
public interface BudgetBaseMapper {

   int insertBatchBudgetBaseList(HashMap paramMap);

   List<BudgetBase> selectBudgetBaseListByStationId(Long assetsStationId);

   int deleteBudgetBaseByStationId(Long assetsStationId);
}