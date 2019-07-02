package com.wanma.eichong.assets.entity;

import com.wanma.eichong.assets.utils.Pager;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.16 运营信息基表 operate_info
 */
@Getter
@Setter
@ToString
public class OperateInfo  {
    private Long operateId; //operate_id 主键id bigint(20)
    private Long assetsStationId; //assets_station_id 充电站基表id bigint(20)
    private String recordName; //record_name 记录名称 varchar(50)
    private String presentTime; //present_time 设备物料到场时间 datetime
    private BigDecimal totalMoney; //总金额（列表总和）
    private Long createId; //create_id 录入人 bigint(20)
    private Long modifyId; //modify_id 修改人 bigint(20)
    private String modifier;
    private String createTime; //create_time 录入时间 datetime
    private String modifyTime; //modify_time 修改时间 datetime
    private Pager pager;
}
