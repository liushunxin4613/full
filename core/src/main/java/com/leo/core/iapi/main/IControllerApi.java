package com.leo.core.iapi.main;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleableRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leo.core.iapi.IAction;
import com.leo.core.iapi.IBindBeanCallback;
import com.leo.core.iapi.core.IAttachApi;

/**
 * 控制器
 *
 * @param <T> 自身
 * @param <C> 控制器
 */
public interface IControllerApi<T extends IControllerApi, C> extends INewApi, IAttachApi<T, C> {

    /**
     * 初始化controller
     *
     * @param controller controller
     * @return 本身
     */
    T initController(C controller);

    /**
     * 获取Controller
     *
     * @return Controller
     */
    C getController();

    /**
     * 获取Activity
     *
     * @return Activity
     */
    Context getContext();

    /**
     * 获取Application
     *
     * @return Application
     */
    Application getApplication();

    /**
     * 获取Activity
     *
     * @return Activity
     */
    Activity getActivity();

    /**
     * 获取FragmentActivity
     *
     * @return FragmentActivity
     */
    FragmentActivity getFragmentActivity();

    /**
     * 是否绑定的是Activity上
     *
     * @return true是, false否
     */
    boolean isActivity();

    /**
     * 获取Fragment
     *
     * @return Fragment
     */
    Fragment getFragment();

    /**
     * 是否绑定的是Fragment上
     *
     * @return true是, false否
     */
    boolean isFragment();

    /**
     * 获取ApiBv
     *
     * @return ApiBv
     */
    Adapter getAdapter();

    /**
     * 是否是ApiBv
     *
     * @return true是, false否
     */
    boolean isAdapter();

    /**
     * 更新UI线程
     *
     * @param action action
     * @return 本身
     */
    T runOnUiThread(IAction action);

    /**
     * 更新UI线程
     *
     * @param action action
     * @return 本身
     */
    T runOnUiThread(IAction action, long delayMillis);

    /**
     * 更新非UI线程
     *
     * @param action action
     * @return 本身
     */
    T runOnOtherThread(IAction action);

    /**
     * 返回根视图节点ID
     *
     * @return 根视图节点ID
     */
    @LayoutRes
    Integer getRootViewResId();

    /**
     * 设置resId
     *
     * @return resId
     */
    T setRootViewResId(Integer resId);

    /**
     * 样式资源数组
     *
     * @return 样式资源数组
     */
    @StyleableRes
    int[] getStyleableRes();

    /**
     * 初始化样式
     *
     * @param a 样式
     */
    void initStyleable(TypedArray a);

    /**
     * 是否绑定的是View上
     *
     * @return true是, false否
     */
    boolean isView();

    /**
     * 返回根视图
     *
     * @return 根视图
     */
    View getRootView();

    /**
     * 返回根视图Class
     *
     * @return Class
     */
    Class<? extends View> getRootViewClz();

    /**
     * 返回根视图Class Api
     *
     * @return Class Api
     */
    Class<? extends IControllerApi> getRootViewClzApi();

    /**
     * 设置根视图Class
     *
     * @param clz clz
     * @return 本身
     */
    T setRootViewClz(Class<? extends View> clz);

    /**
     * 置根视图Class Api
     *
     * @param clz clz
     * @return 本身
     */
    T setRootViewClzApi(Class<? extends IControllerApi> clz);

    /**
     * 附加context
     *
     * @param context 上下文
     */
    void onAttach(Context context);

    /**
     * 创建时调用
     *
     * @param savedInstanceState 存储信息
     */
    @CallSuper
    void onCreate(@Nullable Bundle savedInstanceState);

    /**
     * 初始化
     *
     * @param savedInstanceState 视图数据
     */
    void init(Bundle savedInstanceState);

    /**
     * 创建View时调用
     *
     * @param inflater           布局管理器
     * @param container          父容器
     * @param savedInstanceState 存储信息
     */
    @Nullable
    View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * onCreateView 后立即调用
     *
     * @param view               view
     * @param savedInstanceState 存储信息
     */
    void onViewCreated(View view, @Nullable Bundle savedInstanceState);

    /**
     * 初始化视图
     */
    void initView();

    /**
     * 初始化数据
     */
    void initData();

    /**
     * activity 创建时调用
     *
     * @param savedInstanceState 存储信息
     */
    @CallSuper
    void onActivityCreated(@Nullable Bundle savedInstanceState);

    /**
     * 可见时调用
     */
    @CallSuper
    void onStart();

    /**
     * 获得焦点时调用
     */
    @CallSuper
    void onResume();

    /**
     * 未销毁且重新启用时调用
     */
    @CallSuper
    void onRestart();

    /**
     * 失去焦点时调用
     */
    @CallSuper
    void onPause();

    /**
     * 不可见时调用
     */
    @CallSuper
    void onStop();

    /**
     * 销毁view时调用
     */
    @CallSuper
    void onDestroyView();

    /**
     * 销毁Fragment时调用
     */
    @CallSuper
    void onDestroy();

    /**
     * 解除附加时调用
     */
    @CallSuper
    void onDetach();

    /**
     * 设置是否隐藏
     *
     * @param isVisibleToUser 是否隐藏
     */
    void setUserVisibleHint(boolean isVisibleToUser);

    /**
     * 菜单是否隐藏
     *
     * @param menuVisible 是否隐藏
     */
    void setMenuVisibility(boolean menuVisible);

    /**
     * activity result
     *
     * @param requestCode requestCode
     * @param resultCode  resultCode
     * @param data        data
     */
    void onActivityResult(int requestCode, int resultCode, Intent data);

    /**
     * new intent 时调用
     *
     * @param intent intent
     */
    void onNewIntent(Intent intent);

    /**
     * 查找资源
     *
     * @param resId 资源id
     * @param <V>   view泛型
     * @return view
     */
    <V extends View> V findViewById(@IdRes int resId);

    /**
     * 查找资源
     *
     * @param resId 资源id
     * @param <V>   view泛型
     * @return view
     */
    <V extends View> V findViewById(View rootView, @IdRes int resId);

    /**
     * 按键监听
     *
     * @param keyCode keyCode
     * @param event   event
     * @return 按键监听处理
     */
    boolean onKeyDown(int keyCode, KeyEvent event);

    /**
     * 后退监听
     */
    void onBackPressed();

    /**
     * app旋转
     *
     * @param newConfig newConfig
     */
    void onConfigurationChanged(Configuration newConfig);

    /**
     * 权限请求处理
     */
    void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults);

    /**
     * 终止时调用,但异常除外
     */
    @CallSuper
    void onTerminate();

    /**
     * 资源匮乏时调用
     *
     * @param level 级别
     */
    @CallSuper
    void onTrimMemory(int level);

    /**
     * 清理内存时处理
     */
    @CallSuper
    void onLowMemory();

    /**
     * 绑定上下文
     *
     * @param base 上下文
     */
    void attachBaseContext(Context base);

    /**
     * 执行单个holder
     *
     * @param bean     bean
     * @param position position
     * @param <B>      B
     */
    <B> void onBindViewHolder(B bean, int position);

    //自定义的

    /**
     * 加入BindBeanCallback
     *
     * @param callback callback
     * @param <B>      B
     * @return 本身
     */
    <B> T putBindBeanCallback(Class<B> clz, IBindBeanCallback<B> callback);

    /**
     * 设置状态栏颜色深浅
     *
     * @param dark true为深,false为浅
     * @return 本身
     */
    T statusBar(boolean dark);

}
