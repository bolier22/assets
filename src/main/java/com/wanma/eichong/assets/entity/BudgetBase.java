package com.wanma.eichong.assets.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.3 立项预算表-2.1基表信息表（导入数据） budget_base
 */
@Getter
@Setter
@ToString
public class BudgetBase {
    private Long budgetId; //budget_id 主键id bigint(20)
    private Long assetsStationId; //assets_station_id 充电站基表 id bigint(20)
    private String budgetIndex; //budget_index 序列（排序合并） varchar(20)
    private String projectBase; //project_base 项目基表信息表 varchar(100)
    private String baseInfo; //base_info 基础信息 varchar(100)
    private String budget1yearNumber; //budget_1year_number 第1年数值 varchar(20)
    private String budget2yearNumber; //budget_2year_number 第2年数值 varchar(20)
    private String budget3yearNumber; //budget_3year_number 第3年数值 varchar(20)
    private String budget4yearNumber; //budget_4year_number 第4年数值 varchar(20)
    private String budget5yearNumber; //budget_5year_number 第5年数值 varchar(20)
    private String remark; //remark 备注 varchar(500)
    private Long createId; //create_id 录入人 bigint(20)
    private Long modifyId; //modify_id 修改人 bigint(20)
    private Timestamp createTime; //create_time 录入时间 datetime
    private Timestamp modifyTime; //modify_time 修改时间 datetime
}