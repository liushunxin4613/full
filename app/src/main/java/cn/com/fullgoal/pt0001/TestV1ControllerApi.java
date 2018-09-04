package cn.com.fullgoal.pt0001;

import android.os.Bundle;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.api.config.ParseTypeBean;
import com.ylink.fullgoal.controllerApi.func.MainControllerApi;

import java.util.Map;

public class TestV1ControllerApi<T extends TestV1ControllerApi, C> extends MainControllerApi<T, C> {

    public TestV1ControllerApi(C controller) {
        super(controller);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        add(String.class, (type, baseUrl, path, map, what, msg, field, response) -> {
            Map<String, Object> m = TextUtils.toJSONMap(response);
            if(TextUtils.check(m)){
                ee("userList", m.get("userList"));
            }
        });
        api().queryUserData();
    }

}