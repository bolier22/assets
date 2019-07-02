package com.wanma.eichong.assets.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.8 实施预算汇总表（条目表-导入数据） imp7tion_budget_collect_list
 */
@Getter
@Setter
@ToString
public class Imp7tionBudgetCollectList  {
    private Long budgetListId; //budget_list_id 主键id bigint(20)
    private Long budgetCollectId; //budget_collect_id 实施预算汇总表 id bigint(20)
    private String budgetListIndex; //budget_list_index 序列（分类） varchar(20)
    private Integer budgetListType; //budget_list_type 类型（1:条目，2:合计，3：总计） tinyint(1)
    private String budgetProjectName; //budget_project_name 项目 varchar(100)
    private String budgetListName; //budget_list_name 名称及说明 varchar(100)
    private String budgetListUnit; //budget_list_unit 单位 varchar(50)
    private String budgetListAmount; //budget_list_amount 工程数量 varchar(20)
    private String budgetListMoney; //budget_list_money 工料单价（元）2018 varchar(20)
    private String budgetTotalMoney; //budget_total_money 合价（元） varchar(20)
    private String remark; //remark 备注 varchar(500)
    private Long createId; //create_id 录入人 bigint(20)
    private Long modifyId; //modify_id 修改人 bigint(20)
    private Timestamp createTime; //create_time 录入时间 datetime
    private Timestamp modifyTime; //modify_time 修改时间 datetime
}
