package com.wanma.eichong.assets.security;

import com.wanma.eichong.assets.config.RedisConfig;
import com.wanma.eichong.assets.config.ResultEnum;
import com.wanma.eichong.assets.entity.UserAssets;
import com.wanma.eichong.assets.exception.RunException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("=========request.getRequestURI()=========" + request.getRequestURI());

        if ("OPTIONS".equals(request.getMethod().toUpperCase())) {
            System.out.println("=========一次嗅探请求========="+RequestMethod.OPTIONS.name());
            return true;
        } else {
            if(request.getRequestURI().endsWith("getValidCode")){
                response.addHeader("validCode", request.getSession().getId());
                return true;
            }

            String authHeader = request.getHeader("authorization");
            System.out.println("========authorization===========" + authHeader);
            if (authHeader == null || !authHeader.startsWith("assets ")) {
                throw new RunException(ResultEnum.TOKEN_INVALID);
            }
            //取得token
            String token = authHeader.substring(7);
            try {
                UserAssets userAssets = JwtUtil.checkToken(token);
                response.addHeader("authorization", JwtUtil.getToken(userAssets));
                return true;
            } catch (Exception e) {
                response.setHeader("Access-Control-Allow-Origin", "*");//* or origin as u prefer
                response.setHeader("Access-Control-Allow-Credentials", "true");
                response.setHeader("Access-Control-Allow-Methods", "PUT, POST, GET, OPTIONS, DELETE");
                response.setHeader("Access-Control-Max-Age", "3600");
                response.setHeader("Access-Control-Allow-Headers", "content-type, authorization");
                throw new RunException(ResultEnum.UNKOWN_EXCEPTION);
            }
        }
    }
}