package com.ylink.fullgoal.api.item;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.norm.OnClickNorm;

public class LineControllerApi<T extends LineControllerApi, C, P extends OnClickNorm> extends OnClickControllerApi<T, C, P> {

    public LineControllerApi(C controller) {
        super(controller);
    }

    @Override
    public ViewGroup onCreateViewGroup(ViewGroup group, @NonNull View rootView) {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(rootView);
        LayoutInflater.from(getContext()).inflate(R.layout.v_line, layout);
        return layout;
    }

}