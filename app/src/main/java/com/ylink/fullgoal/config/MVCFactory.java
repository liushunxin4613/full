package com.ylink.fullgoal.config;

import android.content.res.XmlResourceParser;

import com.google.gson.reflect.TypeToken;
import com.leo.core.api.api.VsApi;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.inter.IPathMsgAction;
import com.leo.core.util.FileUtil;
import com.leo.core.util.MD5Util;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.config.vo.ConfigFileVo;
import com.ylink.fullgoal.config.vo.ConfigV1Vo;
import com.ylink.fullgoal.config.vo.ConfigVo;
import com.ylink.fullgoal.config.vo.TemplateVo;
import com.ylink.fullgoal.controllerApi.core.ControllerApi;
import com.ylink.fullgoal.controllerApi.surface.BaseSearchControllerApi;
import com.ylink.fullgoal.vo.SearchVo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;

import static com.leo.core.util.TextUtils.checkParams;
import static com.leo.core.util.TextUtils.contains;

public class MVCFactory extends VsApi<MVCFactory> {

    private final static boolean LOCAL = false;
    private final static String FILTER[] = {};
    private final static String DIR = "config";

    private static MVCFactory instance;

    public static MVCFactory getInstance() {
        if (instance == null) {
            synchronized (MVCFactory.class) {
                if (instance == null) {
                    instance = new MVCFactory();
                }
            }
        }
        return instance;
    }

    private boolean copy;
    private String config;
    private ConfigV1Vo vo;
    private ControllerApi api;

    public MVCFactory init(ControllerApi api) {
        this.api = api;
        return getThis();
    }

    public void start() {
        String[] args = getConfigDir().list();
        api().ii("args", args);
        add(ConfigVo.class, (fieldName, path, what, msg, vo) -> {
            if (vo.isSuccess()) {
                execute(vo.getConfigList(), fileVo -> onFileVo(args, fileVo));
            }
        });
        add(ResponseBody.class, (fieldName, path, what, fileName, body) -> {
            if (TextUtils.check(path, fileName)) {
                File file = new File(getConfigDir(), fileName);
                if (FileUtil.writeFile(file.getPath(), body.byteStream())) {
                    api().ii("保存成功", file.getPath());
                    onFile(file);
                }
            }
        });
        api().api().queryConfig();
    }

    private void onFile(File file) {
        if (TextUtils.check(file) && TextUtils.check(file.getName()) && file.exists()) {
            switch (file.getName()) {
                case "config.json":
                    initConfig(LOCAL, file.getName());
                    setVo((ConfigV1Vo) api().decode(getConfig(), ConfigV1Vo.class));
                    initConfigVo();
                    break;
            }
        }
    }

    private void initConfigVo() {
        if (!TextUtils.check(getVo())) {
            return;
        }
        api().ii("vo", getVo());
        execute(getVo().getFileList(), vo -> {
            File file = new File(getConfigDir(), vo.getFile());
            if (!file.exists()) {
                download(vo.getFile(), vo.getUrl());
            } else {
                String md5 = MD5Util.getFileMD5(file);
                api().ii(String.format("%s => md5: %s, hashcode: %s", file.getPath(), md5,
                        vo.getHashcode()));
                if (!TextUtils.equals(md5, vo.getHashcode())) {
                    download(vo.getFile(), vo.getUrl());
                }
            }
        });
    }

    public void onData(String path, String params, List list, BaseSearchControllerApi api,
                       IObjAction<List<ViewBean>> action) {
        if (TextUtils.check(path, list, api, action, getVo()) && TextUtils.check(getVo().getViewList())) {
            api().ii("path", path);
            api().ii("params", params);
            executeBol(getVo().getViewList(), vo -> {
                if (TextUtils.equals(path, vo.getPath()) && checkParams(params, vo.getParams())) {
                    action.execute(getVBData(vo.getXml(), vo.getList(), list, api));
                    return true;
                }
                return false;
            });
        }
    }

    private boolean check(ConfigFileVo vo) {
        return TextUtils.equals(vo.getName(), "config");
    }

    private void initConfig(boolean local, String config) {
        if (TextUtils.check(config)) {
            if (local) {
                this.config = api().getAssetsString(config);
            } else {
                this.config = FileUtil.readFile(new File(getConfigDir(), config));
            }
        }
    }

    private ConfigV1Vo getVo() {
        return vo;
    }

    private void setVo(ConfigV1Vo vo) {
        this.vo = vo;
    }

    private String getConfig() {
        return config;
    }

    private ControllerApi api() {
        return api;
    }

    private File getConfigDir() {
        return api().getRootDir(DIR);
    }

    private <A> void add(Class<A> root, IPathMsgAction<A> action) {
        api().add(root, action);
    }

    private List<ViewBean> getVBData(String xml, List<TemplateVo> templateData, List list,
                                     BaseSearchControllerApi api) {
        if (TextUtils.check(xml, templateData, list)) {
            List<ViewBean> data = new ArrayList<>();
            execute(list, item -> executeNon(new ViewBean(xml, getXmlResourceParser(xml),
                    templateData, item, (bean, view) -> api.finishActivity(new SearchVo<>(
                    api.getSearch(), api.getKey(), bean.getMap(), new TypeToken<SearchVo<Map<String,
                    String>>>() {
            }))), data::add));
            return data;
        }
        return null;
    }

    private void onFileVo(String[] args, ConfigFileVo vo) {
        if (TextUtils.check(vo) && vo.isSafety() && check(vo)) {
            if (contains(FILTER, vo.getFile()) || !contains(args, vo.getFile())) {
                download(vo.getFile(), vo.getUrl());
            } else {
                File file = new File(getConfigDir(), vo.getFile());
                String md5 = MD5Util.getFileMD5(file);
                api().ii(String.format("%s => md5: %s, hashcode: %s", file.getPath(), md5,
                        vo.getHashcode()));
                if (!TextUtils.equals(md5, vo.getHashcode())) {
                    download(vo.getFile(), vo.getUrl());
                } else {
                    onFile(file);
                }
            }
        }
    }

    private void download(String name, String url) {
        if (TextUtils.check(name) && TextUtils.isHttpUrl(url)) {
            api().api().download(url, name);
        }
    }

    private XmlResourceParser getXmlResourceParser(String xml) {
        return TextUtils.isEmpty(xml) ? null : api()
                .openFileLayoutXmlPullParser(getConfigDir(), xml);
    }

}