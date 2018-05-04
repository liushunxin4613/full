package com.leo.core.iapi.main;

import android.content.Context;

import com.leo.core.iapi.core.IApi;

public interface Adapter<T extends Adapter> extends IApi{
    Context getContext();
}
