package com.wanma.eichong.assets.utils;

import com.google.gson.Gson;
import com.wanma.eichong.assets.config.Constants;
import com.wanma.eichong.assets.entity.*;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import static java.util.regex.Pattern.*;

/**
 * @Description  execl读取工具类
 * @author gxj
 * @date 2018/08/16
 */
public class ReadExcel {
    private static final Logger logger = LoggerFactory.getLogger(ReadExcel.class);

    //总行数  
    private int totalRows = 0;
    //总条数  
    private int totalCells = 0;
    //错误信息接收器  
    private String errorMsg;

    //构造方法  
    public ReadExcel() {
    }

    //获取总行数  
    public int getTotalRows() {
        return totalRows;
    }

    //获取总列数  
    public int getTotalCells() {
        return totalCells;
    }

    //获取错误信息  
    public String getErrorInfo() {
        return errorMsg;
    }

    /**
     * 读EXCEL文件，获取信息集合
     *
     * @param mFile
     * @return
     */
        public List<Object> getBudgetExcelInfo(MultipartFile mFile,Long stationId,int fileType,Long userId) {
        List<Object> userList = null;
            //获取文件名
        String fileName = mFile.getOriginalFilename();
        try {
            // 根据文件名判断文件是2003版本还是2007版本
            boolean isExcel2003 = true;
            if (isExcel2007(fileName)) {
                isExcel2003 = false;
            }
            logger.info("/getExcelInfo/" + isExcel2003);
            userList = new ReadExcel().createBudgetExcel(mFile.getInputStream(), isExcel2003,stationId,fileType,userId);

        } catch (Exception e) {
            logger.error("/getExcelInfo/" + e.getMessage());
        }
        return userList;
    }

    /**
     * 根据excel里面的内容读取客户信息
     *
     * @param is          输入流
     * @param isExcel2003 excel是2003还是2007版本
     * @return
     * @throws IOException
     */
    public List<Object> createBudgetExcel(InputStream is, boolean isExcel2003,Long stationId,int fileType,Long userId) {
        List<Object> userList = null;
        try {
            Workbook wb = null;
            // 当excel是2003时,创建excel2003
            if (isExcel2003) {
                wb = new HSSFWorkbook(is);
            } else {
                // 当excel是2007时,创建excel2007
                wb = new XSSFWorkbook(is);
            }
            // 读取Excel里面客户的信息
            userList = readBudgetExcelValue(wb,stationId,fileType,userId);
        } catch (IOException e) {
            logger.error("/createExcel/" + e.getMessage());
        }
        return userList;
    }

    /**
     * 读取Excel里面客户的信息
     *
     * @param wb
     * @return
     * 1    立项测算结果（2.1，2.2，2.3，2.4）
     * 2	实施预算汇总表
     * 3	实施预算分项明细
     * 4	总决算汇总表
     * 5	决算分项明细
     * 6	运营物料信息
     * 7	协议文件表
     */
    private List<Object> readBudgetExcelValue(Workbook wb,Long stationId,int fileType,Long userId) {
        List<Object> listObj = new ArrayList<>();
        try {

            if(fileType == Constants.FILETYPE_CODE1){//1    立项测算结果（2.1，2.2，2.3，2.4） 立项测算表-2.1
                // 循环工作表Sheet  0:BudgetBase 1:BudgetFirstYearsCost 2:BudgetFirst3yearsPeriodQuota 3:BudgetCompletePeriodQuota
                listObj.add(0,analysisBudgetBase(wb,0,stationId,fileType,userId));
                listObj.add(1,analysisBudgetFirstYearsCost(wb,1,stationId,fileType,userId));
                listObj.add(2,analysisBudgetFirst3yearsPeriodQuota(wb,2,stationId,fileType,userId));
                listObj.add(3,analysisBudgetCompletePeriodQuota(wb,3,stationId,fileType,userId));
            }else if(fileType == Constants.FILETYPE_CODE2){//2	实施预算汇总表
                listObj.addAll(analysisImp7tion(wb,0,stationId,fileType,userId));//项目实施预算表-下载模版（含导入解析）
            }else if(fileType == Constants.FILETYPE_CODE4){//4	总决算汇总表
                listObj.addAll(analysisFinal8sCollect(wb,0,stationId,fileType,userId));//决算汇总表
            }
        } catch (Exception e) {
            logger.error("/readExcelValue/" + e.getMessage());
        }
        return listObj;
    }

    class Result{
        public boolean merged;
        public String cellValue;
        public Result(boolean merged,String cellValue){
            this.merged = merged;
            this.cellValue = cellValue;
        }
    }


    /**
     *决算汇总表-下载模版（含导入解析） FinalAccountsCollect FinalAccountsCollectList
     * @param wb
     * @param numSheet
     */
    public List<Object> analysisFinal8sCollect(Workbook wb,int numSheet,Long stationId,int fileType,Long userId){
        List<Object> final8sBudget = new ArrayList<Object>();
        List<FinalAccountsCollectList> final8sCollectListList = new ArrayList<FinalAccountsCollectList>();
        try{
            //  解析每一个shell
            Sheet sheet = wb.getSheetAt(numSheet);
            // 得到Excel的行数
            //this.totalRows = sheet.getPhysicalNumberOfRows();//获取的是最后一行的编号（编号从0开始）。
            //获取的是物理行数，也就是不包括那些空行（隔行）的情况。
            this.totalRows = sheet.getLastRowNum();

            FinalAccountsCollect final8sCollectBase = new FinalAccountsCollect();
            // 循环Excel行数
            for (int rowNum = 0; rowNum < totalRows; rowNum++) {
                Row row = sheet.getRow(rowNum);
                if(rowNum == 0){//表头名称
                    Cell cell = row.getCell(0);
                    String cellVal = trim(getValString(cell,fileType));
                    final8sCollectBase.setBudgetCollectTitle(cellVal);
                }else if(rowNum == 1){
                    //项目名称
                    Cell cell = row.getCell(2);
                    String cellVal = trim(getValString(cell,fileType));
                    final8sCollectBase.setProjectName(cellVal);

                    //项目阶段
                    cell = row.getCell(5);
                    cellVal = trim(getValString(cell,fileType));
                    final8sCollectBase.setProjectStage(cellVal);
                }else if(rowNum == 2){
                    //建设单位（城市公司）
                    Cell cell = row.getCell(2);
                    String cellVal = trim(getValString(cell,fileType));
                    final8sCollectBase.setProjectUnit(cellVal);

                    //立项预算总投资（元）
                    cell = row.getCell(5);
                    cellVal = trim(getValString(cell,fileType));
                    cellVal = replaceDotZearo(cellVal);
                    final8sCollectBase.setBudgetMoney(cellVal);
                }else if(rowNum == 3){
                    //项目概况
                    Cell cell = row.getCell(2);
                    String cellVal = trim(getValString(cell,fileType));
                    final8sCollectBase.setProjectDesc(cellVal);
                }else if(rowNum == 4){
                    //充电桩规格数量
                    Cell cell = row.getCell(2);
                    String cellVal = trim(getValString(cell,fileType));
                    cellVal = replaceDotZearo(cellVal);
                    final8sCollectBase.setChargingPilesAmount(cellVal);

                    //总功率（kw)
                    cell = row.getCell(5);
                    cellVal = trim(getValString(cell,fileType));
                    cellVal = replaceDotZearo(cellVal);
                    final8sCollectBase.setTotalPower(cellVal);
                }else if(rowNum == 5){
                    //变压器规格数量
                    Cell cell = row.getCell(2);
                    String cellVal = trim(getValString(cell,fileType));
                    cellVal = replaceDotZearo(cellVal);
                    final8sCollectBase.setTra6erAmount(cellVal);

                    //总容量（kva)
                    cell = row.getCell(5);
                    cellVal = trim(getValString(cell,fileType));
                    cellVal = replaceDotZearo(cellVal);
                    final8sCollectBase.setTotalCapacity(cellVal);
                }else if(rowNum>6){
                    // 得到Excel的列数(前提是有行数)
                    if (totalRows > 1 && sheet.getRow(rowNum) != null) {
                        this.totalCells = sheet.getRow(rowNum).getPhysicalNumberOfCells();
                    }
                    //Row row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }

                    FinalAccountsCollectList final8sCollectList = new FinalAccountsCollectList();

                    // 循环Excel的列
                    for (int cellNum = 0; cellNum < this.totalCells; cellNum++) {
                        Cell cell = row.getCell(cellNum);
                        Result result = getCellValue(sheet,rowNum,cellNum);

                        if (null != cell && !cell.equals("")) {
                            String cellVal = trim(getValString(cell,fileType));
                            if(result.merged){
                                cellVal = result.cellValue;
                            }

                            if(cellVal.contains("负责人")){
                                break;
                            }

                            if (cellNum == 0) {
                                cellVal = replaceDotZearo(cellVal);
                                final8sCollectList.setListSeq(cellVal);//序列
                            } else if (cellNum == 1) {
                                final8sCollectList.setProjectName(cellVal);//项目名称
                            }else if (cellNum == 2) {
                                cellVal = str2Scale2(cellVal);
                                final8sCollectList.setBudgetMoney(cellVal);//立项预算总投资（元）
                            }else if (cellNum == 3) {
                                cellVal = str2Scale2(cellVal);
                                final8sCollectList.setFinal8sCollectMoney(cellVal);//决算总投资（元）
                            }else if (cellNum == 4) {
                                cellVal = str2Scale2(cellVal);
                                final8sCollectList.setCoreMoney(cellVal);//净核减+（增-)造价（元）
                            }else if (cellNum == 5) {
                                cellVal = str2Scale2OfPre(cellVal);
                                final8sCollectList.setCorePer5ge(cellVal);//净核减+（增-）百分率
                            }else if (cellNum == 6) {
                                final8sCollectList.setCons5ionUnit(cellVal);//施工单位
                            }
                        }
                    }

                    Cell cell2 = row.getCell(0);
                    String cellVal2 = trim(getValString(cell2,fileType));

                    if(cellVal2.contains("负责人")){
                        //城市公司项目负责人
                        Cell cell = row.getCell(2);
                        String cellVal = trim(getValString(cell,fileType));
                        final8sCollectBase.setProjectCityLeader(cellVal);

                        //工程总部负责人
                        cell = row.getCell(5);
                        cellVal = trim(getValString(cell,fileType));
                        final8sCollectBase.setProjectHeadLeader(cellVal);

                        Result result = getCellValue(sheet,rowNum+1,0);
                        final8sCollectBase.setRemark1(result.cellValue);//注1

                        result = getCellValue(sheet,rowNum+2,0);
                        final8sCollectBase.setRemark2(result.cellValue);//注2

                        result = getCellValue(sheet,rowNum+3,0);
                        final8sCollectBase.setRemark3(result.cellValue);//注3

                        result = getCellValue(sheet,rowNum+4,0);
                        final8sCollectBase.setRemark4(result.cellValue);//注4
                        break;
                    }
                    final8sCollectList.setCreateId(userId);
                    final8sCollectListList.add(final8sCollectList);
                }
            }
            final8sCollectBase.setCreateId(userId);
            final8sCollectBase.setAssetsStationId(stationId);
            final8sBudget.add(0,1);
            final8sBudget.add(1,final8sCollectBase);
            final8sBudget.add(2,final8sCollectListList);
        }catch (Exception e){
            final8sBudget.add(0,e.getMessage());
            logger.error(this.getClass() + ".analysisFinal8sCollect is error=STRAT#{}#END",e.getMessage());
        }
        return final8sBudget;
    }


    /**
     *项目实施预算表-下载模版（含导入解析）
     * @param wb
     * @param numSheet
     */
    public List<Object> analysisImp7tion(Workbook wb,int numSheet,Long stationId,int fileType,Long userId){
        List<Object> imp7tionBudget = new ArrayList<Object>();
        List<Imp7tionBudgetCollectList> imp7tionBudgetCollectListList = new ArrayList<Imp7tionBudgetCollectList>();
        try{
            //  解析每一个shell
            Sheet sheet = wb.getSheetAt(numSheet);
            // 得到Excel的行数
            //this.totalRows = sheet.getPhysicalNumberOfRows();//获取的是最后一行的编号（编号从0开始）。
            //获取的是物理行数，也就是不包括那些空行（隔行）的情况。
            this.totalRows = sheet.getLastRowNum();

            Imp7tionBudgetCollectBase imp7tionBudgetCollectBase = new Imp7tionBudgetCollectBase();
            // 循环Excel行数
            for (int rowNum = 0; rowNum < totalRows; rowNum++) {
                Row row = sheet.getRow(rowNum);
                //表头名称
                if(rowNum == 0){
                    Cell cell = row.getCell(0);
                    String cellVal = trim(getValString(cell,fileType));
                    imp7tionBudgetCollectBase.setBudgetCollectTitle(cellVal);
                }else if(rowNum == 1){
                    Cell cell = row.getCell(2);
                    String cellVal = trim(getValString(cell,fileType));
                    //工程名称
                    imp7tionBudgetCollectBase.setBudgetProjectName(cellVal);
                }else if(rowNum>2){
                    // 得到Excel的列数(前提是有行数)
                    if (totalRows > 1 && sheet.getRow(rowNum) != null) {
                        this.totalCells = sheet.getRow(rowNum).getPhysicalNumberOfCells();
                    }
                    if (row == null) {
                        continue;
                    }

                    Imp7tionBudgetCollectList imp7tionBudgetCollectList = new Imp7tionBudgetCollectList();

                    // 循环Excel的列
                    for (int cellNum = 0; cellNum < this.totalCells; cellNum++) {
                        Cell cell = row.getCell(cellNum);
                        Result result = getCellValue(sheet,rowNum,cellNum);

                        if (null != cell && !cell.equals("")) {
                            String cellVal = trim(getValString(cell,fileType));
                            if(result.merged){
                                cellVal = result.cellValue;
                            }

                            if(cellVal.contains("小计")||cellVal.contains("总计")){
                               break;
                            }

                            //类型（1:条目，2:合计，3：总计）
                            imp7tionBudgetCollectList.setBudgetListType(1);
                            if (cellNum == 0) {
                                //序列（分类）
                                cellVal = replaceDotZearo(cellVal);
                                imp7tionBudgetCollectList.setBudgetListIndex(cellVal);
                            } else if (cellNum == 1) {
                                //项目
                                imp7tionBudgetCollectList.setBudgetProjectName(cellVal);
                            }else if (cellNum == 2) {
                                //名称及说明
                                imp7tionBudgetCollectList.setBudgetListName(cellVal);
                            }else if (cellNum == 3) {
                                //单位
                                imp7tionBudgetCollectList.setBudgetListUnit(cellVal);
                            }else if (cellNum == 4) {
                                cellVal = str2Scale2(cellVal);
                                //工程数量
                                imp7tionBudgetCollectList.setBudgetListAmount(cellVal);
                            }else if (cellNum == 5) {
                                cellVal = str2Scale2(cellVal);
                                //工料单价（元）2018
                                imp7tionBudgetCollectList.setBudgetListMoney(cellVal);
                            }else if (cellNum == 6) {
                                cellVal = str2Scale2(cellVal);
                                //合价（元）
                                imp7tionBudgetCollectList.setBudgetTotalMoney(cellVal);
                            }else if (cellNum == 7) {
                                //备注
                                imp7tionBudgetCollectList.setRemark(cellVal);
                            }
                        }
                    }

                    Cell cell2 = row.getCell(0);
                    String cellVal2 = trim(getValString(cell2,fileType));

                    if(cellVal2.contains("小计")){
                        Result result = getCellValue(sheet,rowNum-1,0);
                        String cellValPre = result.cellValue;
                        imp7tionBudgetCollectList.setBudgetListIndex(cellValPre);

                        imp7tionBudgetCollectList.setBudgetListType(2);

                        Cell cell3 = row.getCell(6);
                        String cellVal3 = trim(getValString(cell3,fileType));
                        cellVal3 = str2Scale2(cellVal3);
                        imp7tionBudgetCollectList.setBudgetTotalMoney(cellVal3);

                        cell3 = row.getCell(7);
                        cellVal3 = trim(getValString(cell3,fileType));
                        imp7tionBudgetCollectList.setRemark(cellVal3);

                    }else if(cellVal2.contains("总计")){

                        Result result = getCellValue(sheet,rowNum-1,0);
                        String cellValPre = result.cellValue;
                        int num = NumberUtils.numberCN2Arab(cellValPre)+1;
                        cellValPre = NumberUtils.numberKArab2CN(num);
                        imp7tionBudgetCollectList.setBudgetListIndex(cellValPre);

                        imp7tionBudgetCollectList.setBudgetListType(3);
                        Cell cell3 = row.getCell(6);
                        String cellVal3 = trim(getValString(cell3,fileType));
                        cellVal3 = str2Scale2(cellVal3);
                        imp7tionBudgetCollectList.setBudgetTotalMoney(cellVal3);

                        cell3 = row.getCell(7);
                        cellVal3 = trim(getValString(cell3,fileType));
                        imp7tionBudgetCollectList.setRemark(cellVal3);
                    }
                    imp7tionBudgetCollectList.setCreateId(userId);
                    imp7tionBudgetCollectListList.add(imp7tionBudgetCollectList);
                }
            }
            imp7tionBudgetCollectBase.setCreateId(userId);
            imp7tionBudgetCollectBase.setAssetsStationId(stationId);
            imp7tionBudget.add(0,1);
            imp7tionBudget.add(1,imp7tionBudgetCollectBase);
            imp7tionBudget.add(2,imp7tionBudgetCollectListList);
        }catch (Exception e){
            imp7tionBudget.add(0,0);
            logger.error(this.getClass() + ".analysisImp7tion is error=STRAT#{}#END",e.getMessage());
        }
        return imp7tionBudget;
    }

    /**
     *立项测算表-2.1
     * @param wb
     * @param numSheet
     */
    public List<Object> analysisBudgetBase(Workbook wb,int numSheet,Long stationId,int fileType,Long userId){
        List<Object> budgetList = new ArrayList<Object>();
        try{
            //  解析每一个shell
            Sheet sheet = wb.getSheetAt(numSheet);
            // 得到Excel的行数
            //this.totalRows = sheet.getPhysicalNumberOfRows();//获取的是最后一行的编号（编号从0开始）。
            //获取的是物理行数，也就是不包括那些空行（隔行）的情况。
            this.totalRows = sheet.getLastRowNum();

            List<Object> budgetBaseList = new ArrayList<Object>();
            // 循环Excel行数
            for (int rowNum = 1; rowNum <= totalRows; rowNum++) {

                // 得到Excel的列数(前提是有行数)
                if (totalRows > 1 && sheet.getRow(rowNum) != null) {
                    this.totalCells = sheet.getRow(rowNum).getPhysicalNumberOfCells();
                }
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }

                BudgetBase budgetBase = new BudgetBase();
                // 循环Excel的列
                for (int cellNum = 0; cellNum < this.totalCells; cellNum++) {
                    Cell cell = row.getCell(cellNum);
                    Result result = getCellValue(sheet,rowNum,cellNum);

                    if (null != cell && !cell.equals("")) {
                        String cellVal = trim(getValString(cell,fileType));
                        if(result.merged){
                            cellVal = result.cellValue;
                        }

                        if (cellNum == 0) {//序列（排序合并）
                            cellVal = replaceDotZearo(cellVal);
                            budgetBase.setBudgetIndex(cellVal);
                        } else if (cellNum == 1) {//项目基表信息表
                            budgetBase.setProjectBase(cellVal);
                        }else if (cellNum == 2) {//基础信息
                            budgetBase.setBaseInfo(cellVal);
                        }else if (cellNum == 3) {//第1年数值
                            cellVal = str2Scale2(cellVal);
                            budgetBase.setBudget1yearNumber(cellVal);
                        }else if (cellNum == 4) {//第2年数值
                            cellVal = str2Scale2(cellVal);
                            budgetBase.setBudget2yearNumber(cellVal);
                        }else if (cellNum == 5) {//第3年数值
                            cellVal = str2Scale2(cellVal);
                            budgetBase.setBudget3yearNumber(cellVal);
                        }else if (cellNum == 6) {//第4年数值
                            cellVal = str2Scale2(cellVal);
                            budgetBase.setBudget4yearNumber(cellVal);
                        }else if (cellNum == 7) {//第5年数值
                            cellVal = str2Scale2(cellVal);
                            budgetBase.setBudget5yearNumber(cellVal);
                        }else if (cellNum == 8) {//备注
                            budgetBase.setRemark(cellVal);
                        }
                    }
                }
                budgetBase.setCreateId(userId);
                budgetBase.setAssetsStationId(stationId);
                budgetBaseList.add(budgetBase);
            }
            budgetList.add(0,1);
            budgetList.add(1,budgetBaseList);
        }catch (Exception e){
            budgetList.add(0,0);
            logger.error(this.getClass() + ".analysisBudgetBase is error=STRAT#{}#END",e.getMessage());
        }
        return budgetList;
    }

    /**
     *立项测算表-2.2
     * @param wb
     * @param numSheet
     */
    public List<Object> analysisBudgetFirstYearsCost(Workbook wb,int numSheet,Long stationId,int fileType,Long userId){
        List<Object> budgetList = new ArrayList<Object>();
        try{
            //  解析每一个shell
            Sheet sheet = wb.getSheetAt(numSheet);
            // 得到Excel的行数
            //this.totalRows = sheet.getPhysicalNumberOfRows();//获取的是最后一行的编号（编号从0开始）。
            //获取的是物理行数，也就是不包括那些空行（隔行）的情况。
            this.totalRows = sheet.getLastRowNum();
            List<Object> budgetFirstYearsCostList = new ArrayList<Object>();
            // 循环Excel行数
            for (int rowNum = 1; rowNum <= totalRows; rowNum++) {

                // 得到Excel的列数(前提是有行数)
                if (totalRows > 1 && sheet.getRow(rowNum) != null) {
                    this.totalCells = sheet.getRow(rowNum).getPhysicalNumberOfCells();
                }
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }

                BudgetFirstYearsCost budgetFirstYearsCost = new BudgetFirstYearsCost();
                // 循环Excel的列
                for (int cellNum = 0; cellNum < this.totalCells; cellNum++) {
                    Cell cell = row.getCell(cellNum);
                    Result result = getCellValue(sheet,rowNum,cellNum);

                    if (null != cell && !cell.equals("")) {
                        String cellVal = trim(getValString(cell,fileType));
                        if(result.merged){
                            cellVal = result.cellValue;
                        }

                        if (cellNum == 0) {
                            cellVal = replaceDotZearo(cellVal);
                            //序列（排序合并）
                            budgetFirstYearsCost.setBudgetIndex(cellVal);
                        } else if (cellNum == 1) {
                            //类目
                            budgetFirstYearsCost.setCostCategory(cellVal);
                        }else if (cellNum == 2) {
                            //金额（元/年）
                            cellVal = str2Scale2(cellVal);
                            budgetFirstYearsCost.setCostMoney(cellVal);
                        }else if (cellNum == 3) {
                            //计算原则
                            budgetFirstYearsCost.setCal4tionRule(cellVal);
                        }else if (cellNum == 4) {
                            //备注
                            budgetFirstYearsCost.setRemark(cellVal);
                        }
                    }
                }
                budgetFirstYearsCost.setCreateId(userId);
                budgetFirstYearsCost.setAssetsStationId(stationId);
                budgetFirstYearsCostList.add(budgetFirstYearsCost);
            }

            budgetList.add(0,1);
            budgetList.add(1,budgetFirstYearsCostList);
        }catch (Exception e){
            budgetList.add(0,0);
            logger.error(this.getClass() + ".analysisBudgetFirstYearsCost is error=STRAT#{}#END",e.getMessage());
        }
        return budgetList;
    }

    /**
     *立项测算表-2.3
     * @param wb
     * @param numSheet
     */
    public List<Object> analysisBudgetFirst3yearsPeriodQuota(Workbook wb,int numSheet,Long stationId,int fileType,Long userId){
        List<Object> budgetList = new ArrayList<Object>();
        try{
            //  解析每一个shell
            Sheet sheet = wb.getSheetAt(numSheet);
            // 得到Excel的行数
            //this.totalRows = sheet.getPhysicalNumberOfRows();//获取的是最后一行的编号（编号从0开始）。
            //获取的是物理行数，也就是不包括那些空行（隔行）的情况。
            this.totalRows = sheet.getLastRowNum();

            List<Object> budgetFirst3yearsPeriodQuotaList = new ArrayList<Object>();
            // 循环Excel行数
            for (int rowNum = 1; rowNum <= totalRows; rowNum++) {

                // 得到Excel的列数(前提是有行数)
                if (totalRows > 1 && sheet.getRow(rowNum) != null) {
                    this.totalCells = sheet.getRow(rowNum).getPhysicalNumberOfCells();
                }
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }

                BudgetFirst3yearsPeriodQuota budgetFirst3yearsPeriodQuota = new BudgetFirst3yearsPeriodQuota();
                // 循环Excel的列
                for (int cellNum = 0; cellNum < this.totalCells; cellNum++) {
                    Cell cell = row.getCell(cellNum);
                    Result result = getCellValue(sheet,rowNum,cellNum);

                    if (null != cell && !cell.equals("")) {
                        String cellVal = trim(getValString(cell,fileType));
                        if(result.merged){
                            cellVal = result.cellValue;
                        }
                        if (cellNum == 0) {
                            //序列（排序合并）
                            cellVal = replaceDotZearo(cellVal);
                            budgetFirst3yearsPeriodQuota.setBudgetIndex(cellVal);
                        } else if (cellNum == 1) {
                            //第三年项目运营指标
                            budgetFirst3yearsPeriodQuota.setBudget3yearQuota(cellVal);
                        }else if (cellNum == 2) {
                            //第1年数值
                            cellVal = str2Scale2(cellVal);
                            budgetFirst3yearsPeriodQuota.setBudget1yearNumber(cellVal);
                        }else if (cellNum == 3) {
                            //第2年数值
                            cellVal = str2Scale2(cellVal);
                            budgetFirst3yearsPeriodQuota.setBudget2yearNumber(cellVal);
                        }else if (cellNum == 4) {
                            //第3年数值
                            cellVal = str2Scale2(cellVal);
                            budgetFirst3yearsPeriodQuota.setBudget3yearNumber(cellVal);
                        }else if (cellNum == 5) {
                            //备注
                            budgetFirst3yearsPeriodQuota.setRemark(cellVal);
                        }
                    }
                }
                budgetFirst3yearsPeriodQuota.setCreateId(userId);
                budgetFirst3yearsPeriodQuota.setAssetsStationId(stationId);
                budgetFirst3yearsPeriodQuotaList.add(budgetFirst3yearsPeriodQuota);
            }

            budgetList.add(0,1);
            budgetList.add(1,budgetFirst3yearsPeriodQuotaList);
        }catch (Exception e){
            budgetList.add(0,0);
            logger.error(this.getClass() + ".analysisBudgetFirst3yearsPeriodQuota is error=STRAT#{}#END",e.getMessage());
        }
        return budgetList;
    }

    /**
     *立项测算表-2.4
     * @param wb
     * @param numSheet
     */
    public List<Object> analysisBudgetCompletePeriodQuota(Workbook wb,int numSheet,Long stationId,int fileType,Long userId){
        List<Object> budgetList = new ArrayList<Object>();
        try{
            //  解析每一个shell
            Sheet sheet = wb.getSheetAt(numSheet);
            // 得到Excel的行数
            //this.totalRows = sheet.getPhysicalNumberOfRows();//获取的是最后一行的编号（编号从0开始）。
            //获取的是物理行数，也就是不包括那些空行（隔行）的情况。
            this.totalRows = sheet.getLastRowNum();
            List<Object> budgetCompletePeriodQuotaList = new ArrayList<Object>();
            // 循环Excel行数
            for (int rowNum = 1; rowNum <= totalRows; rowNum++) {

                // 得到Excel的列数(前提是有行数)
                if (totalRows > 1 && sheet.getRow(rowNum) != null) {
                    this.totalCells = sheet.getRow(rowNum).getPhysicalNumberOfCells();
                }
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }

                BudgetCompletePeriodQuota budgetCompletePeriodQuota = new BudgetCompletePeriodQuota();
                // 循环Excel的列
                for (int cellNum = 0; cellNum < this.totalCells; cellNum++) {
                    Cell cell = row.getCell(cellNum);
                    Result result = getCellValue(sheet,rowNum,cellNum);

                    if (null != cell && !cell.equals("")) {
                        String cellVal = trim(getValString(cell,fileType));
                        if(result.merged){
                            cellVal = result.cellValue;
                        }
                        if (cellNum == 0) {
                            //序列（排序合并）
                            cellVal = replaceDotZearo(cellVal);
                            budgetCompletePeriodQuota.setBudgetIndex(cellVal);
                        } else if (cellNum == 1) {
                            //全周期项目运营指标
                            budgetCompletePeriodQuota.setBudgetQuota(cellVal);
                        }else if (cellNum == 2) {
                            //数值
                            cellVal = str2Scale2(cellVal);
                            budgetCompletePeriodQuota.setBudgetNumber(cellVal);
                        }else if (cellNum == 3) {
                            //备注
                            budgetCompletePeriodQuota.setRemark(cellVal);
                        }
                    }
                }
                budgetCompletePeriodQuota.setCreateId(userId);
                budgetCompletePeriodQuota.setAssetsStationId(stationId);
                budgetCompletePeriodQuotaList.add(budgetCompletePeriodQuota);
            }

            budgetList.add(0,1);
            budgetList.add(1,budgetCompletePeriodQuotaList);
        }catch (Exception e){
            budgetList.add(0,0);
            logger.error(this.getClass() + ".analysisBudgetCompletePeriodQuota is error=STRAT#{}#END",e.getMessage());
        }
        return budgetList;
    }


    /**
     * 指定工作表、行、列下的内容
     * @return String
     */
    public Result getCellValue(Sheet sheet,int rowNum, int colNum) {
        String strExcelCell = "";
        boolean merged = false;
        try {
            //判断是否是合并单元格，如果是，就将行、列索引改为合并单元格的索引
            for(int numMR = 0; numMR < sheet.getNumMergedRegions(); numMR++){
                //获取合并单元格
                CellRangeAddress cellRangeAddress = sheet.getMergedRegion(numMR);
                int firstColumnInd = cellRangeAddress.getFirstColumn();
                int lastColumnInd = cellRangeAddress.getLastColumn();
                int firstRowInd = cellRangeAddress.getFirstRow();
                int lastRowInd = cellRangeAddress.getLastRow();
                //如果当前单元格在这个合并单元格里
                if(rowNum >= firstRowInd && rowNum <= lastRowInd && colNum >= firstColumnInd && colNum <= lastColumnInd){
                    rowNum = firstRowInd;
                    colNum = firstColumnInd;
                    merged = true;
                    break;
                }

            }
            Row row = sheet.getRow(rowNum);
            if (row.getCell((short) colNum) != null) {
                switch (row.getCell((short) colNum).getCellType()) {
                    case Cell.CELL_TYPE_FORMULA:
                        strExcelCell = "FORMULA ";
                        break;
                    case Cell.CELL_TYPE_NUMERIC: {
                        strExcelCell = String.valueOf(row.getCell((short) colNum).getNumericCellValue());
                    }
                    break;
                    case Cell.CELL_TYPE_STRING:
                        strExcelCell = row.getCell((short) colNum).getStringCellValue();
                        break;
                    case Cell.CELL_TYPE_BLANK:
                        strExcelCell = "";
                        break;
                    default:
                        strExcelCell = "";
                        break;
                }
            }
        } catch (Exception e) {
            logger.error(this.getClass() + ".getCellValue is error=STRAT#{}#END",e.getMessage());
        }
        if(strExcelCell.endsWith(".0")){
            strExcelCell = strExcelCell.substring(0,strExcelCell.lastIndexOf(".0"));
        }
        return new Result(merged,strExcelCell);
    }


    /**
     * 去除字符串中头部和尾部所包含的空格（包括:空格(全角，半角)、制表符、换页符等）
     * @param
     * @return
     */
    public static String trim(String str){
        String dest = "";
        if (str != null) {
            Matcher matcher = compile("\\s*|\t|\r|\n").matcher(str);
            dest = matcher.replaceAll("");
        }
        return dest;
    }

    /**
     * 去掉序号的 .00 变成整数
     * @param value
     * @return
     */
    public String replaceDotZearo(String value){
        if(value.endsWith(".0")){
            return value.replace(".0","");
        }
        return value;
    }

    /**
     * 将字符串转换为保留两位小数的字符串
     * @param value
     * @return
     */
    public String str2Scale2OfPre(String value){
        if(value.isEmpty()){
            return "0.00";
        }
        BigDecimal num100 = new BigDecimal(100);
        BigDecimal big = new BigDecimal(value);

        return value = big.multiply(num100).setScale(2, BigDecimal.ROUND_HALF_UP).toString()+"%";
    }


    /**
     * 将字符串转换为保留两位小数的字符串
     * @param value
     * @return
     */
    public String str2Scale2(String value){
        if(value.isEmpty()){
            return "0.00";
        }
        BigDecimal big = new BigDecimal(value);
        return value = big.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    private String getValString(Cell cell,int fileType) {
        String value = "";
        try{
            switch (cell.getCellType()) {
                //数值型
                case Cell.CELL_TYPE_NUMERIC:
                    value = String.valueOf(cell.getNumericCellValue());
                    break;
                //字符串类型
                case Cell.CELL_TYPE_STRING:
                    value = cell.getStringCellValue().toString();
                    break;
                // 公式类型
                case Cell.CELL_TYPE_FORMULA:
                    //读公式计算值
                    value = String.valueOf(cell.getNumericCellValue());
                    // 如果获取的数据值为非法值,则转换为获取字符串
                    if (value.equals("NaN")) {
                        value = cell.getStringCellValue().toString();
                    }
                    break;
                // 空值
                case Cell.CELL_TYPE_BLANK:
                    value = "";
                    break;
                default:
                    value = cell.getStringCellValue().toString();
            }
            return value;
        }catch (Exception e){
            logger.error(this.getClass() + ".getValString is error=STRAT#{}#END",e.getMessage());
            return "";

        }

    }
    private String getVal(Cell cell) {
        String value = "";
        if (null == cell) {
            return value;
        }
        switch (cell.getCellType()) {
            //数值型
            case Cell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    //如果是date类型则 ，获取该cell的date值
                    Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    value = format.format(date);
                } else {// 纯数字
                    BigDecimal big = new BigDecimal(cell.getNumericCellValue());
                    value = big.toString();
                    //解决1234.0  去掉后面的.0
                    if (null != value && !"".equals(value.trim())) {
                        String[] item = value.split("[.]");
                        if (1 < item.length && "0".equals(item[1])) {
                            value = item[0];
                        }
                    }
                }
                break;
            //字符串类型   
            case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue().toString();
                break;
            // 公式类型
            case Cell.CELL_TYPE_FORMULA:
                //读公式计算值
                value = String.valueOf(cell.getNumericCellValue());
                // 如果获取的数据值为非法值,则转换为获取字符串
                if (value.equals("NaN")) {
                    value = cell.getStringCellValue().toString();
                }
                break;
            // 布尔类型
            case Cell.CELL_TYPE_BOOLEAN:
                value = " " + cell.getBooleanCellValue();
                break;
            // 空值
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            // 故障
            case Cell.CELL_TYPE_ERROR:
                value = "";
                break;
            default:
                value = cell.getStringCellValue().toString();
        }
        return value;
    }

    /**
     * 验证EXCEL文件
     *
     * @param filePath
     * @return
     */
    public boolean validateExcel(String filePath) {
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
            errorMsg = "文件名不是excel格式";
            return false;
        }
        return true;
    }

    /**
     * 描述：是否是2003的excel，返回true是2003
     */
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /**
     * @描述：是否是2007的excel，返回true是2007
     * @param filePath
     * @return
     */
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }
}  
