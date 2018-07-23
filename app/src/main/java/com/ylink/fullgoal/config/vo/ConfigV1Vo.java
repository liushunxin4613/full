package com.ylink.fullgoal.config.vo;

import java.util.List;

public class ConfigV1Vo {

    private List<ConfigFileVo> fileList;
    private List<ViewVo> viewList;

    public List<ConfigFileVo> getFileList() {
        return fileList;
    }

    public void setFileList(List<ConfigFileVo> fileList) {
        this.fileList = fileList;
    }

    public List<ViewVo> getViewList() {
        return viewList;
    }

    public void setViewList(List<ViewVo> viewList) {
        this.viewList = viewList;
    }

}