package com.wanma.eichong.assets.service.impl;

import com.wanma.eichong.assets.config.Constants;
import com.wanma.eichong.assets.entity.AssetsChargingStation;
import com.wanma.eichong.assets.entity.OperateInfo;
import com.wanma.eichong.assets.entity.OperateInfoList;
import com.wanma.eichong.assets.entity.UserAssets;
import com.wanma.eichong.assets.mapper.AssetsChargingStationMapper;
import com.wanma.eichong.assets.mapper.OperateInfoListMapper;
import com.wanma.eichong.assets.mapper.OperateInfoMapper;
import com.wanma.eichong.assets.service.OperateInfoService;
import com.wanma.eichong.assets.service.UserAssetsService;
import com.wanma.eichong.assets.utils.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.16 运营信息基表 operate_info
 */
@Service
public class OperateInfoServiceImpl implements OperateInfoService {
    private final OperateInfoMapper operateInfoMapper;
    private final UserAssetsService userAssetsService;
    private final OperateInfoListMapper operateInfoListMapper;
    private final AssetsChargingStationMapper assetsChargingStationMapper;

    @Autowired
    public OperateInfoServiceImpl(OperateInfoMapper operateInfoMapper, UserAssetsService userAssetsService, OperateInfoListMapper operateInfoListMapper, AssetsChargingStationMapper assetsChargingStationMapper) {
        this.operateInfoMapper = operateInfoMapper;
        this.userAssetsService = userAssetsService;
        this.operateInfoListMapper = operateInfoListMapper;
        this.assetsChargingStationMapper = assetsChargingStationMapper;
    }

    @Override
    public Long selectOperateCountByStationId(OperateInfo operateInfo) {
        return operateInfoMapper.selectOperateCountByStationId(operateInfo);
    }

    @Override
    public List<OperateInfo> selectOperateListByStationId(OperateInfo operateInfo) {
        List<OperateInfo> operateInfoList = operateInfoMapper.selectOperateListByStationId(operateInfo);
        if (operateInfoList.size()==0){
            return operateInfoList;
        }
        Set<Long> userIdSet = new HashSet<>();
        for (OperateInfo info:operateInfoList){
            userIdSet.add(info.getModifyId());
        }
        Map<Long,UserAssets> userAssetsMap = userAssetsService.selectUserAssetsByIds(new ArrayList<>(userIdSet));
        for (OperateInfo info:operateInfoList){
            UserAssets userAssets = userAssetsMap.get(info.getModifyId());
            if (userAssets != null){
                info.setModifier(userAssets.getUserAccount());
            }
        }
        return operateInfoList;
    }

    @Override
    public void deleteOperateByOperateId(Long operateId) {
        operateInfoMapper.deleteOperateByOperateId(operateId);
        operateInfoListMapper.deleteOperateByOperateId(operateId);
    }

    @Override
    public OperateInfo selectOperateByOperateId(Long operateId) {
        return operateInfoMapper.selectOperateByOperateId(operateId);
    }

    @Override
    public List<OperateInfoList> selectOperateInfoListListByOperateId(Long operateId) {
        return operateInfoListMapper.selectOperateInfoListListByOperateId(operateId);
    }

    @Override
    public void saveOperateInfo(OperateInfo operateInfo, List<OperateInfoList> operateInfoListList, UserAssets loginUser) {
        BigDecimal totalMoney = new BigDecimal(0);
        for (OperateInfoList operateInfoList:operateInfoListList){
            operateInfoList.setUnitPrice(operateInfoList.getUnitPrice().multiply(new BigDecimal(100)));
            operateInfoList.setTotalMoney(operateInfoList.getUnitPrice().multiply(new BigDecimal(operateInfoList.getAmount())));
            totalMoney = totalMoney.add(operateInfoList.getTotalMoney());
        }
        operateInfo.setTotalMoney(totalMoney);
        if (ObjectUtil.isEmpty(operateInfo.getOperateId())||operateInfo.getOperateId()==0){//新增
            operateInfo.setCreateId(loginUser.getUserId());
            operateInfo.setModifyId(loginUser.getUserId());
            operateInfoMapper.insertOperateInfo(operateInfo);
        }else {
            operateInfo.setModifyId(loginUser.getUserId());
            operateInfoMapper.updateOperateInfo(operateInfo);
        }
        for (OperateInfoList operateInfoList:operateInfoListList){
            if (ObjectUtil.isEmpty(operateInfoList.getListId())||operateInfoList.getListId()==0){//新增
                operateInfoList.setOperateId(operateInfo.getOperateId());
                operateInfoList.setCreateId(loginUser.getUserId());
                operateInfoList.setModifyId(loginUser.getUserId());
                operateInfoListMapper.insertOperateInfoList(operateInfoList);
            }else {
                operateInfoList.setModifyId(loginUser.getUserId());
                operateInfoListMapper.updateOperateInfoList(operateInfoList);
            }
        }
        AssetsChargingStation assetsChargingStation = new AssetsChargingStation();
        assetsChargingStation.setAssetsStationId(operateInfo.getAssetsStationId());
        assetsChargingStation.setStationStage(Constants.STATION_STAGE_4);
        assetsChargingStation.setModifyId(loginUser.getUserId());
        assetsChargingStationMapper.updateAssetsChargingStation(assetsChargingStation);
    }

    @Override
    public void deleteOperateByStationId(Long assetsStationId) {
        OperateInfo operateInfo = new OperateInfo();
        operateInfo.setAssetsStationId(assetsStationId);
        List<OperateInfo> operateInfoList = operateInfoMapper.selectOperateListByStationId(operateInfo);
        for (OperateInfo info:operateInfoList){
            operateInfoMapper.deleteOperateByOperateId(info.getOperateId());
            operateInfoListMapper.deleteOperateByOperateId(info.getOperateId());
        }
    }
}
