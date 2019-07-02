package com.wanma.eichong.assets.controller;

import com.alibaba.fastjson.JSONObject;
import com.wanma.eichong.assets.config.ResultEnum;
import com.wanma.eichong.assets.entity.FileList;
import com.wanma.eichong.assets.utils.JsonResult;
import com.wanma.eichong.assets.utils.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Description 文件导入
 * @author libg
 * @date 2019/05/05
 */
@RestController
@RequestMapping("/fileList")
public class FileListController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(FileListController.class);

    /**
     * @auth libg 2019.05.11
     * @desc 上传文件思路：
     *         1.接收file将文件上传至服务器
     *         2.将文件信息保存至fileList表中
     * @param file
     * @param stationId
     * @param fileType
     * @param request
     * @return
     */
    @PostMapping("/{stationId}/{fileType}/uploadFile")
    public JsonResult uploadFile(@RequestParam(value = "file", required = false) MultipartFile file, @PathVariable Long stationId, @PathVariable Integer fileType,HttpServletRequest request) {
        JsonResult result = new JsonResult();
        try {
            if (file.isEmpty()) {
                result.initError(ResultEnum.IMPORT_FILE_NOTNULL);
                return result;
            }
            commonUploadFile(file, stationId, fileType);

        }catch (Exception e){
            logger.error(e.getMessage());
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return result;
    }



    /**
     * @auth libg 2019.05.11 19:39
     * @desc 查询思路： 1.根据传入的站点id和文件类型查询fileList 文件列表
     * @param assetsStationId 站点主键
     * @param fileType 文件类型
     * @return
     */
    @RequestMapping("/queryFileList")
    public JsonResult queryFileList(Long assetsStationId,Integer fileType) {
        JsonResult result = new JsonResult();
        Map map = new HashMap();
        try {
            List<FileList> fileList = fileListService.selectFileListListByStationIdAndType(assetsStationId,fileType);
            fileListSetUrl(fileList);
            map.put("fileList",fileList);
            result.setDataObj(map);
        }catch (Exception e){
            logger.error(e.getMessage());
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return result;

    }


    /**
     * @auth libg 2019.05.11 18:23
     * @desc
     *  删除思路：
      *  1.根据fileId查询出文件信息（使用文件地址）
      *  2.根据fileId删除fileList中的文件记录
      *  3.根据fileList中记录存放的文件地址删除文件
     * @auth libg 2019.05.08
     * @desc 根据fileId主键删除对应的记录 以及对应的文件
     * @param jsonObject fileId
     * @return
     */
    @PostMapping("/deleteFileListByFileId")
    public JsonResult putExcleImp7tion(@RequestBody JSONObject jsonObject) {
        JsonResult result = new JsonResult();
        try {
            String fileId = JsonUtils.getStrValue(jsonObject, "fileId");
            if(StringUtils.isEmpty(fileId)){
                result.initError(ResultEnum.PARAM_ERROR);
            }
            commonDeleteFile(Long.valueOf(fileId),null, -1);
        }catch (Exception e){
            logger.error(e.getMessage());
            result.initError(ResultEnum.UNKOWN_EXCEPTION);
        }
        return result;
    }
}
