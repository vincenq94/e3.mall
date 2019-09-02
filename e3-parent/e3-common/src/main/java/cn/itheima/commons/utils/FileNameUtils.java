package cn.itheima.commons.utils;

/**
 * @ClassName FileNameUtils
 * @Description
 * @Author ShanKun
 * @Date 2019-06-03 16:19
 * @Version 1.0
 * @Copyright by 天津津湖数据有限公司
 **/
public class FileNameUtils {
    /**
     * @Description 获取不带扩展名的文件名
     * @Author ShanKun
     * @Date 2019-06-03 16:40:21
     * @Param [filename]
     * @Return java.lang.String
     * @Copyright by 天津津湖数据有限公司
     **/
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * @Description 获取文件扩展名
     * @Author ShanKun
     * @Date 2019-06-03 16:39:46
     * @Param [filename]
     * @Return java.lang.String
     * @Copyright by 天津津湖数据有限公司
     **/
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }
}
