package com.leo.core.iapi.inter;

import java.util.Map;

public interface IUrlAction<A, B> {
    B execute(A api, String url, String path, Map<String, String> map);
}