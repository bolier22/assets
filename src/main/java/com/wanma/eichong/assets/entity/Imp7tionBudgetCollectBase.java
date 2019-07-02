package com.wanma.eichong.assets.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.7 实施预算汇总表（基表-导入数据） imp7tion_budget_collect_base
 */
@Getter
@Setter
@ToString
public class Imp7tionBudgetCollectBase implements Serializable {
    private Long budgetCollectId; //budget_collect_id 主键id bigint(20)
    private Long assetsStationId; //assets_station_id 充电站基表 id bigint(20)
    private String budgetCollectTitle; //budget_collect_title 表头名称 varchar(200)
    private String budgetProjectName; //budget_project_name 工程名称 varchar(100)
    private Long createId; //create_id 录入人 bigint(20)
    private Long modifyId; //modify_id 修改人 bigint(20)
    private Timestamp createTime; //create_time 录入时间 datetime
    private Timestamp modifyTime; //modify_time 修改时间 datetime
    /**
     setBudgetcollectid(); //主键id
     setAssetsstationid(); //充电站基表 id
     setBudgetcollecttitle(); //表头名称
     setBudgetprojectname(); //工程名称
     setCreateid(); //录入人
     setModifyid(); //修改人
     setCreatetime(); //录入时间
     setModifytime(); //修改时间


     **/
}