package com.leo.core.iapi;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.leo.core.bean.RadioButtonBean;
import com.leo.core.iapi.main.IControllerApi;

import java.util.List;

public interface IRadioFragmentApi<T extends IRadioFragmentApi, F extends Fragment> extends INotifyDataSetChangedApi<T> {

    /**
     * FragmentActivity
     * @return FragmentActivity
     */
    FragmentActivity getFragmentActivity();

    /**
     * 获取PagerAdapter
     * @return PagerAdapter
     */
    PagerAdapter getPagerAdapter();

    /**
     * new PagerAdapter
     * @return PagerAdapter
     */
    PagerAdapter newPagerAdapter();

    /**
     * 获取R data
     * @return R data
     */
    <R> List<R> getData();

    /**
     * new R data
     * @return R data
     */
    <R> List<R> newData();

    /**
     * 获取R
     * @param args args
     * @param <R> R
     * @return R
     */
    <R> R get(Object... args);

    /**
     * R
     * @param position position
     * @return R
     */
    <R> R getItem(int id, int position);

    /**
     * 按钮位置
     * @param id 按钮id
     * @return 位置
     */
    int getPosition(int id);

    /**
     * 获取default fragment index
     * @return fragment index
     */
    int getDefaultFragmentIndex();

    /**
     * 获取fragment index
     * @return fragment index
     */
    int getFragmentIndex();

    /**
     * 获取fragment count
     * @return fragment count
     */
    int getFragmentCount();

    /**
     * Fragment
     * @param position position
     * @return Fragment
     */
    Fragment create(int position);

    /**
     * RadioGroup控制按钮
     * @return 按钮
     */
    RadioGroup getBottomGroup();

    /**
     * RadioButton ResId
     * @return RadioButton
     */
    Integer getRadioButtonResId();

    /**
     * 被控制页面
     * @return 视图
     */
    FrameLayout getContentLayout();

    /**
     * 获取Fragment
     * @return Fragment
     */
    Fragment getPositionFragment(int position);

    /**
     * 点击按钮
     * @param position 位置
     */
    void onCheckRadioGroup(int position);

}
