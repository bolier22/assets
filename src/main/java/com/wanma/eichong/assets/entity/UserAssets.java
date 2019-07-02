package com.wanma.eichong.assets.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
* 资产管理平台管理员
*/
@Setter
@Getter
@ToString
public class UserAssets {
    private Long userId;
    private String userAccount;
    private String userPassword;
    private Integer userLevel;
    private Integer userStatus;//1:正常 2:冻结  3:删除
    private String gmtCreate;
    private Long relatedId;//绑定的ims管理员
    private Long cpyId;//渠道id
    private String cpyName;//渠道名称
    String validCode;//验证码
    private String creator;
    private String modifier;


}