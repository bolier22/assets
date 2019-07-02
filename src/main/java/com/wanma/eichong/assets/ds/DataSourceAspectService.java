package com.wanma.eichong.assets.ds;
import java.lang.annotation.*;

/** 
 *自定义注解 拦截service 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
public  @interface DataSourceAspectService {
    String dataSourceName()  default "";
    String description()  default "";
}