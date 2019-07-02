package com.wanma.eichong.assets.service.impl;

import com.google.common.collect.Maps;
import com.wanma.eichong.assets.ds.DataSourceAspectService;
import com.wanma.eichong.assets.ds.DynamicDataSource;
import com.wanma.eichong.assets.entity.UserAssets;
import com.wanma.eichong.assets.mapper.UserAssetsMapper;
import com.wanma.eichong.assets.service.UserAssetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserAssetsServiceImpl implements UserAssetsService {
    @Autowired
    private UserAssetsMapper userAssetsMapper;

    @DataSourceAspectService(dataSourceName = "masterDB")
    @Override
    public UserAssets loginUser(UserAssets tempUser) {
        return userAssetsMapper.loginUser(tempUser);
    }

    @DataSourceAspectService(dataSourceName = "masterDB")
    @Override
    public UserAssets selectUserAssetsById(Long userId) {
        return userAssetsMapper.selectUserAssetsById(userId);
    }

    @DataSourceAspectService(dataSourceName = "masterDB")
    @Override
    public Map<Long, UserAssets> selectUserAssetsByIds(List<Long> userIdList) {
        List<UserAssets> userAssetsList = userAssetsMapper.selectUserAssetsByIds(userIdList);
        return Maps.uniqueIndex(userAssetsList,UserAssets::getUserId);
    }
}
