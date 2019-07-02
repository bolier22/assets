package com.wanma.eichong.assets.mapper;

import com.wanma.eichong.assets.entity.OperateInfoList;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.17 运营信息条目表 operate_info_list
 */
@Mapper
@Component
public interface OperateInfoListMapper {
    void deleteOperateByOperateId(Long operateId);

    List<OperateInfoList> selectOperateInfoListListByOperateId(Long operateId);

    void insertOperateInfoList(OperateInfoList operateInfoList);

    void updateOperateInfoList(OperateInfoList operateInfoList);
}
