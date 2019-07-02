package com.wanma.eichong.assets.service;

import com.wanma.eichong.assets.entity.BaseEquipmentModel;

import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.14 设备型号表 base_equipment_model
 */
public interface BaseEquipmentModelService {
    List<BaseEquipmentModel> selectBaseEqu5tModelByEqu5tId(Long equ5tId);
}