package com.ylink.fullgoal.config;

import android.content.res.XmlResourceParser;

import com.google.gson.reflect.TypeToken;
import com.leo.core.api.api.VsApi;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.inter.IPathMsgAction;
import com.leo.core.util.FileUtil;
import com.leo.core.util.LogUtil;
import com.leo.core.util.MD5Util;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.config.vo.ConfigV1Vo;
import com.ylink.fullgoal.config.vo.TemplateVo;
import com.ylink.fullgoal.controllerApi.core.ControllerApi;
import com.ylink.fullgoal.controllerApi.surface.BaseSearchControllerApi;
import com.ylink.fullgoal.fg.DataFg;
import com.ylink.fullgoal.vo.SearchVo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.leo.core.util.TextUtils.checkParams;

public class MVCFactoryV1 extends VsApi<MVCFactoryV1> {

    private final static boolean LOCAL = true;
    private final static String FILTER[] = {};
    private final static String DIR = "configV1";
    private final static String ROOT_FILE = "config.json";

    private static MVCFactoryV1 instance;

    public static MVCFactoryV1 getInstance() {
        if (instance == null) {
            synchronized (MVCFactoryV1.class) {
                if (instance == null) {
                    instance = new MVCFactoryV1();
                }
            }
        }
        return instance;
    }

    private boolean copy;
    private String config;
    private ConfigV1Vo vo;
    private ControllerApi api;

    public MVCFactoryV1 init(ControllerApi api) {
        this.api = api;
        return getThis();
    }

    public void start() {
        String[] args = getConfigDir().list();
        api().ii(String.format("dir: %s, list: %s", getConfigDir().getPath(),
                LogUtil.getLog((Object) args)));
        add(DataFg.class, byte[].class, (fieldName, path, what, fileName, bytes) -> {
            if (TextUtils.check(path, fileName)) {
                File file = new File(getConfigDir(), fileName);
                if (FileUtil.writeFile(file.getPath(), bytes)) {
                    api().ii("保存成功", file.getPath());
                    onFile(file);
                }
            }
        });
        download(ROOT_FILE);
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
                download(vo.getFile());
            } else {
                String md5 = MD5Util.getFileMD5(file);
                api().ii(String.format("%s => md5: %s, hashcode: %s", file.getPath(), md5,
                        vo.getHashcode()));
                if (!TextUtils.equals(md5, vo.getHashcode())) {
                    download(vo.getFile());
                }
            }
        });
    }

    public void onData(String path, String params, List list, BaseSearchControllerApi api,
                       IObjAction<List<ViewBean>> action) {
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

    private File getConfigDir() {
        return api().getRootDir(DIR);
    }

    private <A> void add(Class<A> root, IPathMsgAction<A> action) {
        api().add(root, action);
    }

    private <A, B> void add(Class<A> root, Class<B> clz, IPathMsgAction<B> action) {
        api().add(root, clz, action);
    }

    private List<ViewBean> getVBData(String xml, List<TemplateVo> templateData, List list,
                                     BaseSearchControllerApi api) {
        if (TextUtils.check(xml, templateData, list)) {
            List<ViewBean> data = new ArrayList<>();
            execute(list, item -> executeNon(new ViewBean(xml, getXmlResourceParser(xml),
                    templateData, item, (bean, view) -> api.finishActivity(new SearchVo<>(
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

    private XmlResourceParser getXmlResourceParser(String xml) {
        return TextUtils.isEmpty(xml) ? null : api()
                .openFileLayoutXmlPullParser(getConfigDir(), xml);
    }

}