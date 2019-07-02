package com.wanma.eichong.assets.service;

import com.wanma.eichong.assets.entity.UserAssets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface LoginService {
    void generateCaptcha(HttpServletRequest request, HttpServletResponse response, Integer width, Integer height) throws IOException;

    UserAssets login(UserAssets userAssets, HttpServletRequest request, HttpServletResponse response);
}
