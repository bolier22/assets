package com.wanma.eichong.assets.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.10 决算汇总表-条目表（导入数据） final_accounts_collect_list
 */
@Getter
@Setter
@ToString
public class FinalAccountsCollectList implements Serializable {
    private Long final8sListId; //final8s_list_id 主键id bigint(20)
    private Long final8sCollectId; //final8s_collect_id 决汇总表 id bigint(20)
    private String listSeq; //list_seq 序列 varchar(20)
    private String projectName; //project_name 项目名称 varchar(100)
    private String budgetMoney; //budget_money 立项预算总投资（元） varchar(20)
    private String final8sCollectMoney; //final8s_collect_money 决算总投资（元） varchar(20)
    private String coreMoney; //core_money 净核减+（增-)造价（元） varchar(20)
    private String corePer5ge; //core_per5ge 净核减+（增-）百分率 varchar(20)
    private String cons5ionUnit; //cons5ion_unit 施工单位 varchar(100)
    private Long createId; //create_id 录入人 bigint(20)
    private Long modifyId; //modify_id 修改人 bigint(20)
    private Timestamp createTime; //create_time 录入时间 datetime
    private Timestamp modifyTime; //modify_time 修改时间 datetime


    /**
     setFinal8slistid(); //主键id
     setFinal8scollectid(); //决汇总表 id
     setListseq(); //序列
     setProjectname(); //项目名称
     setBudgetmoney(); //立项预算总投资（元）
     setFinal8scollectmoney(); //决算总投资（元）
     setCoremoney(); //净核减+（增-)造价（元）
     setCoreper5ge(); //净核减+（增-）百分率
     setCons5ionunit(); //施工单位
     setCreateid(); //录入人
     setModifyid(); //修改人
     setCreatetime(); //录入时间
     setModifytime(); //修改时间


     **/
}
