package com.leo.core.core;

import android.support.annotation.NonNull;
import android.view.KeyEvent;

import com.leo.core.R;
import com.leo.core.api.api.ActivityLifecycleCallbacksApi;
import com.leo.core.api.api.CameraApi;
import com.leo.core.api.api.ConfigApi;
import com.leo.core.api.api.DataApi;
import com.leo.core.api.api.DataTypeApi;
import com.leo.core.api.api.FileApi;
import com.leo.core.api.api.GalleryApi;
import com.leo.core.api.api.GsonDecodeApi;
import com.leo.core.api.api.MD5Api;
import com.leo.core.api.api.MergeApi;
import com.leo.core.api.api.VosApi;
import com.leo.core.api.api.VsApi;
import com.leo.core.api.inter.MsgSubscriber;
import com.leo.core.api.api.ObjectApi;
import com.leo.core.api.api.PicassoLoadImageApi;
import com.leo.core.api.api.StartApi;
import com.leo.core.api.api.SubjoinApi;
import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.api.main.HttpApi;
import com.leo.core.api.main.ShowApi;
import com.leo.core.factory.ActionApiFactory;
import com.leo.core.iapi.api.IActivityLifecycleCallbacksApi;
import com.leo.core.iapi.api.IUrlApi;
import com.leo.core.iapi.api.IVosApi;
import com.leo.core.iapi.api.IVsApi;
import com.leo.core.net.RetrofitSubscriber;
import com.leo.core.net.UrlApi;
import com.leo.core.other.Transformer;

import rx.Observable;

public class BaseControllerApi<T extends BaseControllerApi, C> extends CoreControllerApi<T, C> {

    public BaseControllerApi(C controller) {
        super(controller);
    }

    @Override
    public ViewApi viewApi() {
        return (ViewApi) super.viewApi();
    }

    @Override
    public ViewApi newViewApi() {
        return new ViewApi(getThis());
    }

    @Override
    public DataApi dataApi() {
        return (DataApi) super.dataApi();
    }

    @Override
    public DataApi newDataApi() {
        return new DataApi().bind(getContext()).switchDefault();
    }

    @Override
    public ShowApi showApi() {
        return (ShowApi) super.showApi();
    }

    @Override
    public ShowApi newShowApi() {
        return new ShowApi(this, newDecodeApi());
    }

    @Override
    public HttpApi httpApi() {
        return (HttpApi) super.httpApi();
    }

    @Override
    public HttpApi newHttpApi() {
        return new HttpApi(getThis(), newTransformer());
    }

    @Override
    public GsonDecodeApi decodeApi() {
        return (GsonDecodeApi) super.decodeApi();
    }

    @Override
    public GsonDecodeApi newDecodeApi() {
        return new GsonDecodeApi();
    }

    @Override
    public MD5Api md5Api() {
        return (MD5Api) super.md5Api();
    }

    @Override
    public MD5Api newMd5Api() {
        return new MD5Api();
    }

    @Override
    public ObjectApi objectApi() {
        return (ObjectApi) super.objectApi();
    }

    @Override
    public ObjectApi newObjectApi() {
        return new ObjectApi();
    }

    @Override
    public ActionApiFactory actionApi() {
        return (ActionApiFactory) super.actionApi();
    }

    @Override
    public ActionApiFactory newActionApi() {
        return ActionApiFactory.getInstance();
    }

    @Override
    public StartApi startApi() {
        return (StartApi) super.startApi();
    }

    @Override
    public StartApi newStartApi() {
        return new StartApi(getThis());
    }

    @Override
    public PicassoLoadImageApi loadImageApi() {
        return (PicassoLoadImageApi) super.loadImageApi();
    }

    @Override
    public PicassoLoadImageApi newLoadImageApi() {
        return new PicassoLoadImageApi(getContext(), R.mipmap.default_icon, R.mipmap.error_icon);
    }

    @Override
    public ConfigApi configApi() {
        return (ConfigApi) super.configApi();
    }

    @Override
    public ConfigApi newConfigApi() {
        return new ConfigApi();
    }

    @Override
    public DataTypeApi dataTypeApi() {
        return (DataTypeApi) super.dataTypeApi();
    }

    @Override
    public DataTypeApi newDataTypeApi() {
        return new DataTypeApi();
    }

    @Override
    public MergeApi mergeApi() {
        return (MergeApi) super.mergeApi();
    }

    @Override
    public MergeApi newMergeApi() {
        return new MergeApi();
    }

    @Override
    public SubjoinApi subjoinApi() {
        return (SubjoinApi) super.subjoinApi();
    }

    @Override
    public SubjoinApi newSubjoinApi() {
        return new SubjoinApi(getThis());
    }

    @Override
    public GalleryApi galleryApi() {
        return (GalleryApi) super.galleryApi();
    }

    @Override
    public GalleryApi newGalleryApi() {
        return new GalleryApi(getThis());
    }

    @Override
    public FileApi fileApi() {
        return (FileApi) super.fileApi();
    }

    @Override
    public FileApi newFileApi() {
        return new FileApi(getThis());
    }

    @Override
    public CameraApi cameraApi() {
        return (CameraApi) super.cameraApi();
    }

    @Override
    public CameraApi newCameraApi() {
        return new CameraApi(getThis());
    }

    @Override
    public IUrlApi newApi() {
        return new UrlApi(getThis());
    }

    @Override
    public IVsApi newVsApi() {
        return new VsApi();
    }

    @Override
    public IVosApi newVosApi() {
        return new VosApi(getThis());
    }

    @Override
    public <B, M> Observable.Transformer<B, M> newTransformer() {
        return new Transformer();
    }

    @Override
    public <B> MsgSubscriber<T, B> newSubscriber() {
        return new RetrofitSubscriber(parseApi().copy());
    }

    @Override
    public IActivityLifecycleCallbacksApi newActivityLifecycleApi() {
        return new ActivityLifecycleCallbacksApi();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        galleryApi().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}