package com.ylink.fullgoal.config.vo;

import java.util.List;

public class ConfigV1Vo {

    private List<ConfigFileVo> files;
    private List<ViewVo> view;

    public List<ConfigFileVo> getFiles() {
        return files;
    }

    public void setFiles(List<ConfigFileVo> files) {
        this.files = files;
    }

    public List<ViewVo> getView() {
        return view;
    }

    public void setView(List<ViewVo> view) {
        this.view = view;
    }

}