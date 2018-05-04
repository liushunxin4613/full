package com.leo.core.iapi;

import com.bigkoo.pickerview.TimePickerView;
import com.leo.core.iapi.main.IHasControllerApi;

public interface ISubjoinApi<T extends ISubjoinApi> extends IHasControllerApi<T> {

    T setOnTimeSelectListener(TimePickerView.OnTimeSelectListener listener);

    TimePickerView timePickerView();

    TimePickerView newTimePickerView();

}
