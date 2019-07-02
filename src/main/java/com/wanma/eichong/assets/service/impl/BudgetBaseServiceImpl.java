package com.wanma.eichong.assets.service.impl;

import com.wanma.eichong.assets.entity.BudgetBase;
import com.wanma.eichong.assets.entity.BudgetCompletePeriodQuota;
import com.wanma.eichong.assets.entity.BudgetFirst3yearsPeriodQuota;
import com.wanma.eichong.assets.entity.BudgetFirstYearsCost;
import com.wanma.eichong.assets.mapper.*;
import com.wanma.eichong.assets.service.BudgetBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.3 立项预算表-2.1基表信息表（导入数据）
 */
@Service
public class BudgetBaseServiceImpl implements BudgetBaseService {
    private static final Logger logger = LoggerFactory.getLogger(BudgetBaseServiceImpl.class);

    private final BudgetBaseMapper budgetBaseMapper;
    private final BudgetFirstYearsCostMapper budgetFirstYearsCostMapper;
    private final BudgetFirst3yearsPeriodQuotaMapper budgetFirst3yearsPeriodQuotaMapper;
    private final BudgetCompletePeriodQuotaMapper budgetCompletePeriodQuotaMapper;

    @Autowired
    public BudgetBaseServiceImpl(BudgetBaseMapper budgetBaseMapper, BudgetFirstYearsCostMapper budgetFirstYearsCostMapper, BudgetFirst3yearsPeriodQuotaMapper budgetFirst3yearsPeriodQuotaMapper, BudgetCompletePeriodQuotaMapper budgetCompletePeriodQuotaMapper, FileListMapper fileListMapper) {
        this.budgetBaseMapper = budgetBaseMapper;
        this.budgetFirstYearsCostMapper = budgetFirstYearsCostMapper;
        this.budgetFirst3yearsPeriodQuotaMapper = budgetFirst3yearsPeriodQuotaMapper;
        this.budgetCompletePeriodQuotaMapper = budgetCompletePeriodQuotaMapper;
    }

    private static HashMap paramMap = new HashMap<>();
    public static HashMap getInstanceParamMap() {
        if (paramMap == null) {
            synchronized (BudgetBaseServiceImpl.class) {
                if (paramMap == null) {
                    paramMap = new HashMap();
                }
            }
        }
        paramMap.clear();
        return paramMap;
    }


    @Transactional
    @Override
    public int insertBatchBudgetList(List<Object> obj,Integer fileType) {
        int resultCode = 0;
        try{
            List<Object> budgetListObj = new ArrayList<Object>();
            budgetListObj = (List<Object>) obj.get(0);//              BudgetBase budgetBase
            String code = budgetListObj.get(0).toString();
            if("1".equals(code)){
                List<BudgetBase> budgetBaseList = (List<BudgetBase>) budgetListObj.get(1);
                BudgetBaseServiceImpl.getInstanceParamMap().put("budgetBaseList",budgetBaseList);
                if(deleteBudgetByStationIdNoTransactional(budgetBaseList.get(0).getAssetsStationId(),fileType) == 0){
                    return 0;
                }
                budgetBaseMapper.insertBatchBudgetBaseList(paramMap);
                resultCode += 1;
            }

            budgetListObj = (List<Object>) obj.get(1);//              BudgetFirstYearsCost budgetFirstYearsCost
            code = budgetListObj.get(0).toString();
            if("1".equals(code)){
                List<BudgetFirstYearsCost> budgetBaseList = (List<BudgetFirstYearsCost>) budgetListObj.get(1);
                BudgetBaseServiceImpl.getInstanceParamMap().put("budgetBaseList",budgetBaseList);
                budgetFirstYearsCostMapper.insertBatchBudgetFirstYearsCostList(paramMap);
                resultCode += 1;
            }


            budgetListObj = (List<Object>) obj.get(2);//              BudgetFirst3yearsPeriodQuota budgetFirst3yearsPeriodQuota
            code = budgetListObj.get(0).toString();
            if("1".equals(code)){
                List<BudgetFirst3yearsPeriodQuota> budgetBaseList = (List<BudgetFirst3yearsPeriodQuota>) budgetListObj.get(1);
                BudgetBaseServiceImpl.getInstanceParamMap().put("budgetBaseList",budgetBaseList);
                budgetFirst3yearsPeriodQuotaMapper.insertBatchBudgetFirst3yearsPeriodQuotaList(paramMap);
                resultCode += 1;
            }

            budgetListObj = (List<Object>) obj.get(3);//              BudgetCompletePeriodQuota budgetCompletePeriodQuota
            code = budgetListObj.get(0).toString();
            if("1".equals(code)){
                List<BudgetCompletePeriodQuota> budgetBaseList = (List<BudgetCompletePeriodQuota>) budgetListObj.get(1);
                BudgetBaseServiceImpl.getInstanceParamMap().put("budgetBaseList",budgetBaseList);
                budgetCompletePeriodQuotaMapper.insertBatchBudgetCompletePeriodQuotaList(paramMap);
                resultCode += 1;
            }
        }catch (Exception e){
            logger.error(this.getClass() + ".insertBatchBudgetList is error=STRAT#{}#END",e.getMessage());
        }
        return resultCode == 4 ? resultCode:0;
    }


    @Transactional
    @Override
    public int deleteBudgetByStationId(Long assetsStationId,Integer fileType){
        return deleteBudgetByStationIdNoTransactional(assetsStationId,fileType);
    }

    public int deleteBudgetByStationIdNoTransactional(Long assetsStationId,Integer fileType){
        try{
            budgetBaseMapper.deleteBudgetBaseByStationId(assetsStationId);
            budgetFirstYearsCostMapper.deleteBudgetFirstYearsCostByStationId(assetsStationId);
            budgetFirst3yearsPeriodQuotaMapper.deleteBudgetFirst3yearsPeriodQuotaByStationId(assetsStationId);
            budgetCompletePeriodQuotaMapper.deleteBudgetCompletePeriodQuotaByStationId(assetsStationId);
        }catch (Exception e){
            logger.error(this.getClass() + ".deleteBudgetByStationId is error=STRAT#{}#END",e.getMessage());
            return 0;
        }
        return 1;
    }

    @Override
    public List<BudgetBase> selectBudgetBaseListByStationId(Long assetsStationId) {
        return budgetBaseMapper.selectBudgetBaseListByStationId(assetsStationId);
    }

    @Override
    public List<BudgetFirstYearsCost> selectBudgetFirstYearsCostListByStationId(Long assetsStationId) {
        return budgetFirstYearsCostMapper.selectBudgetFirstYearsCostListByStationId(assetsStationId);
    }

    @Override
    public List<BudgetFirst3yearsPeriodQuota> selectBudgetFirst3yearsPeriodQuotaListByStationId(Long assetsStationId) {
        return budgetFirst3yearsPeriodQuotaMapper.selectBudgetFirst3yearsPeriodQuotaListByStationId(assetsStationId);
    }

    @Override
    public List<BudgetCompletePeriodQuota> selectBudgetCompletePeriodQuotaListByStationId(Long assetsStationId) {
        return budgetCompletePeriodQuotaMapper.selectBudgetCompletePeriodQuotaListByStationId(assetsStationId);
    }
}