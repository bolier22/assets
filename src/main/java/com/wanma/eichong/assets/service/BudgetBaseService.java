package com.wanma.eichong.assets.service;

import com.wanma.eichong.assets.entity.BudgetBase;
import com.wanma.eichong.assets.entity.BudgetCompletePeriodQuota;
import com.wanma.eichong.assets.entity.BudgetFirst3yearsPeriodQuota;
import com.wanma.eichong.assets.entity.BudgetFirstYearsCost;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.3 立项预算表-2.1基表信息表（导入数据）
 */
public interface BudgetBaseService {
    int insertBatchBudgetList(List<Object> obj,Integer fileType);

    int deleteBudgetByStationId(Long assetsStationId,Integer fileType);

    List<BudgetBase> selectBudgetBaseListByStationId(Long assetsStationId);

    List<BudgetFirstYearsCost> selectBudgetFirstYearsCostListByStationId(Long assetsStationId);

    List<BudgetFirst3yearsPeriodQuota> selectBudgetFirst3yearsPeriodQuotaListByStationId(Long assetsStationId);

    List<BudgetCompletePeriodQuota> selectBudgetCompletePeriodQuotaListByStationId(Long assetsStationId);
}