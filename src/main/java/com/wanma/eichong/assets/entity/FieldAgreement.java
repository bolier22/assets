package com.wanma.eichong.assets.entity;

import com.wanma.eichong.assets.utils.Pager;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.11 场地协议表 field_agreement
 */
@Getter
@Setter
@ToString
public class FieldAgreement  {
    private Long fieldAgr5tId; //field_agr5t_id 主键id bigint(20)
    private String fieldAgr5tName; //field_agr5t_name 协议名称 varchar(100)
    private String fieldAgr5tCode; //field_agr5t_code 协议OA流程号 varchar(100)
    private Integer fieldAgr5tType; //field_agr5t_type 协议类型(1:场地协议，2：合作协议) tinyint(1)
    private Long assetsStationId; //assets_station_id 充电站id bigint(20)
    private List<Long> assetsStationIdList;
    private String siteRentBeginTime;//场地租金起付日
    private Long createId; //create_id 录入人 bigint(20)
    private Long modifyId; //modify_id 修改人 bigint(20)
    private String createTime; //create_time 录入时间 datetime
    private String modifyTime; //modify_time 修改时间 datetime
    private Pager pager;
    private String stationCode;//充电站编号
    private String stationName;
    private String cityName;
    private String modifier;

}