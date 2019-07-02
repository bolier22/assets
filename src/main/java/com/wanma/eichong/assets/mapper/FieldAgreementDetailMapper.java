package com.wanma.eichong.assets.mapper;

import com.wanma.eichong.assets.entity.FieldAgreementDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.12 协议详情表 field_agreement_detail
 */
@Mapper
@Component
public interface FieldAgreementDetailMapper {
    void deleteFieldAgreementDetailByFieldId(Long fieldAgr5tId);

    void insertFieldAgreementDetail(FieldAgreementDetail detail);

    List<FieldAgreementDetail> selectFieldAgreementDetailListById(Long fieldAgr5tId);

    void updateFieldAgreementDetail(FieldAgreementDetail detail);

}

