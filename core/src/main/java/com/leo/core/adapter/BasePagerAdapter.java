package com.leo.core.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.leo.core.api.main.DataApi;
import com.leo.core.iapi.inter.IAction;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.TextUtils;

public class BasePagerAdapter<T extends IControllerApi> extends PagerAdapter {

    private DataApi<DataApi, T> api;

    public BasePagerAdapter() {
        api = new DataApi<>();
    }

    public DataApi<DataApi, T> getApi() {
        return api;
    }

    public BasePagerAdapter add(T api) {
        if (api != null) {
            getApi().add(api);
        }
        return this;
    }

    public BasePagerAdapter remove(T api) {
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

    public T getItemApi(int position) {
        return getApi().getItem(position);
    }

    public View getItemView(int position) {
        return getItemApi(position).getRootView();
    }

    @Override
    public int getCount() {
        return getApi().getCount();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = getItemView(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
