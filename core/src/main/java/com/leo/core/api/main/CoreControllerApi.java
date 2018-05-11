package com.leo.core.api.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.leo.core.api.core.AttachApi;
import com.leo.core.core.BaseControllerApiView;
import com.leo.core.iapi.IAction;
import com.leo.core.iapi.IActionApi;
import com.leo.core.iapi.IBindBeanCallback;
import com.leo.core.iapi.ICallbackApi;
import com.leo.core.iapi.IConfigApi;
import com.leo.core.iapi.IDataApi;
import com.leo.core.iapi.IDataTypeApi;
import com.leo.core.iapi.IDecodeApi;
import com.leo.core.iapi.IFileApi;
import com.leo.core.iapi.IGalleryApi;
import com.leo.core.iapi.ILoadImageApi;
import com.leo.core.iapi.IMD5Api;
import com.leo.core.iapi.IMergeApi;
import com.leo.core.iapi.IObjectApi;
import com.leo.core.iapi.IRunApi;
import com.leo.core.iapi.IStartApi;
import com.leo.core.iapi.ISubjoinApi;
import com.leo.core.iapi.ITAction;
import com.leo.core.iapi.IUserApi;
import com.leo.core.iapi.OnAddListener;
import com.leo.core.iapi.core.IApi;
import com.leo.core.iapi.main.Adapter;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.iapi.main.IHttpApi;
import com.leo.core.iapi.main.IShowApi;
import com.leo.core.iapi.main.IViewApi;
import com.leo.core.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;

@SuppressLint("MissingSuperCall")
public class CoreControllerApi<T extends CoreControllerApi, C> extends AttachApi<T, C> implements
        IControllerApi<T, C>, IViewApi<T>, IShowApi<T>, IHttpApi<T>, IMD5Api, IDataApi<T>, IObjectApi<T>,
        IActionApi<T, IApi>, IStartApi<T>, IUserApi<T>, ILoadImageApi<T>, IConfigApi<T>, IDataTypeApi<T>,
        IMergeApi<T>, IGalleryApi<T>, IFileApi {

    private C controller;
    private Handler mainHandler;
    private Context context;
    private Application application;
    private Activity activity;
    private Fragment fragment;
    private Dialog dialog;
    private View rootView;
    private Adapter adapter;

    //api
    private IViewApi viewApi;
    private IShowApi showApi;
    private IDecodeApi decodeApi;
    private IHttpApi httpApi;
    private IMD5Api md5Api;
    private IDataApi dataApi;
    private IObjectApi objectApi;
    private IActionApi actionApi;
    private IStartApi startApi;
    private IUserApi userApi;
    private ILoadImageApi loadImageApi;
    private IConfigApi configApi;
    private IDataTypeApi dataTypeApi;
    private IMergeApi mergeApi;
    private ISubjoinApi subjoinApi;
    private IGalleryApi galleryApi;
    private IFileApi fileApi;

    //other
    private Integer rootViewResId;
    private Subscriber subscriber;
    private Observable.Transformer transformer;
    private Class<? extends View> viewClz;
    private Class<? extends IControllerApi> controllerApiClz;
    private Map<Class, IBindBeanCallback> callbackMap;

    private List<OnAddListener> addListeners;

    public CoreControllerApi(C controller) {
        super(controller);
        initController(controller);
    }

    @Override
    public T initController(C controller) {
        this.controller = controller;
        mainHandler = new Handler(Looper.getMainLooper());
        if (controller instanceof Activity) {
            activity = (Activity) controller;
            context = activity.getBaseContext();
            application = activity.getApplication();
        } else if (controller instanceof Fragment) {
            fragment = (Fragment) controller;
            activity = fragment.getActivity();
            context = fragment.getContext();
            application = activity == null ? null : activity.getApplication();
        } else if (controller instanceof View) {
            rootView = (View) controller;
            context = rootView.getContext();
            if (context instanceof Activity) {
                activity = (Activity) context;
            }
        } else if (controller instanceof Adapter) {
            adapter = (Adapter) controller;
            context = adapter.getContext();
            if (context instanceof Activity) {
                activity = (Activity) context;
            }
        } else if (controller instanceof Application) {
            application = (Application) controller;
            context = application.getBaseContext();
            if (context instanceof Activity) {
                activity = (Activity) context;
            }
        } else if(controller instanceof Dialog){
            dialog = (Dialog) controller;
            context = dialog.getContext();
            if (context instanceof Activity) {
                activity = (Activity) context;
            }
        }
        return getThis();
    }

    @Override
    public IViewApi viewApi() {
        if (viewApi == null) {
            viewApi = newViewApi();
            if (viewApi == null) {
                throw new NullPointerException("newViewApi 不能为空");
            }
        }
        return viewApi;
    }

    @Override
    public IViewApi newViewApi() {
        return null;
    }

    @Override
    public IDataApi dataApi() {
        if (dataApi == null) {
            dataApi = newDataApi();
            if (dataApi == null) {
                throw new NullPointerException("newDataApi 不能为空");
            }
        }
        return dataApi;
    }

    @Override
    public IDataApi newDataApi() {
        return null;
    }

    @Override
    public IMD5Api md5Api() {
        if (md5Api == null) {
            md5Api = newMd5Api();
            if (md5Api == null) {
                throw new NullPointerException("newMd5Api 不能为空");
            }
        }
        return md5Api;
    }

    @Override
    public IMD5Api newMd5Api() {
        return null;
    }

    @Override
    public IShowApi showApi() {
        if (showApi == null) {
            showApi = newShowApi();
            if (showApi == null) {
                throw new NullPointerException("newShowApi 不能为空");
            }
        }
        return showApi;
    }

    @Override
    public IShowApi newShowApi() {
        return null;
    }

    @Override
    public IHttpApi httpApi() {
        if (httpApi == null) {
            httpApi = newHttpApi();
            if (httpApi == null) {
                throw new NullPointerException("newHttpApi 不能为空");
            }
        }
        return httpApi;
    }

    @Override
    public IHttpApi newHttpApi() {
        return null;
    }

    @Override
    public IDecodeApi decodeApi() {
        if (decodeApi == null) {
            decodeApi = newDecodeApi();
            if (decodeApi == null) {
                throw new NullPointerException("newDecodeApi 不能为空");
            }
        }
        return decodeApi;
    }

    @Override
    public IDecodeApi newDecodeApi() {
        return null;
    }

    @Override
    public IObjectApi objectApi() {
        if (objectApi == null) {
            objectApi = newObjectApi();
            if (objectApi == null) {
                throw new NullPointerException("newObjectApi 不能为空");
            }
        }
        return objectApi;
    }

    @Override
    public IObjectApi newObjectApi() {
        return null;
    }

    @Override
    public IActionApi actionApi() {
        if (actionApi == null) {
            actionApi = newActionApi();
            if (actionApi == null) {
                throw new NullPointerException("newActionApi 不能为空");
            }
        }
        return actionApi;
    }

    @Override
    public IActionApi newActionApi() {
        return null;
    }

    @Override
    public IStartApi startApi() {
        if (startApi == null) {
            startApi = newStartApi();
            if (startApi == null) {
                throw new NullPointerException("newStartApi 不能为空");
            }
        }
        return startApi;
    }

    @Override
    public IStartApi newStartApi() {
        return null;
    }

    @Override
    public IUserApi userApi() {
        if (userApi == null) {
            userApi = newUserApi();
            if (userApi == null) {
                throw new NullPointerException("newUserApi 不能为空");
            }
        }
        return userApi;
    }

    @Override
    public IUserApi newUserApi() {
        return null;
    }

    @Override
    public ILoadImageApi loadImageApi() {
        if (loadImageApi == null) {
            loadImageApi = newLoadImageApi();
            if (loadImageApi == null) {
                throw new NullPointerException("newLoadImageApi 不能为空");
            }
        }
        return loadImageApi;
    }

    @Override
    public ILoadImageApi newLoadImageApi() {
        return null;
    }

    @Override
    public IConfigApi configApi() {
        if (configApi == null) {
            configApi = newConfigApi();
            if (configApi == null) {
                throw new NullPointerException("newConfigApi 不能为空");
            }
        }
        return configApi;
    }

    @Override
    public IConfigApi newConfigApi() {
        return null;
    }

    @Override
    public IDataTypeApi dataTypeApi() {
        if (dataTypeApi == null) {
            dataTypeApi = newDataTypeApi();
            if (dataTypeApi == null) {
                throw new NullPointerException("newDataTypeApi 不能为空");
            }
        }
        return dataTypeApi;
    }

    @Override
    public IDataTypeApi newDataTypeApi() {
        return null;
    }

    @Override
    public IMergeApi mergeApi() {
        if (mergeApi == null) {
            mergeApi = newMergeApi();
            if (mergeApi == null) {
                throw new NullPointerException("newDataTypeApi 不能为空");
            }
        }
        return mergeApi;
    }

    @Override
    public IMergeApi newMergeApi() {
        return null;
    }

    @Override
    public ISubjoinApi api() {
        if (subjoinApi == null) {
            subjoinApi = newApi();
            if (subjoinApi == null) {
                throw new NullPointerException("newApi 不能为空");
            }
        }
        return subjoinApi;
    }

    @Override
    public ISubjoinApi newApi() {
        return null;
    }

    @Override
    public IGalleryApi galleryApi() {
        if (galleryApi == null) {
            galleryApi = newGalleryApi();
            if (galleryApi == null) {
                throw new NullPointerException("newGalleryApi 不能为空");
            }
        }
        return galleryApi;
    }

    @Override
    public IGalleryApi newGalleryApi() {
        return null;
    }

    @Override
    public IFileApi fileApi() {
        if (fileApi == null) {
            fileApi = newFileApi();
            if (fileApi == null) {
                throw new NullPointerException("newFileApi 不能为空");
            }
        }
        return fileApi;
    }

    @Override
    public IFileApi newFileApi() {
        return null;
    }

    @Override
    public C getController() {
        return controller;
    }

    @Override
    public Context getContext() {
        return activity != null ? activity : context;
    }

    @Override
    public Application getApplication() {
        return application;
    }

    @Override
    public Activity getActivity() {
        return activity;
    }

    @Override
    public FragmentActivity getFragmentActivity() {
        return getActivity() instanceof FragmentActivity ? (FragmentActivity) getActivity() : null;
    }

    @Override
    public <D extends Dialog> D getDialog() {
        return (D) dialog;
    }

    @Override
    public Intent getIntent(Class<? extends Activity> clz, Class<? extends IControllerApi>... args) {
        return startApi().getIntent(clz, args);
    }

    @Override
    public T startActivity(Class<? extends Activity> clz, Class<? extends IControllerApi>... args) {
        startApi().startActivity(clz, args);
        return getThis();
    }

    @Override
    public Intent getIntent(Class<? extends Activity> clz, Bundle bundle, Class<? extends IControllerApi>... args) {
        return startApi().getIntent(clz, bundle, args);
    }

    @Override
    public T startActivity(Class<? extends Activity> clz, Bundle bundle, Class<? extends IControllerApi>... args) {
        startApi().startActivity(clz, bundle, args);
        return getThis();
    }

    @Override
    public T startFinishActivity(Class<? extends Activity> clz, Class<? extends IControllerApi>... args) {
        startApi().startFinishActivity(clz, args);
        return getThis();
    }

    @Override
    public T startFinishActivity(Class<? extends Activity> clz, Bundle bundle, Class<? extends IControllerApi>... args) {
        startApi().startFinishActivity(clz, bundle, args);
        return getThis();
    }

    @Override
    public Bundle getBundle(Class<? extends IControllerApi>... args) {
        return startApi().getBundle(args);
    }

    @Override
    public Fragment getFragment(Class<? extends Fragment> clz, Class<? extends IControllerApi>... args) {
        return startApi().getFragment(clz, args);
    }

    @Override
    public Bundle getBundle(Bundle bundle, Class<? extends IControllerApi>... args) {
        return startApi().getBundle(bundle, args);
    }

    @Override
    public Fragment getFragment(Class<? extends Fragment> clz, Bundle bundle, Class<? extends IControllerApi>... args) {
        return startApi().getFragment(clz, bundle, args);
    }

    @Override
    public boolean isActivity() {
        return getController() instanceof Activity;
    }

    @Override
    public Fragment getFragment() {
        return fragment;
    }

    @Override
    public boolean isFragment() {
        return getController() instanceof Fragment;
    }

    @Override
    public boolean isDialog() {
        return getController() instanceof Dialog;
    }

    @Override
    public T dialogShow() {
        if(isDialog() && !getDialog().isShowing()){
            getDialog().show();
        }
        return getThis();
    }

    @Override
    public T dismiss() {
        if(isDialog()){
            getDialog().dismiss();
        }
        return getThis();
    }

    @Override
    public Adapter getAdapter() {
        return adapter;
    }

    @Override
    public boolean isAdapter() {
        return getController() instanceof Adapter;
    }

    @Override
    public T runOnUiThread(IAction action) {
        if (action != null) {
            mainHandler.post(action::action);
        }
        return getThis();
    }

    @Override
    public T runOnUiThread(IAction action, long delayMillis) {
        if (action != null && delayMillis >= 0) {
            mainHandler.postDelayed(action::action, delayMillis);
        }
        return getThis();
    }

    @Override
    public T runOnOtherThread(IAction action) {
        if (action != null) {
            new Thread(action::action).start();
        }
        return getThis();
    }

    @LayoutRes
    @Override
    public Integer getRootViewResId() {
        return rootViewResId;
    }

    @Override
    public T setRootViewResId(Integer resId) {
        rootViewResId = resId;
        return getThis();
    }

    @Override
    public int[] getStyleableRes() {
        return null;
    }

    @Override
    public void initStyleable(TypedArray a) {
    }

    @Override
    public boolean isView() {
        return getController() instanceof View;
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public Class<? extends View> getRootViewClz() {
        return viewClz;
    }

    @Override
    public Class<? extends IControllerApi> getRootViewClzApi() {
        return controllerApiClz;
    }

    @Override
    public T setRootViewClz(Class<? extends View> clz) {
        viewClz = clz;
        return getThis();
    }

    @Override
    public T setRootViewClzApi(Class<? extends IControllerApi> clz) {
        controllerApiClz = clz;
        return getThis();
    }

    @Override
    public void attachBaseContext(Context base) {
    }

    @Override
    public <B> void onBindViewHolder(B bean, int position) {
        if (callbackMap != null && bean != null) {
            IBindBeanCallback callback = callbackMap.get(bean.getClass());
            if (callback != null) {
                callback.onBindBean(bean, position);
            }
        }
    }

    @Override
    public <B> T putBindBeanCallback(Class<B> clz, IBindBeanCallback<B> callback) {
        if (clz != null) {
            if (callbackMap == null) {
                callbackMap = new HashMap<>();
            }
            callbackMap.put(clz, callback);
        }
        return getThis();
    }

    @Override
    public void onAttach(Context context) {
        if (isFragment()) {
            activity = fragment.getActivity();
            this.context = fragment.getContext();
            application = activity == null ? null : activity.getApplication();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        init(savedInstanceState);
        if (isActivity()) {
            if (getRootViewClz() != null) {
                rootView = getObject(getRootViewClz()
                        , new Class[]{Context.class}
                        , new Object[]{getContext()});
                if(rootView instanceof BaseControllerApiView && getRootViewClzApi() != null){
                    ((BaseControllerApiView) rootView).init(getRootViewClzApi(), null);
                }
            }
            if (getRootViewResId() != null) {
                getActivity().setContentView(getRootViewResId());
                rootView = getActivity().getWindow().getDecorView();
            } else if (getRootView() != null) {
                getActivity().setContentView(getRootView());
            }
            initView();
            initData();
        } else if(isDialog()){
            if (getRootViewClz() != null) {
                rootView = getObject(getRootViewClz()
                        , new Class[]{Context.class}
                        , new Object[]{getContext()});
                if(rootView instanceof BaseControllerApiView && getRootViewClzApi() != null){
                    ((BaseControllerApiView) rootView).init(getRootViewClzApi(), null);
                }
            }
            if (getRootViewResId() != null) {
                getDialog().setContentView(getRootViewResId());
                Window window = getDialog().getWindow();
                if(window != null){
                    rootView = window.getDecorView();
                }
            } else if (getRootView() != null) {
                getDialog().setContentView(getRootView());
            }
            initView();
            initData();
        }
    }

    @Override
    public void init(Bundle savedInstanceState) {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (isFragment()) {
            if (getRootViewClz() != null && getRootViewClzApi() != null) {
                rootView = getObject(getRootViewClz()
                        , new Class[]{Class.class, Context.class}
                        , new Object[]{getRootViewClzApi(), getContext()});
            }
        }
        if (getRootViewResId() != null) {
            if (isView()) {
                rootView = inflater.inflate(getRootViewResId(), container);
            } else {
                rootView = inflater.inflate(getRootViewResId(), container, false);
            }
        }
        return getRootView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView();
        initData();
    }

    @Override
    public void initView() {
        if (getRootView() != null)
            ButterKnife.bind(this, getRootView());
    }

    @Override
    public void initData() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onRestart() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onDestroyView() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onDetach() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    public void onNewIntent(Intent intent) {
    }

    @Override
    public <V extends View> V findViewById(int resId) {
        return findViewById(getRootView(), resId);
    }

    @Override
    public <V extends View> V findViewById(View rootView, int resId) {
        return rootView == null ? null : (V) rootView.findViewById(resId);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    }

    @Override
    public void onTerminate() {
    }

    @Override
    public void onTrimMemory(int level) {
    }

    @Override
    public void onLowMemory() {
    }

    @Override
    public T statusBar(boolean dark) {
        StatusBarUtil.StatusBar(getActivity(), dark);
        return getThis();
    }

    @Override
    public boolean checkView(View view) {
        return viewApi().checkView(view);
    }

    @Override
    public T setVisibility(View view, int visibility) {
        viewApi().setVisibility(view, visibility);
        return getThis();
    }

    @Override
    public T setVisibility(int visibility, View... args) {
        viewApi().setVisibility(visibility, args);
        return getThis();
    }

    @Override
    public T setText(View view, CharSequence text) {
        viewApi().setText(view, text);
        return getThis();
    }

    @Override
    public T setViewText(TextView tv, CharSequence text, boolean gone) {
        viewApi().setViewText(tv, text, gone);
        return getThis();
    }

    @Override
    public T setViewText(TextView tv, CharSequence text) {
        viewApi().setViewText(tv, text);
        return getThis();
    }

    @Override
    public T setImage(ImageView iv, Object path) {
        viewApi().setImage(iv, path);
        return getThis();
    }

    @Override
    public T setIcon(ImageView icon, boolean show) {
        viewApi().setIcon(icon, show);
        return getThis();
    }

    @Override
    public T setOnClickListener(View view, View.OnClickListener listener) {
        viewApi().setOnClickListener(view, listener);
        return getThis();
    }

    @Override
    public T setOnLongClickListener(View view, View.OnLongClickListener listener) {
        viewApi().setOnLongClickListener(view, listener);
        return getThis();
    }

    @Override
    public T setTextHint(EditText et, String hint) {
        viewApi().setTextHint(et, hint);
        return getThis();
    }

    @Override
    public T setResBg(View view, int resId) {
        viewApi().setResBg(view, resId);
        return getThis();
    }

    @Override
    public T setColorBg(View view, int color) {
        viewApi().setColorBg(view, color);
        return getThis();
    }

    @Override
    public T setTextColor(TextView tv, int color) {
        viewApi().setTextColor(tv, color);
        return getThis();
    }

    @Override
    public String encode(Object obj) {
        return showApi().encode(obj);
    }

    @Override
    public <R> R decode(Object in, Object param) {
        return (R) showApi().decode(in, param);
    }

    @Override
    public T show(@NonNull CharSequence text) {
        showApi().show(text);
        return getThis();
    }

    @Override
    public String getLog(Object obj) {
        return showApi().getLog(obj);
    }

    @Override
    public T ii(Object obj) {
        showApi().ii(obj);
        return getThis();
    }

    @Override
    public T ii(CharSequence text, Object obj) {
        showApi().ii(text, obj);
        return getThis();
    }

    @Override
    public T ee(Object obj) {
        showApi().ee(obj);
        return getThis();
    }

    @Override
    public T ee(CharSequence text, Object obj) {
        showApi().ee(text, obj);
        return getThis();
    }

    public T addOnAddListener(OnAddListener listener) {
        if (listener != null) {
            if (addListeners == null) {
                addListeners = new ArrayList<>();
            }
            addListeners.add(listener);
        }
        return getThis();
    }

    @Override
    public <R> T add(Class<R> clz, IRunApi<R> api) {
        IRunApi<R> ap = obj -> execute(addListeners, listener -> listener.onAdd(obj));
        httpApi().add(clz, ap);
        httpApi().add(clz, api);
        return getThis();
    }

    @Override
    public <R> T listAdd(Class<R> clz, IRunApi<List<R>> api) {
        IRunApi<List<R>> ap = data -> execute(addListeners, listener -> listener.onListAdd(data));
        httpApi().listAdd(clz, ap);
        httpApi().listAdd(clz, api);
        return getThis();
    }

    @Override
    public T replaceClzAddApiAll(Map<String, List<IRunApi>> map) {
        httpApi().replaceClzAddApiAll(map);
        return getThis();
    }

    @Override
    public <R> R create(String url, Class<R> clz) {
        return (R) httpApi().create(url, clz);
    }

    @Override
    public <R> R getApi(String url) {
        return (R) httpApi().getApi(url);
    }

    @Override
    public <R> R getApi() {
        return (R) httpApi().getApi();
    }

    @Override
    public <R, M> Observable.Transformer<R, M> transformer() {
        if (transformer == null) {
            transformer = newTransformer();
            if (transformer == null) {
                throw new NullPointerException("newTransformer 不能为空");
            }
        }
        return transformer;
    }

    @Override
    public <R, M> Observable.Transformer<R, M> newTransformer() {
        return null;
    }

    @Override
    public <R> Subscriber<R> subscriber() {
        if (subscriber == null) {
            subscriber = newSubscriber();
            if (subscriber == null) {
                throw new NullPointerException("newTransformer 不能为空");
            }
        }
        return subscriber;
    }

    @Override
    public <R> Subscriber<R> newSubscriber() {
        return null;
    }

    @Override
    public <R> T setNewSubscriber(Subscriber<R> newSubscriber) {
        httpApi().setNewSubscriber(newSubscriber);
        return getThis();
    }

    @Override
    public <R> T observable(Observable<R> observable) {
        httpApi().setNewSubscriber(newSubscriber());
        httpApi().observable(observable);
        return getThis();
    }

    @Override
    public String md5(String text) {
        return md5Api().md5(text);
    }

    @Override
    public String mmd5(String text) {
        return md5Api().mmd5(text);
    }

    @Override
    public T switchDefault() {
        dataApi().switchDefault();
        return getThis();
    }

    @Override
    public T switchTable(String key) {
        dataApi().switchTable(key);
        return getThis();
    }

    @Override
    public T switchTable(@NonNull String key, int mode) {
        dataApi().switchTable(key, mode);
        return getThis();
    }

    @Override
    public <V> T saveData(@NonNull String key, @NonNull V value) {
        dataApi().saveData(key, value);
        return getThis();
    }

    @Override
    public <V> T saveData(V value) {
        dataApi().saveData(value);
        return getThis();
    }

    @Override
    public <V> T saveData(@NonNull String key, @NonNull List<V> value) {
        dataApi().saveData(key, value);
        return getThis();
    }

    @Override
    public <V> T saveData(List<V> value) {
        dataApi().saveData(value);
        return getThis();
    }

    @Override
    public String getString(@NonNull String key) {
        return dataApi().getString(key);
    }

    @Override
    public <C> C getBean(Class<C> clz) {
        return (C) dataApi().getBean(clz);
    }

    @Override
    public <C> C getBean(@NonNull String key, @NonNull Class<C> cls) {
        return (C) dataApi().getBean(key, cls);
    }

    @Override
    public <C> List<C> getBeanData(Class<C> clz) {
        return dataApi().getBeanData(clz);
    }

    @Override
    public <C> List<C> getBeanData(String key, Class<C> clz) {
        return dataApi().getBeanData(key, clz);
    }

    @Override
    public <C> List<C> getBeanData(@NonNull String key, @NonNull Class<? extends List> lCls, @NonNull Class<C> cls) {
        return dataApi().getBeanData(key, lCls, cls);
    }

    @Override
    public <C> List<C> getBeanData(Class<? extends List> lClz, Class<C> clz) {
        return dataApi().getBeanData(lClz, clz);
    }

    @Override
    public List<String> getStringData(String key) {
        return dataApi().getStringData(key);
    }

    @Override
    public Map<String, ?> getAllBean() {
        return dataApi().getAllBean();
    }

    @Override
    public String getAllBeanJsonString() {
        return dataApi().getAllBeanJsonString();
    }

    @Override
    public T remove(@NonNull String key) {
        dataApi().remove(key);
        return getThis();
    }

    @Override
    public Map<String, ?> getConfigMap() {
        return configApi().getConfigMap();
    }

    @Override
    public Map<String, ?> newConfigMap() {
        return configApi().newConfigMap();
    }

    @Override
    public boolean hasConfig(String key) {
        return configApi().hasConfig(key);
    }

    @Override
    public <R> T addConfig(String key, R value) {
        configApi().addConfig(key, value);
        return getThis();
    }

    @Override
    public <R> R getConfig(String key) {
        return (R) configApi().getConfig(key);
    }

    @Override
    public <R> T executeConfig(String key, ITAction<R> action) {
        configApi().executeConfig(key, action);
        return getThis();
    }

    @Override
    public <R> T executeConfig(String key, R compare, IAction action) {
        configApi().executeConfig(key, compare, action);
        return getThis();
    }

    @Override
    public T removeConfig(String key) {
        configApi().removeConfig(key);
        return getThis();
    }

    @Override
    public T removeConfigAll() {
        configApi().removeConfigAll();
        return getThis();
    }

    @Override
    public T replaceConfigAll(Map<String, ?> map) {
        configApi().replaceConfigAll(map);
        return getThis();
    }

    @Override
    public T removeDataAll() {
        dataApi().removeDataAll();
        return getThis();
    }

    @Override
    public T remove(Class clz) {
        dataApi().remove(clz);
        return getThis();
    }

    @Override
    public T removeData(String key) {
        dataApi().removeData(key);
        return getThis();
    }

    @Override
    public T removeData(Class clz) {
        dataApi().removeData(clz);
        return getThis();
    }

    @Override
    public Class getClass(String name) {
        return objectApi().getClass(name);
    }

    @Override
    public Object getObject(String name) {
        return objectApi().getObject(name);
    }

    @Override
    public <P> P getObject(Class<P> clz) {
        return (P) objectApi().getObject(clz);
    }

    @Override
    public <P> P getObject(String name, Class<P> pClz) {
        return (P) objectApi().getObject(name, pClz);
    }

    @Override
    public <P> P getObject(Class<P> clz, Class[] cs, Object[] os) {
        return (P) objectApi().getObject(clz, cs, os);
    }

    @Override
    public <P> P getObject(Class<P> clz, Class cs, Object os) {
        return (P) objectApi().getObject(clz, cs, os);
    }

    @Override
    public Object getObject(String name, Class[] cs, Object[] os) {
        return objectApi().getObject(name, cs, os);
    }

    @Override
    public Object getObject(String name, Class cs, Object os) {
        return objectApi().getObject(name, cs, os);
    }

    @Override
    public <P> P getObject(String name, Class<P> pClz, Class[] cs, Object[] os) {
        return (P) objectApi().getObject(name, pClz, cs, os);
    }

    @Override
    public <P> P getObject(String name, Class<P> pClz, Class cs, Object os) {
        return (P) objectApi().getObject(name, pClz, cs, os);
    }

    @Override
    public long currentTimeMillis() {
        return actionApi().currentTimeMillis();
    }

    @Override
    public Map<Long, List<IApi>> getActionMap() {
        return actionApi().getActionMap();
    }

    @Override
    public Map<Long, List<IApi>> getUIActionMap() {
        return actionApi().getUIActionMap();
    }

    @Override
    public Map<Long, List<IApi>> newActionMap() {
        return actionApi().newActionMap();
    }

    @Override
    public Map<Long, List<IApi>> newUIActionMap() {
        return actionApi().newUIActionMap();
    }

    @Override
    public Set<Integer> getCancelData() {
        return actionApi().getCancelData();
    }

    @Override
    public Set<Integer> newCancelData() {
        return actionApi().newCancelData();
    }

    @Override
    public T setInterval(int interval) {
        actionApi().setInterval(interval);
        return getThis();
    }

    @Override
    public int getInterval() {
        return actionApi().getInterval();
    }

    @Override
    public T start() {
        actionApi().start();
        return getThis();
    }

    @Override
    public T stop() {
        actionApi().stop();
        return getThis();
    }

    @Override
    public T action(IApi action) {
        actionApi().action(action);
        return getThis();
    }

    @Override
    public int put(long time, IApi action) {
        return actionApi().put(time, action);
    }

    @Override
    public int put(long time, IApi action, int interval, int num) {
        return actionApi().put(time, action, interval, num);
    }

    @Override
    public int putUI(long time, IApi action) {
        return actionApi().putUI(time, action);
    }

    @Override
    public int putUI(long time, IApi action, int interval, int num) {
        return actionApi().putUI(time, action, interval, num);
    }

    @Override
    public T cancel(int actionCode) {
        actionApi().cancel(actionCode);
        return getThis();
    }

    @Override
    public int add(long time, IApi action) {
        return actionApi().add(time, action);
    }

    @Override
    public int add(long time, IApi action, int interval, int num) {
        return actionApi().add(time, action, interval, num);
    }

    @Override
    public int addUI(long time, IApi action) {
        return actionApi().addUI(time, action);
    }

    @Override
    public int addUI(long time, IApi action, int interval, int num) {
        return actionApi().addUI(time, action, interval, num);
    }

    @Override
    public boolean isLogin() {
        return userApi().isLogin();
    }

    @Override
    public T loginOut() {
        userApi().loginOut();
        return getThis();
    }

    @Override
    public <R> T initUser(R user) {
        userApi().initUser(user);
        return getThis();
    }

    @Override
    public <R> R getUser() {
        return (R) userApi().getUser();
    }

    @Override
    public String getUId() {
        return userApi().getUId();
    }

    @Override
    public String getUserName() {
        return userApi().getUserName();
    }

    @Override
    public String getToken() {
        return userApi().getToken();
    }

    @Override
    public String getUTime() {
        return userApi().getUTime();
    }

    @Override
    public <R> R getDrive() {
        return (R) loadImageApi().getDrive();
    }

    @Override
    public int getDefaultRes() {
        return loadImageApi().getDefaultRes();
    }

    @Override
    public int getErrorRes() {
        return loadImageApi().getErrorRes();
    }

    @Override
    public T setCacheEnable(boolean enable) {
        loadImageApi().setCacheEnable(enable);
        return getThis();
    }

    @Override
    public T init(Map<String, ?> map) {
        loadImageApi().init(map);
        return getThis();
    }

    @Override
    public T load(Object path, ImageView iv) {
        loadImageApi().load(path, iv);
        return getThis();
    }

    @Override
    public T load(Object path, ImageView iv, ICallbackApi api) {
        loadImageApi().load(path, iv, api);
        return getThis();
    }

    @Override
    public String formatString(String format, Object... args) {
        return dataTypeApi().formatString(format, args);
    }

    @Override
    public <R> R[] newArray(Class clz, int length) {
        return (R[]) mergeApi().newArray(clz, length);
    }

    @Override
    public <R> R[] merge(Class<R> clz, boolean end, R[] args, R... args1) {
        return (R[]) mergeApi().merge(clz, end, args, args1);
    }

    @Override
    public T openImageSelector() {
        galleryApi().openImageSelector();
        return getThis();
    }

    @Override
    public String getAssetsString(String file) {
        return fileApi().getAssetsString(file);
    }

}
