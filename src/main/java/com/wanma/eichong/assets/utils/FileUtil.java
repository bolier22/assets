package com.wanma.eichong.assets.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

/**
 * 删除文件和目录
 *
 */
public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 删除文件，可以是文件或文件夹
     * @param fileName
     *            要删除的文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            logger.error("删除文件失败:" + fileName + "不存在！");
            return false;
        } else {
            if (file.isFile())
                return deleteFile(fileName);
            else
                return deleteDirectory(fileName);
        }
    }

    /**
     * 删除单个文件
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                logger.error("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                logger.error("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            logger.error("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    /**
     * 删除目录及目录下的文件
     * @param dir
     *            要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            logger.error("删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = FileUtil.deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = FileUtil.deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            logger.error("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            logger.error("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }

    public static void uploadFile(HttpServletRequest request, MultipartFile file1, String filePath) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        Collection<MultipartFile> files = fileMap.values();
        // 服务器文件保存路径 在项目根目录下upload文件夹中
        try {
            for (MultipartFile file : files) {
                // 获取文件名
                String fileName = file.getOriginalFilename();
                if (fileName.isEmpty()) {
                    continue;
                }
                //如果文件夹不存在则创建
                FileUtil.isExist(filePath);
                File inputFile = new File(filePath , fileName);
                InputStream input = file.getInputStream();
                FileOutputStream output = new FileOutputStream(inputFile);
                IOUtils.copy(input, output);
                input.close();
                output.close();
            }
        } catch (Exception e) {
            logger.error("excel error:"+e.getMessage());
        }
    }

    //不带文件名的Path：如：D:\xml2\2018\04\
    public static void isExist(String filePath) {
        String paths[] = filePath.split("\\\\");
        String dir = paths[0];
        for (int i = 0; i < paths.length - 1; i++) {//注意此处循环的长度
            try {
                dir = dir + "/" + paths[i + 1];
                File dirFile = new File(dir);
                if (!dirFile.exists()) {
                    dirFile.mkdir();
                    logger.info("创建目录为：" + dir);
                }
            } catch (Exception err) {
                logger.error("文件夹创建发生异常");
            }
        }
    }

    public static void main(String[] args) {
//        String filePath = "D:\\xml2\\2018\\04\\12.xls";
//        String filePath = "http://cs.ep.eichong.com:2481/ims/static/excel/shiShi.xls";
//        FileUtil.deleteFile(filePath);// 删除单个文件

//        String filePath = "D:\\xml2\\2018\\04\\23";
//        DeleteFileUtil.deleteDirectory(filePath);// 删除一个目录

        //文件或文件夹都可以删除
//        String filePath = "D:\\xml2\\2018\\04\\34";
//        DeleteFileUtil.delete(filePath);
        String chines = "删#除*一%个12目bcd录";
        System.out.println(FileUtil.str2Pinyin(chines,null));

    }


    /**
     * 拼音
     * @param str
     * @param fill
     * @return
     */
    private final static HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
    static {
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
    }

    /**
     * 汉字转拼音
     * @param str
     * @return
     */
    public static String str2Pinyin(String str, String fill) {
        try {
            StringBuffer sb = new StringBuffer();
            if (fill == null)
                fill = "";
            boolean isCn = true;
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (i > 0 && isCn) {
                    sb.append(fill);
                }
                if (c == ' ') {
                    sb.append(fill);
                }
                // 1、判断c是不是中文
                if (c >= '\u4e00' && c <= '\u9fa5') {
                    isCn = true;
                    sb.append(PinyinHelper.toHanyuPinyinStringArray(c, format)[0]);
                } else {
                    // 不是中文
                    if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                        sb.append(c);
                    }
                    isCn = false;
                }
            }
            return sb.toString();
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 取得汉字拼音首字母
     * @param str
     * @return
     */
    public static String strFirst2Pinyin(String str) {
        try {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                // 1、判断c是不是中文
                if (c >= '\u4e00' && c <= '\u9fa5') {
                    sb.append(PinyinHelper.toHanyuPinyinStringArray(c, format)[0]
                            .charAt(0));
                } else {
                }
            }
            return sb.toString();
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 汉字转换位汉语全拼，英文字符不变，特殊字符丢失
     * 支持多音字，生成方式如（重当参:zhongdangcen,zhongdangcan,chongdangcen
     * ,chongdangshen,zhongdangshen,chongdangcan）
     *
     * @param chines
     *            汉字
     * @return 拼音
     */
    public static String converterToSpell(String chines) {
        StringBuffer pinyinName = new StringBuffer();
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    // 取得当前汉字的所有全拼
                    String[] strs = PinyinHelper.toHanyuPinyinStringArray(
                            nameChar[i], defaultFormat);
                    if (strs != null) {
                        for (int j = 0; j < strs.length; j++) {
                            pinyinName.append(strs[j]);
                            if (j != strs.length - 1) {
                                pinyinName.append(",");
                            }
                        }
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName.append(nameChar[i]);
            }
            pinyinName.append(" ");
        }
        // return pinyinName.toString();
        return parseTheChineseByObject(discountTheChinese(pinyinName.toString()));
    }
    /**
     * 去除多音字重复数据
     *
     * @param theStr
     * @return
     */
    private static List<Map<String, Integer>> discountTheChinese(String theStr) {
        // 去除重复拼音后的拼音列表
        List<Map<String, Integer>> mapList = new ArrayList<Map<String, Integer>>();
        // 用于处理每个字的多音字，去掉重复
        Map<String, Integer> onlyOne = null;
        String[] firsts = theStr.split(" ");
        // 读出每个汉字的拼音
        for (String str : firsts) {
            onlyOne = new Hashtable<String, Integer>();
            String[] china = str.split(",");
            // 多音字处理
            for (String s : china) {
                Integer count = onlyOne.get(s);
                if (count == null) {
                    onlyOne.put(s, new Integer(1));
                } else {
                    onlyOne.remove(s);
                    count++;
                    onlyOne.put(s, count);
                }
            }
            mapList.add(onlyOne);
        }
        return mapList;
    }
    /**
     * 解析并组合拼音，对象合并方案
     *
     * @return
     */
    private static String parseTheChineseByObject(
            List<Map<String, Integer>> list) {
        Map<String, Integer> first = null; // 用于统计每一次,集合组合数据
        // 遍历每一组集合
        for (int i = 0; i < list.size(); i++) {
            // 每一组集合与上一次组合的Map
            Map<String, Integer> temp = new Hashtable<String, Integer>();
            // 第一次循环，first为空
            if (first != null) {
                // 取出上次组合与此次集合的字符，并保存
                for (String s : first.keySet()) {
                    for (String s1 : list.get(i).keySet()) {
                        String str = s + s1;
                        temp.put(str, 1);
                    }
                }
                // 清理上一次组合数据
                if (temp != null && temp.size() > 0) {
                    first.clear();
                }
            } else {
                for (String s : list.get(i).keySet()) {
                    String str = s;
                    temp.put(str, 1);
                }
            }
            // 保存组合数据以便下次循环使用
            if (temp != null && temp.size() > 0) {
                first = temp;
            }
        }
        String returnStr = "";
        if (first != null) {
            Object[] array = first.keySet().toArray();
            for (int i = 0; i < array.length; i++) {
                if(i == 0){
                    returnStr = (String) array[i];
                }
            }
        }
        return returnStr;
    }
}