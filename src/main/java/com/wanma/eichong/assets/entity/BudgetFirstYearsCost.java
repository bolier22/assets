package com.wanma.eichong.assets.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.4 立项预算表-2.2首年运营成本表（导入数据） budget_first_years_cost
 */
@Getter
@Setter
@ToString
public class BudgetFirstYearsCost implements Serializable {
    private Long budget1yearId; //budget_1year_id 主键id bigint(20)
    private Long assetsStationId; //assets_station_id 充电站基表 id bigint(20)
    private String budgetIndex; //budget_index 序列（排序合并） varchar(20)
    private String costCategory; //cost_category 类目 varchar(100)
    private String costMoney; //cost_money 金额（元/年） varchar(20)
    private String cal4tionRule; //cal4tion_rule 计算原则 varchar(100)
    private String remark; //remark 备注 varchar(500)
    private Long createId; //create_id 录入人 bigint(20)
    private Long modifyId; //modify_id 修改人 bigint(20)
    private Timestamp createTime; //create_time 录入时间 datetime
    private Timestamp modifyTime; //modify_time 修改时间 datetime
    /**

     setBudget1yearid(); //主键id
     setAssetsstationid(); //充电站基表 id
     setBudgetindex(); //序列（排序合并）
     setCostcategory(); //类目
     setCostmoney(); //金额（元/年）
     setCal4tionrule(); //计算原则
     setRemark(); //备注
     setCreateid(); //录入人
     setModifyid(); //修改人
     setCreatetime(); //录入时间
     setModifytime(); //修改时间

     **/
}
