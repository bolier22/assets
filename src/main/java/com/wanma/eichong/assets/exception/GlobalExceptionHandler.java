package com.wanma.eichong.assets.exception;

import com.google.gson.Gson;
import com.wanma.eichong.assets.utils.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        JsonResult result  = new JsonResult();
        result.initError(2001,e.getMessage());
        return new Gson().toJson(result);
    }
}