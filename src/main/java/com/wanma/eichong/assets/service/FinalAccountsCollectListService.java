package com.wanma.eichong.assets.service;

import com.wanma.eichong.assets.entity.FinalAccountsCollectList;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.10 决算汇总表-条目表（导入数据） final_accounts_collect_list
 */
public interface FinalAccountsCollectListService {

    List<FinalAccountsCollectList> selectFinalAccountsCollectListListByStationId(Long final8sCollectId);
}
