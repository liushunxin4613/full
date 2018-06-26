package com.ylink.fullgoal.bean;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.leo.core.iapi.api.IBindBeanApi;
import com.leo.core.iapi.main.IBindControllerApi;
import com.leo.core.util.SoftInputUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.bi.SearchWaterfallBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.controllerApi.surface.RecycleControllerApi;

import java.util.ArrayList;
import java.util.List;

public class SearchWaterfallBean extends ApiBean<SearchWaterfallBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, SearchWaterfallBean> newDefApi() {
        return new SearchWaterfallBi();
    }

    private List<String> data;
    private List<TvSBean> beanData;
    private transient ImageView closeIv;
    private transient IBindBeanApi<RecycleControllerApi, String> textApi;

    public SearchWaterfallBean(String name, List<String> data,
                               IBindBeanApi<RecycleControllerApi, String> textApi) {
        super(name);
        setData(data);
        setTextApi(textApi);
    }

    public List<String> getData() {
        return data;
    }

    public List<TvSBean> getBeanData() {
        return beanData;
    }

    public void setData(List<String> data) {
        this.data = data;
        if (!TextUtils.isEmpty(data)) {
            this.beanData = new ArrayList<>();
            for (String item : this.data) {
                this.beanData.add(new TvSBean(item));
            }
        } else {
            this.beanData = null;
        }
    }

    public ImageView getCloseIv() {
        return closeIv;
    }

    public SearchWaterfallBean setCloseIv(ImageView closeIv) {
        this.closeIv = closeIv;
        if (closeIv != null) {
            closeIv.setOnClickListener(v -> {
                if (getTextView() != null) {
                    getTextView().setText(null);
                }
            });
        }
        return this;
    }

    public IBindBeanApi<RecycleControllerApi, String> getTextApi() {
        return textApi;
    }

    public SearchWaterfallBean setTextApi(IBindBeanApi<RecycleControllerApi, String> textApi) {
        this.textApi = textApi;
        return this;
    }

    @Override
    public void setTextView(TextView textView) {
        super.setTextView(textView);
        if (textView instanceof EditText) {
            textView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence text, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence text, int start, int before, int count) {
                    if (getCloseIv() != null) {
                        getCloseIv().setVisibility(count > 0 ? View.VISIBLE : View.INVISIBLE);
                    }
                    if (getTextApi() != null && getApi() instanceof RecycleControllerApi) {
                        getTextApi().onItem((RecycleControllerApi) getApi(), text.toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable edit) {
                }
            });
            textView.setOnEditorActionListener((v, actionId, event) -> {
                switch (actionId) {
                    default:
                        return false;
                    case EditorInfo.IME_ACTION_SEARCH://搜索
                        SoftInputUtil.hidSoftInput(v);
                        if (getTextApi() != null && getApi() instanceof RecycleControllerApi) {
                            getTextApi().onItem((RecycleControllerApi) getApi(), v.getText().toString());
                        }
                        return true;
                }
            });
        }
    }
}
