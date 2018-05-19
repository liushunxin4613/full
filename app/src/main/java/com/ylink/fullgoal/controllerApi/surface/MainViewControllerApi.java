package com.ylink.fullgoal.controllerApi.surface;

import android.support.v4.app.Fragment;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.leo.core.api.BaseRadioFragmentApi;
import com.leo.core.bean.RadioButtonBean;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.api.surface.Camera1ControllerApi;
import com.ylink.fullgoal.api.surface.Camera2ControllerApi;
import com.ylink.fullgoal.api.surface.CountyControllerApi;
import com.ylink.fullgoal.api.surface.MyControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

import static com.ylink.fullgoal.config.Config.MAIN_FRAGMENT_INDEX;

public class MainViewControllerApi<T extends MainViewControllerApi, C> extends SurfaceControllerApi<T, C> {

    @Bind(R.id.vg)
    FrameLayout vg;
    @Bind(R.id.group)
    RadioGroup group;

    public MainViewControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.c_main;
    }

    @Override
    public void initView() {
        super.initView();
        statusBar(true);
        new BaseRadioFragmentApi(getActivity()) {

            @Override
            public RadioGroup getBottomGroup() {
                return group;
            }

            @Override
            public Integer getRadioButtonResId() {
                return R.layout.v_radio_button;
            }

            @Override
            public FrameLayout getContentLayout() {
                return vg;
            }

            @Override
            public int getDefaultFragmentIndex() {
                return MAIN_FRAGMENT_INDEX;
            }

            @Override
            public void onCheckRadioGroup(int position) {
                super.onCheckRadioGroup(position);
                MAIN_FRAGMENT_INDEX = position;
            }

            @Override
            public List<RadioButtonBean> newData() {
                return Arrays.asList(get(R.drawable.home_selected, "首页")
                        , get(R.drawable.county_selected, "项目")
                        , get(R.drawable.my_selected, "我的")
                );
            }

            @Override
            public Fragment getPositionFragment(int position) {
                switch (position) {
                    case 0:
//                        return getFragment(HomeControllerApi.class);
                        return getFragment(Camera2ControllerApi.class);
                    case 1:
                        return getFragment(CountyControllerApi.class);
                    case 2:
                        return getFragment(MyControllerApi.class);
                }
                return null;
            }

        }.init();
    }

}
