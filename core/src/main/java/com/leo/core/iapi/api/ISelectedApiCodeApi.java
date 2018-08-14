package com.leo.core.iapi.api;

public interface ISelectedApiCodeApi extends IApiCodeApi {

    /**
     * 获取选中apiCode
     * @return apiCode
     */
    String getSelectedApiCode();

    /**
     * 设置选中apiCode
     * @param apiCode apiCode
     */
    void setSelectedApiCode(String apiCode);

}
