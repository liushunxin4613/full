package com.ylink.fullgoal.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.leo.core.util.DisneyUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChooseDialog extends Dialog {

    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.wheel_view)
    WheelView wheelView;
    @Bind(R.id.finish_bt)
    Button finishBt;

    private String title;
    private List<String> items;
    private WheelView.OnWheelViewListener onWheelViewListener;

    public ChooseDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
    }

    public ChooseDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public ChooseDialog setItems(List<String> items) {
        this.items = items;
        return this;
    }

    public ChooseDialog setOnWheelViewListener(WheelView.OnWheelViewListener listener) {
        this.onWheelViewListener = listener;
        return this;
    }

    public ChooseDialog setSeletion(int position){
        if(position < TextUtils.count(items)){
            wheelView.setSeletion(position);
        }
        return this;
    }

    public WheelView getWheelView() {
        return wheelView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_dialog_wheel_view);
        ButterKnife.bind(this);
        Window dialogWindow = getWindow();
        if (dialogWindow != null) {
            dialogWindow.setGravity(Gravity.BOTTOM);
            dialogWindow.setWindowAnimations(R.style.DialogStyle); // 添加动画
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = DisneyUtil.getScreenDispaly().getX();
            dialogWindow.setAttributes(lp);
        }
        finishBt.setOnClickListener(v -> dismiss());
        nameTv.setText(title);
        wheelView.setItems(items);
        wheelView.setOnWheelViewListener(onWheelViewListener);
    }

}
