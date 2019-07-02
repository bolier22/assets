package com.wanma.eichong.assets.service;

import com.wanma.eichong.assets.entity.FieldAgreement;
import com.wanma.eichong.assets.entity.FieldAgreementDetail;
import com.wanma.eichong.assets.entity.UserAssets;
import com.wanma.eichong.assets.utils.BaseResultDTO;

import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.11 场地协议表 field_agreement
 */
public interface FieldAgreementService {
    Long selectFieldAgreementCount(FieldAgreement fieldAgreement);

    List<FieldAgreement> selectFieldAgreementList(FieldAgreement fieldAgreement);

    BaseResultDTO modifyFieldAgreementForSelect(FieldAgreement fieldAgreement);

    void deleteFieldAgreement(Long fieldAgr5tId);

    void saveFieldAgr5tList(FieldAgreement fieldAgreement, List<FieldAgreementDetail> fieldAgreementDetailList, UserAssets loginUser);

    FieldAgreement selectFieldAgreementById(Long fieldAgr5tId);

    List<FieldAgreementDetail> selectFieldAgreementDetailList(Long fieldAgr5tId);

    void deleteFieldAgreementByStationId(Long assetsStationId);
}