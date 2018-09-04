package cn.com.fullgoal.pt0001;

import com.leo.core.core.BaseControllerApiActivity;
import com.leo.core.core.BaseControllerApiView;
import com.leo.core.iapi.main.IControllerApi;

public class TestActivity<T extends TestActivity, C extends IControllerApi> extends BaseControllerApiActivity<T, C> {

    @Override
    public IControllerApi newControllerApi() {
        return new TestV1ControllerApi(this);
    }

}