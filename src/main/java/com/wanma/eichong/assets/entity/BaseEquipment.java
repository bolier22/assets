package com.wanma.eichong.assets.entity;

import com.wanma.eichong.assets.utils.Pager;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.13 设备信息表 base_equipment
 */
@Getter
@Setter
@ToString
public class BaseEquipment  {
    private Long equ5tId; //equ5t_id 主键id bigint(20)
    private String equ5tName; //equ5t_name 设备名称 varchar(100)
    private Long createId; //create_id 录入人 bigint(20)
    private Long modifyId; //modify_id 修改人 bigint(20)
    private Timestamp createTime; //create_time 录入时间 datetime
    private Timestamp modifyTime; //modify_time 修改时间 datetime
    private Pager pager;

    List<BaseEquipmentModel> equipmentModelList;

}