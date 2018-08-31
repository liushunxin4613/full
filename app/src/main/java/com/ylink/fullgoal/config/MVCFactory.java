package com.ylink.fullgoal.config;

import com.google.gson.reflect.TypeToken;
import com.leo.core.api.api.VsApi;
import com.leo.core.iapi.core.INorm;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.inter.IPathMsgAction;
import com.leo.core.util.MD5Util;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.config.vo.ConfigV1Vo;
import com.ylink.fullgoal.config.vo.TemplateVo;
import com.ylink.fullgoal.controllerApi.core.ControllerApi;
import com.ylink.fullgoal.controllerApi.surface.BaseSearchControllerApi;
import com.ylink.fullgoal.db.table.Text;
import com.ylink.fullgoal.fg.DataFg;
import com.ylink.fullgoal.norm.ViewNorm;
import com.ylink.fullgoal.vo.SearchVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.leo.core.util.TextUtils.checkParams;

public class MVCFactory extends VsApi<MVCFactory> {

    private final static String DIR = "config";
    private final static String ROOT_FILE = "config_1";
    private final static String ROOT_FILE_NAME = "config_1.json";

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
                String response = new String(bytes);
                String hashCode = MD5Util.getStringMD5(response);
                response = TextUtils.toJsonString(response);
                switch (fileName) {
                    case ROOT_FILE_NAME:
                        Text.sav(fileName, Text.TYPE_VIEW_CONFIG, hashCode, response);
                        String config = Text.queryJson(ROOT_FILE, Text.TYPE_VIEW_CONFIG);
                        if (TextUtils.check(config)) {
                            setConfig(config);
                            setVo((ConfigV1Vo) api().decode(getConfig(), ConfigV1Vo.class));
                            initConfigVo();
                        }
                        break;
                    default:
                        Text.savViewJson(fileName, hashCode, response);
                        break;
                }
            }
        });
        download(ROOT_FILE_NAME);
    }

    private void initConfigVo() {
        if (!TextUtils.check(getVo())) {
            return;
        }
        execute(getVo().getFileList(), vo -> {
            String hashcode = Text.hashcode(vo.getFile());
            if (!TextUtils.equals(hashcode, vo.getHashcode())) {
                api().ii(String.format("%s => md5: %s, hashcode: %s", vo.getFile(),
                        hashcode, vo.getHashcode()));
                download(vo.getFile());
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
                    action.execute(getVBData(vo.getName(), vo.getList(), list, api));
                    return true;
                }
                return false;
            });
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

    public void setConfig(String config) {
        this.config = config;
    }

    private ControllerApi api() {
        return api;
    }

    private <A, B> void add(Class<A> root, Class<B> clz, IPathMsgAction<B> action) {
        api().add(root, clz, action);
    }

    private List<INorm> getVBData(String name, List<TemplateVo> templateData, List list,
                                  BaseSearchControllerApi api) {
        if (TextUtils.check(name, templateData, list)) {
            List<INorm> data = new ArrayList<>();
            execute(list, item -> executeNon(new ViewNorm(name, templateData, item,
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