package com.leo.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

public class FileSizeUtil {

    public static final int SIZETYPE_B = 1;//获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;//获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;//获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;//获取文件大小单位为GB的double值

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    public static double getFileOrFilesSize(String filePath, int sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getFormetFileSize(blockSize, sizeType);
    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String getAutoFileOrFilesSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getFormetFileSize(blockSize);
    }

    /**
     * 获取指定文件大小
     */
    public static long getFileSize(File file) {
        try {
            if (file.exists()) {
                return new FileInputStream(file).available();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取指定文件夹
     */
    private static long getFileSizes(File f) {
        long size = 0;
        File flist[] = f.listFiles();
        for (File aFlist : flist) {
            if (aFlist.isDirectory()) {
                size = size + getFileSizes(aFlist);
            } else {
                size = size + getFileSize(aFlist);
            }
        }
        return size;
    }

    public static String getFormetFileSize(File file) {
        long length = 0;
        if (file != null && file.exists() && file.exists()) {
            length = file.length();
        }
        return getFormetFileSize(length);
    }

    public static double getFormetFileSize(File file, int sizeType) {
        long length = 0;
        if (file != null && file.exists() && file.exists()) {
            length = file.length();
        }
        return getFormetFileSize(length, sizeType);
    }

    /**
     * 转换文件大小
     */
    public static String getFormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        if (fileS <= 0) {
            return "0B";
        } else if (fileS < 1024) {
            return df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            return df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            return df.format((double) fileS / 1048576) + "MB";
        } else {
            return df.format((double) fileS / 1073741824) + "GB";
        }
    }

    /**
     * 转换文件大小,指定转换的类型
     */
    public static double getFormetFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZETYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }
}
