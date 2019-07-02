package com.wanma.eichong.assets.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: zhugf
 * @Description:
 * @Date created in 2018/5/14
 */
@Component
@ConfigurationProperties(prefix = "system-params")
@Getter
@Setter
public class SystemParams {
 
    private String imageUri;

    private String imageName;

    private long costMaxTime;

    private String sftpHost;//: 10.9.2.49
    private int sftpPort;//: 22
    private String sftpUsername;//: apache
    private String sftpPassword;//: Frdr2312dd22
    private String sftpDir;//: /var/www/html/deploy/test/
    private String fileDownload;//http://10.9.2.49/deploy/test/
}
