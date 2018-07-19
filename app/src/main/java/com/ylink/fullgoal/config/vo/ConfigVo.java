package com.ylink.fullgoal.config.vo;

import java.util.List;

public class ConfigVo extends BaseVo {

    private List<ConfigFileVo> configList;

    public List<ConfigFileVo> getConfigList() {
        return configList;
    }

    public void setConfigList(List<ConfigFileVo> configList) {
        this.configList = configList;
    }

}