package com.wanma.eichong.assets.controller;

import com.wanma.eichong.assets.entity.*;
import com.wanma.eichong.assets.config.ResultEnum;
import com.wanma.eichong.assets.service.BaseEquipmentModelService;
import com.wanma.eichong.assets.service.BaseEquipmentService;
import com.wanma.eichong.assets.utils.JsonResult;
import com.wanma.eichong.assets.utils.ObjectUtil;
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
public class BaseEquipmentController extends BaseController{
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final BaseEquipmentService baseEquipmentService;
    private final BaseEquipmentModelService baseEquipmentModelService;

    @Autowired
    public BaseEquipmentController(BaseEquipmentService baseEquipmentService, BaseEquipmentModelService baseEquipmentModelService) {
        this.baseEquipmentService = baseEquipmentService;
        this.baseEquipmentModelService = baseEquipmentModelService;
    }

    /**
     *  设备列表查询
     */
    @RequestMapping(value = "/baseEquipment/queryEqu5tList")
    public JsonResult queryEqu5tList(BaseEquipment baseEquipment,Pager pager) {
        JsonResult result = new JsonResult();
        try {
            Long total = baseEquipmentService.selectBaseEquipmentCount(baseEquipment);
            if (total <= pager.getOffset()) {
                pager.setPageNo(1L);
            }
            baseEquipment.setPager(pager);
            List<BaseEquipment> equipmentList =  baseEquipmentService.selectBaseEquipmentList(baseEquipment);
            pager.setTotal(total);
            Map<String,Object> map = new HashMap<>();
            map.put("baseEquipmentList",equipmentList);
            map.put("pager",pager);
            result.setDataObj(map);
        }catch (Exception e){
            LOGGER.error(this.getClass() + ".queryEqu5tList() error:", e);
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return result;
    }

    /**
     *  根据设备id查询设备型号列表
     */
    @RequestMapping(value = "/baseEquipment/queryEqu5tModelListByEqu5tId")
    public JsonResult queryEqu5tModelListByEqu5tId(Long equ5tId) {
        JsonResult result = new JsonResult();
        try {
            List<BaseEquipmentModel> equipmentModelList =  baseEquipmentModelService.selectBaseEqu5tModelByEqu5tId(equ5tId);
            Map<String,Object> map = new HashMap<>();
            map.put("baseEquipmentModelList",equipmentModelList);
            result.setDataObj(map);
        }catch (Exception e){
            LOGGER.error(this.getClass() + ".queryBaseEqu5tModelByEqu5tId() error:", e);
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return result;
    }

    /**
     * 保存设备和型号列表
     */
    @RequestMapping(value = "/baseEquipment/saveEqu5tAndModel")
    public JsonResult saveEqu5tAndModel() {
        JsonResult result = new JsonResult();
        UserAssets loginUser = getUserAssets();
        try {
            String baseEqu5tObj = request.getParameter("baseEqu5tObj");
            BaseEquipment baseEquipment = SerializationUtil.gson2Object(baseEqu5tObj,BaseEquipment.class);
            List<BaseEquipmentModel> equipmentModelList = new ArrayList<>();
            String baseEqu5tModelList = request.getParameter("baseEqu5tModelList");
            if (ObjectUtil.isNotEmpty(baseEqu5tModelList)){
                JSONArray JsonList = JSONArray.fromObject(baseEqu5tModelList);
                for (int i = 0; i < JsonList.size(); i++) {
                    BaseEquipmentModel baseEquipmentModel = (BaseEquipmentModel) JSONObject.toBean(JsonList.getJSONObject(i),BaseEquipmentModel.class);
                    equipmentModelList.add(baseEquipmentModel);
                }
            }
            baseEquipmentService.saveEqu5tAndModel(baseEquipment,equipmentModelList,loginUser);
        }catch (Exception e){
            LOGGER.error(this.getClass() + ".saveEqu5tAndModel() error:", e);
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return result;
    }

    /**
     * 设备列表查询 下拉栏无分页
     */
    @RequestMapping(value = "/baseEqu5t/queryEqu5tList")
    public JsonResult queryEqu5tList(BaseEquipment baseEquipment){
        JsonResult result = new JsonResult();
        try {
            List<BaseEquipment> equipmentList =  baseEquipmentService.selectBaseEquipmentList(baseEquipment);
            Map<String,Object> map = new HashMap<>();
            map.put("baseEquipmentList",equipmentList);
            result.setDataObj(map);
        }catch (Exception e){
            LOGGER.error(this.getClass() + ".baseEqu5t/queryEqu5tList() error:", e);
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return result;
    }

    /**
     * 删除设备
     */
    @RequestMapping(value = "/baseEquipment/deleteEqu5tById")
    public JsonResult deleteEqu5tById(BaseEquipment baseEquipment){
        JsonResult result = new JsonResult();
        try {
            baseEquipmentService.deleteEqu5tById(baseEquipment);
        }catch (Exception e){
            LOGGER.error(this.getClass() + ".baseEquipment/deleteEqu5tById() error:", e);
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return result;
    }

}
