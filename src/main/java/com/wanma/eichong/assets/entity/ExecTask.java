package com.wanma.eichong.assets.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ExecTask {
    private Long id;

    private String name;

    private String execUrl;

    private String execKey;

    private Long execExpired;

    private Boolean execStatus;

    private String execServiceIp;

    private Long execTime;

    private Date beginTime;

    private Date endTime;

    private String sendEmail;

}