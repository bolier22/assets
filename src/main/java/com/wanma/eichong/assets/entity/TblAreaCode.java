package com.wanma.eichong.assets.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
* @author libg
* 2018-06-01
* tbl_area_code  地区信息表
*/
@Getter
@Setter
public class TblAreaCode implements Serializable {  

	private String areaId; //area_id ID varchar
	private String areaCode; //area_code 代码 char
	private String areaName; //area_name 名称 varchar
	private String areaShortName; //area_short_name 简称 char
	private String areaFullName; //area_full_name 区域全路径名称 varchar
	private String areaFullCode; //area_full_code 全路径ID值 varchar
	private String areaPostcode; //area_postcode 邮编 char
	private String areaAreaCode; //area_area_code 区号 char
	private String areaLevel; //area_level 级别 char
	private String areaSupCode; //area_sup_code 上级ID varchar
	private String areaRemark; //area_remark 备注 varchar
	private List<TblAreaCode> areaCodeList;

	
}
