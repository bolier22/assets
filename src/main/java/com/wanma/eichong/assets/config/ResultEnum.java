package com.wanma.eichong.assets.config;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ResultEnum {

    SUCCESS(1000,"成功"),//保存成功

    SAVE_SUCCESS(1000,"保存成功"),//保存成功
    SAVE_FAILED(2001,"保存失败"),//保存失败

    DELETE_SUCCESS(1000,"删除成功"),//删除成功
    DELETE_FAILED(3001,"删除失败"),//删除失败

    UPDATE_SUCCESS(1000,"修改成功"),//修改成功
    UPDATE_FAILED(4001,"修改失败"),//修改失败

    QUERY_SUCCESS(1000,"查询成功"),//查询成功
    QUERY_FAILED(5001,"查询失败"),//查询失败

    IMPORT_SUCCESS(1000,"导入成功"),//导入成功
    IMPORT_FAILED(6001,"导入失败"),//导入失败
    IMPORT_FILE_NOTNULL(6002,"导入文件为空"),//未知异常

    EXEC_SUCCESS(1000,"执行成功"),//执行成功
    EXEC_FAILED(7001,"执行失败"),//执行失败

    EXEC_TIME_OUT(8001,"执行超时"),//执行超时
    SYSTEM_EXCEPTION(8002,"系统异常"),//系统异常
    UNKOWN_EXCEPTION(8003,"未知异常"),//未知异常


    PARAM_ERROR(8004,"参数有误"),//参数有误

    ERROR_MSG_NOT_ALLOW(9001,"无权限访问"),//无权限访问接口
    TOKEN_INVALID(9002,"Token无效，请重新登录");//Token无效


    /**
     * 结果信息编号，对应字典
     */
    private Integer status;

    /**
     * 返回的消息
     */
    private String message;

    ResultEnum(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
