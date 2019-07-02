package com.wanma.eichong.assets.controller;

import com.wanma.eichong.assets.config.Constants;
import com.wanma.eichong.assets.config.SystemParams;
import com.wanma.eichong.assets.entity.FileList;
import com.wanma.eichong.assets.entity.UserAssets;
import com.wanma.eichong.assets.security.JwtUtil;
import com.wanma.eichong.assets.service.FileListService;
import com.wanma.eichong.assets.utils.FileUtil;
import com.wanma.eichong.assets.utils.SftpTooL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class BaseController {
    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    protected HttpServletResponse response;
    protected HttpServletRequest request;

    @Autowired
    SystemParams systemParams;

    @Autowired
    FileListService fileListService;

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.response = response;
        this.request = request;
    }

    /**
     * 获取当前用户信息(获取用户其他信息可以从这个获取，比如Level级别,adminType)
     *
     * @return UserDO
     */
    public UserAssets getCurrentLoginUser() {
        UserAssets login = null;
        try {
            login = (UserAssets) request.getSession().getAttribute("loginUser");
            if (null == login) {
                LOGGER.error(this.getClass() + "-getCurrentLoginUser is error, login is null");
            }
        } catch (Exception e) {
            LOGGER.error(this.getClass() + "-getCurrentLoginUser is error", e);
        }
        return login;
    }

    public void commonUploadFile(MultipartFile file, Long stationId, Integer fileType) {
        String host = systemParams.getSftpHost();//"10.9.2.49";
        int port = systemParams.getSftpPort();//22;
        String username = systemParams.getSftpUsername();//"apache";
        String password = systemParams.getSftpPassword();//"Frdr2312dd22";
        int handleType = 1;//1:上传 2：删除文件
        String directory = systemParams.getSftpDir();//"/var/www/html/deploy/test/";
        String folderPath = "excel/"+stationId+"/"+fileType+"/";
        String fileNameOrigin = file.getOriginalFilename();

        String name = fileNameOrigin.substring(0,fileNameOrigin.lastIndexOf("."));
        String fileSuffix = fileNameOrigin.substring(fileNameOrigin.lastIndexOf("."));
        String fileName = FileUtil.str2Pinyin(name,null)+fileSuffix;
        SftpTooL.uploadMultipartFile(host,port,username,password,file,handleType,directory,folderPath,fileName);

        Long userId = 10001l;
        String fileUrl = folderPath+fileName;
        //stationId 充电站基表id,fileName 文件名,filePath+fileName 下载路径,
        // fileType 文件类型（1-实施预算分项明细表，2-决算分项明细表，3-协议文件表，4-实施预算-分项明细文件，5-决算分项明细文件）,userId 录入人
        FileList fileList = new FileList();
        fileList.initFileList(stationId,fileNameOrigin,fileUrl,fileType,userId);
        fileListService.insertFileList(fileList);
    }

    public void commonDeleteFile(Long fileId,Long stationId, Integer fileType){
        String fileUrl = "";
        if(Constants.FILETYPE_CODE1 == fileType||Constants.FILETYPE_CODE2 == fileType||Constants.FILETYPE_CODE4 == fileType){
            List<FileList> fileList = fileListService.selectFileListListByStationIdAndType(stationId,fileType);
            if(fileList.size()>0){
                fileUrl = fileList.get(0).getFileUrl();
            }
            fileListService.deleteFileListByStationIdAndType(stationId,fileType);
        }else{
            FileList fileList = fileListService.selectFileListByFileId(fileId);
            fileUrl = fileList.getFileUrl();
            fileListService.deleteFileListByFileId(Long.valueOf(fileId));
        }

        if(fileUrl!=""){
            String host = systemParams.getSftpHost();//"10.9.2.49";
            int port = systemParams.getSftpPort();//22;
            String username = systemParams.getSftpUsername();//"apache";
            String password = systemParams.getSftpPassword();//"Frdr2312dd22";
            int handleType = 2;//1:上传 2：删除文件
            String directory = systemParams.getSftpDir();//"/var/www/html/deploy/test/";
            String folderPath = directory+fileUrl;//.substring(0,fileUrl.lastIndexOf("/")+1);//"excel/"+stationId+"/"+fileType+"";
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/")+1);
            SftpTooL.uploadMultipartFile(host,port,username,password,null,handleType,null,folderPath,fileName);
        }
    }

    /**
     * 获取登陆UserAssets信息
     * @return
     */
    public UserAssets getUserAssets(){
        String authHeader = request.getHeader("authorization");
        //取得token
        String token = authHeader.substring(7);
        try {
            return JwtUtil.checkToken(token);
        } catch (ServletException e) {
            LOGGER.error("获取token信息发生异常/"+e.getMessage());
            return null;
        }
    }

    /**
     * 将fileList补全下载的路径
     * @param fileList
     */
    public void fileListSetUrl(List<FileList> fileList){
        String fileDownLoad = systemParams.getFileDownload();
        fileList.forEach(file->{
            file.setFileUrl(fileDownLoad+file.getFileUrl());
        });
    }
}
