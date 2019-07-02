package com.wanma.eichong.assets.service.impl;

import com.google.common.base.Strings;
import com.wanma.eichong.assets.config.Constants;
import com.wanma.eichong.assets.config.RedisConfig;
import com.wanma.eichong.assets.entity.UserAssets;
import com.wanma.eichong.assets.exception.RunException;
import com.wanma.eichong.assets.service.LoginService;
import com.wanma.eichong.assets.service.UserAssetsService;
import com.wanma.eichong.assets.utils.CaptchaUtil;
import com.wanma.eichong.assets.utils.MD5Util;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class LoginServiceImpl implements LoginService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final UserAssetsService userAssetsService;

    @Autowired
    RedisConfig redisConfig;

    @Autowired
    public LoginServiceImpl(UserAssetsService userAssetsService) {
        this.userAssetsService = userAssetsService;
    }

    @Override
    public void generateCaptcha(HttpServletRequest request, HttpServletResponse response, Integer width, Integer height) throws IOException {
        CaptchaUtil captchaUtil = new CaptchaUtil(width, height);
        redisConfig.setValidTimeStr("assets:validCode:"+request.getSession().getId(),captchaUtil.getCode().toUpperCase(), 30L);
        captchaUtil.write(response.getOutputStream());
    }

    @Override
    public UserAssets login(UserAssets userAssets, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("login---getSession().getId():"+request.getSession().getId());
        // 校验用户名
        if (Strings.isNullOrEmpty(userAssets.getUserAccount())) {
            LOGGER.warn(this.getClass() + "-login is failed, username is null");
            throw new RunException(2011, "用户名为空");
        }
        // 校验密码
        if (Strings.isNullOrEmpty(userAssets.getUserPassword())) {
            LOGGER.warn(this.getClass() + "-login is failed, passwd is null");
            throw new RunException(2011, "密码为空");
        }
        // 校验账号
        UserAssets loginUser = userAssetsService.loginUser(userAssets);
        if (loginUser == null) {
            throw new RunException(2011, "用户名或密码错误");
        }
        // 数据库获取的密码进行二次加密md5（数据库密码 + 账户）
        String loginPassword = loginUser.getUserPassword();
        String loginAccount = loginUser.getUserAccount();
        loginUser.setUserPassword(MD5Util.Md5(loginPassword + loginAccount));
        if (!loginUser.getUserPassword().equals(userAssets.getUserPassword().substring(0, userAssets.getUserPassword().length() - 1))) {
            LOGGER.warn(this.getClass() + "-login is failed, userPassword is error|username={}|loginErrorCount={}", userAssets.getUserAccount());
            throw new RunException(2011, "密码不正确");
        }

        String decodedCaptcha = redisConfig.strGet("assets:validCode:"+request.getSession().getId());
        // 校验验证码
        if (StringUtil.isNullOrEmpty(decodedCaptcha)||!decodedCaptcha.equals(userAssets.getValidCode().toUpperCase())) {
            throw new RunException(2011, "验证码不正确");
        }
        request.getSession().setAttribute(Constants.SESSION_ASSETS_USER,loginUser);
        return loginUser;
    }

}
