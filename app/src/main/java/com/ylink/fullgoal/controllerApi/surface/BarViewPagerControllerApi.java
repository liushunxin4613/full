package com.ylink.fullgoal.controllerApi.surface;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ylink.fullgoal.R;

import butterknife.Bind;

public class BarViewPagerControllerApi<T extends BarViewPagerControllerApi, C> extends ViewPagerControllerApi<T, C> {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.back_tv)
    TextView backTv;
    @Bind(R.id.center_tv)
    TextView centerTv;
    @Bind(R.id.right_tv)
    TextView rightTv;

    public BarViewPagerControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_bar_view_pager;
    }

    public T setTitle(CharSequence title) {
        setText(centerTv, title);
        setText(backTv, title);
        return getThis();
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

    public TextView getRightTv() {
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
