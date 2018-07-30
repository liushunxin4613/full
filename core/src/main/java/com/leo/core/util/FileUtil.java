package com.leo.core.util;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FileUtil {

    public static File getFileByUri(Context context, Uri uri) {
        if (context != null && uri != null) {
            String path = null;
            if (TextUtils.equals(uri.getScheme(), "file")) {
                path = uri.getEncodedPath();
                if (path != null) {
                    path = Uri.decode(path);
                    ContentResolver cr = context.getContentResolver();
                    Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            new String[]{MediaStore.Images.ImageColumns._ID,
                                    MediaStore.Images.ImageColumns.DATA},
                            "(" + MediaStore.Images.ImageColumns.DATA + "=" + "'" + path + "'" + ")",
                            null, null);
                    int index = 0;
                    for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                        index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                        index = cur.getInt(index);
                        path = cur.getString(cur.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
                    }
                    cur.close();
                    if (index > 0) {
                        Uri u = Uri.parse("content://media/external/images/media/" + index);
                        LogUtil.ee(FileUtil.class, "uri: " + u);
                    }
                }
                if (path != null) {
                    return new File(path);
                }
            } else if (TextUtils.equals("content", uri.getScheme())) {
                // 4.2.2以后
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    path = cursor.getString(columnIndex);
                }
                cursor.close();
                return new File(path);
            }
        }
        return null;
    }

    public static String getFileSuffix(File file, boolean suffix) {
        return file == null ? null : getFileSuffix(file.getName(), suffix);
    }

    private static String getFileSuffix(String fileName, boolean suffix) {
        if (!TextUtils.isEmpty(fileName)) {
            int index = fileName.lastIndexOf(".");
            if (index >= 0) {
                return suffix ? fileName.substring(index)
                        : fileName.substring(0, index);
            }
        }
        return fileName;
    }

    /**
     * 读取小型文件的内容
     *
     * @param file 想要读取的文件对象
     * @return 返回文件内容
     */
    @SuppressLint("NewApi")
    public static String readFile(File file) {
        if (!(file != null && file.exists() && file.isFile())) {
            return null;
        }
        if (file.length() >= 8 * 1024 * 1024) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), "UTF-8"));
            String s;
            while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
                result.append(System.lineSeparator()).append(s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String text = result.toString();
        if (!TextUtils.isEmpty(text)) {
            text = text.trim();
        }
        return text;
    }

    public static boolean writeFile(String filePath, InputStream inputStream) {
        if (null == filePath || filePath.length() < 1) {
            return false;
        }
        try {
            File file = new File(filePath);
            if (file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.exists() && file.isFile()) {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                int c = inputStream.read(buf);
                while (-1 != c) {
                    fileOutputStream.write(buf, 0, c);
                    c = inputStream.read(buf);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean writeFile(String filePath, byte[] bytes) {
        if (!TextUtils.check(filePath, bytes)) {
            return false;
        }
        try {
            File file = new File(filePath);
            if (file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.exists() && file.isFile()) {
                FileOutputStream fileOutputStream
                        = new FileOutputStream(file);
                fileOutputStream.write(bytes);
                fileOutputStream.flush();
                fileOutputStream.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Map<String, Object> toFileMap(File file) {
        if (file != null && file.exists()) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("fileName", file.getName());
            map.put("filePath", file.getPath());
            map.put("isDirectory", file.isDirectory());
            map.put("isFile", file.isFile());
            map.put("isHidden", file.isHidden());
            if (file.isDirectory()) {
                List<Map<String, Object>> data = new ArrayList<>();
                for (File f : file.listFiles()) {
                    RunUtil.executeNon(toFileMap(f), data::add);
                }
                map.put("list", data);
            }
            return map;
        }
        return null;
    }

}