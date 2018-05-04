package com.ylink.fullgoal.controllerApi.surface;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;

import butterknife.Bind;

public class RecycleBarControllerApi<T extends RecycleBarControllerApi, C> extends RecycleControllerApi<T, C> {

    @Bind(R.id.bar_content_tv)
    TextView barContentTv;
    @Bind(R.id.bar_back_ib)
    ImageButton barBackIb;
    @Bind(R.id.bar_right_ib)
    ImageButton barRightIb;
    @Bind(R.id.bar_right_tv)
    Button barRightTv;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    public RecycleBarControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getRootViewResId() {
        return R.layout.l_bar_recycle;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public T setTitle(CharSequence title){
        setViewText(barContentTv, title);
        return getThis();
    }

    public T hideBackIv(){
        setVisibility(barBackIb, View.INVISIBLE);
        return getThis();
    }

    public T setRightTv(CharSequence text){
        setViewText(barRightTv, text);
        return getThis();
    }

    public T setOnClickListener(String name, View.OnClickListener listener){
        if(!TextUtils.isEmpty(name)){
            switch (name){
                case "back":
                    setOnClickListener(barBackIb, listener);
                    break;
                case "rightTv":
                    setOnClickListener(barRightTv, listener);
                    break;
                case "rightIv":
                    setOnClickListener(barRightIb, listener);
                    break;
            }
        }
        return getThis();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getActivity().finish();
    }

    @Override
    public void initView() {
        super.initView();
        setOnClickListener("back", view -> onBackPressed());
    }

}
