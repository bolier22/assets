package com.wanma.eichong.assets.mapper;


import com.wanma.eichong.assets.entity.TblAreaCode;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface AreaCodeMapper {

	//Province;
	List<TblAreaCode> findProvince();

	//City;
	List<TblAreaCode> findCity(String areaSupCode);

	//Area;
	List<TblAreaCode> findArea(String areaSupCode);

	//Town;
	List<TblAreaCode> findTown(String areaSupCode);

	TblAreaCode findNameByAreaId(String areaId);

	TblAreaCode findNameByAreaCode(String areaCode);
}
