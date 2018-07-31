package com.leo.core.core.bean;

import android.content.res.XmlResourceParser;
import android.view.View;

import com.leo.core.api.core.ThisApi;
import com.leo.core.iapi.api.IKeywordApi;
import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IApiBean;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.TextUtils;

/**
 * 基础ApiBean
 */
public abstract class CoreApiBean<T extends CoreApiBean, A extends IControllerApi,
        AA extends IControllerApi> extends ThisApi<T> implements IApiBean<A, AA>, IKeywordApi {

    private transient Object apiId = TextUtils.getRandom();
    private transient IControllerApi api;
    private transient boolean enable = true;
    private transient String apiCode;
    private transient String filter;
    private transient XmlResourceParser parser;

    @Override
    public abstract A getControllerApi(AA api);

    @Override
    public abstract Integer getApiType();

    @Override
    public boolean checkApi() {
        return getApiXmlResourceParser() != null || getApiType() != null;
    }

    @Override
    public XmlResourceParser getApiXmlResourceParser() {
        return parser;
    }

    @Override
    public void setApiXmlResourceParser(XmlResourceParser parser) {
        this.parser = parser;
    }

    @Override
    public Object getApiId() {
        return apiId;
    }

    @Override
    public String getApiCode() {
        if (apiCode != null) {
            return apiCode;
        }
        return getDefaultApiCode();
    }

    protected String getDefaultApiCode() {
        return null;
    }

    @Override
    public String getFilter() {
        return filter;
    }

    public T setApiCode(String apiCode) {
        this.apiCode = apiCode;
        return (T) this;
    }

    public T setFilter(String filter) {
        this.filter = filter;
        return (T) this;
    }

    public IControllerApi getApi() {
        return api;
    }

    public void setApi(IControllerApi api) {
        this.api = api;
    }

    public boolean isEnable() {
        return enable;
    }

    public CoreApiBean setEnable(boolean enable) {
        this.enable = enable;
        return this;
    }

    protected <B> B getEnable(B a, B b) {
        return isEnable() ? a : b;
    }

    protected View.OnClickListener getOnBVClickListener(OnBVClickListener<T> bvListener) {
        return bvListener == null ? null : v -> bvListener.onBVClick((T) this, v);
    }

}