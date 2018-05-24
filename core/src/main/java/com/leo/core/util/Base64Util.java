package com.leo.core.util;

import android.os.Environment;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Base64Util {

    /**
     * 文件转base64字符串
     *
     * @param file file
     * @return String
     */
    public static String getFileToBase64(File file) {
        if (file != null) {
            InputStream in = null;
            try {
                in = new FileInputStream(file);
                byte[] bytes = new byte[in.available()];
                int length = in.read(bytes);
                return Base64.encodeToString(bytes, 0, length, Base64.DEFAULT);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * base64字符串转文件
     *
     * @param base64 base64
     * @return File
     */
    public static File getBase64ToFile(String fileName, String base64) {
        if (!TextUtils.isEmpty(fileName) && !TextUtils.isEmpty(base64)) {
            File file = null;
            FileOutputStream out = null;
            try {
                //解码，然后将字节转换为文件
                file = new File(Environment.getExternalStorageDirectory(), fileName);
                if (!file.exists())
                    file.createNewFile();
                //将字符串转换为byte数组
                byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
                ByteArrayInputStream in = new ByteArrayInputStream(bytes);
                byte[] buffer = new byte[1024];
                out = new FileOutputStream(file);
                int byteSum = 0;
                int byteRead;
                while ((byteRead = in.read(buffer)) != -1) {
                    byteSum += byteRead;
                    //文件写操作
                    out.write(buffer, 0, byteRead);
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return file;
        }
        return null;
    }

}
