package com.wanma.eichong.assets.service.impl;

import com.wanma.eichong.assets.entity.BaseEquipment;
import com.wanma.eichong.assets.entity.BaseEquipmentModel;
import com.wanma.eichong.assets.entity.UserAssets;
import com.wanma.eichong.assets.mapper.BaseEquipmentMapper;
import com.wanma.eichong.assets.mapper.BaseEquipmentModelMapper;
import com.wanma.eichong.assets.service.BaseEquipmentService;
import com.wanma.eichong.assets.utils.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.13 设备信息表 base_equipment
 */
@Service
public class BaseEquipmentServiceImpl implements BaseEquipmentService {

    private final BaseEquipmentMapper baseEquipmentMapper;
    private final BaseEquipmentModelMapper baseEquipmentModelMapper;

    @Autowired
    public BaseEquipmentServiceImpl(BaseEquipmentMapper baseEquipmentMapper, BaseEquipmentModelMapper baseEquipmentModelMapper) {
        this.baseEquipmentMapper = baseEquipmentMapper;
        this.baseEquipmentModelMapper = baseEquipmentModelMapper;
    }

    @Override
    public List<BaseEquipment> selectBaseEquipmentList(BaseEquipment baseEquipment) {
        List<BaseEquipment> baseEquipmentList = baseEquipmentMapper.selectBaseEquipmentList(baseEquipment);
        for (BaseEquipment equipment : baseEquipmentList){
            List<BaseEquipmentModel> equipmentModelList = baseEquipmentModelMapper.selectBaseEqu5tModelByEqu5tId(equipment.getEqu5tId());
            equipment.setEquipmentModelList(equipmentModelList);
        }
        return baseEquipmentList;
    }

    @Override
    public Long selectBaseEquipmentCount(BaseEquipment baseEquipment) {
        return baseEquipmentMapper.selectBaseEquipmentCount(baseEquipment);
    }

    @Override
    @Transactional
    public void saveEqu5tAndModel(BaseEquipment baseEquipment, List<BaseEquipmentModel> equipmentModelList, UserAssets loginUser) {
        //修改设备信息
        baseEquipment.setModifyId(loginUser.getUserId());
        if (ObjectUtil.isEmpty(baseEquipment.getEqu5tId())||baseEquipment.getEqu5tId()==0){//新建
            baseEquipment.setCreateId(loginUser.getUserId());
            baseEquipmentMapper.insertBaseEquipment(baseEquipment);
        }else {
            baseEquipmentMapper.updateBaseEquipment(baseEquipment);
        }
        List<BaseEquipmentModel> oldEquipmentModelList = baseEquipmentModelMapper.selectBaseEqu5tModelByEqu5tId(baseEquipment.getEqu5tId());
        Set<Long> modelIdList = new HashSet<>();
        for (BaseEquipmentModel baseEquipmentModel:equipmentModelList){
            modelIdList.add(baseEquipmentModel.getEqu5tModelId());
        }
        for (BaseEquipmentModel model:oldEquipmentModelList){
            if (!modelIdList.contains(model.getEqu5tModelId())){
                baseEquipmentModelMapper.deleteBaseEquipmentModelByModelId(model.getEqu5tModelId());
            }
        }

        //修改设备型号
        for (BaseEquipmentModel baseEquipmentModel:equipmentModelList){
            if (ObjectUtil.isEmpty(baseEquipmentModel.getEqu5tModelId())||baseEquipmentModel.getEqu5tModelId()==0){//新建
                baseEquipmentModel.setEqu5tId(baseEquipment.getEqu5tId());
                baseEquipmentModel.setCreateId(loginUser.getUserId());
                baseEquipmentModel.setModifyId(loginUser.getUserId());
                baseEquipmentModelMapper.insertBaseEquipmentModel(baseEquipmentModel);
            }else {
                baseEquipmentModel.setModifyId(loginUser.getUserId());
                baseEquipmentModelMapper.updateBaseEquipmentModel(baseEquipmentModel);
            }
        }
    }

    @Override
    public void deleteEqu5tById(BaseEquipment baseEquipment) {
        baseEquipmentMapper.deleteBaseEquipmentById(baseEquipment.getEqu5tId());
        baseEquipmentModelMapper.deleteBaseEquipmentModelByEquipId(baseEquipment.getEqu5tId());
    }
}