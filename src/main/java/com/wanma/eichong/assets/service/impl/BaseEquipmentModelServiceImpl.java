package com.wanma.eichong.assets.service.impl;

import com.wanma.eichong.assets.entity.BaseEquipmentModel;
import com.wanma.eichong.assets.mapper.BaseEquipmentModelMapper;
import com.wanma.eichong.assets.service.BaseEquipmentModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.14 设备型号表 base_equipment_model
 */
@Service
public class BaseEquipmentModelServiceImpl implements BaseEquipmentModelService {
    private final BaseEquipmentModelMapper baseEquipmentModelMapper;

    public BaseEquipmentModelServiceImpl(BaseEquipmentModelMapper baseEquipmentModelMapper) {
        this.baseEquipmentModelMapper = baseEquipmentModelMapper;
    }

    @Override
    public List<BaseEquipmentModel> selectBaseEqu5tModelByEqu5tId(Long equ5tId) {
        return baseEquipmentModelMapper.selectBaseEqu5tModelByEqu5tId(equ5tId);
    }
}