package com.ylink.fullgoal.controllerApi.surface;

import android.widget.EditText;
import android.widget.TextView;


import com.ylink.fullgoal.R;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.main.MainActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginControllerApi<T extends LoginControllerApi, C> extends SurfaceControllerApi<T, C> {

    public LoginControllerApi(C controller) {
        super(controller);
    }

    @Override
    public void initView() {
        super.initView();
        statusBar(true);
    }

}
