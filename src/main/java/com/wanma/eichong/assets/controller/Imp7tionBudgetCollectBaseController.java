package com.wanma.eichong.assets.controller;

import com.wanma.eichong.assets.entity.Imp7tionBudgetCollectBase;
import com.wanma.eichong.assets.entity.Imp7tionBudgetCollectList;
import com.wanma.eichong.assets.config.ResultEnum;
import com.wanma.eichong.assets.service.Imp7tionBudgetCollectBaseService;
import com.wanma.eichong.assets.service.Imp7tionBudgetCollectListService;
import com.wanma.eichong.assets.utils.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class Imp7tionBudgetCollectBaseController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final Imp7tionBudgetCollectBaseService imp7tionBudgetCollectBaseService;
    private final Imp7tionBudgetCollectListService imp7tionBudgetCollectListService;

    @Autowired
    public Imp7tionBudgetCollectBaseController(Imp7tionBudgetCollectBaseService imp7tionBudgetCollectBaseService, Imp7tionBudgetCollectListService imp7tionBudgetCollectListService) {
        this.imp7tionBudgetCollectBaseService = imp7tionBudgetCollectBaseService;
        this.imp7tionBudgetCollectListService = imp7tionBudgetCollectListService;
    }

    /**
     *  1.7 实施预算汇总表
     *  1.8 实施预算汇总表
     */
    @RequestMapping(value = "/imp7tionBudget/queryImp7tionBudgetCollect")
    public JsonResult queryImp7tionBudgetCollect(Long assetsStationId) {
        JsonResult result = new JsonResult();
        try {
            Imp7tionBudgetCollectBase budgetCollectBase =
                    imp7tionBudgetCollectBaseService.selectImp7tionBudgetCollectBaseByStationId(assetsStationId);
            List<Imp7tionBudgetCollectList> budgetCollectListList = new ArrayList<>();
            if (budgetCollectBase !=null){
                budgetCollectListList = imp7tionBudgetCollectListService.selectImp7tionBudgetCollectListByStationId(budgetCollectBase.getBudgetCollectId());
            }
            Map<String,Object> map = new HashMap<>();
            map.put("imp7tionBudgetCollectBaseObj",budgetCollectBase);
            map.put("imp7tionBudgetCollectList",budgetCollectListList);
            result.setDataObj(map);
        }catch (Exception e){
            LOGGER.error(this.getClass() + ".queryBudgetBaseByStationId() error:", e);
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return result;
    }








}
