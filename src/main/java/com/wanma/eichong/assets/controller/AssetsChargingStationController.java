package com.wanma.eichong.assets.controller;

import com.google.common.collect.Lists;
import com.wanma.eichong.assets.config.Constants;
import com.wanma.eichong.assets.entity.AssetsChargingStation;
import com.wanma.eichong.assets.entity.AssetsEquipmentList;
import com.wanma.eichong.assets.entity.UserAssets;
import com.wanma.eichong.assets.config.ResultEnum;
import com.wanma.eichong.assets.service.AssetsChargingStationService;
import com.wanma.eichong.assets.service.AssetsEquipmentListService;
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

@RestController
@RequestMapping("/chargingStation")
public class AssetsChargingStationController extends BaseController{
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final AssetsChargingStationService assetsChargingStationService;
    private final AssetsEquipmentListService assetsEquipmentListService;

    @Autowired
    public AssetsChargingStationController(AssetsChargingStationService assetsChargingStationService, AssetsEquipmentListService assetsEquipmentListService) {
        this.assetsChargingStationService = assetsChargingStationService;
        this.assetsEquipmentListService = assetsEquipmentListService;
    }

    /**
     *  根据市编号查询站点列表
     */
    @RequestMapping(value = "queryChargingStationListByCityCode")
    public JsonResult queryChargingStationListByCityCode(String cityCode) {
        JsonResult result = new JsonResult();
        try {
            List<AssetsChargingStation> chargingStationList =
                    assetsChargingStationService.selectChargingStationListByCityCode(cityCode);
            Map<String,Object> map = new HashMap<>();
            map.put("chargingStationList",chargingStationList);
            result.setDataObj(map);
        }catch (Exception e){
            LOGGER.error(this.getClass() + ".queryChargingStationListByCityCode() error:", e);
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return result;
    }

    /**
     *  电站资产列表分页查询
     */
    @RequestMapping(value = "queryChargingStationList")
    public JsonResult queryChargingStationList(AssetsChargingStation assetsChargingStation,Pager pager) {
        JsonResult result = new JsonResult();
        try {
            Long total = assetsChargingStationService.selectChargingStationCount(assetsChargingStation);
            if (total <= pager.getOffset()) {
                pager.setPageNo(1L);
            }
            assetsChargingStation.setPager(pager);
            List<AssetsChargingStation> chargingStationList =
                    assetsChargingStationService.selectChargingStationList(assetsChargingStation);
            pager.setTotal(total);
            Map<String,Object> map = new HashMap<>();
            map.put("chargingStationList",chargingStationList);
            map.put("pager",pager);
            result.setDataObj(map);
        }catch (Exception e){
            LOGGER.error(this.getClass() + ".queryChargingStationList() error:", e);
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return result;
    }

    /**
     *  保存电站和设备清单列表信息
     */
    @RequestMapping(value = "saveChargingStation")
    public JsonResult saveChargingStation() {
        JsonResult result = new JsonResult();
        UserAssets loginUser = getUserAssets();
        try {
            String assetsChargingStationObj = request.getParameter("assetsChargingStationObj");
            AssetsChargingStation chargingStation = SerializationUtil.gson2Object(assetsChargingStationObj,AssetsChargingStation.class);
            List<AssetsEquipmentList> equipmentList = new ArrayList<>();
            String assetsEquipmentList = request.getParameter("assetsEquipmentList");
            if (ObjectUtil.isNotEmpty(assetsEquipmentList)){
                JSONArray JsonList = JSONArray.fromObject(assetsEquipmentList);
                for (int i = 0; i < JsonList.size(); i++) {
                    AssetsEquipmentList equipment = (AssetsEquipmentList) JSONObject.toBean(JsonList.getJSONObject(i),AssetsEquipmentList.class);
                    equipmentList.add(equipment);
                }
            }
            BaseResultDTO resultDTO = assetsChargingStationService.saveChargingStation(chargingStation,equipmentList,loginUser);
            if (resultDTO.isFailed()) {
                result.initError(Integer.valueOf(resultDTO.getResultCode()),resultDTO.getErrorDetail());
            }else {
                result.initError(ResultEnum.SAVE_SUCCESS);
            }
        }catch (Exception e){
            LOGGER.error(this.getClass() + ".saveChargingStation() error:", e);
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return result;
    }

    /**
     * 根据站点id查询电站和设备清单信息
     */
    @RequestMapping(value = "queryChargingStationByStationId")
    public JsonResult queryChargingStationByStationId(Long  assetsStationId) {
        JsonResult result = new JsonResult();
        try {
            AssetsChargingStation chargingStation = assetsChargingStationService.selectChargingStationById(assetsStationId);
            List<AssetsEquipmentList> equipmentList = assetsEquipmentListService.selectEquipmentListByStationId(assetsStationId);
            Map<String,Object> map = new HashMap<>();
            map.put("chargingStationObj",chargingStation);
            map.put("assetsEquipmentList",equipmentList);
            result.setDataObj(map);
        }catch (Exception e){
            LOGGER.error(this.getClass() + ".queryChargingStationByStationId() error:", e);
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return result;
    }

    /**
     * 导出
     */
    @RequestMapping(value = "exportChargingStationList")
    public JsonResult exportChargingStationList(AssetsChargingStation assetsChargingStation) {
        JsonResult result = new JsonResult();
        try {
            List<AssetsChargingStation> chargingStationList =
                    assetsChargingStationService.selectChargingStationListForExcel(assetsChargingStation);
            List<String> attrList = Lists.newArrayList("stationName", "stationCode", "stationStageStr", "power", "cost", "cityName",
                    "projectLeader","approvalFlowingCode","createTime","modifier");
            List<String> header = Lists.newArrayList("充电站名称", "充电站编号", "阶段", "总功率（kW）", "总投资额(万元)", "城市",
                    "项目负责人","OA流程号","创建时间","操作人");
            ExcelExporterUtil.exportExcel(response, attrList, header, chargingStationList, AssetsChargingStation.class, Constants.FILE_STATION_EXPORT, Constants.FILE_STATION_EXPORT_SHEET);
            chargingStationList.clear();
        }catch (Exception e){
            LOGGER.error(this.getClass() + ".queryChargingStationByStationId() error:", e);
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return result;
    }

    /**
     * 删除充电站资产
     */
    @RequestMapping(value = "deleteChargingStationById")
    public JsonResult deleteChargingStationById(Long assetsStationId) {
        JsonResult result = new JsonResult();
        try {
            assetsChargingStationService.deleteChargingStationById(assetsStationId);
        }catch (Exception e){
            LOGGER.error(this.getClass() + ".queryChargingStationByStationId() error:", e);
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return result;
    }

    /**
     *  全量查询没有协议的站点
     */
    @RequestMapping(value = "queryChargingStationListForFieldAgreement")
    public JsonResult queryChargingStationListForFieldAgreement() {
        JsonResult result = new JsonResult();
        try {
            List<AssetsChargingStation> chargingStationList =
                    assetsChargingStationService.queryChargingStationListForFieldAgreement();
            Map<String,Object> map = new HashMap<>();
            map.put("chargingStationList",chargingStationList);
            result.setDataObj(map);
        }catch (Exception e){
            LOGGER.error(this.getClass() + ".queryChargingStationListForFieldAgreement() error:", e);
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return result;
    }
}
