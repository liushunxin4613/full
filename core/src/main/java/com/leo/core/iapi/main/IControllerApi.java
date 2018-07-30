package com.leo.core.iapi.main;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.leo.core.iapi.inter.IAction;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.core.IAttachApi;

import java.io.File;
import java.lang.reflect.Type;

/**
 * 控制器
 *
 * @param <T> 自身
 * @param <C> 控制器
 */
public interface IControllerApi<T extends IControllerApi, C> extends INewApi, IAttachApi<T, C>, IOnCom {

    /**
     * 初始化controller
     *
     * @param controller controller
     */
    void initController(C controller);

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
     * 是否是Application
     */
    boolean isApplication();

    /**
     * 设置activity
     */
    T setActivity(Activity activity);

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
     * 获取FragmentActivity
     *
     * @return FragmentActivity
     */
    <D extends Dialog> D getDialog();

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
     * 是否绑定的是dialog上
     *
     * @return true是, false否
     */
    boolean isDialog();

    /**
     * 显示dialog
     *
     * @return 本身
     */
    T dialogShow();

    /**
     * 显示dialog
     *
     * @return 本身
     */
    T dismiss();

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
     * 返回根视图节点ID
     *
     * @return 根视图节点ID
     */
    @LayoutRes
    Integer getDefRootViewResId();

    /**
     * 设置resId
     *
     * @return 本身
     */
    T setRootViewResId(Integer resId);

    /**
     * 设置rootView
     *
     * @return 本身
     */
    T setRootView(View rootView);

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
     * view controllerApi
     *
     * @return IControllerApi
     */
    IControllerApi getViewControllerApi();

    /**
     * 返回根视图xml文件
     *
     * @return XmlResourceParser
     */
    XmlResourceParser getRootXmlResourceParser();

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
     * 返回根视图container
     * @return container
     */
    ViewGroup getRootContainer();

    /**
     * 设置根视图container
     * @param container container
     * @return 本身
     */
    T setRootContainer(ViewGroup container);

    /**
     * 返回根视图xml文件
     *
     * @param parser parser
     * @return 本身
     */
    T setRootXmlResourceParser(XmlResourceParser parser);

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
    void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                    @NonNull int[] grantResults);

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
    <B> T onBindViewHolder(B bean, int position);

    /**
     * 测量view的大学
     *
     * @param widthMeasureSpec  横向长度
     * @param heightMeasureSpec 纵向长度
     */
    void onMeasure(int widthMeasureSpec, int heightMeasureSpec);

    /**
     * 给view以及子view测量
     *
     * @param changed 表示view有了新的尺寸和位置
     * @param l       相对于父view的左侧距离
     * @param t       相对于父view的上侧距离
     * @param r       相对于父view的右侧距离
     * @param b       相对于父view的底侧距离
     */
    void onLayout(boolean changed, int l, int t, int r, int b);

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
     * @param tag 资源tag
     * @param <V>   view泛型
     * @return view
     */
    <V extends View> V findViewWithTag(Object tag);

    /**
     * 关闭activity
     *
     * @param obj obj
     * @return 本身
     */
    T finishActivity(Object obj);

    /**
     * 获取信息
     *
     * @param args args
     * @param <B>  <B>
     * @return 对象
     */
    <B> B getFinish(Type... args);

    /**
     * 手势监听
     */
    boolean onTouchEvent(MotionEvent event);

    /**
     * 启动activity
     * @param intent intent
     * @param requestCode requestCode
     * @param options options
     */
    void onStartActivityForResult(Intent intent, int requestCode, @Nullable Bundle options);

    //自定义的

    /**
     * 初始化添加action
     */
    void initAddAction();

    /**
     * 布局
     */
    LayoutInflater inflater();

    /**
     * 获取布局文件
     * @param dir dir
     * @param xml xml
     * @return XmlResourceParser
     */
    XmlResourceParser openFileLayoutXmlPullParser(File dir, String xml);

    /**
     * 获取布局文件
     * @param xml xml
     * @return XmlResourceParser
     */
    XmlResourceParser openAssetsLayoutXmlPullParser(String xml);

    /**
     * 更新数据
     */
    void notifyDataChanged();

    /**
     * 执行
     */
    T executeBundle(IObjAction<Bundle> api);

    /**
     * 设置状态栏颜色深浅
     *
     * @param dark true为深,false为浅
     * @return 本身
     */
    T statusBar(boolean dark);

    /**
     * 执行
     * @param action action
     * @return 本身
     */
    T execute(IAction action);

    /**
     * 执行数据
     *
     * @param obj    obj
     * @param clz    clz
     * @param action action
     * @param <B>    <B>
     * @return 本身
     */
    <B> T execute(Object obj, Class<B> clz, IObjAction<B> action);

    /**
     * 执行数据
     *
     * @param obj    obj
     * @param token  token
     * @param action action
     * @param <B>    <B>
     * @return 本身
     */
    <B> T execute(Object obj, TypeToken<B> token, IObjAction<B> action);

}