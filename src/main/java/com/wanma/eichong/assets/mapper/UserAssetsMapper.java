package com.wanma.eichong.assets.mapper;

import com.wanma.eichong.assets.entity.UserAssets;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserAssetsMapper {
    UserAssets loginUser(UserAssets userAssets);

    UserAssets selectUserAssetsById(Long userId);

    List<UserAssets> selectUserAssetsByIds(@Param("userIdList") List<Long> userIdList);
}
