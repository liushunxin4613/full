package com.ylink.fullgoal.factory;

import android.support.annotation.NonNull;

import com.leo.core.api.api.VsApi;
import com.leo.core.util.MD5Util;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.controllerApi.core.AppControllerApi;
import com.ylink.fullgoal.db.table.Files;
import com.ylink.fullgoal.db.table.JsonFile;

import java.util.List;
import java.util.Map;

public class FilesFactory extends VsApi<FilesFactory> {

    private static FilesFactory instance;

    public static FilesFactory getInstance() {
        if (instance == null) {
            synchronized (FilesFactory.class) {
                if (instance == null) {
                    instance = new FilesFactory();
                }
            }
        }
        return instance;
    }

    //对外访问接口

    /**
     * 初始化数据,必先处理
     *
     * @param api api
     */
    public final void init(AppControllerApi api) {
        this.api = api;
    }

    //内部属性和方法

    private AppControllerApi api;

    private AppControllerApi getApi() {
        return api;
    }

    public void openCore() {
        open(JsonFile.CORE_JSON);
    }

    private void open(String name) {
        if (TextUtils.check(name)) {
            String text = getApi().getAssetsString(name);
            String path = Files.FILE_TYPE_ASSETS_HEADER + name;
            String md5 = MD5Util.getStringMD5(text);
            if (!Files.check(name, md5)) {
                initJsonText(path, text);
                onOpen(name);
                Files.sav(name, null, Files.FILE_TYPE_ASSETS, md5);
            }
        }
    }

    private void onOpen(@NonNull String name) {
        switch (name) {
            case JsonFile.CORE_JSON:
                List<JsonFile> data = JsonFile.queryList("init.fileName");
                execute(data, obj -> open(obj.getValue()));
                break;
        }
    }

    private void initJsonText(String root, String text) {
        if (TextUtils.check(text)) {
            JsonFile.clear(root);
            recursive(root, null, null, null, TextUtils.toJSONMap(text));
        }
    }

    private void recursive(String root, String spectrum, String parent, String name, Object obj) {
        if (obj instanceof List) {
            execute((List) obj, item -> recursive(root, spectrum, parent, name, item));
        } else if (obj instanceof Map) {
            execute((Map) obj, (key, value) -> {
                if (key instanceof String) {
                    recursive(root, gv(spectrum, parent), name, (String) key, value);
                }
            });
        } else {
            JsonFile.sav(root, name, valueOf(obj), parent, spectrum);
        }
    }

    private String gv(String a, String b) {
        if (TextUtils.isEmpty(b)) {
            return null;
        } else if (TextUtils.isEmpty(a)) {
            return b;
        } else {
            return String.format("%s.%s", a, b);
        }
    }

    private String valueOf(Object obj) {
        return obj == null ? null : String.valueOf(obj);
    }

}