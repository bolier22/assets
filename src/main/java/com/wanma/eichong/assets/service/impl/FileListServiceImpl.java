package com.wanma.eichong.assets.service.impl;

import com.wanma.eichong.assets.config.Constants;
import com.wanma.eichong.assets.entity.FileList;
import com.wanma.eichong.assets.mapper.FileListMapper;
import com.wanma.eichong.assets.service.FileListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auth libg
 * @time 2019-04-29 12:20:20
 * @desc 1.15 下载文件列表 file_list
 */
@Service
public class FileListServiceImpl implements FileListService {

    @Autowired
    FileListMapper fileListMapper;

    /**
     1	立项测算结果（2.1，2.2，2.3，2.4）
     2	实施预算汇总表
     3	实施预算分项明细
     4	总决算汇总表
     5	决算分项明细
     6	运营物料信息
     7	协议文件表
     * @param fileList
     * @return
     */
    @Override
    public Long insertFileList(FileList fileList) {
        int fileType = fileList.getFileType();//1,2,4类型的文件 一个站点只留一个文件
        if(Constants.FILETYPE_CODE1 == fileType||Constants.FILETYPE_CODE2 == fileType||Constants.FILETYPE_CODE4 == fileType){
            // stationId fileType 删除 fileList表中的记录
            fileListMapper.deleteFileListByStationIdAndType(fileList.getAssetsStationId(),fileList.getFileType());
        }
        return fileListMapper.insertFileList(fileList);
    }

    @Override
    public List<FileList> selectFileListListByStationIdAndType(Long stationId, Integer fileType){
        return fileListMapper.selectFileListListByStationIdAndType(stationId, fileType);
    }

    @Override
    public FileList selectFileListByFileId(Long fileId) {
        return fileListMapper.selectFileListByFileId(fileId);
    }

    @Override
    public int deleteFileListByStationIdAndType(Long assetsStationId, Integer fileType) {
        return fileListMapper.deleteFileListByStationIdAndType(assetsStationId,fileType);
    }

    @Override
    public int deleteFileListByFileId(Long fileId) {
        return fileListMapper.deleteFileListByFileId(fileId);
    }

    @Override
    public void deleteFileListByStationId(Long assetsStationId) {
        List<FileList> fileListList = fileListMapper.selectFileListListByStationId(assetsStationId);
        for (FileList file:fileListList){
            fileListMapper.deleteFileListByFileId(file.getFileId());
            fileListMapper.deleteFileListByFileId(file.getFileId());
        }
    }
}
/**

        2.	获取验证码
        请求地址：http://127.0.0.1:8080/assets/ /

        3.	用户登录
        请求地址：http://127.0.0.1:8080/assets/ /

        4.	根据市编号查询站点列表
        请求地址：http://127.0.0.1:8080/assets/chargingStation/queryChargingStationListByCityCode

        5.	充电站资产列表分页查询
        请求地址：http://127.0.0.1:8080/assets/chargingStation/queryChargingStationList

        6.	保存电站和设备清单列表信息
        请求地址：http://127.0.0.1:8080/assets/chargingStation/saveChargingStation

        7.	根据查询条件导出充电站列表
        请求地址：http://127.0.0.1:8080/assets/chargingStation/exportChargingStationList

        8.	根据站点id查询电站和设备清单信息
        请求地址：http://127.0.0.1:8080/assets/chargingStation/queryChargingStationByStationId

        9.	设备列表查询
        请求地址：http://127.0.0.1:8080/assets/baseEqu5t /queryEqu5tList

        10.	根据设备id查询设备型号列表
        请求地址：http://127.0.0.1:8080/assets/ baseEqu5tModel/queryBaseEqu5tModelByEqu5tId

        11.	根据设备清单ids删除信息
        请求地址：http://127.0.0.1:8080/assets/equ5tList /deleteEqu5tListByAssetsEqu5tIdList

        12.	导入（立项测算）
        请求地址：http://127.0.0.1:8080/assets/budget/ importBudgetFile

        13.	删除（立项测算）
        请求地址：http://127.0.0.1:8080/assets/budget/deleteBudgetFileByStationId

        14.	根据站点id查询2.1信息
        请求地址：http://127.0.0.1:8080/assets/budget/queryBudgetBaseByStationId

        15.	根据站点id查询2.2信息
        请求地址：http://127.0.0.1:8080/assets/budget/queryBudget1yearByStationId

        16.	根据站点id查询2.3信息
        请求地址：http://127.0.0.1:8080/assets/budget/queryBudget3yearByStationId

        17.	根据站点id查询2.4信息
        请求地址：http://127.0.0.1:8080/assets/budget/queryBudgetQuotaByStationId

        18.	导入（实施预算汇总）
        请求地址：http://127.0.0.1:8080/assets/imp7tionBudget /importImp7tionFile

        19.	删除（实施预算汇总）
        请求地址：http://127.0.0.1:8080/assets/imp7tionBudget/deleteImp7tionFileByStationId

        20.	根据站点id查询预算汇总信息及条目表
        请求地址：http://127.0.0.1:8080/assets/imp7tionBudget/queryImp7tionBudgetCollect

        21.	导入（决算汇总）
        请求地址：http://127.0.0.1:8080/assets/ fina8sCollect/ importFina8sFileStationId

        22.	删除（决算汇总）
        请求地址：http://127.0.0.1:8080/assets/ fina8sCollect/ deleteFina8sFileByStationId

        23.	根据站点id查询决算汇总表及条目信息
        请求地址：http://127.0.0.1:8080/assets/fina8sCollect/queryFina8sCollectByStationId

        24.	根据站点id分页查询运营数据列表
        请求地址：http://127.0.0.1:8080/assets/ /

        25.	根据运营id删除信息
        请求地址：http://127.0.0.1:8080/assets/operateInfo/deleteOperateByOperateId

        26.	根据运营id查询详情
        请求地址：http://127.0.0.1:8080/assets/operateInfo/queryOperateByOperateId

        27.	导入（运营）
        请求地址：http://127.0.0.1:8080/assets/operateInfo/importOperateFile

        28.	导出（运营）直接下载导入的文件
        请求地址：http://127.0.0.1:8080/assets/ /

        29.	设备表分页查询
        请求地址：http://127.0.0.1:8080/assets/baseEquipment/queryEqu5tList

        30.	保存设备和型号列表
        请求地址：http://127.0.0.1:8080/assets/baseEquipment/saveEqu5tAndModel

        31.	设备表型号列表查询
        请求地址：http://127.0.0.1:8080/assets/baseEquipment/queryEqu5tModelListByEqu5tId

        32.	设备表型号删除
        请求地址：http://127.0.0.1:8080/assets/baseEquipment/deleteEqu5tModelByEqu5tModelId

        33.	设备表型号修改
        请求地址：http://127.0.0.1:8080/assets/baseEquipment/updateEqu5tModelByEqu5tModelId

        34.	协议列表分页查询
        请求地址：http://127.0.0.1:8080/assets/fieldAgreement/queryFieldAgr5tList
        请求方式：post

        35.	根据协议id查询协议信息
        请求地址：http://127.0.0.1:8080/assets/fieldAgreement/queryFieldAgr5tByFieldAgr5tId
        请求方式：post

        36.	保存协议信息
        请求地址：http://127.0.0.1:8080/assets/fieldAgreement/saveFieldAgr5tList
        请求方式：post

        37.	上传文件
        请求地址：http://127.0.0.1:8080/assets/fileList/{stationId}/{fileType}/uploadFile

        38.	查询文件列表
        请求地址：http://127.0.0.1:8080/assets/fileList/queryFileList
        请求方式：post

        39.	删除文件
        请求地址：http://127.0.0.1:8080/assets/fileList/deleteFileListByFileId
        请求方式：post

        40.	下载文件
        请求地址：http://127.0.0.1:8080/assets/ fileList/downFileList

        41.	下载模版（立项测算）url下载
        请求地址：http://127.0.0.1:8080/assets/budget/ downloadBudgetTemplate
        请求方式：post

        42.	下载模版（实施预算汇总）url下载
        请求地址：http://127.0.0.1:8080/assets/imp7tionBudget /downloadImp7tionTemplate
        请求方式：post

        43.	下载模版（决算汇总）url下载
        请求地址：http://127.0.0.1:8080/assets/ /
        请求方式：post

        44.	下载模版（运营）url下载
        请求地址：http://127.0.0.1:8080/assets/ /
        请求方式：post

        45.	省列表
        请求地址：http://127.0.0.1:8080/assets/areaCode/getProvinceList
        请求方式：post

        46.	根据省编号查询市列表
        请求地址：http://127.0.0.1:8080/assets/areaCode/getCityListByAreaSupCode
        请求方式：post
***/

