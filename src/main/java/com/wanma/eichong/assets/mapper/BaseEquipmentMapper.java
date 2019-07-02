package com.wanma.eichong.assets.mapper;

import com.wanma.eichong.assets.entity.BaseEquipment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.13 设备信息表 base_equipment
 */
@Mapper
@Component
public interface BaseEquipmentMapper {
    List<BaseEquipment> selectBaseEquipmentList(BaseEquipment baseEquipment);

    Long selectBaseEquipmentCount(BaseEquipment baseEquipment);

    void updateBaseEquipment(BaseEquipment baseEquipment);

    void insertBaseEquipment(BaseEquipment baseEquipment);

    BaseEquipment selectBaseEquipmentById(Long equ5tId);

    void deleteBaseEquipmentById(Long equ5tId);
}