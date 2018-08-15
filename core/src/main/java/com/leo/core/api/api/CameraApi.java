package com.leo.core.api.api;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.iapi.api.ICameraApi;
import com.leo.core.iapi.inter.IAction;
import com.leo.core.iapi.inter.IMsgAction;
import com.leo.core.util.FileSizeUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

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

    private Context getContext() {
        return controllerApi().getContext();
    }

    private final static int PERMISSION_MEDIA_REQUEST_CODE = 0x101;

    @SuppressLint("CheckResult")
    private void op(IAction low, IAction action) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isCameraCanUse()) {
                if (action != null) {
                    action.execute();
                }
            } else {
                final RxPermissions rxPermissions = new RxPermissions(controllerApi().getFragmentActivity());
                rxPermissions.requestEachCombined(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(permission -> {
                            if (permission.granted && isCameraCanUse()) {
                                if (action != null) {
                                    action.execute();
                                }
                            } else {
                                show("请开启拍照权限");
                            }
                        });
            }
        } else {
            if (low != null) {
                low.execute();
            }
        }
    }

    private static boolean isCameraCanUse() {
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            mCamera.setParameters(mCamera.getParameters());
            return true;
        } catch (Exception ignored) {
        } finally {
            if (mCamera != null) {
                mCamera.release();
            }
        }
        return false;
    }

    @SuppressLint("InlinedApi")
    @Override
    public void openCamera(int type, IMsgAction<File> action) {
        this.photoType = type;
        this.action = action;
        if (hasSdCard()) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            String photo = System.currentTimeMillis() + ".jpg";
            photoFile = new File(getRootDir(), photo);
            op(() -> {
                // 从文件中创建uri
                Uri photoUri = Uri.fromFile(photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            }, () -> {
                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, photoFile.getAbsolutePath());
                Uri photoUri = controllerApi().getActivity().getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            });
            controllerApi().getActivity().startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_CAMERA:
                if (resultCode == RESULT_OK && photoFile != null) {
                    /*BitmapUtil.saveBitmapFile(BitmapUtil.getOptionBitmap(photoFile.getPath(), 4),
                            photoFile);
                    ii("photoFile.getPath()", photoFile.getPath());
                    ii("photoFile", FileSizeUtil.getFormetFileSize(photoFile));*/
                    /*String path = PhotoBitmapUtils.amendRotatePhoto(photoFile.getPath(),
                            controllerApi().getContext());*/
                    Luban.with(controllerApi().getContext())
                            .load(photoFile)
                            .setTargetDir(getRootDir().getPath())
                            .ignoreBy(500)
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