package com.wanma.eichong.assets.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.14 设备型号表 base_equipment_model
 */
@Getter
@Setter
@ToString
public class BaseEquipmentModel implements Serializable {
    private Long equ5tModelId; //equ5t_list_id 主键id bigint(20)
    private Long equ5tId; //equ5t_id 设备主键id bigint(20)
    private String equ5tModelName; //equ5t_model_name 型号名称 varchar(100)
    private Long createId; //create_id 录入人 bigint(20)
    private Long modifyId; //modify_id 修改人 bigint(20)
    private String createTime; //create_time 录入时间 datetime
    private String modifyTime; //modify_time 修改时间 datetime
}