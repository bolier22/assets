package com.wanma.eichong.assets.controller;

import com.wanma.eichong.assets.entity.FinalAccountsCollect;
import com.wanma.eichong.assets.entity.FinalAccountsCollectList;
import com.wanma.eichong.assets.config.ResultEnum;
import com.wanma.eichong.assets.service.FinalAccountsCollectListService;
import com.wanma.eichong.assets.service.FinalAccountsCollectService;
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
public class FinalAccountsCollectController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final FinalAccountsCollectService finalAccountsCollectService;
    private final FinalAccountsCollectListService finalAccountsCollectListService;

    @Autowired
    public FinalAccountsCollectController(FinalAccountsCollectService finalAccountsCollectService, FinalAccountsCollectListService finalAccountsCollectListService) {
        this.finalAccountsCollectService = finalAccountsCollectService;
        this.finalAccountsCollectListService = finalAccountsCollectListService;
    }



    /**
     *  查询决算汇总表及条目信息
     */
    @RequestMapping(value = "/fina8sCollect/queryFina8sCollectByStationId")
    public JsonResult queryFina8sCollectByStationId(Long assetsStationId) {
        JsonResult result = new JsonResult();
        try {
            FinalAccountsCollect finalAccountsCollect =
                    finalAccountsCollectService.selectFinalAccountsCollectByStationId(assetsStationId);
            List<FinalAccountsCollectList> finalAccountsCollectList = new ArrayList<>();
            if (finalAccountsCollect !=null){
                finalAccountsCollectList = finalAccountsCollectListService.selectFinalAccountsCollectListListByStationId(finalAccountsCollect.getFinal8sCollectId());
            }
            Map<String,Object> map = new HashMap<>();
            map.put("finalAccountsCollectObj",finalAccountsCollect);
            map.put("finalAccountsCollectList",finalAccountsCollectList);
            result.setDataObj(map);
        }catch (Exception e){
            LOGGER.error(this.getClass() + ".queryBudgetBaseByStationId() error:", e);
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return result;
    }
}
