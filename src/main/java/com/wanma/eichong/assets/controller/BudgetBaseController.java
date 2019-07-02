package com.wanma.eichong.assets.controller;

import com.wanma.eichong.assets.entity.*;
import com.wanma.eichong.assets.config.ResultEnum;
import com.wanma.eichong.assets.service.BudgetBaseService;
import com.wanma.eichong.assets.utils.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 立项excel信息查询
 */
@RestController
public class BudgetBaseController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final BudgetBaseService budgetBaseService;

    @Autowired
    public BudgetBaseController(BudgetBaseService budgetBaseService) {
        this.budgetBaseService = budgetBaseService;
    }




    /**
     *  立项预算表-2.1基表信息表信息
     */
    @RequestMapping(value = "/budget/queryBudgetBaseByStationId")
    public JsonResult queryBudgetBaseByStationId(Long assetsStationId) {
        JsonResult result = new JsonResult();
        try {
            List<BudgetBase> budgetBaseList =budgetBaseService.selectBudgetBaseListByStationId(assetsStationId);
            Map<String,Object> map = new HashMap<>();
            map.put("budgetBase",budgetBaseList);
            result.setDataObj(map);
        }catch (Exception e){
            LOGGER.error(this.getClass() + ".queryBudgetBaseByStationId() error:", e);
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return result;
    }

    /**
     *  立项预算表-2.2首年运营成本表信息
     */
    @RequestMapping(value = "/budget/queryBudget1yearByStationId")
    public JsonResult queryBudget1yearByStationId(Long assetsStationId) {
        JsonResult result = new JsonResult();
        try {
            List<BudgetFirstYearsCost> budgetFirstYearsCostList =budgetBaseService.selectBudgetFirstYearsCostListByStationId(assetsStationId);
            Map<String,Object> map = new HashMap<>();
            map.put("budgetFirstYearsCost",budgetFirstYearsCostList);
            result.setDataObj(map);
        }catch (Exception e){
            LOGGER.error(this.getClass() + ".queryBudget1yearByStationId() error:", e);
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return result;
    }

    /**
     *  2.3前三年运营指标信息
     */
    @RequestMapping(value = "/budget/queryBudget3yearByStationId")
    public JsonResult queryBudget3yearByStationId(Long assetsStationId) {
        JsonResult result = new JsonResult();
        try {
            List<BudgetFirst3yearsPeriodQuota> budgetFirst3yearsPeriodQuotaList =
                    budgetBaseService.selectBudgetFirst3yearsPeriodQuotaListByStationId(assetsStationId);
            Map<String,Object> map = new HashMap<>();
            map.put("budgetFirst3yearsPeriodQuota",budgetFirst3yearsPeriodQuotaList);
            result.setDataObj(map);
        }catch (Exception e){
            LOGGER.error(this.getClass() + ".queryBudget3yearByStationId() error:", e);
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return result;
    }

    /**
     *  立项预算表-2.4全周期运营指标表
     */
    @RequestMapping(value = "/budget/queryBudgetQuotaByStationId")
    public JsonResult queryBudgetQuotaByStationId(Long assetsStationId) {
        JsonResult result = new JsonResult();
        try {
            List<BudgetCompletePeriodQuota> budgetCompletePeriodQuotaList =
                    budgetBaseService.selectBudgetCompletePeriodQuotaListByStationId(assetsStationId);
            Map<String,Object> map = new HashMap<>();
            map.put("budgetCompletePeriodQuota",budgetCompletePeriodQuotaList);
            result.setDataObj(map);
        }catch (Exception e){
            LOGGER.error(this.getClass() + ".queryBudget3yearByStationId() error:", e);
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return result;
    }
}
