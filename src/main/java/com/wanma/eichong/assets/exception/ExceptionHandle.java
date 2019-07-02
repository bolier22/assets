package com.wanma.eichong.assets.exception;

import com.wanma.eichong.assets.utils.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class ExceptionHandle {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    private static final String ERROR_VIEW = "common/error";
    @ExceptionHandler
    @ResponseBody
    public Object handle(HttpServletRequest request,RunException run){
        //获取错误的地址
        String excepUrl = "";
        Object obj = request.getRequestURL();
        if(obj!=null){
            excepUrl = obj.toString();
        }
        JsonResult result  = new JsonResult();
        result.initError(run.getStatus(),run.getMessage());
        return result;
    }

    /**
     * 判断是否是ajax请求方式
     * @param request
     * @return
     */
    private static boolean isAjax(HttpServletRequest request){
        String header = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(header)?true:false;
    }

    private static ModelAndView resultModelAndView(Integer status,String message,String excepUrl){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("status",status);
        modelAndView.addObject("message",message);
        modelAndView.addObject("excepUrl",excepUrl);
        modelAndView.setViewName(ERROR_VIEW);
        return modelAndView;

    }
}
