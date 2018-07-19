package com.ylink.fullgoal.config.vo;

import com.leo.core.iapi.inter.ISafety;
import com.leo.core.util.TextUtils;

public class ConfigFileVo implements ISafety {

    /**
     * name : config
     * file : config.json
     * version : 1
     * state : 1
     * hashcode : c03902a7e745312e69cfff53de6efa18
     * url : http://111.231.231.226/uploadfile/20180716/20180716143222V4CFF.json
     */

    private String name;
    private String file;
    private String version;
    private String state;
    private String hashcode;
    private String url;

    @Override
    public boolean isSafety() {
        return TextUtils.check(getName(), getFile(), getVersion(), getState(), getHashcode())
                && TextUtils.isHttpUrl(getUrl());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getHashcode() {
        return hashcode;
    }

    public void setHashcode(String hashcode) {
        this.hashcode = hashcode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}