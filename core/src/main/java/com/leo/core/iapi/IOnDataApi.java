package com.leo.core.iapi;

import java.util.List;

public interface IOnDataApi<T> {
    void onData(List<T> data);
}
