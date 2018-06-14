package com.leo.core.iapi.inter;

import java.util.List;

public interface OnAddListener {

    <R> void onAdd(R obj);

    <R> void onListAdd(List<R> data);

}
