package com.ylink.fullgoal.api.config;

import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.api.main.HasCoreControllerApi;
import com.leo.core.util.Base64Util;
import com.leo.core.util.LogUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.config.Api;
import com.ylink.fullgoal.config.Config;
import com.ylink.fullgoal.vo.UrlVo;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

public class UrlApi<T extends UrlApi> extends HasCoreControllerApi<T> {

    //根URL
    private final static String ROOT_URL = Config.ROOT_URL;
    //上传URL
    private final static String UPLOAD_URL = Config.UPLOAD_URL;
    //图片上传页面
    private final static String UPLOAD_IMAGE_URL = Config.UPLOAD_IMAGE_URL;
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
     * 以BASE64上传图片
     *
     * @param image  image
     */
    private void uploadBase64Image(String image) {
        observable(getApi().uploadBase64Image(image));
    }

    /**
     * 上传图片
     */
    public void uploadBase64Image(File file) {
        String base64 = Base64Util.getFileToBase64(file);
        if(!TextUtils.isEmpty(base64)){
            uploadBase64Image(base64);
        }
    }

}
