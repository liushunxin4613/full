package com.leo.core.api;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.leo.core.api.core.ThisApi;
import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.api.main.HasCoreControllerApi;
import com.leo.core.iapi.IGalleryApi;
import com.leo.core.iapi.main.IControllerApi;

import java.util.List;

import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.imageloader.GlideImageLoader;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.BaseResultEvent;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;

import static com.leo.core.config.Config.IMAGE_PATH;

public class GalleryApi<T extends GalleryApi> extends HasCoreControllerApi<T> implements IGalleryApi<T> {

    private final int PERMISSIONS_REQUEST_READ_CONTACTS = 8;

    private boolean isOpen;

    public GalleryApi(CoreControllerApi controllerApi) {
        super(controllerApi);
    }

    @Override
    public T openImageSelector() {
        //自定义方法的单选
        RxGalleryFinal
                .with(controllerApi().getContext())
                .image()
                .radio()
                .crop()
                .imageLoader(ImageLoaderType.GLIDE)
                .subscribe(new RxBusResultDisposable<BaseResultEvent>() {
                    @Override
                    protected void onEvent(BaseResultEvent baseResultEvent) throws Exception {
                        controllerApi().e("baseResultEvent", baseResultEvent);
                    }
                })
                .openGallery();
        return getThis();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (isOpen && requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                controllerApi().show("您拒绝了授权，请在 设置-应用管理 中开启此应用的储存授权。");
            }
        }
    }

}
