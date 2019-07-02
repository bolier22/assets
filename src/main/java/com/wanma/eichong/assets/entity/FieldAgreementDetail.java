package com.wanma.eichong.assets.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.12 协议详情表 field_agreement_detail
 */
@Getter
@Setter
@ToString
public class FieldAgreementDetail  {
    private Long fieldDetailId; //field_detail_id 主键id bigint(20)
    private Long fieldAgr5tId; //field_agr5t_id 协议id bigint(20)
    private String fieldBase; //field_base 项目基本信息表 varchar(100)
    private String fieldBaseInfo; //field_base_info 基本信息 varchar(100)
    private String fieldYear1; //field_year1 第1年 varchar(20)
    private String fieldYear2; //field_year2 第2年 varchar(20)
    private String fieldYear3; //field_year3 第3年 varchar(20)
    private String fieldYear4; //field_year4 第4年 varchar(20)
    private String fieldYear5; //field_year5 第5年 varchar(20)
    private String fieldYear6; //field_year6 第6年 varchar(20)
    private String fieldYear7; //field_year7 第7年 varchar(20)
    private String fieldYear8; //field_year8 第8年 varchar(20)
    private String fieldYear9; //field_year9 第9年 varchar(20)
    private String fieldYear10; //field_year10 第10年 varchar(20)
    private String remark; //remark 备注（特殊条款，违约条款，其他） varchar(500)
    private Long createId; //create_id 录入人 bigint(20)
    private Long modifyId; //modify_id 修改人 bigint(20)
    private String createTime; //create_time 录入时间 datetime
    private String modifyTime; //modify_time 修改时间 datetime
}

