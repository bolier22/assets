package com.wanma.eichong.assets.mapper;

import com.wanma.eichong.assets.entity.FinalAccountsCollect;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.9 决算汇总表（导入数据） final_accounts_collect
 */
@Mapper
@Component
public interface FinalAccountsCollectMapper {

    Long insertFinalAccountsCollect(FinalAccountsCollect finalAccountsCollect);

    int deleteFinalAccountsCollectByStationId(Long assetsStationId);

    FinalAccountsCollect selectFinalAccountsCollectByStationId(Long assetsStationId);
}