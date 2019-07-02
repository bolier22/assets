package com.wanma.eichong.assets.mapper;

import com.wanma.eichong.assets.entity.OperateInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.16 运营信息基表 operate_info
 */
@Mapper
@Component
public interface OperateInfoMapper {
    Long selectOperateCountByStationId(OperateInfo operateInfo);

    List<OperateInfo> selectOperateListByStationId(OperateInfo operateInfo);

    void deleteOperateByOperateId(Long operateId);

    OperateInfo selectOperateByOperateId(Long operateId);

    void insertOperateInfo(OperateInfo operateInfo);

    void updateOperateInfo(OperateInfo operateInfo);
}
