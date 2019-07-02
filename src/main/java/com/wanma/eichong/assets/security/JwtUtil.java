package com.wanma.eichong.assets.security;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanma.eichong.assets.entity.UserAssets;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.ServletException;
import java.util.Date;

public class JwtUtil {
	final static String base64EncodedSecretKey = "base64EncodedxdianhuAgentProject";//私钥
    final static long TOKEN_EXP = 1000 * 1800;//1000毫秒 = 1秒 过期时间,测试使用60秒  1800 三十分钟


    public static String getToken(UserAssets userAssets) {
        System.out.println("--------UserAccount------"+userAssets.getUserAccount());
        JSONObject jsonParam = new JSONObject();
        String userAssetsJson = jsonParam.toJSONString(userAssets);
        String tk = Jwts.builder()
                .setSubject(userAssets.getUserAccount())//主题 铭文头 可识别主体 信息 标识  代表这个JWT的主体，即它的所有人
                .claim("token", userAssetsJson)
                .setIssuedAt(new Date())//是一个时间戳，代表这个JWT的签发时间
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXP)) /*过期时间*/
                .signWith(SignatureAlgorithm.HS256, base64EncodedSecretKey)
                .compact();
        return "assets "+tk;
    }



    /**
     * @Date:17-12-12 下午6:21
     * @Author:root
     * @Desc:检查token,只要不正确就会抛出异常
     **/
    public static UserAssets checkToken(String token) throws ServletException {
        try {

            final Claims claims = Jwts.parser().setSigningKey(base64EncodedSecretKey).parseClaimsJws(token).getBody();
            System.out.println("有效期内");
            return (UserAssets) JSON2Obj(claims.get("token").toString(),UserAssets.class);

        } catch (ExpiredJwtException e1) {
            throw new ServletException("token expired");
        } catch (Exception e) {
            throw new ServletException("other token exception");
        }
    }
    /**
     * 将jsonStr转化为实体bean
     * @param jsonStr
     * @param obj
     * @return
     */
    public static<T> Object JSON2Obj(String jsonStr,Class<T> obj) {
        T t = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            t = objectMapper.readValue(jsonStr,
                    obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

//    final static String base64EncodedSecretKey = "base64EncodedSecretKey";//私钥
//    final static long TOKEN_EXP = 1000 * 60;//过期时间,测试使用60秒
//
//    public static String getToken(String userName) {
//    	System.out.println("--------userName------"+userName);
//    	String tk = Jwts.builder()
//                .setSubject(userName)
//                .claim("roles", "user")
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXP)) /*过期时间*/
//                .signWith(SignatureAlgorithm.HS256, base64EncodedSecretKey)
//                .compact();
//    	System.out.println("==========userName=="+tk);
//        return tk;
//    }
//
//    /**
//     * @Date:17-12-12 下午6:21
//     * @Author:root
//     * @Desc:检查token,只要不正确就会抛出异常
//     **/
//    public static void checkToken(String token) throws ServletException {
//        try {
//            final Claims claims = Jwts.parser().setSigningKey(base64EncodedSecretKey).parseClaimsJws(token).getBody();
//        } catch (ExpiredJwtException e1) {
//            throw new ServletException("token expired");
//        } catch (Exception e) {
//            throw new ServletException("other token exception");
//        }
//    }
}