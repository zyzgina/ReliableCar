package com.kaopujinfu.appsys.customlayoutlibrary.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by zuoliji on 2017/3/28.
 * 说明
 */
public class FileUtils {
    public static final String FILE_PATH = "/kaopuCar/";

    /**
     * 获取根目录
     */
    public static String getFilePath() {
        String filePath = null;
        filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        filePath += FILE_PATH;
        File file = new File(filePath);
        if (!file.exists())
            file.mkdirs();
        return filePath;
    }

    /**
     * 获取靠谱上传目录
     */
    public static String getKaopuUploadPath() {
        String filePath = null;
        filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + FILE_PATH + "kaopu/upload/";
        File file = new File(filePath);
        if (!file.exists())
            file.mkdirs();
        return filePath;
    }

    /**
     * 获取看车上传目录
     */
    public static String getCarUploadPath() {
        String filePath = null;
        filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + FILE_PATH + "car/upload/";
        File file = new File(filePath);
        if (!file.exists())
            file.mkdirs();
        return filePath;
    }

    /**
     * 获取看车拍照目录
     */
    public static String getCarPhotographPath() {
        String filePath = null;
        filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + FILE_PATH + "car/Photograph/";
        File file = new File(filePath);
        if (!file.exists())
            file.mkdirs();
        return filePath;
    }

    /**
     * 获取缓存日志目录
     */
    public static String getLogFilePath() {
        String filePath = null;
        filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + FILE_PATH + "LOG/";
        File file = new File(filePath);
        if (!file.exists())
            file.mkdirs();
        return filePath;
    }

    /**
     * 获取缓存错误日志目录
     */
    public static String getCrashLogFilePath() {
        String filePath = null;
        filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + FILE_PATH + "LOG/error/";
        File file = new File(filePath);
        if (!file.exists())
            file.mkdirs();
        return filePath;
    }

    /**
     * 获取文件夹的总大小
     *
     * @param filePath
     * @return
     */
    public static long getFolderSize(String filePath) {
        long size = 0;
        try {
            File folder = new File(filePath);
            if (folder.exists()) {
                File[] fileList = folder.listFiles();
                for (int i = 0; i < fileList.length; i++) {
                    File file = fileList[i];
                    if (file.isDirectory()) {
                        size += getFolderSize(file.getAbsolutePath());
                    } else {
                        size += file.length();
                    }
                }
            } else {
                size = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 遍历文件夹查找文件夹和子文件夹的所有文件
     *
     * @param fileList
     * @param file
     * @return
     */
    public static List<File> findFiles(List<File> fileList, File file) {
        // 判断SD卡是否存在
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File files[] = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        findFiles(fileList, f);
                    } else {
                        fileList.add(f);
                    }
                }
            }
        }
        return fileList;
    }

    /**
     * 遍历文件夹查找文件夹和子文件夹的所有文件
     *
     * @param fileList
     * @param file
     * @return
     */
    public static List<String> findFilePaths(List<String> fileList, File file) {
        // 判断SD卡是否存在
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File files[] = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        findFilePaths(fileList, f);
                    } else {
                        fileList.add(f.getAbsolutePath());
                    }
                }
            }
        }
        return fileList;
    }

    /**
     * 获取文件大小
     *
     * @param file
     * @return
     */
    public static String getSize(File file) {
        DecimalFormat df = new DecimalFormat("#.00");
        String size;
        long fileSize = file.length();
        if (fileSize < 1024) {
            size = df.format((double) fileSize) + "B";
        } else if (fileSize < 1048576) {
            size = df.format((double) fileSize / 1024) + "KB";
        } else if (fileSize < 1073741824) {
            size = df.format((double) fileSize / 1048576) + "M";
        } else {
            size = df.format((double) fileSize / 1073741824) + "G";
        }
        return size;
    }

    /**
     * 删除文件夹下所有文件
     *
     * @param folderPath
     */
    public static void delFolder(String folderPath) {
        try {
            //删除完里面所有内容
            delAllFile(folderPath);
            //删除空文件夹
            File myFilePath = new File(folderPath);
            myFilePath.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件夹下的所有文件
     *
     * @param path
     * @return
     */
    private static void delAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] tmpList = file.list();
        File tmp = null;
        for (int i = 0; i < tmpList.length; i++) {
            if (path.endsWith(File.separator)) {
                tmp = new File(path + tmpList[i]);
            } else {
                tmp = new File(path + File.separator + tmpList[i]);
            }
            if (tmp.isFile()) {
                tmp.delete();
            }
            if (tmp.isDirectory()) {
                delAllFile(path + "/" + tmpList[i]);
                delFolder(path + "/" + tmpList[i]);
            }
        }
    }

    /**
     * 刪除文件
     *
     * @param path
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }

    /**
     * 保存图片文件
     */
    public static void saveBitMap(String path, Bitmap bitmap) {
        File upload = new File(path);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(upload);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制文件
     *
     * @param fromFile 来源目录
     * @param toFile   存放目录
     */
    public static int CopySdcardFile(String fromFile, String toFile) {
        try {
            InputStream fosfrom = new FileInputStream(fromFile);
            OutputStream fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            fosfrom.close();
            fosto.close();
            return 0;

        } catch (Exception ex) {
            return -1;
        }
    }
}
