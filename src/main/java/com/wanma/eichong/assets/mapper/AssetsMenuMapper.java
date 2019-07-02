package com.wanma.eichong.assets.mapper;

import com.wanma.eichong.assets.entity.AssetsMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface AssetsMenuMapper {
    List<AssetsMenu> selectMenuList(AssetsMenu assetsMenu);
}
