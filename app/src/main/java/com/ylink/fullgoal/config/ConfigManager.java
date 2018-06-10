package com.ylink.fullgoal.config;

import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.iapi.inter.IPathMsgAction;
import com.leo.core.util.FileUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import java.io.File;

import okhttp3.ResponseBody;

public class ConfigManager{

    public SurfaceControllerApi api;

    public void init(SurfaceControllerApi api){
        this.api = api;
    }

    public SurfaceControllerApi api() {
        return api;
    }

    public <A> void add(Class<A> root, IPathMsgAction<A> action) {
        api().add(root, action);
    }

    public void start(){
        add(ResponseBody.class, (path, what, filePath, body) -> {
            if(TextUtils.check(path, filePath) && path.endsWith(filePath)){
                File file = new File(api().getRootDir("config"), filePath);
                if (FileUtil.writeFile(file.getPath(), body.byteStream())) {
                    api().ii("保存成功", file.getPath());
                }
            }
        });
        api().api().queryConfig();
    }

}