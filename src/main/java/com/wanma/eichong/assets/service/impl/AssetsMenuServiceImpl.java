package com.wanma.eichong.assets.service.impl;

import com.wanma.eichong.assets.ds.DataSourceAspectService;
import com.wanma.eichong.assets.entity.AssetsMenu;
import com.wanma.eichong.assets.mapper.AssetsMenuMapper;
import com.wanma.eichong.assets.service.AssetsMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetsMenuServiceImpl implements AssetsMenuService {
    private final AssetsMenuMapper assetsMenuMapper;

    @Autowired
    public AssetsMenuServiceImpl(AssetsMenuMapper assetsMenuMapper) {
        this.assetsMenuMapper = assetsMenuMapper;
    }

    @DataSourceAspectService(dataSourceName = "masterDB")
    @Override
    public List<AssetsMenu> selectMenuList(AssetsMenu assetsMenu) {
        return assetsMenuMapper.selectMenuList(assetsMenu);
    }
}
