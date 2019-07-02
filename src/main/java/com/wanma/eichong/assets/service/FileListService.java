package com.wanma.eichong.assets.service;

import com.wanma.eichong.assets.entity.FileList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.15 下载文件列表 file_list
 */
public interface FileListService {

    Long insertFileList(FileList fileList);

    List<FileList> selectFileListListByStationIdAndType(Long stationId, Integer fileType);

    FileList selectFileListByFileId(Long fileId);

    int deleteFileListByStationIdAndType(Long assetsStationId, Integer fileType);

    int deleteFileListByFileId(Long fileId);

    void deleteFileListByStationId(Long assetsStationId);
}