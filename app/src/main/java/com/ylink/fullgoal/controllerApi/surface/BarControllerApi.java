package com.ylink.fullgoal.controllerApi.surface;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import butterknife.Bind;

public class BarControllerApi<T extends BarControllerApi, C> extends SurfaceControllerApi<T, C> {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.back_tv)
    TextView backTv;
    @Bind(R.id.center_tv)
    TextView centerTv;
    @Bind(R.id.right_tv)
    TextView rightTv;

    private String title;

    public BarControllerApi(C controller) {
        super(controller);
    }

    public T setTitle(CharSequence title) {
        this.title = vr(title, CharSequence::toString);
        setText(centerTv, title);
        setText(backTv, title);
        return getThis();
    }

    protected String getTitle(){
        return title;
    }

    public T hideBackIv() {
        setVisibility(View.VISIBLE, centerTv);
        setVisibility(View.INVISIBLE, backTv, backIv);
        return getThis();
    }

    public T setRightTv(CharSequence text, View.OnClickListener listener) {
        setVisibility(View.VISIBLE, rightTv);
        setText(rightTv, text);
        setOnClickListener(rightTv, listener);
        return getThis();
    }

    protected TextView getRightTv() {
        return rightTv;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getActivity().finish();
    }

    @Override
    public void initView() {
        super.initView();
        setOnClickListener(backIv, view -> onBackPressed());
    }

}
