package com.leo.core.core;

import com.leo.core.api.api.VsApi;
import com.leo.core.iapi.api.ISelectedApiCodeApi;

public class SelectedApiCodeApi<T extends SelectedApiCodeApi> extends VsApi<T> implements ISelectedApiCodeApi {

    private transient String selectedApiCode;

    @Override
    public String getApiCode() {
        return null;
    }

    @Override
    public String getSelectedApiCode() {
        return selectedApiCode;
    }

    @Override
    public void setSelectedApiCode(String apiCode) {
        this.selectedApiCode = apiCode;
    }

}