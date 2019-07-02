package com.wanma.eichong.assets.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.5 立项预算表-2.3前三年运营指标表（导入数据） budget_first_3years_period_quota
 */
@Getter
@Setter
@ToString
public class BudgetFirst3yearsPeriodQuota implements Serializable {
    private Long budget3yearId; //budget_3year_id 主键id bigint(20)
    private Long assetsStationId; //assets_station_id 充电站基表 id bigint(20)
    private String budgetIndex; //budget_index 序列（排序合并） varchar(11)
    private String budget3yearQuota; //budget_3year_quota 第三年项目运营指标 varchar(100)
    private String budget1yearNumber; //budget_1year_number 第1年数值 varchar(20)
    private String budget2yearNumber; //budget_2year_number 第2年数值 varchar(20)
    private String budget3yearNumber; //budget_3year_number 第3年数值 varchar(20)
    private String remark; //remark 备注 varchar(500)
    private Long createId; //create_id 录入人 bigint(20)
    private Long modifyId; //modify_id 修改人 bigint(20)
    private Timestamp createTime; //create_time 录入时间 datetime
    private Timestamp modifyTime; //modify_time 修改时间 datetime
    /**
     setBudget3yearid(); //主键id
     setAssetsstationid(); //充电站基表 id
     setBudgetindex(); //序列（排序合并）
     setBudget3yearquota(); //第三年项目运营指标
     setBudget1yearnumber(); //第1年数值
     setBudget2yearnumber(); //第2年数值
     setBudget3yearnumber(); //第3年数值
     setRemark(); //备注
     setCreateid(); //录入人
     setModifyid(); //修改人
     setCreatetime(); //录入时间
     setModifytime(); //修改时间


     **/
}