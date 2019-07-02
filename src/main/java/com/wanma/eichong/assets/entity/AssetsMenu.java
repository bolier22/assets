package com.wanma.eichong.assets.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 资产管理平台菜单表
 */
@Setter
@Getter
@ToString
public class AssetsMenu {
    private Long userId;
    private Long menuId;//菜单id
    private Integer menuType;//菜单分类 1：菜单 2：按钮
    private String contents;//菜单名称
    private String sort;//菜单排序
    private String url;//链接
    private Long parentMenuId;//父菜单id
    private String isSelected;
}
