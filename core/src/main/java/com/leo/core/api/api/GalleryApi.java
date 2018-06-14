package com.leo.core.api.api;

import android.content.pm.PackageManager;
import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.api.main.HasCoreControllerApi;
import com.leo.core.iapi.api.IGalleryApi;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.BaseResultEvent;

public class GalleryApi<T extends GalleryApi> extends HasCoreControllerApi<T> implements IGalleryApi<T> {

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
                    protected void onEvent(BaseResultEvent baseResultEvent) {
                        controllerApi().ee("baseResultEvent", baseResultEvent);
                    }
                })
                .openGallery();
        return getThis();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        int PERMISSIONS_REQUEST_READ_CONTACTS = 8;
        if (isOpen && requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                controllerApi().show("您拒绝了授权，请在 设置-应用管理 中开启此应用的储存授权。");
            }
        }
    }

}

