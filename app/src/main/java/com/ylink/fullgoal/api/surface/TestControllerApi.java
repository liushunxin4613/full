package com.ylink.fullgoal.api.surface;

import com.leo.core.iapi.inter.IAction;
import com.ylink.fullgoal.config.JsonHelper;
import com.ylink.fullgoal.config.MVCFactory;
import com.ylink.fullgoal.config.Node;
import com.ylink.fullgoal.controllerApi.surface.RecycleBarControllerApi;

import java.util.List;

public class TestControllerApi<T extends TestControllerApi, C> extends RecycleBarControllerApi<T, C> {

    public TestControllerApi(C controller) {
        super(controller);
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("测试列表");
        add(String.class, (path, what, msg, response)
                -> JsonHelper.newBuilder()
                .add(List.class, (parent, list) -> onData(
                        path, msg, list), new Node("list"))
                .execute(response));
        addUI(1000, (IAction) () -> {
            api().val("1");
            api().val("2");
        });
        start();
    }

    private void onData(String path, String params, List list) {
        //manage.onData(path, code, list, data -> initDataAction(d -> d.addAll(data)));
        MVCFactory.getInstance().onData(path, params, list, data -> {
            ii("data", data);
            execute(getLineData(data), this::add);
            notifyDataSetChanged();
        });
    }

}