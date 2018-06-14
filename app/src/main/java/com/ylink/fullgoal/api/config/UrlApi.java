package com.ylink.fullgoal.api.config;

import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.api.main.HasCoreControllerApi;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.api.IUrlApi;
import com.leo.core.util.Base64Util;
import com.leo.core.util.LogUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.config.Api;
import com.ylink.fullgoal.config.UrlConfig;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

public class UrlApi<T extends UrlApi> extends HasCoreControllerApi<T> implements IUrlApi<T> {

    //根URL
    private final static String ROOT_URL = UrlConfig.ROOT_URL;
    //上传URL
    private final static String UPLOAD_URL = UrlConfig.UPLOAD_URL;
    //图片上传页面
    private final static String UPLOAD_IMAGE_URL = UrlConfig.UPLOAD_IMAGE_URL;
    //图片上传固定参数
    private final static String UPLOAD_IMAGE_PARAM = "files";
    //图片上传类型
    private final static String UPLOAD_IMAGE_TYPE = "image/*";

    public UrlApi(CoreControllerApi controllerApi) {
        super(controllerApi);
    }

    //通用

    /**
     * api
     *
     * @return api
     */
    private Api getApi() {
        return (Api) controllerApi().getApi();
    }

    /**
     * api url
     *
     * @param url url
     * @return api
     */
    private Api getApi(String url) {
        return (Api) controllerApi().getApi(url);
    }

    /**
     * 执行observable
     *
     * @param observable observable
     * @param <B>        <B>
     */
    private <B> void observable(Observable<B> observable) {
        controllerApi().observable(observable);
    }

    private <B> void observable(String path, int what, String msg, Observable<B> observable) {
        controllerApi().observable(observable, path, what, msg);
    }

    /**
     * 下载文件
     *
     * @param url url
     */
    public void downloadFile(String url) {
        observable(getApi().downloadFile(url));
    }

    /**
     * 上传文件
     *
     * @param url  url
     * @param body body
     */
    private void upload(String url, RequestBody body) {
        observable(getApi().upload(url, body));
    }

    /**
     * 上传图片
     */
    private void uploadImage(String name, File... args) {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(args)) {
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            execute(args, file -> builder.addFormDataPart(name, file.getName(),
                    RequestBody.create(MediaType.parse(UPLOAD_IMAGE_TYPE), file)));
            upload(UPLOAD_URL, builder.build());
        }
    }

    /**
     * 上传图片
     */
    public void uploadImage(File... args) {
        uploadImage(UPLOAD_IMAGE_PARAM, args);
    }

    /**
     * 上传图片
     */
    public void uploadBase64Image(File file, String msg, int type, String uploadType) {
        String base64 = Base64Util.getFileToBase64(file);
        if (!TextUtils.isEmpty(base64)) {
            post("uploadImage", map -> {
                map.put("image", base64);
                map.put("type", uploadType);
            }, type, msg);
        }
    }

    /**
     * 提交post数据
     */
    @Override
    public T post(String path) {
        post(path, null, -1, null);
        return getThis();
    }

    /**
     * 提交post数据
     */
    @Override
    public T post(String path, IObjAction<Map<String, Object>> action) {
        post(path, action, -1, null);
        return getThis();
    }

    /**
     * 提交post数据
     */
    @Override
    public T post(String path, IObjAction<Map<String, Object>> action, int what) {
        post(path, action, what, null);
        return getThis();
    }

    /**
     * 提交post数据
     */
    @Override
    public T post(String path, IObjAction<Map<String, Object>> action, String tag) {
        post(path, action, -1, tag);
        return getThis();
    }

    /**
     * 提交post数据
     */
    @Override
    public T post(String path, IObjAction<Map<String, Object>> action, int what, String tag) {
        if (!TextUtils.isEmpty(path)) {
            observable(path, what, tag, getApi().post(path, getCleanMapAction(action)));
        }
        return getThis();
    }

    /**
     * 提交post数据
     */
    @Override
    public T get(String path) {
        get(path, null, -1, null);
        return getThis();
    }

    /**
     * 提交get数据
     */
    @Override
    public T get(String path, IObjAction<Map<String, Object>> action) {
        get(path, action, -1, null);
        return getThis();
    }

    /**
     * 提交get数据
     */
    @Override
    public T get(String path, IObjAction<Map<String, Object>> action, int what) {
        get(path, action, what, null);
        return getThis();
    }

    /**
     * 提交get数据
     */
    @Override
    public T get(String path, IObjAction<Map<String, Object>> action, String tag) {
        get(path, action, -1, tag);
        return getThis();
    }

    /**
     * 提交get数据
     */
    @Override
    public T get(String path, IObjAction<Map<String, Object>> action, int what, String tag) {
        if (!TextUtils.isEmpty(path)) {
            observable(path, what, tag, getApi().get(path, getCleanMapAction(action)));
        }
        return getThis();
    }

    /**
     * 提交post数据
     */
    @Override
    public T post(String url, String path) {
        post(url, path, null, -1, null);
        return getThis();
    }

    /**
     * 提交post数据
     */
    @Override
    public T post(String url, String path, IObjAction<Map<String, Object>> action) {
        post(url, path, action, -1, null);
        return getThis();
    }

    /**
     * 提交post数据
     */
    @Override
    public T post(String url, String path, IObjAction<Map<String, Object>> action, int what) {
        post(url, path, action, what, null);
        return getThis();
    }

    /**
     * 提交post数据
     */
    @Override
    public T post(String url, String path, IObjAction<Map<String, Object>> action, String tag) {
        post(url, path, action, -1, tag);
        return getThis();
    }

    /**
     * 提交post数据
     */
    @Override
    public T post(String url, String path, IObjAction<Map<String, Object>> action, int what, String tag) {
        if (!TextUtils.isEmpty(path)) {
            observable(path, what, tag, getApi(url).post(path, getCleanMapAction(action)));
        }
        return getThis();
    }

    /**
     * 提交post数据
     */
    @Override
    public T get(String url, String path) {
        get(url, path, null, -1, null);
        return getThis();
    }

    /**
     * 提交get数据
     */
    @Override
    public T get(String url, String path, IObjAction<Map<String, Object>> action) {
        get(url, path, action, -1, null);
        return getThis();
    }

    /**
     * 提交get数据
     */
    @Override
    public T get(String url, String path, IObjAction<Map<String, Object>> action, int what) {
        get(url, path, action, what, null);
        return getThis();
    }

    /**
     * 提交get数据
     */
    @Override
    public T get(String url, String path, IObjAction<Map<String, Object>> action, String tag) {
        get(url, path, action, -1, tag);
        return getThis();
    }

    /**
     * 提交get数据
     */
    @Override
    public T get(String url, String path, IObjAction<Map<String, Object>> action, int what, String tag) {
        if (!TextUtils.isEmpty(path)) {
            observable(path, what, tag, getApi(url).get(path, getCleanMapAction(action)));
        }
        return getThis();
    }

    @Override
    public Map<String, String> getCleanMapAction(IObjAction<Map<String, Object>> action) {
        if (action != null) {
            Map<String, String> map = new HashMap<>();
            Map<String, Object> actionMap = new HashMap<>();
            action.execute(actionMap);
            if (!TextUtils.isEmpty(actionMap)) {
                for (Map.Entry<String, Object> entry : actionMap.entrySet()) {
                    if (!TextUtils.isEmpty(entry.getKey()) && entry.getValue() != null) {
                        map.put(entry.getKey(), controllerApi().getLog(entry.getValue()));
                    }
                }
//                LogUtil.ii(this, "map -> " + LogUtil.getLog(map));
                return map;
            }
        }
        return new HashMap<>();
    }

}
