package com.wanma.eichong.assets.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.6 立项预算表-2.4全周期运营指标表（导入数据） budget_complete_period_quota
 */
@Getter
@Setter
@ToString
public class BudgetCompletePeriodQuota  {
    private Long budgetQuotaId; //budget_quota_id 主键id bigint(20)
    private Long assetsStationId; //assets_station_id 充电站基表 id bigint(20)
    private String budgetIndex; //budget_index 序列（排序合并） varchar(20)
    private String budgetQuota; //budget_quota 全周期项目运营指标 varchar(100)
    private String budgetNumber; //budget_number 数值 varchar(20)
    private String remark; //remark 备注 varchar(500)
    private Long createId; //create_id 录入人 bigint(20)
    private Long modifyId; //modify_id 修改人 bigint(20)
    private Timestamp createTime; //create_time 录入时间 datetime
    private Timestamp modifyTime; //modify_time 修改时间 datetime
}