package com.leo.core.core;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.KeyEvent;

import com.leo.core.R;
import com.leo.core.api.ConfigApi;
import com.leo.core.api.DataApi;
import com.leo.core.api.DataTypeApi;
import com.leo.core.api.FileApi;
import com.leo.core.api.GalleryApi;
import com.leo.core.api.GsonDecodeApi;
import com.leo.core.api.MD5Api;
import com.leo.core.api.MergeApi;
import com.leo.core.api.ObjectApi;
import com.leo.core.api.PicassoLoadImageApi;
import com.leo.core.api.StartApi;
import com.leo.core.api.SubjoinApi;
import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.api.main.HttpApi;
import com.leo.core.api.main.ShowApi;
import com.leo.core.config.Config;
import com.leo.core.factory.ActionApiFactory;
import com.leo.core.iapi.IActionApi;
import com.leo.core.iapi.IConfigApi;
import com.leo.core.iapi.IDataApi;
import com.leo.core.iapi.IDataTypeApi;
import com.leo.core.iapi.IDecodeApi;
import com.leo.core.iapi.IFileApi;
import com.leo.core.iapi.ILoadImageApi;
import com.leo.core.iapi.IMD5Api;
import com.leo.core.iapi.IMergeApi;
import com.leo.core.iapi.IObjectApi;
import com.leo.core.iapi.IStartApi;
import com.leo.core.iapi.ISubjoinApi;
import com.leo.core.iapi.main.IHttpApi;
import com.leo.core.iapi.main.IShowApi;
import com.leo.core.iapi.main.IViewApi;
import com.leo.core.net.RetrofitSubscriber;
import com.leo.core.other.Transformer;

import rx.Observable;
import rx.Subscriber;

public class BaseControllerApi<T extends BaseControllerApi, C> extends CoreControllerApi<T, C> {

    public BaseControllerApi(C controller) {
        super(controller);
    }

    @Override
    public IViewApi newViewApi() {
        return new ViewApi(getThis());
    }

    @Override
    public IDataApi newDataApi() {
        return new DataApi().bind(getContext()).switchDefault();
    }

    @Override
    public IShowApi newShowApi() {
        return new ShowApi(this, newDecodeApi());
    }

    @Override
    public IHttpApi newHttpApi() {
        return new HttpApi(newTransformer());
    }

    @Override
    public IDecodeApi newDecodeApi() {
        return new GsonDecodeApi();
    }

    @Override
    public <B, M> Observable.Transformer<B, M> newTransformer() {
        return new Transformer();
    }

    @Override
    public <B> Subscriber<B> newSubscriber() {
        return new RetrofitSubscriber(parseApi());
    }

    @Override
    public IMD5Api newMd5Api() {
        return new MD5Api();
    }

    @Override
    public IObjectApi newObjectApi() {
        return new ObjectApi();
    }

    @Override
    public IActionApi newActionApi() {
        return ActionApiFactory.getInstance();
    }

    @Override
    public IStartApi newStartApi() {
        return new StartApi(getThis());
    }

    @Override
    public ILoadImageApi newLoadImageApi() {
        return new PicassoLoadImageApi(getContext(), R.mipmap.default_icon, R.mipmap.error_icon);
    }

    @Override
    public IConfigApi newConfigApi() {
        return new ConfigApi();
    }

    @Override
    public IDataTypeApi newDataTypeApi() {
        return new DataTypeApi();
    }

    @Override
    public IMergeApi newMergeApi() {
        return new MergeApi();
    }

    @Override
    public ISubjoinApi newSubjoinApi() {
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
    public IFileApi newFileApi() {
        return new FileApi(getThis());
    }

    @Override
    public void onFinish() {
        super.onFinish();
        saveData(Config.LAST_FINISH_ACTIVITY, (String) getExecute(getActivity(),
                activity -> activity.getClass().getName()));
        saveData(Config.LAST_FINISH_CONTROLLER_API, getClass().getName());
    }

    @Override
    public void onStartActivity(Intent intent) {
        super.onStartActivity(intent);
        executeNon(intent, obj -> saveData(Config.LAST_START_ACTIVITY, (String) getExecute(getActivity(),
                activity -> activity.getClass().getName())));
        saveData(Config.LAST_START_CONTROLLER_API, getClass().getName());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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
