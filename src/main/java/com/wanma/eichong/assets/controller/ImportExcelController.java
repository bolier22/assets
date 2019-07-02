package com.wanma.eichong.assets.controller;

import com.wanma.eichong.assets.config.Constants;
import com.wanma.eichong.assets.config.ResultEnum;
import com.wanma.eichong.assets.service.BudgetBaseService;
import com.wanma.eichong.assets.service.FileListService;
import com.wanma.eichong.assets.service.FinalAccountsCollectService;
import com.wanma.eichong.assets.service.Imp7tionBudgetCollectBaseService;
import com.wanma.eichong.assets.utils.JsonResult;
import com.wanma.eichong.assets.utils.ReadExcel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @Description 文件导入
 * @author libg
 * @date 2019/05/05
 */
@RestController
@RequestMapping("/excel")
public class ImportExcelController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(ImportExcelController.class);
    private final BudgetBaseService budgetBaseService;
    private final Imp7tionBudgetCollectBaseService imp7tionBudgetCollectBaseService;
    private final FinalAccountsCollectService finalAccountsCollectService;
    private final FileListService fileListService;
    @Autowired
    public ImportExcelController(BudgetBaseService budgetBaseService, Imp7tionBudgetCollectBaseService imp7tionBudgetCollectBaseService, FinalAccountsCollectService finalAccountsCollectService, FileListService fileListService) {
        this.budgetBaseService = budgetBaseService;
        this.imp7tionBudgetCollectBaseService = imp7tionBudgetCollectBaseService;
        this.finalAccountsCollectService = finalAccountsCollectService;
        this.fileListService = fileListService;
    }

    //1	立项测算结果（2.1，2.2，2.3，2.4）
    //2	实施预算汇总表
    //3	实施预算分项明细
    //4	总决算汇总表
    //5	决算分项明细
    //6	运营物料信息
    //7	协议文件表

    /**
     * @auth libg 2019.05.09 11:22
     * @desc  文件导入思路（1，2，4）：
     *          1.接收file文件，电站id，文件类型
     *          2.根据类型分别解析相应的文件（1，2，4）并将解析出的结果以list返回
     *          3.根据文件类型插入到对应的数据库表中
     *          4.插入表之前首先删除条件为电站id和文件类型相同的数据（1，2，4）这几种文件只保留一份数据以及fileList中的记录
     *          5.插入成功后将文件上传至服务器进行保存文件
     *          6.将文件生成相应的记录插入到文件列表中（1，2，4确保唯一性插入之前根据站点id和类型先删除记录）
     * @param file
     * @param stationId
     * @param fileType
     * @param request
     * @return
     */
    @PostMapping("/import/{stationId}/{fileType}/budget")
    public JsonResult importBudget(@RequestParam(value = "file", required = false) MultipartFile file, @PathVariable Long stationId, @PathVariable Integer fileType,HttpServletRequest request) {
        JsonResult result = new JsonResult();
        try {
            Long userId = getUserAssets().getUserId();
            List<Object> obj = new ReadExcel().getBudgetExcelInfo(file,stationId,fileType,userId);
            int resultCode = 0;
            if(Constants.FILETYPE_CODE1 == fileType){
                resultCode = budgetBaseService.insertBatchBudgetList(obj,fileType);
            }else if(Constants.FILETYPE_CODE2 == fileType){
                resultCode = imp7tionBudgetCollectBaseService.insertImp7tionBudgetCollect(obj,fileType);
            }else if(Constants.FILETYPE_CODE4 == fileType){
                resultCode = finalAccountsCollectService.insertFinalAccountsCollect(obj,fileType);
            }
            if(resultCode!=0){
//                //stationId 充电站基表id,fileName 文件名,filePath+fileName 下载路径,
//                // fileType 文件类型（1-实施预算分项明细表，2-决算分项明细表，3-协议文件表，4-实施预算-分项明细文件，5-决算分项明细文件）,userId 录入人
                commonUploadFile(file, stationId, fileType);
            }else{
                result.initError(ResultEnum.IMPORT_FAILED);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            result.initError(ResultEnum.IMPORT_FAILED);
        }
        return result;
    }


    /**
     * @auth libg 2019.05.10 15:34
     * @desc 文件删除思路（1，2，4）：
     *        1.根据站点id和文件类型删除对应的表数据
     *        2.根据站点id和文件类型删除对应的文件夹
     *        3.根据站点id和文件类型删除对应的fileList表记录
     * @param stationId
     * @param fileType
     * @return
     */
    @PostMapping("/delete/{stationId}/{fileType}/budget")
    public JsonResult deleteBudget(@PathVariable Long stationId, @PathVariable Integer fileType) {
        JsonResult result = new JsonResult();
        try {
            commonDeleteFile(null,stationId, fileType);

            int resultCode = 0;
            if(Constants.FILETYPE_CODE1 == fileType){
                resultCode = budgetBaseService.deleteBudgetByStationId(stationId,fileType);
            }else if(Constants.FILETYPE_CODE2 == fileType){
                resultCode = imp7tionBudgetCollectBaseService.deleteImp7tionBudgetCollectByStationId(stationId,fileType);
            }else if(Constants.FILETYPE_CODE4 == fileType){
                resultCode = finalAccountsCollectService.deleteFinalAccountsCollectByStationId(stationId,fileType);
            }

            if(resultCode==0){
                result.initError(ResultEnum.DELETE_FAILED);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            result.initError(ResultEnum.DELETE_FAILED);
        }
        return result;
    }
}
