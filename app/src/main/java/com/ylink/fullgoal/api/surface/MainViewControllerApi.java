package com.ylink.fullgoal.api.surface;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.reflect.TypeToken;
import com.leo.core.bean.BaseBean;
import com.leo.core.iapi.IRunApi;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.bean.ItemBean;
import com.ylink.fullgoal.controllerApi.surface.RecycleControllerApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainViewControllerApi<T extends MainViewControllerApi, C> extends RecycleControllerApi<T, C> {

    public MainViewControllerApi(C controller) {
        super(controller);
    }

    @Override
    public void initView() {
        super.initView();
        BaseBean<ItemBean> root = decode(getAssetsString("jsonApi.json"), new TypeToken<BaseBean<ItemBean>>() {
        }.getType());
        e("root", root);
        if (root != null) {
            Map<String, IRunApi<ItemBean>> map = new HashMap<>();
            map.put("user", obj -> {
                obj.setDetail("张三");
            });
            map.put("depart", obj -> {
                obj.setDetail("人力资源部");
            });
            map.put("paperType", obj -> {
                obj.setOnClickListener(v -> {
                    show(obj.getNickname());
                });
            });
            replaceIBAll(root.getList(), map).notifyDataSetChanged();
        }
    }

    private T replaceIBAll(List<ItemBean> data, Map<String, IRunApi<ItemBean>> map) {
        if (!TextUtils.isEmpty(data) && !TextUtils.isEmpty(map)) {
            replaceApiAll(data, true, obj -> {
                if (!TextUtils.isEmpty(obj.getName())) {
                    IRunApi<ItemBean> api = map.get(obj.getName());
                    if (api != null) {
                        api.execute(obj);
                    }
                }
            });
        }
        return getThis();
    }

}
