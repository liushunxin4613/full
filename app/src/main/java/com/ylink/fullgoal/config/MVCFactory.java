package com.ylink.fullgoal.config;

import com.google.gson.reflect.TypeToken;
import com.leo.core.api.api.VsApi;
import com.leo.core.iapi.core.INorm;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.inter.IPathMsgAction;
import com.leo.core.util.FileUtil;
import com.leo.core.util.MD5Util;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.config.vo.ConfigV1Vo;
import com.ylink.fullgoal.config.vo.TemplateVo;
import com.ylink.fullgoal.controllerApi.core.ControllerApi;
import com.ylink.fullgoal.controllerApi.surface.BaseSearchControllerApi;
import com.ylink.fullgoal.fg.DataFg;
import com.ylink.fullgoal.norm.ViewNorm;
import com.ylink.fullgoal.vo.SearchVo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.leo.core.util.TextUtils.checkParams;

public class MVCFactory extends VsApi<MVCFactory> {

    private final static String DIR = "config";
    private final static boolean LOCAL = false;
    private final static String ROOT_FILE = "config.json";

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

    private String config;
    private ConfigV1Vo vo;
    private ControllerApi api;

    public MVCFactory init(ControllerApi api) {
        this.api = api;
        return getThis();
    }

    public void start() {
        add(DataFg.class, byte[].class, (type, baseUrl, path, map, what, fileName, field, bytes) -> {
            if (TextUtils.check(path, fileName)) {
                File file = new File(getConfigDir(), fileName);
                if (FileUtil.writeFile(file.getPath(), bytes)) {
                    api().ii("保存成功", file.getPath());
                    api().ii("md5", MD5Util.getFileMD5(file));
                    onFile(file);
                }
            }
        });
        File file = new File(getConfigDir(), ROOT_FILE);
        if(file.exists()){
            onFile(file);
        } else {
            download(ROOT_FILE);
        }
    }

    private void onFile(File file) {
        if (TextUtils.check(file) && TextUtils.check(file.getName()) && file.exists()) {
            switch (file.getName()) {
                case ROOT_FILE:
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
//        api().ii("vo", getVo());
        execute(getVo().getFileList(), vo -> {
            File file = new File(getConfigDir(), vo.getFile());
            if (!file.exists()) {
                download(vo.getFile());
            } else {
                String md5 = MD5Util.getFileMD5(file);
                if (!TextUtils.equals(md5, vo.getHashcode())) {
                    api().ii(String.format("%s => md5: %s, hashcode: %s", file.getPath(), md5,
                            vo.getHashcode()));
                    download(vo.getFile());
                }
            }
        });
    }

    public void onData(String path, String params, List list, BaseSearchControllerApi api,
                       IObjAction<List<INorm>> action) {
        if (TextUtils.check(path, list, action, api, getVo()) && TextUtils.check(getVo().getViewList())) {
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

    public File getConfigDir() {
        return api().getRootDir(DIR);
    }

    private <A, B> void add(Class<A> root, Class<B> clz, IPathMsgAction<B> action) {
        api().add(root, clz, action);
    }

    private List<INorm> getVBData(String xml, List<TemplateVo> templateData, List list,
                                  BaseSearchControllerApi api) {
        if (TextUtils.check(xml, templateData, list)) {
            List<INorm> data = new ArrayList<>();
            execute(list, item -> executeNon(new ViewNorm(xml, templateData, item,
                    (bean, view) -> api.finishActivity(new SearchVo<>(
                            api.getSearch(), api.getKey(), bean.getMap(),
                            new TypeToken<SearchVo<Map<String, String>>>() {
                            }))), data::add));
            return data;
        }
        return null;
    }

    private void download(String fileName) {
        if (TextUtils.check(fileName)) {
            api().api().uploadFile(fileName);
        }
    }

}