package com.wanma.eichong.assets.service.impl;

import com.wanma.eichong.assets.entity.FinalAccountsCollectList;
import com.wanma.eichong.assets.mapper.FinalAccountsCollectListMapper;
import com.wanma.eichong.assets.service.FinalAccountsCollectListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.10 决算汇总表-条目表（导入数据） final_accounts_collect_list
 */
@Service
public class FinalAccountsCollectListServiceImpl implements FinalAccountsCollectListService {
    private final FinalAccountsCollectListMapper finalAccountsCollectListMapper;

    @Autowired
    public FinalAccountsCollectListServiceImpl(FinalAccountsCollectListMapper finalAccountsCollectListMapper) {
        this.finalAccountsCollectListMapper = finalAccountsCollectListMapper;
    }

    @Override
    public List<FinalAccountsCollectList> selectFinalAccountsCollectListListByStationId(Long final8sCollectId) {
        return finalAccountsCollectListMapper.selectFinalAccountsCollectListListByStationId(final8sCollectId);
    }
}
