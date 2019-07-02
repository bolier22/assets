package com.wanma.eichong.assets.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.9 决算汇总表（导入数据） final_accounts_collect
 */
@Getter
@Setter
@ToString
@Repository
public class FinalAccountsCollect {
    private Long final8sCollectId; //final8s_collect_id 主键id bigint(20)
    private Long assetsStationId; //assets_station_id 充电站基表 id bigint(20)
    private String budgetCollectTitle; //budget_collect_title 表头名称 varchar(200)
    private String projectName; //project_name 项目名称 varchar(100)
    private String projectStage; //project_stage 项目阶段 varchar(100)
    private String projectUnit; //project_unit 建设单位（城市公司） varchar(100)
    private String budgetMoney; //budget_money 立项预算总投资（元） varchar(20)
    private String projectDesc; //project_desc 项目概况 varchar(500)
    private String chargingPilesAmount; //charging_piles_amount 充电桩规格数量 varchar(20)
    private String totalPower; //total_power 总功率（kw) varchar(20)
    private String tra6erAmount; //tra6er_amount 变压器规格数量 varchar(20)
    private String totalCapacity; //total_capacity 总容量（kva) varchar(20)
    private String projectCityLeader; //project_city_leader 城市公司项目负责人 varchar(50)
    private String projectHeadLeader; //project_head_leader 工程总部负责人 varchar(50)
    private String remark1; //remark1 注1 varchar(100)
    private String remark2; //remark2 注2 varchar(100)
    private String remark3; //remark3 注3 varchar(100)
    private String remark4; //remark4 注4 varchar(100)
    private Long createId; //create_id 录入人 bigint(20)
    private Long modifyId; //modify_id 修改人 bigint(20)
    private Timestamp createTime; //create_time 录入时间 datetime
    private Timestamp modifyTime; //modify_time 修改时间 datetime

}