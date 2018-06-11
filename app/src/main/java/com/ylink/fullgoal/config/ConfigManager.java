package com.ylink.fullgoal.config;

import com.leo.core.api.api.VsApi;
import com.leo.core.iapi.inter.IPathMsgAction;
import com.leo.core.util.FileUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.config.vo.ConfigVo;
import com.ylink.fullgoal.controllerApi.core.ControllerApi;

import java.io.File;

import okhttp3.ResponseBody;

public class ConfigManager extends VsApi<ConfigManager> {

    public ControllerApi api;

    public ConfigManager init(ControllerApi api) {
        this.api = api;
        return this;
    }

    public ControllerApi api() {
        return api;
    }

    public <A> void add(Class<A> root, IPathMsgAction<A> action) {
        api().add(root, action);
    }

    public void start() {
        add(ConfigVo.class, (path, what, msg, vo) -> {
            if (vo.isSuccess()) {
                execute(vo.getConfigList(), url -> {
                    if (TextUtils.isHttpUrl(url)) {
                        api().api().download(url);
                    }
                });
            }
        });
        add(ResponseBody.class, (path, what, filePath, body) -> {
            if (TextUtils.check(path, filePath) && path.endsWith(filePath)) {
                File file = new File(api().getRootDir("config"), filePath);
                if (FileUtil.writeFile(file.getPath(), body.byteStream())) {
                    api().ii("保存成功", file.getPath());
                }
            }
        });
        api().api().queryConfig();
    }

}