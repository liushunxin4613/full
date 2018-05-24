package com.leo.core.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.leo.core.api.main.DataApi;
import com.leo.core.iapi.IAction;
import com.leo.core.util.TextUtils;

public class BasePagerAdapter<T extends View> extends PagerAdapter {

    private DataApi<DataApi, T> api;

    public BasePagerAdapter() {
        api = new DataApi<>();
    }

    public DataApi<DataApi, T> getApi() {
        return api;
    }

    public BasePagerAdapter add(T view) {
        if (view != null) {
            getApi().add(view);
        }
        return this;
    }

    public BasePagerAdapter remove(T view) {
        if (view != null) {
            getApi().remove(view);
        }
        return this;
    }

    public void execute(IAction action, Object... args) {
        if (action != null && !TextUtils.isEmits(args)) {
            action.execute();
        }
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
        T obj = getApi().getItem(position);
        container.addView(obj);
        return obj;
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
