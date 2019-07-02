package com.wanma.eichong.assets.mapper;

import com.wanma.eichong.assets.entity.FileList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.15 下载文件列表 file_list
 */
@Mapper
@Component
public interface FileListMapper {

    Long insertFileList(FileList fileList);

    int deleteFileListByStationIdAndType(@Param("assetsStationId") Long assetsStationId,@Param("fileType") Integer fileType);

    List<FileList> selectFileListListByStationIdAndType(@Param("assetsStationId") Long assetsStationId,@Param("fileType") Integer fileType);

    FileList selectFileListByFileId(Long fileId);

    int deleteFileListByFileId(@Param("fileId") Long fileId);

    List<FileList> selectFileListListByStationId(Long assetsStationId);
}