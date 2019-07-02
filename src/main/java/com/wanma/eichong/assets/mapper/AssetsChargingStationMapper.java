package com.wanma.eichong.assets.mapper;

import com.wanma.eichong.assets.entity.AssetsChargingStation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.1 充电站资产基表 assets_charging_station
 */
@Mapper
@Component
public interface AssetsChargingStationMapper {
    List<AssetsChargingStation> selectChargingStationListByCityCode(@Param("cityCode") String cityCode);

    Long selectChargingStationCount(AssetsChargingStation assetsChargingStation);

    List<AssetsChargingStation> selectChargingStationList(AssetsChargingStation assetsChargingStation);

    void insertAssetsChargingStation(AssetsChargingStation chargingStation);

    void updateAssetsChargingStation(AssetsChargingStation chargingStation);

    AssetsChargingStation selectChargingStationById(Long assetsStationId);

    List<AssetsChargingStation> selectChargingStationByCode(String stationCode);

    List<AssetsChargingStation> selectChargingStationByIds(@Param("stationIdList")List<Long> stationIdList);

    void deleteChargingStationById(Long assetsStationId);

    List<AssetsChargingStation> queryChargingStationListForFieldAgreement();

    Long checkUnique(AssetsChargingStation chargingStation);
}