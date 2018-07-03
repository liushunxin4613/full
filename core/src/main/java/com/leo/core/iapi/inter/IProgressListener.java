package com.leo.core.iapi.inter;

import com.leo.core.iapi.core.IApi;

public interface IProgressListener extends IApi{
    void onLoading(long progress, long total);
}
