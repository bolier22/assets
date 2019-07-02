package com.wanma.eichong.assets.service;

import com.wanma.eichong.assets.entity.UserAssets;

import java.util.List;
import java.util.Map;

public interface UserAssetsService {
    UserAssets loginUser(UserAssets tempUser);

    UserAssets selectUserAssetsById(Long userId);

    Map<Long,UserAssets> selectUserAssetsByIds(List<Long> userIdList);
}
