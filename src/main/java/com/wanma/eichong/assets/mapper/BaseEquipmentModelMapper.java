package com.wanma.eichong.assets.mapper;

import com.wanma.eichong.assets.entity.BaseEquipmentModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.14 设备型号表 base_equipment_model
 */
@Mapper
@Component
public interface BaseEquipmentModelMapper {
    List<BaseEquipmentModel> selectBaseEqu5tModelByEqu5tId(Long equ5tId);

    void deleteBaseEquipmentModelByEquipId(Long equ5tId);

    void insertBaseEquipmentModel(BaseEquipmentModel baseEquipmentModel);

    void updateBaseEquipmentModel(BaseEquipmentModel baseEquipmentModel);

    BaseEquipmentModel selectBaseEqu5tModelByModelId(Long approvalModelId);

    void deleteBaseEquipmentModelByModelId(Long equ5tModelId);
}