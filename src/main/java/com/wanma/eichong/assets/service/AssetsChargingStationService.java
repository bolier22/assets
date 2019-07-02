package com.wanma.eichong.assets.service;


import com.wanma.eichong.assets.entity.AssetsChargingStation;
import com.wanma.eichong.assets.entity.AssetsEquipmentList;
import com.wanma.eichong.assets.entity.UserAssets;
import com.wanma.eichong.assets.utils.BaseResultDTO;

import java.util.List;
import java.util.Map;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.1 充电站资产基表 assets_charging_station
 */
public interface AssetsChargingStationService {
    List<AssetsChargingStation> selectChargingStationListByCityCode(String cityCode);

    List<AssetsChargingStation> selectChargingStationList(AssetsChargingStation assetsChargingStation);

    Long selectChargingStationCount(AssetsChargingStation assetsChargingStation);

    BaseResultDTO saveChargingStation(AssetsChargingStation chargingStation, List<AssetsEquipmentList> equipmentList, UserAssets loginUser);

    AssetsChargingStation selectChargingStationById(Long assetsStationId);

    Map<Long,AssetsChargingStation> selectChargingStationByIds(List<Long> stationIdList);

    List<AssetsChargingStation> selectChargingStationListForExcel(AssetsChargingStation assetsChargingStation);

    void deleteChargingStationById(Long assetsStationId);

    List<AssetsChargingStation> queryChargingStationListForFieldAgreement();
}