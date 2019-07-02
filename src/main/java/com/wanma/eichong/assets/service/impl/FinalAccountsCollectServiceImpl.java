package com.wanma.eichong.assets.service.impl;

import com.wanma.eichong.assets.entity.FinalAccountsCollect;
import com.wanma.eichong.assets.entity.FinalAccountsCollectList;
import com.wanma.eichong.assets.mapper.FileListMapper;
import com.wanma.eichong.assets.mapper.FinalAccountsCollectListMapper;
import com.wanma.eichong.assets.mapper.FinalAccountsCollectMapper;
import com.wanma.eichong.assets.service.FinalAccountsCollectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.9 决算汇总表（导入数据） final_accounts_collect
 */
@Service
public class FinalAccountsCollectServiceImpl implements FinalAccountsCollectService {
    private static final Logger logger = LoggerFactory.getLogger(FinalAccountsCollectServiceImpl.class);

    @Autowired
    FinalAccountsCollectMapper finalAccountsCollectMapper;

    @Autowired
    FinalAccountsCollectListMapper finalAccountsCollectListMapper;

    @Autowired
    FileListMapper fileListMapper;

    @Transactional
    @Override
    public int insertFinalAccountsCollect(List<Object> fina8sListObj,Integer fileType) {
//        final8sBudget.add(0,1);
//        final8sBudget.add(1,final8sCollectBase);
//        final8sBudget.add(2,final8sCollectListList);
        int resultCode = 0;
        try{
            String code = fina8sListObj.get(0).toString();
            if("1".equals(code)){
                FinalAccountsCollect finalAccountsCollect = (FinalAccountsCollect) fina8sListObj.get(1);
                deleteFinalAccountsCollectByStationIdNoTransactional(finalAccountsCollect.getAssetsStationId(),fileType);//插入之前首先进行删除操作 然后再进行插入

                finalAccountsCollectMapper.insertFinalAccountsCollect(finalAccountsCollect);

                List<FinalAccountsCollectList> fina8sList = (List<FinalAccountsCollectList>)  fina8sListObj.get(2);
                HashMap paramMap = new HashMap<>();
                paramMap.put("final8sCollectId",finalAccountsCollect.getFinal8sCollectId());
                paramMap.put("fina8sList",fina8sList);
                finalAccountsCollectListMapper.insertBatchFinalAccountsCollectList(paramMap);
                resultCode = 1;
            }
        }catch (Exception e){
            logger.error(this.getClass() + ".insertFinalAccountsCollect is error=STRAT#{}#END",e.getMessage());
        }
        return resultCode;
    }

    private int deleteFinalAccountsCollectByStationIdNoTransactional(Long assetsStationId,Integer fileType) {
        int resultCode = 0;
        try{
            FinalAccountsCollect fina8sCollect = finalAccountsCollectMapper.selectFinalAccountsCollectByStationId(assetsStationId);
            if(fina8sCollect != null){
                finalAccountsCollectMapper.deleteFinalAccountsCollectByStationId(assetsStationId);
                finalAccountsCollectListMapper.deleteFinalAccountsCollectListByCollectId(fina8sCollect.getFinal8sCollectId());
                resultCode = 1;
            }
        }catch (Exception e){
            logger.error(this.getClass() + ".deleteFinalAccountsCollectByStationIdNoTransactional is error=STRAT#{}#END",e.getMessage());
        }
        return resultCode;
    }

    @Transactional
    @Override
    public int deleteFinalAccountsCollectByStationId(Long assetsStationId,Integer fileType) {
        return deleteFinalAccountsCollectByStationIdNoTransactional(assetsStationId,fileType);
    }

    @Override
    public FinalAccountsCollect selectFinalAccountsCollectByStationId(Long assetsStationId) {
        return finalAccountsCollectMapper.selectFinalAccountsCollectByStationId(assetsStationId);
    }


}