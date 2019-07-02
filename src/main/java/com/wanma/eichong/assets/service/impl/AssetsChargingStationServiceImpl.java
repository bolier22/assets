package com.wanma.eichong.assets.service.impl;

import com.google.common.collect.Maps;
import com.wanma.eichong.assets.config.Constants;
import com.wanma.eichong.assets.config.ResultCodeConstants;
import com.wanma.eichong.assets.entity.*;
import com.wanma.eichong.assets.mapper.*;
import com.wanma.eichong.assets.service.*;
import com.wanma.eichong.assets.utils.BaseResultDTO;
import com.wanma.eichong.assets.utils.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.1 充电站资产基表 assets_charging_station
 */
@Service
public class AssetsChargingStationServiceImpl implements AssetsChargingStationService {
    private final AssetsChargingStationMapper assetsChargingStationMapper;
    private final UserAssetsService userAssetsService;
    private final AssetsEquipmentListMapper assetsEquipmentListMapper;
    private final AreaCodeMapper areaCodeMapper;
    private final BaseEquipmentModelMapper baseEquipmentModelMapper;
    private final BaseEquipmentMapper baseEquipmentMapper;
    private final BudgetBaseMapper budgetBaseMapper;
    private final BudgetCompletePeriodQuotaMapper budgetCompletePeriodQuotaMapper;
    private final BudgetFirst3yearsPeriodQuotaMapper budgetFirst3yearsPeriodQuotaMapper;
    private final BudgetFirstYearsCostMapper budgetFirstYearsCostMapper;
    @Autowired
    private  FieldAgreementService fieldAgreementService;
    private final FileListService fileListService;
    private final FinalAccountsCollectMapper finalAccountsCollectMapper;
    private final FinalAccountsCollectListMapper finalAccountsCollectListMapper;
    private final Imp7tionBudgetCollectBaseMapper imp7tionBudgetCollectBaseMapper;
    private final Imp7tionBudgetCollectListMapper imp7tionBudgetCollectListMapper;
    private final OperateInfoService operateInfoService;

    @Autowired
    public AssetsChargingStationServiceImpl(AssetsChargingStationMapper assetsChargingStationMapper, UserAssetsService userAssetsService, AssetsEquipmentListMapper assetsEquipmentListMapper, AreaCodeMapper areaCodeMapper,
                                            BaseEquipmentModelMapper baseEquipmentModelMapper, BaseEquipmentMapper baseEquipmentMapper, BudgetBaseMapper budgetBaseMapper, BudgetCompletePeriodQuotaMapper budgetCompletePeriodQuotaMapper, BudgetFirst3yearsPeriodQuotaMapper budgetFirst3yearsPeriodQuotaMapper,
                                            BudgetFirstYearsCostMapper budgetFirstYearsCostMapper,
                                            FileListService fileListService, FinalAccountsCollectMapper finalAccountsCollectMapper, FinalAccountsCollectListMapper finalAccountsCollectListMapper, Imp7tionBudgetCollectBaseMapper imp7tionBudgetCollectBaseMapper, Imp7tionBudgetCollectListMapper imp7tionBudgetCollectListMapper,
                                            OperateInfoService operateInfoService) {
        this.assetsChargingStationMapper = assetsChargingStationMapper;
        this.userAssetsService = userAssetsService;
        this.assetsEquipmentListMapper = assetsEquipmentListMapper;
        this.areaCodeMapper = areaCodeMapper;
        this.baseEquipmentModelMapper = baseEquipmentModelMapper;
        this.baseEquipmentMapper = baseEquipmentMapper;
        this.budgetBaseMapper = budgetBaseMapper;
        this.budgetCompletePeriodQuotaMapper = budgetCompletePeriodQuotaMapper;
        this.budgetFirst3yearsPeriodQuotaMapper = budgetFirst3yearsPeriodQuotaMapper;
        this.budgetFirstYearsCostMapper = budgetFirstYearsCostMapper;
        this.fileListService = fileListService;
        this.finalAccountsCollectMapper = finalAccountsCollectMapper;
        this.finalAccountsCollectListMapper = finalAccountsCollectListMapper;
        this.imp7tionBudgetCollectBaseMapper = imp7tionBudgetCollectBaseMapper;
        this.imp7tionBudgetCollectListMapper = imp7tionBudgetCollectListMapper;
        this.operateInfoService = operateInfoService;
    }

    @Override
    public List<AssetsChargingStation> selectChargingStationListByCityCode(String cityCode) {
        return assetsChargingStationMapper.selectChargingStationListByCityCode(cityCode);
    }

    @Override
    public Long selectChargingStationCount(AssetsChargingStation assetsChargingStation) {
        return assetsChargingStationMapper.selectChargingStationCount(assetsChargingStation);
    }

    @Override
    public List<AssetsChargingStation> selectChargingStationList(AssetsChargingStation assetsChargingStation) {
        List<AssetsChargingStation> assetsChargingStationList = assetsChargingStationMapper.selectChargingStationList(assetsChargingStation);
        Set<Long> userIdSet = new HashSet<>();
        for (AssetsChargingStation chargingStation:assetsChargingStationList){
            userIdSet.add(chargingStation.getModifyId());
        }
        Map<Long,UserAssets> userAssetsMap = userAssetsService.selectUserAssetsByIds(new ArrayList<>(userIdSet));
        for (AssetsChargingStation chargingStation:assetsChargingStationList){
            UserAssets userAssets = userAssetsMap.get(chargingStation.getModifyId());
            if (userAssets !=null){
                chargingStation.setModifier(userAssets.getModifier());
            }
            TblAreaCode areaCode = areaCodeMapper.findNameByAreaCode(chargingStation.getCityCode());
            if (areaCode !=null){
                chargingStation.setCityName(areaCode.getAreaName());
            }
        }
        return assetsChargingStationList;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public BaseResultDTO saveChargingStation(AssetsChargingStation chargingStation, List<AssetsEquipmentList> equipmentList,
                                             UserAssets loginUser) {
        BaseResultDTO resultDTO = new BaseResultDTO();
        if (chargingStation.getStationStage().equals(Constants.STATION_STAGE_0)){//新建 只有基本信息
            resultDTO = doNewBuild(chargingStation,loginUser);
        }else {//1：立项，2：实施，3：决算
            doProjectApproval(chargingStation,loginUser,equipmentList);
        }
        return resultDTO;
    }

    /**
     * 新建
     * @param chargingStation 电站基础数据
     * @param loginUser 操作人
     */
    private BaseResultDTO doNewBuild(AssetsChargingStation chargingStation, UserAssets loginUser) {
        if (ObjectUtil.isEmpty(chargingStation.getAssetsStationId())||
                chargingStation.getAssetsStationId()==0){//id 不存在的时候是新建否则是修改
            //先判断是否有重复的
            Long count = assetsChargingStationMapper.checkUnique(chargingStation);
            if (count>0){
                return new BaseResultDTO(false,ResultCodeConstants.CODE_FAILED,"充电点名称、OA流程号或者充电点编号重复！");
            }
            chargingStation.setCreateId(loginUser.getUserId());
            chargingStation.setModifyId(loginUser.getUserId());
            assetsChargingStationMapper.insertAssetsChargingStation(chargingStation);
        }else {
            //先判断是否有重复的
            Long count = assetsChargingStationMapper.checkUnique(chargingStation);
            if (count>0){
                return new BaseResultDTO(false,ResultCodeConstants.CODE_FAILED,"充电点名称、OA流程号或者充电点编号重复！");
            }
            chargingStation.setModifyId(loginUser.getUserId());
            assetsChargingStationMapper.updateAssetsChargingStation(chargingStation);
        }
        return new BaseResultDTO();
    }
    /**
     * 立项
     * @param chargingStation 电站基础数据
     * @param loginUser 操作人
     * @param equipmentList 设备
     */
    private void doProjectApproval(AssetsChargingStation chargingStation, UserAssets loginUser, List<AssetsEquipmentList> equipmentList) {
        chargingStation.setModifyId(loginUser.getUserId());
        assetsChargingStationMapper.updateAssetsChargingStation(chargingStation);
        for (AssetsEquipmentList equipment:equipmentList){
            if (chargingStation.getStationStage().equals(Constants.STATION_STAGE_1)&&ObjectUtil.isNotEmpty(equipment.getApprovalModelId())){
                BaseEquipmentModel equipmentModel = baseEquipmentModelMapper.selectBaseEqu5tModelByModelId(equipment.getApprovalModelId());
                equipment.setApprovalModelName(equipmentModel.getEqu5tModelName());
            }else if (chargingStation.getStationStage().equals(Constants.STATION_STAGE_2)&&ObjectUtil.isNotEmpty(equipment.getImp7tionModelId())){
                BaseEquipmentModel equipmentModel = baseEquipmentModelMapper.selectBaseEqu5tModelByModelId(equipment.getImp7tionModelId());
                equipment.setImp7tionModelName(equipmentModel.getEqu5tModelName());
            }else if (chargingStation.getStationStage().equals(Constants.STATION_STAGE_3)&&ObjectUtil.isNotEmpty(equipment.getFinal8sModelId())){
                BaseEquipmentModel equipmentModel = baseEquipmentModelMapper.selectBaseEqu5tModelByModelId(equipment.getFinal8sModelId());
                equipment.setFinal8sModelName(equipmentModel.getEqu5tModelName());
            }

            if (equipment.getAssetsEqu5tId()==0||ObjectUtil.isEmpty(equipment.getAssetsEqu5tId())){//新增
                equipment.setCreateId(loginUser.getUserId());
                equipment.setModifyId(loginUser.getUserId());
                BaseEquipment baseEquipment = baseEquipmentMapper.selectBaseEquipmentById(equipment.getEqu5tId());
                equipment.setEqu5tName(baseEquipment.getEqu5tName());
                assetsEquipmentListMapper.insertAssetsEquipment(equipment);
            }else {//修改
                equipment.setModifyId(loginUser.getUserId());
                assetsEquipmentListMapper.updateAssetsEquipment(equipment);
            }
        }
    }

    @Override
    public AssetsChargingStation selectChargingStationById(Long assetsStationId) {
        AssetsChargingStation chargingStation =assetsChargingStationMapper.selectChargingStationById(assetsStationId);
        TblAreaCode province = areaCodeMapper.findNameByAreaCode(chargingStation.getProvinceCode());
        TblAreaCode city = areaCodeMapper.findNameByAreaCode(chargingStation.getCityCode());
        chargingStation.setCityName(province.getAreaName()+city.getAreaName());
        return chargingStation;
    }

    @Override
    public Map<Long, AssetsChargingStation> selectChargingStationByIds(List<Long> stationIdList) {
        List<AssetsChargingStation> assetsChargingStationList = assetsChargingStationMapper.selectChargingStationByIds(stationIdList);
        return Maps.uniqueIndex(assetsChargingStationList,AssetsChargingStation::getAssetsStationId);
    }

    @Override
    public List<AssetsChargingStation> selectChargingStationListForExcel(AssetsChargingStation assetsChargingStation) {
        List<AssetsChargingStation> chargingStationList = selectChargingStationList(assetsChargingStation);
        for (AssetsChargingStation chargingStation:chargingStationList){
            if (chargingStation.getStationStage().equals(Constants.STATION_STAGE_0)){
                chargingStation.setStationStageStr("新建");
            }else if (chargingStation.getStationStage().equals(Constants.STATION_STAGE_1)){
                chargingStation.setStationStageStr("立项");
                chargingStation.setPower(chargingStation.getApprovalTotalPower()+"");
                chargingStation.setCost(chargingStation.getApprovalTotalCost()+"");
            }else if (chargingStation.getStationStage().equals(Constants.STATION_STAGE_2)){
                chargingStation.setStationStageStr("实施");
                chargingStation.setPower(chargingStation.getImp7tionTotalPower()+"");
                chargingStation.setCost(chargingStation.getImp7tionTotalCost()+"");
            }else if (chargingStation.getStationStage().equals(Constants.STATION_STAGE_3)){
                chargingStation.setStationStageStr("决算");
                chargingStation.setPower(chargingStation.getFinal8sTotalPower()+"");
                chargingStation.setCost(chargingStation.getFinal8sTotalCost()+"");
            }else if (chargingStation.getStationStage().equals(Constants.STATION_STAGE_4)){
                chargingStation.setStationStageStr("运营");
                chargingStation.setPower(chargingStation.getFinal8sTotalPower()+"");
                chargingStation.setCost(chargingStation.getFinal8sTotalCost()+"");
            }
        }
        return chargingStationList;
    }

    @Override
    @Transactional
    public void deleteChargingStationById(Long assetsStationId) {
        assetsChargingStationMapper.deleteChargingStationById(assetsStationId);
        assetsEquipmentListMapper.deleteEquipmentListByStationId(assetsStationId);
        budgetBaseMapper.deleteBudgetBaseByStationId(assetsStationId);
        budgetCompletePeriodQuotaMapper.deleteBudgetCompletePeriodQuotaByStationId(assetsStationId);
        budgetFirstYearsCostMapper.deleteBudgetFirstYearsCostByStationId(assetsStationId);
        budgetFirst3yearsPeriodQuotaMapper.deleteBudgetFirst3yearsPeriodQuotaByStationId(assetsStationId);
        fieldAgreementService.deleteFieldAgreementByStationId(assetsStationId);
        FinalAccountsCollect finalAccountsCollect = finalAccountsCollectMapper.selectFinalAccountsCollectByStationId(assetsStationId);
        if (finalAccountsCollect !=null){
            finalAccountsCollectMapper.deleteFinalAccountsCollectByStationId(assetsStationId);
            finalAccountsCollectListMapper.deleteFinalAccountsCollectListByCollectId(finalAccountsCollect.getFinal8sCollectId());
        }
        Imp7tionBudgetCollectBase imp7tionBudgetCollectBase =imp7tionBudgetCollectBaseMapper.selectImp7tionBudgetCollectBaseByStationId(assetsStationId);
        if (imp7tionBudgetCollectBase !=null){
            imp7tionBudgetCollectBaseMapper.deleteImp7tionBudgetCollectBaseByStationId(assetsStationId);
            imp7tionBudgetCollectListMapper.deleteImp7tionBudgetCollectListByCollectId(imp7tionBudgetCollectBase.getBudgetCollectId());
        }
        operateInfoService.deleteOperateByStationId(assetsStationId);
        fileListService.deleteFileListByStationId(assetsStationId);
    }

    @Override
    public List<AssetsChargingStation> queryChargingStationListForFieldAgreement() {
        return assetsChargingStationMapper.queryChargingStationListForFieldAgreement();
    }

}