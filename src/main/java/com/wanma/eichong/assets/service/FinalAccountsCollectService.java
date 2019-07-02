package com.wanma.eichong.assets.service;

import com.wanma.eichong.assets.entity.FinalAccountsCollect;
import com.wanma.eichong.assets.mapper.FinalAccountsCollectListMapper;
import com.wanma.eichong.assets.mapper.FinalAccountsCollectMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.9 决算汇总表（导入数据） final_accounts_collect
 */
public interface FinalAccountsCollectService {
    int insertFinalAccountsCollect(List<Object> obj,Integer fileType);

    int deleteFinalAccountsCollectByStationId(Long assetsStationId,Integer fileType);

    FinalAccountsCollect selectFinalAccountsCollectByStationId(Long assetsStationId);
}