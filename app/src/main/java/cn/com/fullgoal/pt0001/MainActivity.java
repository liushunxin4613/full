package cn.com.fullgoal.pt0001;

import com.leo.core.core.BaseControllerApiActivity;
import com.leo.core.core.BaseControllerApiView;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.api.surface.MainViewControllerApi;
import com.ylink.fullgoal.controllerApi.func.MainControllerApi;

public class MainActivity<T extends MainActivity, C extends IControllerApi> extends BaseControllerApiActivity<T, C> {

    @Override
    public IControllerApi newControllerApi() {
        return new MainControllerApi(this)
                .setRootViewClz(BaseControllerApiView.class)
                .setRootViewClzApi(MainViewControllerApi.class);
    }

}