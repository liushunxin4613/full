package com.ylink.fullgoal.fg;

import android.support.annotation.NonNull;

import com.leo.core.api.core.CoreModel;
import com.leo.core.iapi.core.INorm;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.controllerApi.surface.RecycleControllerApi;
import com.ylink.fullgoal.norm.BankNorm;

public class BankFg extends CoreModel {

    @Override
    protected INorm createNorm(@NonNull IControllerApi controllerApi) {
        if (controllerApi instanceof RecycleControllerApi) {
            RecycleControllerApi api = (RecycleControllerApi) controllerApi;
            return new BankNorm(getBankNo(), getBankOrignalName(), (bean, view) -> {
                api.getActivity().finish();
                api.api().submitBankV1(getBankNo(), getBankName());
            });
        }
        return null;
    }

    /**
     * bankName : 中国工商银行1
     * bankNo : 9556222518655558412
     * bankOrignalName : 中国工商银行
     */

    private String bankNo;
    private String bankName;
    private String bankOrignalName;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankOrignalName() {
        return bankOrignalName;
    }

    public void setBankOrignalName(String bankOrignalName) {
        this.bankOrignalName = bankOrignalName;
    }

}