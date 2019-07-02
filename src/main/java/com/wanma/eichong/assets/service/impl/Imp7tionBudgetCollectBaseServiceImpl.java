package com.wanma.eichong.assets.service.impl;

import com.wanma.eichong.assets.entity.Imp7tionBudgetCollectBase;
import com.wanma.eichong.assets.entity.Imp7tionBudgetCollectList;
import com.wanma.eichong.assets.mapper.FileListMapper;
import com.wanma.eichong.assets.mapper.Imp7tionBudgetCollectBaseMapper;
import com.wanma.eichong.assets.mapper.Imp7tionBudgetCollectListMapper;
import com.wanma.eichong.assets.service.Imp7tionBudgetCollectBaseService;
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
 * @desc 1.7 实施预算汇总表（基表-导入数据） imp7tion_budget_collect_base
 */
@Service
public class Imp7tionBudgetCollectBaseServiceImpl implements Imp7tionBudgetCollectBaseService {
    private static final Logger logger = LoggerFactory.getLogger(FinalAccountsCollectServiceImpl.class);

    private final Imp7tionBudgetCollectBaseMapper imp7tionBudgetCollectBaseMapper;
    private final Imp7tionBudgetCollectListMapper imp7tionBudgetCollectListMapper;
    private final FileListMapper fileListMapper;
    @Autowired
    public Imp7tionBudgetCollectBaseServiceImpl(Imp7tionBudgetCollectBaseMapper imp7tionBudgetCollectBaseMapper, Imp7tionBudgetCollectListMapper imp7tionBudgetCollectListMapper, FileListMapper fileListMapper) {
        this.imp7tionBudgetCollectBaseMapper = imp7tionBudgetCollectBaseMapper;
        this.imp7tionBudgetCollectListMapper = imp7tionBudgetCollectListMapper;
        this.fileListMapper = fileListMapper;
    }

    @Transactional
    @Override
    public int insertImp7tionBudgetCollect(List<Object> imp7tionListObj,Integer fileType) {
        //        imp7tionBudget.add(0,1);
//        imp7tionBudget.add(1,imp7tionBudgetCollectBase);
//        imp7tionBudget.add(2,imp7tionBudgetCollectListList);
        int resultCode = 0;
        try{
            String code = imp7tionListObj.get(0).toString();
            if("1".equals(code)){
                Imp7tionBudgetCollectBase imp7tionCollect = (Imp7tionBudgetCollectBase) imp7tionListObj.get(1);
                deleteImp7tionBudgetCollectByStationIdNoTransactional(imp7tionCollect.getAssetsStationId(),fileType);//插入之前首先进行删除操作 然后再进行插入
                imp7tionBudgetCollectBaseMapper.insertImp7tionBudgetCollectBase(imp7tionCollect);
                List<Imp7tionBudgetCollectList> imp7tionList = (List<Imp7tionBudgetCollectList>)  imp7tionListObj.get(2);
                HashMap paramMap = new HashMap<>();
                paramMap.put("imp7tionList",imp7tionList);
                paramMap.put("budgetCollectId",imp7tionCollect.getBudgetCollectId());
                imp7tionBudgetCollectListMapper.insertBatchImp7tionBudgetCollectList(paramMap);
                resultCode = 1;
            }
        }catch (Exception e){
            logger.error(this.getClass() + ".insertImp7tionBudgetCollect is error=STRAT#{}#END",e.getMessage());
        }
        return resultCode;
    }


    private int deleteImp7tionBudgetCollectByStationIdNoTransactional(Long assetsStationId,Integer fileType) {
        int resultCode = 0;
        try{
            Imp7tionBudgetCollectBase imp7tionBudgetCollectBase = imp7tionBudgetCollectBaseMapper.selectImp7tionBudgetCollectBaseByStationId(assetsStationId);
            if(imp7tionBudgetCollectBase != null){
                imp7tionBudgetCollectBaseMapper.deleteImp7tionBudgetCollectBaseByStationId(assetsStationId);
                imp7tionBudgetCollectListMapper.deleteImp7tionBudgetCollectListByCollectId(imp7tionBudgetCollectBase.getBudgetCollectId());
                resultCode = 1;
            }
        }catch (Exception e){
            logger.error(this.getClass() + ".deleteFinalAccountsCollectByStationIdNoTransactional is error=STRAT#{}#END",e.getMessage());
        }
        return resultCode;
    }

    @Transactional
    @Override
    public int deleteImp7tionBudgetCollectByStationId(Long assetsStationId,Integer fileType) {
        return deleteImp7tionBudgetCollectByStationIdNoTransactional(assetsStationId,fileType);
    }

    @Override
    public Imp7tionBudgetCollectBase selectImp7tionBudgetCollectBaseByStationId(Long assetsStationId) {
        return imp7tionBudgetCollectBaseMapper.selectImp7tionBudgetCollectBaseByStationId(assetsStationId);
    }
}