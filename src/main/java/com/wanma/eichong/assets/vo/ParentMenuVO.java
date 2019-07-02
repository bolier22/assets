package com.wanma.eichong.assets.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单父级
 */
public class ParentMenuVO implements Serializable {
    private String menuId; // 菜单ID
    private String contents; // 菜单名称
    private String imageUrl; // 菜单图片
    private String isSelected;// 是否选中
    private String sort; // 排序
    private List<MenuVO> menuList = new ArrayList<>();

    public static ParentMenuVO valueOf(String contents, String imageUrl, String sort) {
        ParentMenuVO vo = new ParentMenuVO();
        vo.setContents(contents);
        vo.setIsSelected("1");
        vo.setImageUrl(imageUrl);
        vo.setSort(sort);
        return vo;
    }


    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    public List<MenuVO> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuVO> menuList) {
        this.menuList = menuList;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

}
