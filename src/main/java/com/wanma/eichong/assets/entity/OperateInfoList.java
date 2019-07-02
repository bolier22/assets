package com.wanma.eichong.assets.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;


/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.17 运营信息条目表 operate_info_list
 */
@Getter
@Setter
@ToString
public class OperateInfoList  {
    private Long listId; //list_id 主键id bigint(20)
    private Long operateId; //operate_id 运营基表id bigint(20)
    private String equ5tName; //equ5t_name  varchar(100) 设备名称
    private String unit; //unit  varchar(10) 单位
    private Long amount; //amount 数量 bigint(20)
    private BigDecimal unitPrice; //unit_price 单价（分/单位） bigint(20)
    private BigDecimal totalMoney; //total_money 合计（分） bigint(20)
    private Long createId; //create_id 录入人 bigint(20)
    private Long modifyId; //modify_id 修改人 bigint(20)
    private String createTime; //create_time 录入时间 datetime
    private String modifyTime; //modify_time 修改时间 datetime
}
