package com.wanma.eichong.assets.ds;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 切点类
 * @author libg
 * @since 2018-12-10
 * @version 1.0
 **/
@Aspect
@Component
@Order(1)
public  class DataSourceAspect {
    private  static  final Logger logger = LoggerFactory.getLogger(DataSourceAspect. class);

    /**
     * 前置通知 用于拦截service层
     * @param joinPoint 切点
     */
    @Before("execution(* com.wanma.eichong.assets.service.impl.*.*(..))")
    public  void doBefore(JoinPoint joinPoint) {
        try {
            //*========控制台输出=========*//
            logger.info("=====前置通知开始====="+"请求方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
            String dataSourceName = getServiceMthodTargetValue(joinPoint,2);
            logger.info("指定数据源:" +dataSourceName +"=====修改前的数据源====="+ DynamicDataSource.getDataSource());
            if(!"".equals(dataSourceName)){//
                DynamicDataSource.contextHolder.set(dataSourceName);
                return;
            }
            //获取使用注解的方法名称，根据名称切换数据源
            String methodName = joinPoint.getSignature().getName();
            //查询操作切换成从数据库 查询select，修改update，删除delete，保存save 开通
            if(methodName.startsWith("select")){
                DynamicDataSource.contextHolder.set("slaveDB");//从库
            }else if(methodName.startsWith("update")||methodName.startsWith("delete")||methodName.startsWith("save")){//主库
                DynamicDataSource.contextHolder.set("datasource");
            }else{
                DynamicDataSource.contextHolder.set("datasource");//默认统计库
            }
            logger.info("=====修改后的数据源====="+ DynamicDataSource.getDataSource());
        }  catch (Exception e) {
            logger.error("异常信息:{}", e.getMessage());
        }
    }

    @After("execution(* com.wanma.eichong.assets.service.impl.*.*(..))")
    public  void doAfter(JoinPoint joinPoint) {
        DynamicDataSource.contextHolder.set("dataSource");
    }


    /**
     * 获取注解中注解的值  flag:1=description,2:dataSourceName
     * @param joinPoint 切点
     * @return 注解的值
     * @throws Exception
     */
    public  static String getServiceMthodTargetValue(JoinPoint joinPoint, int flag){
        String targetValue = "";
        try{
            String targetName = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            Object[] arguments = joinPoint.getArgs();
            Class targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();

            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class[] clazzs = method.getParameterTypes();
                    if (clazzs.length == arguments.length) {
                        if(flag == 1){
                            targetValue = method.getAnnotation(DataSourceAspectService. class).description();
                        }else if(flag == 2){
                            targetValue = method.getAnnotation(DataSourceAspectService. class).dataSourceName();
                        }
                        break;
                    }
                }
            }
        }catch (Exception e){
            return targetValue = "";
        }
        return targetValue;
    }
}