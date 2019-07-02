package com.wanma.eichong.assets.service.impl;

import com.wanma.eichong.assets.entity.AssetsEquipmentList;
import com.wanma.eichong.assets.mapper.AssetsEquipmentListMapper;
import com.wanma.eichong.assets.service.AssetsEquipmentListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.2 项目数据设备清单表
 */
@Service
public class AssetsEquipmentListServiceImpl implements AssetsEquipmentListService {
    private final AssetsEquipmentListMapper assetsEquipmentListMapper;

    @Autowired
    public AssetsEquipmentListServiceImpl(AssetsEquipmentListMapper assetsEquipmentListMapper) {
        this.assetsEquipmentListMapper = assetsEquipmentListMapper;
    }

    @Override
    public List<AssetsEquipmentList> selectEquipmentListByStationId(Long assetsStationId) {
        return assetsEquipmentListMapper.selectEquipmentListByStationId(assetsStationId);
    }
}