package com.wanma.eichong.assets.controller;

import com.wanma.eichong.assets.entity.UserAssets;
import com.wanma.eichong.assets.exception.RunException;
import com.wanma.eichong.assets.security.JwtUtil;
import com.wanma.eichong.assets.service.LoginService;
import com.wanma.eichong.assets.utils.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录
 */
@RestController
@RequestMapping("/")
public class LoginController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * 验证码图形生成
     */
    @RequestMapping(value = "getValidCode", method = RequestMethod.GET)
    @ResponseBody
    public void generateCaptcha(HttpServletRequest request, HttpServletResponse response) {
        try {
            loginService.generateCaptcha(request, response,  0 ,0 );
        } catch (Exception e) {
            LOGGER.error(this.getClass() + "-generateCaptcha is error", e);
            throw new RunException(2011, "验证码生成失败");
        }
    }

    /**
     * 用户登录 校验用户名、密码 、验证码,当天连续登录不能超过5次
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
//    @ResponseBody
//    public JsonResult login(@RequestBody UserAssets userAssets, HttpServletRequest request, HttpServletResponse response) {
    public JsonResult login(String userAccount, String passWord, String validCode, HttpServletRequest request,
                            HttpServletResponse response) {
        UserAssets userAssets = new UserAssets();
        userAssets.setUserPassword(passWord);
        userAssets.setUserAccount(userAccount);
        userAssets.setValidCode(validCode);
        JsonResult result  = new JsonResult();
        userAssets = loginService.login(userAssets, request, response);
        //从redis中取出生成二维码的key进行校验
        response.setHeader("authorization",JwtUtil.getToken(userAssets));
        return  result;
    }


}
