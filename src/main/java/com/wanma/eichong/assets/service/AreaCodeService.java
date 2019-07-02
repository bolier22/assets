package com.wanma.eichong.assets.service;

import com.wanma.eichong.assets.entity.TblAreaCode;

import java.util.List;

public interface AreaCodeService {
		//Province;
		List<TblAreaCode> findProvince();

		//City;
		List<TblAreaCode> findCity(String areaSupCode);

		//Area;
		List<TblAreaCode> findArea(String areaSupCode);

		//Town;
//		List<TblAreaCode> findTown(String areaSupCode);

	    TblAreaCode getNameByAreaId(String areaId);
    
}
