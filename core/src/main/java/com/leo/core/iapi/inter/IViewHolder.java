package com.leo.core.iapi.inter;

import com.leo.core.iapi.core.IApi;
import com.leo.core.iapi.main.IControllerApi;

public interface IViewHolder<T extends IViewHolder> extends IApi{
    IControllerApi controllerApi();
}