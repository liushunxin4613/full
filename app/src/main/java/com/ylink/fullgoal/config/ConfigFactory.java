package com.ylink.fullgoal.config;

import com.leo.core.api.api.VsApi;
import com.leo.core.iapi.inter.IPathMsgAction;
import com.leo.core.util.FileUtil;
import com.leo.core.util.MD5Util;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.config.vo.ConfigFileVo;
import com.ylink.fullgoal.config.vo.ConfigVo;
import com.ylink.fullgoal.controllerApi.core.ControllerApi;

import java.io.File;

import okhttp3.ResponseBody;

import static com.leo.core.util.TextUtils.contains;

public class ConfigFactory extends VsApi<ConfigFactory> {

    private final static String FILTER[] = {};

    private static ConfigFactory instance;

    public static ConfigFactory getInstance() {
        if (instance == null) {
            synchronized (ConfigFactory.class) {
                if (instance == null) {
                    instance = new ConfigFactory();
                }
            }
        }
        return instance;
    }

    private File rootDir;
    private ControllerApi api;

    public ConfigFactory init(ControllerApi api) {
        this.api = api;
        this.rootDir = api().getRootDir("config");
        return getThis();
    }

    public void start() {
        String[] args = getRootDir().list();
        api().ii("args", args);
        add(ConfigVo.class, (type, baseUrl, path, map, what, msg, field, vo) -> {
            if (vo.isSuccess()) {
                execute(vo.getConfigList(), fileVo -> onFileVo(args, fileVo));
            }
        });
        add(ResponseBody.class, (type, baseUrl, path, map, what, fileName, field, body) -> {
            if (TextUtils.check(path, fileName)) {
                File file = new File(getRootDir(), fileName);
                if (FileUtil.writeFile(file.getPath(), body.byteStream())) {
                    api().ii("保存成功", file.getPath());
                }
            }
        });
        api().api().queryConfig();
    }

    private ControllerApi api() {
        return api;
    }

    private <A> void add(Class<A> root, IPathMsgAction<A> action) {
        api().add(root, action);
    }

    private File getRootDir() {
        return rootDir;
    }

    private void download(String name, String url) {
        if (TextUtils.check(name) && TextUtils.isHttpUrl(url)) {
            api().api().download(url, name);
        }
    }

    private void onFileVo(String[] args, ConfigFileVo vo) {
        if (TextUtils.check(vo) && vo.isSafety()) {
            if (contains(FILTER, vo.getFile()) || !contains(args, vo.getFile())) {
                download(vo.getFile(), vo.getUrl());
            } else {
                String md5 = MD5Util.getFileMD5(new File(getRootDir(), vo.getFile()));
                api().ii(String.format("%s => md5: %s, hashcode: %s", vo.getFile(), md5,
                        vo.getHashcode()));
                if (!TextUtils.equals(md5, vo.getHashcode())) {
                    download(vo.getFile(), vo.getUrl());
                }
            }
        }
    }

}