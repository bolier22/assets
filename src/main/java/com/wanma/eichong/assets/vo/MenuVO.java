package com.wanma.eichong.assets.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuVO implements Serializable {

    private String menuId; // 菜单ID
    private String contents; // 菜单名称
    private String url; // 菜单链接
    private String parentMenuId; // 菜单父级
    private String isSelected; // 是否选中
    private String sort; // 排序
    private String type;//按钮 字段
    private List<MenuVO> menuList = new ArrayList<>();

    public static MenuVO valueOf(String contents, String url, String sort) {
        MenuVO menu = new MenuVO();
        menu.setContents(contents);
        menu.setUrl(url);
        menu.setSort(sort);
        return menu;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(String parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public List<MenuVO> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuVO> menuList) {
        this.menuList = menuList;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
