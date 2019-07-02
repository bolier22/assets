package com.wanma.eichong.assets.service;

import com.wanma.eichong.assets.entity.AssetsMenu;
import com.wanma.eichong.assets.entity.UserAssets;

import java.util.List;

public interface AssetsMenuService {
    List<AssetsMenu> selectMenuList(AssetsMenu assetsMenu);
}
