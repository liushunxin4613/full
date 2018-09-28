package cn.com.fullgoal.pt0001.test;

import android.support.annotation.NonNull;

import com.leo.core.iapi.core.IApi;

import java.util.Map;

public interface IProcessAction extends IApi {
    void execute(@NonNull Map<String, Object> map, @NonNull IProcess pro);
}