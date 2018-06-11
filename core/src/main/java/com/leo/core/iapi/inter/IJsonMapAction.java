package com.leo.core.iapi.inter;

import com.leo.core.iapi.core.IApi;

import java.util.Map;

public interface IJsonMapAction extends IApi {
    void execute(String name, Map<String, Object> map, Object obj);
}
