package com.ylink.fullgoal.config;

import android.content.res.XmlResourceParser;

import com.leo.core.api.api.VsApi;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.inter.IPathMsgAction;
import com.leo.core.util.FileUtil;
import com.leo.core.util.LogUtil;
import com.leo.core.util.MD5Util;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.config.vo.ConfigFileVo;
import com.ylink.fullgoal.config.vo.ConfigVo;
import com.ylink.fullgoal.controllerApi.core.ControllerApi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;

import static com.leo.core.util.TextUtils.checkParams;
import static com.leo.core.util.TextUtils.contains;

public class MVCFactory extends VsApi<MVCFactory> {

    private final static boolean LOCAL = true;
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
    private List<Mvc> data;
    private ControllerApi api;

    private MVCFactory() {
        data = new ArrayList<>();
    }

    public MVCFactory init(ControllerApi api) {
        this.api = api;
        return getThis();
    }

    public void start() {
        String[] args = getConfigDir().list();
        api().ii("args", args);
        add(ConfigVo.class, (path, what, msg, vo) -> {
            if (vo.isSuccess()) {
                execute(vo.getConfigList(), fileVo -> onFileVo(args, fileVo));
            }
        });
        add(ResponseBody.class, (path, what, fileName, body) -> {
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
        if (file != null && file.exists() && TextUtils.equals(file.getName(), "config.json")) {
            initConfig(file.getName());
            JsonHelper.newBuilder()
                    .add(Map.class, (parent, map) -> {
                        String hashcode = (String) map.get("hashcode");
                        String url = (String) map.get("url");
                        String fileName = (String) map.get("file");
                        File f = new File(getConfigDir(), fileName);
                        if (!f.exists()) {
                            download(fileName, url);
                        } else {
                            String md5 = MD5Util.getFileMD5(f);
                            api().ii(String.format("%s => md5: %s, hashcode: %s", f.getPath(), md5,
                                    hashcode));
                            if (!TextUtils.equals(md5, hashcode)) {
                                download(fileName, url);
                            }
                        }
                    }, new Node("files"))
                    .execute(getConfig());
        }
    }

    public void onData(String path, String params, List list, IObjAction<List<ViewBean>> action) {
        checkOriginal();
        if (TextUtils.check(path, list, action)) {
            LogUtil.ee("path", path);
            LogUtil.ee("params", params);
            JsonHelper.newBuilder()
                    .add(Map.class, (parent, m) -> {
                        getData().clear();
                        execute(m, (key, view) -> {
                            if (key instanceof String && view instanceof String) {
                                add((String) key, (String) view);
                            }
                        });
                        action.execute(getVBData(list, (String) parent.get("xml")));
                    }, new Node("view"), new Node("data", m -> TextUtils.equals(path, m.get("path"))
                            && checkParams(params, m.get("params"))))
                    .execute(getConfig());
        }
    }

    private boolean check(ConfigFileVo vo) {
        return TextUtils.equals(vo.getName(), "config");
    }

    private void initConfig(String config) {
        if (TextUtils.check(config)) {
            if (LOCAL) {
                this.config = api().getAssetsString(config);
            } else {
                this.config = FileUtil.readFile(new File(getConfigDir(), config));
            }
        }
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

    private List<Mvc> getData() {
        return data;
    }

    private boolean check(Mvc mvc) {
        return TextUtils.check(mvc) && TextUtils.check(mvc.getKey(), mvc.getView());
    }

    private <A> void add(Class<A> root, IPathMsgAction<A> action) {
        api().add(root, action);
    }

    private List<ViewBean> getVBData(List list, String xml) {
        if (TextUtils.check(list, xml)) {
            List<ViewBean> data = new ArrayList<>();
            execute(list, item -> {
                MVCFactory factory = copy();
                if (item instanceof Map) {
                    Map m = (Map) item;
                    execute(m, (key, value) -> {
                        if (key instanceof String && value instanceof String) {
                            factory.update((String) key, (String) value);
                        }
                    });
                }
                executeNon(factory.getViewBean(xml), data::add);
            });
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

    private void checkOriginal() {
        if (copy) {
            throw new RuntimeException("此方法必须使用源对象");
        }
    }

    private void checkCopy() {
        if (!copy) {
            throw new RuntimeException("此方法必须使用复制对象");
        }
    }

    private MVCFactory copy() {
        MVCFactory factory = new MVCFactory();
        factory.copy = true;
        factory.api = api;
        for (Mvc mvc : getData()) {
            if (check(mvc)) {
                factory.getData().add(new Mvc(mvc.getKey(), mvc.getView()));
            }
        }
        return factory;
    }

    private void add(String key, String view) {
        checkOriginal();
        if (TextUtils.check(key, view)) {
            getData().add(new Mvc(key, view));
        }
    }

    private void update(String key, String value) {
        checkCopy();
        if (TextUtils.check(key, getData())) {
            for (Mvc mvc : getData()) {
                if (check(mvc) && TextUtils
                        .equals(mvc.getKey(), key)) {
                    mvc.setValue(value);
                    return;
                }
            }
        }
    }

    private ViewBean getViewBean(String xml) {
        checkCopy();
        if (!TextUtils.isEmpty(getData())) {
            ViewBean bean = new ViewBean(getXmlResourceParser(xml));
            for (Mvc mvc : getData()) {
                if (check(mvc)) {
                    bean.update(mvc.getView(), mvc.getValue());
                }
            }
            return bean;
        }
        return null;
    }

    private XmlResourceParser getXmlResourceParser(String xml) {
        return TextUtils.isEmpty(xml) ? null : api()
                .openFileLayoutXmlPullParser(getConfigDir(), xml);
    }

}