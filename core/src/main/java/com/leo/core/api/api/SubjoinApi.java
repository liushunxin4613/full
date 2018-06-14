package com.leo.core.api.api;

import android.graphics.Color;

import com.bigkoo.pickerview.TimePickerView;
import com.leo.core.api.main.HasControllerApi;
import com.leo.core.iapi.api.ISubjoinApi;
import com.leo.core.iapi.main.IControllerApi;

public class SubjoinApi<T extends SubjoinApi> extends HasControllerApi<T> implements ISubjoinApi<T> {

    private TimePickerView timePickerView;
    private TimePickerView.OnTimeSelectListener timeSelectListener;

    public SubjoinApi(IControllerApi controllerApi) {
        super(controllerApi);
    }

    @Override
    public T setOnTimeSelectListener(TimePickerView.OnTimeSelectListener listener) {
        timeSelectListener = listener;
        return getThis();
    }

    @Override
    public TimePickerView timePickerView() {
        if (timePickerView == null) {
            timePickerView = newTimePickerView();
            if (timePickerView == null) {
                throw new NullPointerException("newTimePickerView 不能为空");
            }
        }
        return timePickerView;
    }

    @Override
    public TimePickerView newTimePickerView() {
        if (timeSelectListener == null) {
            throw new NullPointerException("timeSelectListener 不能为空,应当setOnTimeSelectListener");
        }
        return new TimePickerView.Builder(controllerApi().getContext(), timeSelectListener)
                .setType(new boolean[]{true, true, false, false, false, false})
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(15)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
//                .setTitleText("请选择时间")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.GRAY)//取消按钮文字颜色
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//               .isDialog(true)//是否显示为对话框样式
                .build();
    }

}
