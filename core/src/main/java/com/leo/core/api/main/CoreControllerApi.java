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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.leo.core.api.inter.MsgSubscriber;
import com.leo.core.api.core.AttachApi;
import com.leo.core.config.Config;
import com.leo.core.core.BaseControllerApiView;
import com.leo.core.iapi.inter.IAction;
import com.leo.core.iapi.api.IActionApi;
import com.leo.core.iapi.api.IBindBeanApi;
import com.leo.core.iapi.api.ICallbackApi;
import com.leo.core.iapi.api.IConfigApi;
import com.leo.core.iapi.api.IDataApi;
import com.leo.core.iapi.api.IDataTypeApi;
import com.leo.core.iapi.api.IDecodeApi;
import com.leo.core.iapi.api.IFileApi;
import com.leo.core.iapi.api.IGalleryApi;
import com.leo.core.iapi.api.IHelperApi;
import com.leo.core.iapi.api.ILoadImageApi;
import com.leo.core.iapi.api.IMD5Api;
import com.leo.core.iapi.api.IMergeApi;
import com.leo.core.iapi.api.IObjectApi;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.api.IParseApi;
import com.leo.core.iapi.inter.IPathMsgAction;
import com.leo.core.iapi.api.ISerialVersionTagApi;
import com.leo.core.iapi.api.IStartApi;
import com.leo.core.iapi.api.ISubjoinApi;
import com.leo.core.iapi.inter.ITAction;
import com.leo.core.iapi.api.IUrlApi;
import com.leo.core.iapi.api.IUserApi;
import com.leo.core.iapi.api.IVgRunApi;
import com.leo.core.iapi.inter.OnAddListener;
import com.leo.core.iapi.core.IApi;
import com.leo.core.iapi.main.Adapter;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.iapi.main.IHttpApi;
import com.leo.core.iapi.main.IShowApi;
import com.leo.core.iapi.main.IViewApi;
import com.leo.core.util.ObjectUtil;
import com.leo.core.util.StatusBarUtil;
import com.leo.core.util.TextUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;
import rx.Observable;

import static com.leo.core.config.Config.RX;
import static com.leo.core.util.TextUtils.getEmptyLength;

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
@SuppressLint("MissingSuperCall")
public class CoreControllerApi<T extends CoreControllerApi, C> extends AttachApi<T, C> implements
        IControllerApi<T, C>, IViewApi<T>, IShowApi<T>, IHttpApi<T>, IMD5Api, IDataApi<T>, IObjectApi<T>,
        IActionApi<T, IApi>, IStartApi<T>, IUserApi<T>, ILoadImageApi<T>, IConfigApi<T>, IDataTypeApi<T>,
        IMergeApi<T>, IGalleryApi<T>, IFileApi, IParseApi<T>, IHelperApi<T>, IUrlApi<T> {

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
    private IUrlApi api;
    private IParseApi parseApi;
    private IHelperApi helperApi;

    //other
    private Integer rootViewResId;
    private MsgSubscriber subscriber;
    private Observable.Transformer transformer;
    private Class<? extends View> viewClz;
    private Class<? extends IControllerApi> controllerApiClz;
    private Map<Class, IBindBeanApi> apiMap;
    private String finish;

    private List<OnAddListener> addListeners;

    public CoreControllerApi(C controller) {
        super(controller);
        initController(controller);
        apiMap = new HashMap<>();
    }

    @Override
    public void initController(C controller) {
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
        } else if (controller instanceof Dialog) {
            dialog = (Dialog) controller;
            context = dialog.getContext();
            if (context instanceof Activity) {
                activity = (Activity) context;
            }
        }
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
    public ISubjoinApi subjoinApi() {
        if (subjoinApi == null) {
            subjoinApi = newSubjoinApi();
            if (subjoinApi == null) {
                throw new NullPointerException("newApi 不能为空");
            }
        }
        return subjoinApi;
    }

    @Override
    public ISubjoinApi newSubjoinApi() {
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
    public IUrlApi api() {
        if (api == null) {
            api = newApi();
            if (api == null) {
                throw new NullPointerException("newApi 不能为空");
            }
        }
        return api;
    }

    @Override
    public IUrlApi newApi() {
        return null;
    }

    @Override
    public IHelperApi helper() {
        if (helperApi == null) {
            helperApi = newHelper();
            if (helperApi == null) {
                throw new NullPointerException("newHelper 不能为空");
            }
        }
        return null;
    }

    @Override
    public IHelperApi newHelper() {
        return null;
    }

    @Override
    public T switchNPApi() {
        parseApi = null;
        return getThis();
    }

    @Override
    public IParseApi parseApi() {
        if (parseApi == null) {
            parseApi = newParseApi();
            if (parseApi == null) {
                throw new NullPointerException("newApi 不能为空");
            }
        }
        return parseApi;
    }

    @Override
    public IParseApi newParseApi() {
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
        if (isDialog() && !getDialog().isShowing()) {
            getDialog().show();
        }
        return getThis();
    }

    @Override
    public T dismiss() {
        if (isDialog()) {
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
            mainHandler.post(action::execute);
        }
        return getThis();
    }

    @Override
    public T runOnUiThread(IAction action, long delayMillis) {
        if (action != null && delayMillis >= 0) {
            mainHandler.postDelayed(action::execute, delayMillis);
        }
        return getThis();
    }

    @Override
    public T runOnOtherThread(IAction action) {
        if (action != null) {
            new Thread(action::execute).start();
        }
        return getThis();
    }

    @LayoutRes
    @Override
    public Integer getDefRootViewResId() {
        return null;
    }

    @LayoutRes
    @Override
    public Integer getRootViewResId() {
        if (rootViewResId != null) {
            return rootViewResId;
        }
        return getDefRootViewResId();
    }

    @Override
    public T setRootViewResId(Integer resId) {
        this.rootViewResId = resId;
        return getThis();
    }

    @Override
    public T setRootView(View rootView) {
        this.rootView = rootView;
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
    public IControllerApi getViewControllerApi() {
        if (getRootView() instanceof BaseControllerApiView) {
            IControllerApi api = ((BaseControllerApiView) getRootView()).controllerApi();
            return TextUtils.equals(api, this) ? null : api;
        }
        return null;
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
    public void onFindViewByIds() {
    }

    @Override
    public synchronized <B> T onBindViewHolder(B bean, int position) {
        executeNon(bean, obj -> executeNon(apiMap.get(bean.getClass()), api -> {
            onFindViewByIds();
            api.onItem(getThis(), bean);
        }));
        return getThis();
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    }

    @Override
    public void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    @Override
    public void onFinish() {
        executeViewControllerApi(IControllerApi::onFinish);
    }

    @Override
    public void onStartActivity(Intent intent) {
        executeViewControllerApi(obj -> obj.onStartActivity(intent));
    }

    @Override
    public T finishActivity(Object obj) {
        getActivity().finish();
        executeNon(obj, o -> saveData(getClass().getName(), obj));
        return getThis();
    }

    @Override
    public <B> B getFinish(Type... args) {
        if (!TextUtils.isEmpty(finish) && !TextUtils.isEmpty(args)) {
            int emptyCount = getEmptyLength(finish);
            for (Type type : args) {
                if (type != null) {
                    if (TextUtils.equals(type, String.class)) {
                        return (B) finish;
                    } else {
                        Object obj = decode(finish, type);
                        if (obj != null && emptyCount == getEmptyLength(encode(obj))) {
                            return (B) obj;
                        }
                    }
                }
            }
            return null;
        }
        return (B) finish;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public void notifyDataChanged() {
    }

    @Override
    public T executeBundle(IObjAction<Bundle> api) {
        if (api != null) {
            if (isActivity()) {
                executeNon(getActivity().getIntent().getExtras(), api);
            } else if (isFragment()) {
                executeNon(getFragment().getArguments(), api);
            }
        }
        return getThis();
    }

    @Override
    public <B> T putBindBeanApi(Class<B> clz, IBindBeanApi<T, B> api) {
        if (clz != null) {
            apiMap.put(clz, api);
        }
        return getThis();
    }

    @Override
    public T clearBindBeanApi() {
        apiMap.clear();
        return getThis();
    }

    @Override
    public void onAttach(Context context) {
        if (isFragment()) {
            activity = fragment.getActivity();
            this.context = fragment.getContext();
            application = activity == null ? null : activity.getApplication();
        }
        executeViewControllerApi(obj -> obj.onAttach(context));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        init(savedInstanceState);
        if (isActivity()) {
            if (getRootViewClz() != null) {
                if(rootView == null){
                    rootView = getObject(getRootViewClz()
                            , new Class[]{Context.class}
                            , new Object[]{getContext()});
                }
                if (rootView instanceof BaseControllerApiView && getRootViewClzApi() != null) {
                    ((BaseControllerApiView) rootView).init(getRootViewClzApi(), (View) null);
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
        } else if (isDialog()) {
            if (getRootViewClz() != null) {
                if(rootView == null){
                    rootView = getObject(getRootViewClz()
                            , new Class[]{Context.class}
                            , new Object[]{getContext()});
                }
                if (rootView instanceof BaseControllerApiView && getRootViewClzApi() != null) {
                    ((BaseControllerApiView) rootView).init(getRootViewClzApi(), (View) null);
                }
            }
            if (getRootViewResId() != null) {
                getDialog().setContentView(getRootViewResId());
                Window window = getDialog().getWindow();
                if (window != null) {
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
                if(rootView == null){
                    rootView = getObject(getRootViewClz()
                            , new Class[]{Class.class, Context.class}
                            , new Object[]{getRootViewClzApi(), getContext()});
                }
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
        onFindViewByIds();
    }

    @Override
    public void initData() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        executeViewControllerApi(obj -> obj.onActivityCreated(savedInstanceState));
    }

    @Override
    public void onStart() {
        executeViewControllerApi(IControllerApi::onStart);
    }

    @Override
    public void onResume() {
        executeViewControllerApi(IControllerApi::onResume);
        String key = getString(Config.LAST_FINISH_CONTROLLER_API);
        if (!TextUtils.isEmpty(key)) {
            finish = getString(key);
            remove(key);
        }
    }

    @Override
    public void onRestart() {
        executeViewControllerApi(IControllerApi::onRestart);
    }

    @Override
    public void onPause() {
        executeViewControllerApi(IControllerApi::onPause);
    }

    @Override
    public void onStop() {
        executeViewControllerApi(IControllerApi::onStop);
    }

    @Override
    public void onDestroyView() {
        executeViewControllerApi(IControllerApi::onDestroyView);
    }

    @Override
    public void onDestroy() {
        executeViewControllerApi(IControllerApi::onDestroy);
    }

    @Override
    public void onDetach() {
        executeViewControllerApi(IControllerApi::onDetach);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        executeViewControllerApi(obj -> obj.onActivityResult(requestCode, resultCode, data));
    }

    @Override
    public void onNewIntent(Intent intent) {
        executeViewControllerApi(obj -> obj.onNewIntent(intent));
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
        executeViewControllerApi(IControllerApi::onBackPressed);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        executeViewControllerApi(obj -> obj.onConfigurationChanged(newConfig));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        executeViewControllerApi(obj -> obj.onRequestPermissionsResult(requestCode, permissions, grantResults));
    }

    @Override
    public void onTerminate() {
        executeViewControllerApi(IControllerApi::onTerminate);
    }

    @Override
    public void onTrimMemory(int level) {
        executeViewControllerApi(obj -> obj.onTrimMemory(level));
    }

    @Override
    public void onLowMemory() {
        executeViewControllerApi(IControllerApi::onLowMemory);
    }

    @Override
    public T statusBar(boolean dark) {
        StatusBarUtil.StatusBar(getActivity(), dark);
        return getThis();
    }

    @Override
    public <B> T execute(Object obj, Class<B> clz, IObjAction<B> action) {
        if (obj != null && clz != null && action != null) {
            if (obj instanceof String) {
                final String txt = ((String) obj).replaceAll(RX, "/");
                B item = decode(txt, clz);
                if (getEmptyLength(txt) == getEmptyLength(encode(item))) {
                    action.execute(item);
                }
            } else {
                action.execute(decode(obj, clz));
            }
        }
        return getThis();
    }

    @Override
    public <B> T execute(Object obj, TypeToken<B> token, IObjAction<B> action) {
        Type type = token == null ? null : token.getType();
        if (obj != null && type != null && action != null) {
            if (obj instanceof String) {
                final String txt = ((String) obj).replaceAll(RX, "/");
                B item = decode(txt, type);
                if (item instanceof ISerialVersionTagApi) {
                    if(((ISerialVersionTagApi) item).isSerialVersionTag(type.toString())){
                        action.execute(item);
                        return getThis();
                    }
                } else if (getEmptyLength(txt) == getEmptyLength(encode(item))) {
                    action.execute(item);
                    return getThis();
                }
            } else {
                action.execute(decode(obj, type));
                return getThis();
            }
        }
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
    public T setOnClickListener(View.OnClickListener listener) {
        viewApi().setOnClickListener(listener);
        return getThis();
    }

    @Override
    public T setOnLongClickListener(View view, View.OnLongClickListener listener) {
        viewApi().setOnLongClickListener(view, listener);
        return getThis();
    }

    @Override
    public T setOnLongClickListener(View.OnLongClickListener listener) {
        viewApi().setOnLongClickListener(listener);
        return getThis();
    }

    @Override
    public T setEnableOnClickListener(View view, View.OnClickListener listener) {
        viewApi().setEnableOnClickListener(view, listener);
        return getThis();
    }

    @Override
    public T setEnableOnClickListener(View.OnClickListener listener) {
        viewApi().setEnableOnClickListener(listener);
        return getThis();
    }

    @Override
    public T setEnableOnLongClickListener(View view, View.OnLongClickListener listener) {
        viewApi().setOnLongClickListener(view, listener);
        return getThis();
    }

    @Override
    public T setEnableOnLongClickListener(View.OnLongClickListener listener) {
        viewApi().setEnableOnLongClickListener(listener);
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
    public T setViewGroupApi(ViewGroup vg, IVgRunApi vgApi) {
        viewApi().setViewGroupApi(vg, vgApi);
        return getThis();
    }

    @Override
    public T setSelected(View view, boolean selected) {
        viewApi().setSelected(view, selected);
        return getThis();
    }

    @Override
    public T setAllSelected(View view, boolean selected) {
        viewApi().setAllSelected(view, selected);
        return getThis();
    }

    @Override
    public T setLayoutParams(View view, ViewGroup.LayoutParams params) {
        viewApi().setLayoutParams(view, params);
        return getThis();
    }

    @Override
    public T execute(IAction action) {
        viewApi().execute(action);
        return getThis();
    }

    @Override
    public String encode(Object obj) {
        return showApi().encode(obj);
    }

    @Override
    public <B> B decode(Object in, Object param) {
        return (B) showApi().decode(in, param);
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

    public void addOnAddListener(OnAddListener listener) {
        if (listener != null) {
            if (addListeners == null) {
                addListeners = new ArrayList<>();
            }
            addListeners.add(listener);
        }
    }

    @Override
    public <B> B create(String url, Class<B> clz) {
        return (B) httpApi().create(url, clz);
    }

    @Override
    public <B> B getApi(String url) {
        return (B) httpApi().getApi(url);
    }

    @Override
    public <B> B getApi() {
        return (B) httpApi().getApi();
    }

    @Override
    public <B, M> Observable.Transformer<B, M> transformer() {
        if (transformer == null) {
            transformer = newTransformer();
            if (transformer == null) {
                throw new NullPointerException("newTransformer 不能为空");
            }
        }
        return transformer;
    }

    @Override
    public <B, M> Observable.Transformer<B, M> newTransformer() {
        return null;
    }

    @Override
    public <B> MsgSubscriber<T, B> subscriber() {
        if (subscriber == null) {
            subscriber = newSubscriber();
            if (subscriber == null) {
                throw new NullPointerException("newTransformer 不能为空");
            }
        }
        return subscriber;
    }

    @Override
    public <B> MsgSubscriber<T, B> newSubscriber() {
        return null;
    }

    @Override
    public <B> T setNewSubscriber(MsgSubscriber<T, B> newSubscriber) {
        httpApi().setNewSubscriber(newSubscriber);
        return getThis();
    }

    @Override
    public <B> T observable(Observable<B> observable) {
        httpApi().setNewSubscriber(newSubscriber());
        httpApi().observable(observable);
        return getThis();
    }

    @Override
    public <B> T observable(Observable<B> observable, String path, int what, String tag) {
        httpApi().setNewSubscriber(newSubscriber());
        httpApi().observable(observable, path, what, tag);
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
    public <V> T saveData(@NonNull String key, List<V> value) {
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
    public <B> B getBean(Class<B> clz) {
        return (B) dataApi().getBean(clz);
    }

    @Override
    public <B> B getBean(@NonNull String key, @NonNull Class<B> cls) {
        return (B) dataApi().getBean(key, cls);
    }

    @Override
    public <B> List<B> getBeanData(Class<B> clz) {
        return dataApi().getBeanData(clz);
    }

    @Override
    public <B> List<B> getBeanData(String key, Class<B> clz) {
        return dataApi().getBeanData(key, clz);
    }

    @Override
    public <B> List<B> getBeanData(@NonNull String key, @NonNull Class<? extends List> lCls, @NonNull Class<B> cls) {
        return dataApi().getBeanData(key, lCls, cls);
    }

    @Override
    public <B> List<B> getBeanData(Class<? extends List> lClz, Class<B> clz) {
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
    public <B> T addConfig(String key, B value) {
        configApi().addConfig(key, value);
        return getThis();
    }

    @Override
    public <B> B getConfig(String key) {
        return (B) configApi().getConfig(key);
    }

    @Override
    public <B> T executeConfig(String key, ITAction<B> action) {
        configApi().executeConfig(key, action);
        return getThis();
    }

    @Override
    public <B> T executeConfig(String key, B compare, IAction action) {
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
    public <B> T initUser(B user) {
        userApi().initUser(user);
        return getThis();
    }

    @Override
    public <B> B getUser() {
        return (B) userApi().getUser();
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
    public <A> A getDepartment() {
        return (A) userApi().getDepartment();
    }

    @Override
    public String getDepartmentCode() {
        return userApi().getDepartmentCode();
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
    public <B> B getDrive() {
        return (B) loadImageApi().getDrive();
    }

    @Override
    public Integer getDefaultRes() {
        return loadImageApi().getDefaultRes();
    }

    @Override
    public Integer getErrorRes() {
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
    public <B> B[] newArray(Class clz, int length) {
        return (B[]) mergeApi().newArray(clz, length);
    }

    @SafeVarargs
    @Override
    public final <B> B[] merge(Class<B> clz, boolean end, B[] args, B... args1) {
        return (B[]) mergeApi().merge(clz, end, args, args1);
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

    private void executeViewControllerApi(IObjAction<IControllerApi> api) {
        executeNon(getViewControllerApi(), api);
    }

    @Override
    public T copy() {
        C c = getController();
        Class clz = c == null ? Object.class : c.getClass();
        return (T) ObjectUtil.getObject(getClass(), new Class[]{clz}, new Object[]{c});
    }

    @Override
    public T addRootType(Class... args) {
        parseApi().addRootType(args);
        return getThis();
    }

    @Override
    public T addRootType(TypeToken... args) {
        parseApi().addRootType(args);
        return getThis();
    }

    @Override
    public T clearRootData() {
        parseApi().clearRootData();
        return getThis();
    }

    @Override
    public <A> T put(String key, TypeToken<A> token) {
        parseApi().put(key, token);
        return getThis();
    }

    @Override
    public <A> T put(String key, Class<A> clz) {
        parseApi().put(key, clz);
        return getThis();
    }

    @Override
    public T clearPutParseMap() {
        parseApi().clearPutParseMap();
        return getThis();
    }

    @Override
    public <A> T add(TypeToken<A> token, IPathMsgAction<A> action) {
        parseApi().add(token, action);
        return getThis();
    }

    @Override
    public <A> T addList(Class<A> clz, IPathMsgAction<List<A>> action) {
        parseApi().addList(clz, action);
        return getThis();
    }

    @Override
    public <A> T add(Class<A> clz, IPathMsgAction<A> action) {
        parseApi().add(clz, action);
        return getThis();
    }

    @Override
    public T clearAddParseMap() {
        parseApi().clearPutParseMap();
        return getThis();
    }

    @Override
    public T clearParse() {
        parseApi().clearParse();
        return getThis();
    }

    @Override
    public <B> T execute(B bean) {
        parseApi().execute(bean);
        return getThis();
    }

    @Override
    public T init(String path, int what, String tag) {
        parseApi().init(path, what, tag);
        return getThis();
    }

    @Override
    public T get(String path) {
        api().get(path);
        return getThis();
    }

    @Override
    public T get(String path, IObjAction<Map<String, Object>> action) {
        api().get(path, action);
        return getThis();
    }

    @Override
    public T get(String path, IObjAction<Map<String, Object>> action, int what) {
        api().get(path, action, what);
        return getThis();
    }

    @Override
    public T get(String path, IObjAction<Map<String, Object>> action, String tag) {
        api().get(path, action, tag);
        return getThis();
    }

    @Override
    public T get(String path, IObjAction<Map<String, Object>> action, int what, String tag) {
        api().get(path, action, what, tag);
        return getThis();
    }

    @Override
    public T post(String path) {
        api().post(path);
        return getThis();
    }

    @Override
    public T post(String path, IObjAction<Map<String, Object>> action) {
        api().post(path, action);
        return getThis();
    }

    @Override
    public T post(String path, IObjAction<Map<String, Object>> action, int what) {
        api().post(path, action, what);
        return getThis();
    }

    @Override
    public T post(String path, IObjAction<Map<String, Object>> action, String tag) {
        api().post(path, action, tag);
        return getThis();
    }

    @Override
    public T post(String path, IObjAction<Map<String, Object>> action, int what, String tag) {
        api().post(path, action, what, tag);
        return getThis();
    }

    @Override
    public T get(String url, String path) {
        api().get(url, path);
        return getThis();
    }

    @Override
    public T get(String url, String path, IObjAction<Map<String, Object>> action) {
        api().get(url, path, action);
        return getThis();
    }

    @Override
    public T get(String url, String path, IObjAction<Map<String, Object>> action, int what) {
        api().get(url, path, action, what);
        return getThis();
    }

    @Override
    public T get(String url, String path, IObjAction<Map<String, Object>> action, String tag) {
        api().get(url, path, action, tag);
        return getThis();
    }

    @Override
    public T get(String url, String path, IObjAction<Map<String, Object>> action, int what, String tag) {
        api().get(url, path, action, what, tag);
        return getThis();
    }

    @Override
    public T post(String url, String path) {
        api().post(url, path);
        return getThis();
    }

    @Override
    public T post(String url, String path, IObjAction<Map<String, Object>> action) {
        api().post(url, path, action);
        return getThis();
    }

    @Override
    public T post(String url, String path, IObjAction<Map<String, Object>> action, int what) {
        api().post(url, path, action, what);
        return getThis();
    }

    @Override
    public T post(String url, String path, IObjAction<Map<String, Object>> action, String tag) {
        api().post(url, path, action, tag);
        return getThis();
    }

    @Override
    public T post(String url, String path, IObjAction<Map<String, Object>> action, int what, String tag) {
        api().post(url, path, action, what, tag);
        return getThis();
    }

    @Override
    public Map<String, String> getCleanMapAction(IObjAction<Map<String, Object>> action) {
        return api().getCleanMapAction(action);
    }

    @Override
    public <B> B uApi() {
        return null;
    }
}
