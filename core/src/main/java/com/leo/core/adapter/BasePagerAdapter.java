package com.leo.core.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.leo.core.api.api.VsApi;
import com.leo.core.api.main.DataApi;
import com.leo.core.iapi.inter.IAction;
import com.leo.core.iapi.inter.IReturnAction;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.TextUtils;

public class BasePagerAdapter<A extends IControllerApi> extends PagerAdapter {

    private DataApi<DataApi, A> api;
    private VsApi vsApi;

    public BasePagerAdapter() {
        api = new DataApi<>();
        vsApi = new VsApi();
    }

    private <AA, BB> BB vr(AA aa, IReturnAction<AA, BB> ab) {
        return (BB) vsApi.vr(aa, ab);
    }

    public DataApi<DataApi, A> getApi() {
        return api;
    }

    public BasePagerAdapter add(A api) {
        if (api != null) {
            getApi().add(api);
        }
        return this;
    }

    public BasePagerAdapter remove(A api) {
        if (api != null) {
            getApi().remove(api);
        }
        return this;
    }

    public void execute(IAction action, Object... args) {
        if (action != null && TextUtils.isEmits(args)) {
            action.execute();
        }
    }

    public A getItemApi(int position) {
        return getApi().getItem(position);
    }

    private View getItemView(int position) {
        return vr(getItemApi(position), IControllerApi::getRootView);
    }

    @Override
    public int getCount() {
        return getApi().getCount();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = getItemView(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

}