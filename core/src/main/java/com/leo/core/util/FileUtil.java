package com.leo.core.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

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

    public static String getFileSuffix(File file, boolean suffix){
        return file == null ? null : getFileSuffix(file.getName(), suffix);
    }

    public static String getFileSuffix(String fileName, boolean suffix){
        if(!TextUtils.isEmpty(fileName)){
            int index = fileName.lastIndexOf(".");
            if(index >= 0){
                return suffix ? fileName.substring(index)
                        : fileName.substring(0, index);
            }
        }
        return fileName;
    }

}
