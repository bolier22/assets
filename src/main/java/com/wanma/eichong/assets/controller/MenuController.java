package com.wanma.eichong.assets.controller;

import com.wanma.eichong.assets.config.Constants;
import com.wanma.eichong.assets.config.MenuType;
import com.wanma.eichong.assets.entity.AssetsMenu;
import com.wanma.eichong.assets.entity.UserAssets;
import com.wanma.eichong.assets.config.ResultEnum;
import com.wanma.eichong.assets.service.AssetsMenuService;
import com.wanma.eichong.assets.utils.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class MenuController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final AssetsMenuService assetsMenuService;

    @Autowired
    public MenuController(AssetsMenuService assetsMenuService) {
        this.assetsMenuService = assetsMenuService;
    }

    /**
     * 查询一级菜单
     */
    @RequestMapping(value = "/getSelfMenuList")
    public JsonResult getSelfMenuList(HttpServletRequest request) {
        JsonResult result = new JsonResult();
        try {
            UserAssets loginUser = (UserAssets) request.getSession().getAttribute(Constants.SESSION_ASSETS_USER);
            if(null == loginUser){
                result.initError(ResultEnum.TOKEN_INVALID);
                return result;
            }
            AssetsMenu assetsMenu = new AssetsMenu();
            assetsMenu.setUserId(loginUser.getUserId());
            assetsMenu.setMenuType(MenuType.MENU_TYPE);
            assetsMenu.setParentMenuId(0L);
            List<AssetsMenu> assetsMenuList = assetsMenuService.selectMenuList(assetsMenu);
            result.setDataObj(assetsMenuList);
        }catch (Exception e){
            LOGGER.error(this.getClass() + ".getSelfButtonTree() error:", e);
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return result;
    }

    /**
     * 根据一级菜单查询二级菜单
     */
    @RequestMapping(value = "getSelfMenuByMenuId")
    public JsonResult getSelfMenuByMenuId(HttpServletRequest request,Long menuId) {
        JsonResult result = new JsonResult();
        try {
            UserAssets loginUser = (UserAssets) request.getSession().getAttribute("loginUser");
            if(null == loginUser){
                result.initError(ResultEnum.TOKEN_INVALID);
                return result;
            }
            AssetsMenu assetsMenu = new AssetsMenu();
            assetsMenu.setUserId(loginUser.getUserId());
            assetsMenu.setMenuType(MenuType.MENU_TYPE);
            assetsMenu.setParentMenuId(menuId);
            List<AssetsMenu> assetsMenuList = assetsMenuService.selectMenuList(assetsMenu);
            result.setDataObj(assetsMenuList);
        }catch (Exception e){
            LOGGER.error(this.getClass() + ".getSelfButtonByMenuId() error:", e);
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return result;
    }

    /**
     * 根据菜单查询 按钮
     */
    @RequestMapping(value = "getSelfButtonByMenuId")
    public JsonResult getSelfButtonByMenuId(HttpServletRequest request,Long menuId) {
        JsonResult result = new JsonResult();
        try {
            UserAssets loginUser = (UserAssets) request.getSession().getAttribute("loginUser");
            if(null == loginUser){
                result.initError(ResultEnum.TOKEN_INVALID);
                return result;
            }
            AssetsMenu assetsMenu = new AssetsMenu();
            assetsMenu.setUserId(loginUser.getUserId());
            assetsMenu.setMenuType(MenuType.BUTTON_TYPE);
            assetsMenu.setParentMenuId(menuId);
            List<AssetsMenu> assetsMenuList = assetsMenuService.selectMenuList(assetsMenu);
            result.setDataObj(assetsMenuList);
        }catch (Exception e){
            LOGGER.error(this.getClass() + ".getSelfButtonByMenuId() error:", e);
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return result;
    }
}
