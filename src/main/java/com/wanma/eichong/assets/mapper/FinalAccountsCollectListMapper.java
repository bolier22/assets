package com.wanma.eichong.assets.mapper;

import com.wanma.eichong.assets.entity.FinalAccountsCollectList;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.10 决算汇总表-条目表（导入数据） final_accounts_collect_list
 */
@Mapper
@Component
public interface FinalAccountsCollectListMapper {
    int insertBatchFinalAccountsCollectList(HashMap paramMap);

    int deleteFinalAccountsCollectListByCollectId(Long final8sCollectId);

    List<FinalAccountsCollectList> selectFinalAccountsCollectListListByStationId(Long final8sCollectId);
}
