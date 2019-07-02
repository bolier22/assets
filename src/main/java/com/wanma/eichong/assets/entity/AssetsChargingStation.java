package com.wanma.eichong.assets.entity;

import com.wanma.eichong.assets.utils.Pager;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.1 充电站资产基表 assets_charging_station
 */
@Getter
@Setter
@ToString
public class AssetsChargingStation  {
    private Long assetsStationId; //assets_station_id 主键id bigint(20)
    private String stationName; //station_name 充电站名称 varchar(100)
    private String stationCode; //station_code 充电站编码（xxx规则） varchar(100)
    private String approvalFlowingCode; //approval_flowing_code OA立项流程号（xxx规则） varchar(100)
    private Integer stationStage; //station_stage 文件类型（所处阶段(0:新建，1：立项，2：实施，3：决算，4：运营)）tinyint(1)
    private String provinceCode; //province_code 省code varchar(100)
    private String cityCode; //city_code 市code varchar(100)
    private String areaCode; //area_code 区code varchar(100)
    private String stationAddress; //station_address 充电站地址 varchar(500)
    private String projectLeader; //project_leader 项目负责人 varchar(100)
    private String approvalTotalPower; //approval_total_power 立项总功率（W) bigint(20)
    private String approvalTotalCost; //approval_total_cost 立项总投资(元) bigint(20)
    private String imp7tionTotalPower; //imp7tion_total_power 实施总功率（W) bigint(20)
    private String imp7tionTotalCost; //imp7tion_total_cost 实施总投资(元) bigint(20)
    private String final8sTotalPower; //final8s_total_power 决算总功率（W) bigint(20)
    private String final8sTotalCost; //final8s_total_cost 决算总投资(元) bigint(20)
    private Long createId; //create_id 录入人 bigint(20)
    private Long modifyId; //modify_id 修改人 bigint(20)
    private String modifier;
    private String createTime; //create_time 录入时间 datetime
    private String modifyTime; //modify_time 修改时间 datetime
    private Pager pager;
    private String createStartTime;
    private String createEndTime;
    private String cityName;
    private String stationStageStr;//导出
    private String power;//导出
    private String cost;//导出

}