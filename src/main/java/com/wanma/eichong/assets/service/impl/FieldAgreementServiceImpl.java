package com.wanma.eichong.assets.service.impl;

import com.wanma.eichong.assets.config.Constants;
import com.wanma.eichong.assets.config.ResultCodeConstants;
import com.wanma.eichong.assets.entity.*;
import com.wanma.eichong.assets.mapper.*;
import com.wanma.eichong.assets.service.AssetsChargingStationService;
import com.wanma.eichong.assets.service.FieldAgreementService;
import com.wanma.eichong.assets.service.FileListService;
import com.wanma.eichong.assets.service.UserAssetsService;
import com.wanma.eichong.assets.utils.BaseResultDTO;
import com.wanma.eichong.assets.utils.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.11 场地协议表 field_agreement
 */
@Service
public class FieldAgreementServiceImpl implements FieldAgreementService {
    private final FieldAgreementMapper fieldAgreementMapper;
    private final AssetsChargingStationMapper assetsChargingStationMapper;
    private final FieldAgreementDetailMapper fieldAgreementDetailMapper;
    private final AssetsChargingStationService assetsChargingStationService;
    private final AreaCodeMapper areaCodeMapper;
    private final UserAssetsService userAssetsService;
    @Autowired
    private FileListService fileListService;

    @Autowired
    public FieldAgreementServiceImpl(FieldAgreementMapper fieldAgreementMapper, AssetsChargingStationMapper assetsChargingStationMapper, FieldAgreementDetailMapper fieldAgreementDetailMapper, AssetsChargingStationService assetsChargingStationService, AreaCodeMapper areaCodeMapper, UserAssetsService userAssetsService) {
        this.fieldAgreementMapper = fieldAgreementMapper;
        this.assetsChargingStationMapper = assetsChargingStationMapper;
        this.fieldAgreementDetailMapper = fieldAgreementDetailMapper;
        this.assetsChargingStationService = assetsChargingStationService;
        this.areaCodeMapper = areaCodeMapper;
        this.userAssetsService = userAssetsService;
    }
    @Override
    public BaseResultDTO modifyFieldAgreementForSelect(FieldAgreement fieldAgreement) {
        if (ObjectUtil.isNotEmpty(fieldAgreement.getStationCode())){
            List<AssetsChargingStation> chargingStationList = assetsChargingStationMapper.selectChargingStationByCode(fieldAgreement.getStationCode());
            if (chargingStationList.size()>0){
                List<Long> assetsStationIdList = new ArrayList<>();
                for (AssetsChargingStation chargingStation:chargingStationList){
                    assetsStationIdList.add(chargingStation.getAssetsStationId());
                }
                fieldAgreement.setAssetsStationIdList(assetsStationIdList);
            }else {
                return new BaseResultDTO(false,ResultCodeConstants.CODE_FAILED,ResultCodeConstants.ERROR_MSG_QUERY);
            }
        }
        return new BaseResultDTO();
    }

    @Override
    public Long selectFieldAgreementCount(FieldAgreement fieldAgreement) {
        return fieldAgreementMapper.selectFieldAgreementCount(fieldAgreement);
    }

    @Override
    public List<FieldAgreement> selectFieldAgreementList(FieldAgreement fieldAgreement) {
        List<FieldAgreement> fieldAgreementList = fieldAgreementMapper.selectFieldAgreementList(fieldAgreement);
        if (fieldAgreementList.size()==0){
            return fieldAgreementList;
        }
        Set<Long> stationIdSet = new HashSet<>();
        Set<Long> userIdSet = new HashSet<>();
        for (FieldAgreement field:fieldAgreementList){
            userIdSet.add(field.getModifyId());
            stationIdSet.add(field.getAssetsStationId());
        }
        Map<Long,AssetsChargingStation> chargingStationMap = assetsChargingStationService.selectChargingStationByIds(new ArrayList<>(stationIdSet));
        Map<Long,UserAssets> userAssetsMap = userAssetsService.selectUserAssetsByIds(new ArrayList<>(userIdSet));
        for (FieldAgreement field:fieldAgreementList){
            AssetsChargingStation chargingStation = chargingStationMap.get(field.getAssetsStationId());
            if (chargingStation != null){
                field.setStationName(chargingStation.getStationName());
                field.setStationCode(chargingStation.getStationCode());
                TblAreaCode areaCode = areaCodeMapper.findNameByAreaCode(chargingStation.getCityCode());
                if (areaCode !=null){
                    field.setCityName(areaCode.getAreaName());
                }
            }
            UserAssets modifier = userAssetsMap.get(field.getModifyId());
            if (modifier !=null){
                field.setModifier(modifier.getUserAccount());
            }
        }
        return fieldAgreementList;
    }

    @Override
    public void deleteFieldAgreement(Long fieldAgr5tId) {
        FieldAgreement fieldAgreement = fieldAgreementMapper.selectFieldAgreementById(fieldAgr5tId);
        if (fieldAgreement !=null){
            fieldAgreementMapper.deleteFieldAgreement(fieldAgr5tId);
            fieldAgreementDetailMapper.deleteFieldAgreementDetailByFieldId(fieldAgr5tId);
            List<FileList> fileListList = fileListService.selectFileListListByStationIdAndType(fieldAgreement.getAssetsStationId(),Constants.FILETYPE_CODE7);
            for(FileList fileList:fileListList){
                fileListService.deleteFileListByFileId(fileList.getFileId());
            }
        }
    }

    @Override
    @Transactional
    public void saveFieldAgr5tList(FieldAgreement fieldAgreement, List<FieldAgreementDetail> fieldAgreementDetailList, UserAssets loginUser) {
        if (ObjectUtil.isEmpty(fieldAgreement.getFieldAgr5tId())||fieldAgreement.getFieldAgr5tId()==0){
            fieldAgreement.setCreateId(loginUser.getUserId());
            fieldAgreement.setModifyId(loginUser.getUserId());
            fieldAgreementMapper.insertFieldAgreement(fieldAgreement);
        }else {
            fieldAgreement.setModifyId(loginUser.getUserId());
            fieldAgreementMapper.updateFieldAgreement(fieldAgreement);
        }
        fieldAgreementDetailMapper.deleteFieldAgreementDetailByFieldId(fieldAgreement.getFieldAgr5tId());
        for (FieldAgreementDetail detail:fieldAgreementDetailList){
            detail.setFieldAgr5tId(fieldAgreement.getFieldAgr5tId());
            detail.setCreateId(loginUser.getUserId());
            detail.setModifyId(loginUser.getUserId());
            fieldAgreementDetailMapper.insertFieldAgreementDetail(detail);
        }
    }

    @Override
    public FieldAgreement selectFieldAgreementById(Long fieldAgr5tId) {
        FieldAgreement fieldAgreement = fieldAgreementMapper.selectFieldAgreementById(fieldAgr5tId);
        if (ObjectUtil.isNotEmpty(fieldAgreement.getAssetsStationId())){
            AssetsChargingStation chargingStation = assetsChargingStationMapper.selectChargingStationById(fieldAgreement.getAssetsStationId());
            if (chargingStation != null){
                fieldAgreement.setStationName(chargingStation.getStationName());
                fieldAgreement.setStationCode(chargingStation.getStationCode());
            }
        }
        return fieldAgreement;
    }

    @Override
    public List<FieldAgreementDetail> selectFieldAgreementDetailList(Long fieldAgr5tId) {
        return fieldAgreementDetailMapper.selectFieldAgreementDetailListById(fieldAgr5tId);
    }

    @Override
    public void deleteFieldAgreementByStationId(Long assetsStationId) {
        FieldAgreement fieldAgreement = fieldAgreementMapper.selectFieldAgreementByStationId(assetsStationId);
        if (fieldAgreement !=null){
            fieldAgreementMapper.deleteFieldAgreementByStationId(assetsStationId);
            fieldAgreementDetailMapper.deleteFieldAgreementDetailByFieldId(fieldAgreement.getFieldAgr5tId());
        }
    }
}