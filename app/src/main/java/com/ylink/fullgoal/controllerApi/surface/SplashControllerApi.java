package com.ylink.fullgoal.controllerApi.surface;

import android.widget.ImageView;
import android.widget.TextView;

import com.leo.core.iapi.inter.IAction;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import butterknife.Bind;
import cn.com.fullgoal.pt0001.MainActivity;

import static com.ylink.fullgoal.config.Config.TIME_SPLASH;

public class SplashControllerApi<T extends SplashControllerApi, C> extends SurfaceControllerApi<T, C> {

    @Bind(R.id.time_tv)
    TextView timeTv;
    @Bind(R.id.content_iv)
    ImageView contentIv;

    private int time = TIME_SPLASH;//引导时间
    private int uiCode;

    public SplashControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.c_splash;
    }

    @Override
    public void initView() {
        super.initView();
        setOnClickListener(contentIv, v -> skip());
        uiCode = addUI(0, (IAction) () -> {
            setViewText(timeTv, formatString("%ds", time));
            if (time <= 0) {
                skip();
            }
            time--;
        }, 1000, time);
        start();
    }

    /**
     * 跳过
     */
    private void skip(){
        if (isLogin()) {
            startFinishActivity(MainActivity.class);
        }
        cancel(uiCode);
    }

}
