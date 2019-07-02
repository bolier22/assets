package com.wanma.eichong.assets.service;

import com.wanma.eichong.assets.entity.OperateInfo;
import com.wanma.eichong.assets.entity.OperateInfoList;
import com.wanma.eichong.assets.entity.UserAssets;

import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.16 运营信息基表 operate_info
 */
public interface OperateInfoService {
    Long selectOperateCountByStationId(OperateInfo operateInfo);

    List<OperateInfo> selectOperateListByStationId(OperateInfo operateInfo);

    void deleteOperateByOperateId(Long operateId);

    OperateInfo selectOperateByOperateId(Long operateId);

    List<OperateInfoList> selectOperateInfoListListByOperateId(Long operateId);

    void saveOperateInfo(OperateInfo operateInfo, List<OperateInfoList> operateInfoListList, UserAssets loginUser);

    void deleteOperateByStationId(Long assetsStationId);
}
