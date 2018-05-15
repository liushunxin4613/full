package com.leo.core.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.leo.core.api.main.DataApi;

public class BasePagerAdapter<T extends View> extends PagerAdapter {

    private DataApi<DataApi, T> api;

    public BasePagerAdapter() {
        api = new DataApi<>();
    }

    public DataApi<DataApi, T> getApi() {
        return api;
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
        container.removeView(getApi().getItem(position));
    }

}
