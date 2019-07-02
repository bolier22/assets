package com.wanma.eichong.assets.utils;

import com.wanma.eichong.assets.config.ResultEnum;
import com.wanma.eichong.assets.exception.RunException;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class JsonResult implements Serializable {

    /**0 :success 1 :fail  3:
     * 结果信息编号，对应字典
     */
    private Integer status;

    /**
     * 返回的消息
     */
    private String errorMsg;
    /**
     * token 令牌
     */
    private String token;
    /**
     * 返回数据
     */
    private  Object dataObj;

    public JsonResult(){
        ResultEnum resultEnum = ResultEnum.SUCCESS;
        this.status = resultEnum.getStatus();
        this.errorMsg = resultEnum.getMessage();
    }

    public void initError(ResultEnum resultEnum){
        this.status = resultEnum.getStatus();
        this.errorMsg = resultEnum.getMessage();
    }


    public void initError(Integer status,String errorMsg){
        this.status = status;
        this.errorMsg = errorMsg;
    }

    public static JsonResult getJsonResult(ResultEnum resultEnum){
        JsonResult jsonResult = new JsonResult();
        jsonResult.initError(resultEnum);
        return jsonResult;
    }

    public static void throwException(ResultEnum resultEnum){
        throw new RunException(resultEnum.PARAM_ERROR);
    }
}
