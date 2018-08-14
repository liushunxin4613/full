package com.ylink.fullgoal.fg;

import android.support.annotation.NonNull;

import com.leo.core.api.core.CoreModel;
import com.leo.core.iapi.core.INorm;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.controllerApi.surface.BaseSearchControllerApi;
import com.ylink.fullgoal.norm.ShareNorm;
import com.ylink.fullgoal.vo.SearchVo;

/**
 * 渠道分摊信息
 */
public class DimenListFg extends CoreModel {

    @Override
    protected INorm createNorm(@NonNull IControllerApi controllerApi) {
        if (controllerApi instanceof BaseSearchControllerApi) {
            BaseSearchControllerApi api = (BaseSearchControllerApi) controllerApi;
            DimenFg fg = (DimenFg) api.decode(api.getKey(), DimenFg.class);
            String code = fg == null ? null : fg.getCode();
            return new ShareNorm(getName(), (bean, view) -> api.finishActivity(
                    new SearchVo<>(api.getSearch(), code, getThis())));
        }
        return null;
    }

    @Override
    protected String[] getSearchFields() {
        return new String[]{getName()};
    }

    @Override
    public String getApiCode() {
        return getCode();
    }

    /**
     * code : 00001
     * name : 账单
     */

    private String code;
    private String name;
    private String dimen;
    private String dimenCode;

    public DimenListFg() {
    }

    public DimenListFg(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDimen() {
        return dimen;
    }

    public void setDimen(String dimen) {
        this.dimen = dimen;
    }

    public String getDimenCode() {
        return dimenCode;
    }

    public void setDimenCode(String dimenCode) {
        this.dimenCode = dimenCode;
    }
}
