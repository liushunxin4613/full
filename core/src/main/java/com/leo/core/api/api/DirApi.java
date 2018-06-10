package com.leo.core.api.api;

import android.os.Environment;

import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.api.main.HasCoreControllerApi;
import com.leo.core.iapi.api.IDirApi;

import java.io.File;

public class DirApi extends HasCoreControllerApi<DirApi> implements IDirApi {

    public DirApi(CoreControllerApi controllerApi) {
        super(controllerApi);
    }

    @Override
    public boolean hasSdCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    @Override
    public File getRootDir(String dir) {
        return controllerApi().getContext().getExternalFilesDir(dir);
    }

    @Override
    public File getRootDir() {
        return getRootDir("app");
    }

}