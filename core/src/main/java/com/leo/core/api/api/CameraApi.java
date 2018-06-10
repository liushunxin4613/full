package com.leo.core.api.api;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;

import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.iapi.api.ICameraApi;
import com.leo.core.iapi.inter.IMsgAction;
import com.leo.core.util.BitmapUtil;
import com.leo.core.util.FileSizeUtil;

import java.io.File;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static android.app.Activity.RESULT_OK;
import static com.leo.core.util.LogUtil.ii;

/**
 * 拍照
 */
public class CameraApi extends DirApi implements ICameraApi {

    private final static int PHOTO_REQUEST_CAMERA = 10001;
    private final static String ROOT_DIR = "photo";

    private int photoType;
    private File photoFile;
    private IMsgAction<File> action;

    public CameraApi(CoreControllerApi controllerApi) {
        super(controllerApi);
    }

    @Override
    public File getRootDir() {
        return getRootDir(ROOT_DIR);
    }

    @Override
    public void openCamera(int type, IMsgAction<File> action) {
        this.photoType = type;
        this.action = action;
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdCard()) {
            // 激活相机
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri photoUri;
            String photo = System.currentTimeMillis() + ".jpg";
            photoFile = new File(getRootDir(), photo);
            if (android.os.Build.VERSION.SDK_INT < 24) {
                // 从文件中创建uri
                photoUri = Uri.fromFile(photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            } else {
                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, photoFile.getAbsolutePath());
                //检查是否有存储权限，以免崩溃
                if (ContextCompat.checkSelfPermission(controllerApi().getContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    show("请开启存储权限");
                    return;
                }
                photoUri = controllerApi().getActivity().getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            }
            // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAMERA
            controllerApi().getActivity().startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_CAMERA:
                if (resultCode == RESULT_OK && photoFile != null) {
                    BitmapUtil.saveBitmapFile(BitmapUtil.getOptionBitmap(photoFile.getPath(), 2),
                            photoFile);
                    Luban.with(controllerApi().getContext())
                            .load(photoFile)
                            .setTargetDir(getRootDir().getPath())
                            .ignoreBy(100)
                            .setCompressListener(new OnCompressListener() {
                                @Override
                                public void onStart() {
                                }

                                @Override
                                public void onSuccess(File lubanFile) {
                                    photoFile.delete();
                                    ii("lubanFile.getPath()", lubanFile.getPath());
                                    ii("lubanFile", FileSizeUtil.getFormetFileSize(lubanFile));
                                    if (action != null) {
                                        action.execute(photoType, null, lubanFile);
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace();
                                }
                            })
                            .launch();
                }
                break;
        }
    }

}