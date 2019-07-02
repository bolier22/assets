package com.wanma.eichong.assets.controller;

import com.wanma.eichong.assets.entity.OperateInfo;
import com.wanma.eichong.assets.entity.OperateInfoList;
import com.wanma.eichong.assets.entity.UserAssets;
import com.wanma.eichong.assets.config.ResultEnum;
import com.wanma.eichong.assets.service.OperateInfoService;
import com.wanma.eichong.assets.utils.JsonResult;
import com.wanma.eichong.assets.utils.Pager;
import com.wanma.eichong.assets.utils.SerializationUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
public class OperateInfoController extends BaseController{
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final OperateInfoService operateInfoService;

    @Autowired
    public OperateInfoController(OperateInfoService operateInfoService) {
        this.operateInfoService = operateInfoService;
    }

    /**
     *  根据站点id分页查询运营数据列表
     */
    @RequestMapping(value = "/operateInfo/queryOperateListByStationId")
    public JsonResult queryOperateListByStationId(OperateInfo operateInfo, Pager pager) {
        JsonResult result = new JsonResult();
        try {
            Long total = operateInfoService.selectOperateCountByStationId(operateInfo);
            if (total <= pager.getOffset()) {
                pager.setPageNo(1L);
            }
            operateInfo.setPager(pager);
            List<OperateInfo> operateInfoList =  operateInfoService.selectOperateListByStationId(operateInfo);
            pager.setTotal(total);
            Map<String,Object> map = new HashMap<>();
            map.put("operateInfoList",operateInfoList);
            map.put("pager",pager);
            result.setDataObj(map);
        }catch (Exception e){
            LOGGER.error(this.getClass() + ".queryOperateListByStationId() error:", e);
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return result;
    }

    /**
     * 根据运营id删除信息
     */
    @RequestMapping(value = "/operateInfo/deleteOperateByOperateId")
    public JsonResult queryOperateListByStationId(Long operateId) {
        JsonResult result = new JsonResult();
        try {
            operateInfoService.deleteOperateByOperateId(operateId);
        }catch (Exception e){
            LOGGER.error(this.getClass() + ".deleteOperateByOperateId() error:", e);
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return result;
    }

    /**
     * 根据运营id查询详情
     */
    @RequestMapping(value = "/operateInfo/queryOperateByOperateId")
    public JsonResult queryOperateByOperateId(Long operateId) {
        JsonResult result = new JsonResult();
        try {
            OperateInfo operateInfo = operateInfoService.selectOperateByOperateId(operateId);
            List<OperateInfoList> operateInfoListList = operateInfoService.selectOperateInfoListListByOperateId(operateId);
            Map<String,Object> map = new HashMap<>();
            map.put("operateInfoObj",operateInfo);
            map.put("operateInfoList",operateInfoListList);
            result.setDataObj(map);
        }catch (Exception e){
            LOGGER.error(this.getClass() + ".deleteOperateByOperateId() error:", e);
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return result;
    }

    /**
     * 保存运营信息
     */
    @RequestMapping(value = "/operateInfo/saveOperateInfo")
    public JsonResult saveOperateInfo() {
        JsonResult result = new JsonResult();

        UserAssets loginUser = getUserAssets();
        try {
            String operateInfoObj = request.getParameter("operateInfoObj");
            OperateInfo operateInfo = SerializationUtil.gson2Object(operateInfoObj,OperateInfo.class);
            List<OperateInfoList> operateInfoListList = new ArrayList<>();
            String operateInfoListObj = request.getParameter("operateInfoListObj");
            JSONArray JsonList = JSONArray.fromObject(operateInfoListObj);
            for (int i = 0; i < JsonList.size(); i++) {
                OperateInfoList operateInfoList = (OperateInfoList) JSONObject.toBean(JsonList.getJSONObject(i),OperateInfoList.class);
                operateInfoListList.add(operateInfoList);
            }
            operateInfoService.saveOperateInfo(operateInfo,operateInfoListList,loginUser);
        }catch (Exception e){
            LOGGER.error(this.getClass() + ".deleteOperateByOperateId() error:", e);
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return result;
    }
}
