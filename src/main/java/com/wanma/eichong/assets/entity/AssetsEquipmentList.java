package com.wanma.eichong.assets.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.2 项目数据设备清单表 assets_equipment_list
 */
@Getter
@Setter
@ToString
public class AssetsEquipmentList  {
    private Long assetsEqu5tId; //assets_equ5t_id 主键id bigint(20)
    private Long assetsStationId; //assets_station_id 充电站基表 id bigint(20)
    private Long equ5tId; //equ5t_id 设备id bigint(20)
    private String equ5tName; //equ5t_name 设备名称 varchar(100)
    private Long approvalModelId; //立项型号id
    private String approvalModelName; //approval_model_name 立项型号 varchar(100)
    private Integer approvalAmount; //approval_amount 立项数量 int(11)
    private Long imp7tionModelId; //实施型号id
    private String imp7tionModelName; //imp7tion_model_name 实施型号 varchar(100)
    private Integer imp7tionAmount; //imp7tion_amount 实施数量 int(11)
    private Long final8sModelId; //决算型号id
    private String final8sModelName; //final8s_model_name 决算型号 varchar(100)
    private Integer final8sAmount; //final8s_amount 决算数量 int(11)
    private Long createId; //create_id 录入人 bigint(20)
    private Long modifyId; //modify_id 修改人 bigint(20)
    private Timestamp createTime; //create_time 录入时间 datetime
    private Timestamp modifyTime; //modify_time 修改时间 datetime

}