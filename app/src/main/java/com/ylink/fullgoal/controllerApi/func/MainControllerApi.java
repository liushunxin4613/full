package com.ylink.fullgoal.controllerApi.func;

import com.ylink.fullgoal.config.Config;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import static com.ylink.fullgoal.config.Config.BACK_PRESSED_INTERVAL;

/**
 * 主功能视图
 */
public class MainControllerApi<T extends SurfaceControllerApi, C> extends SurfaceControllerApi<T, C> {

    private int interval = BACK_PRESSED_INTERVAL;
    private long currentBackPressedTime = 0;

    public MainControllerApi(C controller) {
        super(controller);
    }

    @Override
    public void onBackPressed() {
        if(Config.MAIN){
            if (System.currentTimeMillis() - currentBackPressedTime > interval) {
                currentBackPressedTime = System.currentTimeMillis();
                show("再按一次返回键退出程序");
                return;
            }
        }
        getActivity().finish();//退出
    }

}
