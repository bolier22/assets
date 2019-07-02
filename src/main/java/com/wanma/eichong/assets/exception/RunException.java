package com.wanma.eichong.assets.exception;

import com.wanma.eichong.assets.config.ResultEnum;

public class RunException extends RuntimeException {//异常捕获使用 throw new RunException(resultEnum);
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 构造器
     * @param resultEnum
     */
    public RunException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.status = resultEnum.getStatus();
    }

    /**
     * 构造器
     * @param resultEnum
     * @param ex
     */
    public RunException(ResultEnum resultEnum,Exception ex) {
        super((ex.getMessage())!=null?(resultEnum.getMessage()+":"+ex.getMessage()):resultEnum.getMessage());
        this.status = resultEnum.getStatus();
    }

    /**
     * 构造器
     * @param message
     * @param status
     */
    public RunException(Integer status,String message) {
        super(message);
        this.status = status;
    }

}
