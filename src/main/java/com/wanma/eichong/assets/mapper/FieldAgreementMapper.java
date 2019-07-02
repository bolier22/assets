package com.wanma.eichong.assets.mapper;

import com.wanma.eichong.assets.entity.FieldAgreement;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.11 场地协议表 field_agreement
 */
@Mapper
@Component
public interface FieldAgreementMapper {
    Long selectFieldAgreementCount(FieldAgreement fieldAgreement);

    List<FieldAgreement> selectFieldAgreementList(FieldAgreement fieldAgreement);

    void deleteFieldAgreement(Long fieldAgr5tId);

    void insertFieldAgreement(FieldAgreement fieldAgreement);

    FieldAgreement selectFieldAgreementById(Long fieldAgr5tId);

    void updateFieldAgreement(FieldAgreement fieldAgreement);

    void deleteFieldAgreementByStationId(Long assetsStationId);

    FieldAgreement selectFieldAgreementByStationId(Long assetsStationId);
}