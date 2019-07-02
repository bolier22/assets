package com.wanma.eichong.assets.service.impl;

import com.wanma.eichong.assets.ds.DataSourceAspectService;
import com.wanma.eichong.assets.entity.TblAreaCode;
import com.wanma.eichong.assets.mapper.AreaCodeMapper;
import com.wanma.eichong.assets.service.AreaCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaCodeServiceImpl implements AreaCodeService {

    private final AreaCodeMapper areaCodeMapper;

	@Autowired
	public AreaCodeServiceImpl(AreaCodeMapper areaCodeMapper) {
		this.areaCodeMapper = areaCodeMapper;
	}

	@DataSourceAspectService(dataSourceName = "slaveDB")
	@Override
	public List<TblAreaCode> findProvince() {
		// TODO Auto-generated method stub
		return areaCodeMapper.findProvince();
	}

	@Override
	public List<TblAreaCode> findCity(String areaSupCode) {
		// TODO Auto-generated method stub
		return areaCodeMapper.findCity(areaSupCode);
	}

	@Override
	public List<TblAreaCode> findArea(String areaSupCode) {
		// TODO Auto-generated method stub
		return areaCodeMapper.findArea(areaSupCode);
	}
//
//	@Override
//	public List<TblAreaCode> findTown(String areaSupCode) {
//		// TODO Auto-generated method stub
//		return areaCodeMapper.findTown(areaSupCode);
//	}
	@Override
	public TblAreaCode getNameByAreaId(String areaId) {
		// TODO Auto-generated method stub
		return areaCodeMapper.findNameByAreaId(areaId);
	}

}
