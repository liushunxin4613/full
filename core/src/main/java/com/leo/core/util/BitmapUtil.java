package com.leo.core.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtil {

    public static Bitmap getScaledBitmap(String filePath, int width, int height) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        if (width < 1) {
            width = 1;
        }
        if (height < 1) {
            height = 1;
        }
        return Bitmap.createScaledBitmap(BitmapFactory.decodeFile(filePath), width, height, true);
    }

    public static Bitmap getOptionBitmap(String filePath, int inSampleSize) {
        if (inSampleSize < 1) {
            inSampleSize = 1;
        }
        return BitmapFactory.decodeFile(filePath, getBitmapOption(inSampleSize));
    }

    public static void saveBitmapFile(Bitmap bitmap, File file) {
        if (bitmap != null && file != null) {
            //将要保存图片的路径
            try {
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                bos.flush();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static BitmapFactory.Options getBitmapOption(int inSampleSize) {
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }

}
