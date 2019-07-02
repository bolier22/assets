package com.wanma.eichong.assets.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.15 下载文件列表 file_list
 */
@Getter
@Setter
@ToString
public class FileList implements Serializable {
    private Long fileId; //file_id 主键id bigint(20)
    private Long assetsStationId; //assets_station_id 充电站基表id bigint(20)
    private String fileName; //file_name 文件名 varchar(100)
    private String fileUrl; //file_url 下载路径 varchar(500)
    private Integer fileType; //file_type 文件类型（1-实施预算分项明细表，2-决算分项明细表，3-协议文件表，4-实施预算-分项明细文件，5-决算分项明细文件） tinyint(1)
    private Long createId; //create_id 录入人 bigint(20)
    private Long modifyId; //modify_id 修改人 bigint(20)
    private Timestamp createTime; //create_time 录入时间 datetime
    private Timestamp modifyTime; //modify_time 修改时间 datetime


    public void initFileList(Long assetsStationId, String fileName, String fileUrl, Integer fileType, Long createId) {
        this.assetsStationId = assetsStationId;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.createId = createId;
    }

    /**
     setFileid(); //主键id
     setAssetsstationid(); //充电站基表id
     setFilename(); //文件名
     setFileurl(); //下载路径
     setFiletype(); //文件类型（1-实施预算分项明细表，2-决算分项明细表，3-协议文件表，4-实施预算-分项明细文件，5-决算分项明细文件）
     setCreateid(); //录入人
     setModifyid(); //修改人
     setCreatetime(); //录入时间
     setModifytime(); //修改时间


     **/
}