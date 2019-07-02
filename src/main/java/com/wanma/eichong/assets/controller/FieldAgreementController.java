package com.wanma.eichong.assets.controller;

import com.wanma.eichong.assets.entity.FieldAgreement;
import com.wanma.eichong.assets.entity.FieldAgreementDetail;
import com.wanma.eichong.assets.entity.UserAssets;
import com.wanma.eichong.assets.config.ResultEnum;
import com.wanma.eichong.assets.service.FieldAgreementService;
import com.wanma.eichong.assets.utils.*;
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

/**
 * 协议
 */
@RestController
public class FieldAgreementController extends BaseController{
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final FieldAgreementService fieldAgreementService;

    @Autowired
    public FieldAgreementController(FieldAgreementService fieldAgreementService) {
        this.fieldAgreementService = fieldAgreementService;
    }

    /**
     *  协议列表查询
     */
    @RequestMapping(value = "/fieldAgreement/queryFieldAgr5tList")
    public JsonResult queryFieldAgr5tList(FieldAgreement fieldAgreement, Pager pager) {
        JsonResult result = new JsonResult();
        try {
            List<FieldAgreement> fieldAgreementList = new ArrayList<>();
            BaseResultDTO resultDTO = fieldAgreementService.modifyFieldAgreementForSelect(fieldAgreement);
            if (resultDTO.isSuccess()){
                Long total = fieldAgreementService.selectFieldAgreementCount(fieldAgreement);
                if (total <= pager.getOffset()) {
                    pager.setPageNo(1L);
                }
                fieldAgreement.setPager(pager);
                fieldAgreementList =  fieldAgreementService.selectFieldAgreementList(fieldAgreement);
                pager.setTotal(total);
            }
            Map<String,Object> map = new HashMap<>();
            map.put("fieldAgreementList",fieldAgreementList);
            map.put("pager",pager);
            result.setDataObj(map);
        }catch (Exception e){
            LOGGER.error(this.getClass() + ".queryEqu5tList() error:", e);
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return result;
    }

    /**
     * 协议删除
     */
    @RequestMapping(value = "/fieldAgreement/deleteFieldAgreement")
    public JsonResult deleteFieldAgreement(Long fieldAgr5tId) {
        JsonResult result = new JsonResult();
        try {
            fieldAgreementService.deleteFieldAgreement(fieldAgr5tId);
        }catch (Exception e){
            LOGGER.error(this.getClass() + ".deleteFieldAgreement() error:", e);
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return  result;
    }

    /**
     * 协议新建
     */
    @RequestMapping(value = "/fieldAgreement/saveFieldAgr5tList")
    public JsonResult saveFieldAgr5tList() {
        JsonResult result = new JsonResult();
        UserAssets loginUser = getUserAssets();
        try {
            String fieldAgr5tObj = request.getParameter("fieldAgr5tObj");
            FieldAgreement fieldAgreement = SerializationUtil.gson2Object(fieldAgr5tObj,FieldAgreement.class);
            List<FieldAgreementDetail> fieldAgreementDetailList = new ArrayList<>();
            String fieldAgr5tDetailList = request.getParameter("fieldAgr5tDetailList");
            if (ObjectUtil.isNotEmpty(fieldAgr5tDetailList)){
                JSONArray JsonList = JSONArray.fromObject(fieldAgr5tDetailList);
                for (int i = 0; i < JsonList.size(); i++) {
                    FieldAgreementDetail detail = (FieldAgreementDetail) JSONObject.toBean(JsonList.getJSONObject(i),FieldAgreementDetail.class);
                    fieldAgreementDetailList.add(detail);
                }
            }
            fieldAgreementService.saveFieldAgr5tList(fieldAgreement,fieldAgreementDetailList,loginUser);
        }catch (Exception e){
            LOGGER.error(this.getClass() + ".deleteFieldAgreement() error:", e);
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return  result;
    }

    /**
     * 根据协议id查询协议信息
     */
    @RequestMapping(value = "/fieldAgreement/queryFieldAgr5tByFieldAgr5tId")
    public JsonResult queryFieldAgr5tByFieldAgr5tId(Long fieldAgr5tId) {
        JsonResult result = new JsonResult();
        try {
            FieldAgreement fieldAgreement =  fieldAgreementService.selectFieldAgreementById(fieldAgr5tId);
            List<FieldAgreementDetail> fieldAgreementDetailList = fieldAgreementService.selectFieldAgreementDetailList(fieldAgr5tId);
            Map<String,Object> map = new HashMap<>();
            map.put("fieldAgr5tObj",fieldAgreement);
            map.put("fieldAgr5tDetailList",fieldAgreementDetailList);
            result.setDataObj(map);
        }catch (Exception e){
            LOGGER.error(this.getClass() + ".queryFieldAgr5tByFieldAgr5tId() error:", e);
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return  result;
    }
}
