package com.wanma.eichong.assets.controller;

import com.alibaba.fastjson.JSONObject;
import com.wanma.eichong.assets.entity.TblAreaCode;
import com.wanma.eichong.assets.config.ResultEnum;
import com.wanma.eichong.assets.service.AreaCodeService;
import com.wanma.eichong.assets.utils.JsonResult;
import com.wanma.eichong.assets.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc 地区资源
 * @auth libg
 * @Date 2019-04-27
 */
@RestController
@RequestMapping("/areaCode")		//地区信息
public class AreaCodeController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AreaCodeService areaCodeService;

	/**
	 * 获取省列表
	 */

	@RequestMapping("/getProvinceList")
	public JsonResult getProvinceList() {
		JsonResult result = new JsonResult();
		Map map = new HashMap();
		try {
			List<TblAreaCode> provinceList = areaCodeService.findProvince();
			map.put("provinceList",provinceList);
			result.setDataObj(map);
		}catch (Exception e){
			logger.error(e.getMessage());
			result.initError(ResultEnum.QUERY_FAILED);
		}
		return result;

	}

	/**
	 * 获取市列表
	 * @param {"areaSupCode":"12"}
	 */
	@RequestMapping("/getCityListByAreaSupCode")
	public JsonResult getCityListByAreaSupCode(@RequestBody Map<String,Object> data) {
		JsonResult result = new JsonResult();
		Map map = new HashMap();
		try {
			List<TblAreaCode> cityList = areaCodeService.findCity(data.get("areaSupCode").toString());
			map.put("cityList",cityList);
			result.setDataObj(map);
		}catch (Exception e){
			logger.error(e.getMessage());
			result.initError(ResultEnum.QUERY_FAILED);
		}
		return result;
	}


	/**
	 * 获取区列表
	 * param {"areaSupCode":"125"}
 	 */
	@RequestMapping("/getAreaListByAreaSupCode")
	public JsonResult getAreaListByAreaSupCode(@RequestBody JSONObject jsonObject) {
		JsonResult result = new JsonResult();
		Map map = new HashMap();
		try {
			String areaSupCode = JsonUtils.getStrValue(jsonObject, "areaSupCode");
			List<TblAreaCode> areaList = areaCodeService.findArea(areaSupCode);
			map.put("areaList",areaList);
			result.setDataObj(map);
		}catch (Exception e){
			logger.error(e.getMessage());
			result.initError(ResultEnum.QUERY_FAILED);
		}
		return result;
	}

	/**
	 *	根据地区编号获取地区  接口使用
	 * @param areaCode 地区编号
	 * @return
	 */
	@RequestMapping("/getNameByAreaId")
	public TblAreaCode getNameByAreaId(String areaCode) {
		return areaCodeService.getNameByAreaId(areaCode);
	}

}
