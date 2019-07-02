package com.wanma.eichong.assets.mapper;

import com.wanma.eichong.assets.entity.AssetsEquipmentList;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.2 项目数据设备清单表
 */
@Mapper
@Component
public interface AssetsEquipmentListMapper {
    void insertAssetsEquipment(AssetsEquipmentList equipment);

    void updateAssetsEquipment(AssetsEquipmentList equipment);

    List<AssetsEquipmentList> selectEquipmentListByStationId(Long assetsStationId);

    List<AssetsEquipmentList> selectEquipmentList();

    void deleteEquipmentListByStationId(Long assetsStationId);
}