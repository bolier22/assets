package com.wanma.eichong.assets.service;

import com.wanma.eichong.assets.entity.BaseEquipment;
import com.wanma.eichong.assets.entity.BaseEquipmentModel;
import com.wanma.eichong.assets.entity.UserAssets;

import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.13 设备信息表 base_equipment
 */
public interface BaseEquipmentService {
    List<BaseEquipment> selectBaseEquipmentList(BaseEquipment baseEquipment);

    Long selectBaseEquipmentCount(BaseEquipment baseEquipment);

    void saveEqu5tAndModel(BaseEquipment baseEquipment, List<BaseEquipmentModel> equipmentModelList, UserAssets loginUser);

    void deleteEqu5tById(BaseEquipment baseEquipment);
}