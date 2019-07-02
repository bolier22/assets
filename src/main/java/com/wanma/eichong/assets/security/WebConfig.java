package com.wanma.eichong.assets.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //允许全部请求跨域
        registry.addMapping("/**")
                .allowedMethods("GET", "PUT", "POST", "GET", "OPTIONS")
                .exposedHeaders("authorization").allowCredentials(true);

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器
//        registry.addInterceptor(new JwtInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new JwtInterceptor()).excludePathPatterns("/login").excludePathPatterns("/chargingStation/exportChargingStationList");
//                .excludePathPatterns("/merchant/isNotLog")
//                .excludePathPatterns("/customer/execleTemplate")
//                .excludePathPatterns("/customer/putExcleData/{mercSeq}/{childSeq}")
//                .excludePathPatterns("/voicestatement/fileupload")//文件上传
//                .excludePathPatterns("/callTaskDetail/exportCharacters/{detailSeq}/{callTime}/{mercSeq}/{taskSeq}");//导出文字对话

    }



}