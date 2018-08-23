package com.leo.core.api.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.leo.core.api.inter.MsgSubscriber;
import com.leo.core.api.core.AttachApi;
import com.leo.core.bean.ParseBean;
import com.leo.core.core.BaseControllerApiApp;
import com.leo.core.core.BaseControllerApiView;
import com.leo.core.iapi.api.IActivityLifecycleCallbacksApi;
import com.leo.core.iapi.api.ICameraApi;
import com.leo.core.iapi.api.IDirApi;
import com.leo.core.iapi.api.IRouteApi;
import com.leo.core.iapi.api.IVosApi;
import com.leo.core.iapi.api.IVsApi;
import com.leo.core.iapi.core.INorm;
import com.leo.core.iapi.inter.IAction;
import com.leo.core.iapi.api.IActionApi;
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
import com.leo.core.iapi.api.ISerialVersionTagApi;
import com.leo.core.iapi.api.ISubjoinApi;
import com.leo.core.iapi.inter.IPathMsgAction;
import com.leo.core.iapi.inter.IProgressListener;
import com.leo.core.iapi.inter.IReturnAction;
import com.leo.core.iapi.inter.ITAction;
import com.leo.core.iapi.api.IUrlApi;
import com.leo.core.iapi.api.IUserApi;
import com.leo.core.iapi.api.IVgRunApi;
import com.leo.core.iapi.inter.ImageAction;
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

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;
import rx.Observable;

import static com.leo.core.config.Config.FINISH;
import static com.leo.core.config.Config.RX;
import static com.leo.core.util.TextUtils.getEmptyLength;

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
@SuppressLint("MissingSuperCall")
public class CoreControllerApi<T extends CoreControllerApi, C> extends AttachApi<T, C> implements
        IControllerApi<T, C>, IViewApi<T>, IShowApi<T>, IHttpApi<T>, IMD5Api, IDataApi<T>, IObjectApi<T>,
        IActionApi<T, IApi>, IUserApi<T>, ILoadImageApi<T>, IConfigApi<T>, IDataTypeApi<T>, IMergeApi<T>,
        IGalleryApi<T>, IFileApi, IParseApi<T>, IHelperApi<T>, IUrlApi, IVsApi<T>, IVosApi<T>, IDirApi {

    private C controller;
    private Handler mainHandler;
    private Context context;
    private Application application;
    private Activity activity;
    private Fragment fragment;
    private Dialog dialog;
    private View rootView;
    private Adapter adapter;
    private INorm norm;

    //api
    private IViewApi viewApi;
    private IShowApi showApi;
    private IDecodeApi decodeApi;
    private IHttpApi httpApi;
    private IMD5Api md5Api;
    private IDataApi dataApi;
    private IObjectApi objectApi;
    private IActionApi actionApi;
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
    private ICameraApi cameraApi;
    private IActivityLifecycleCallbacksApi activityLifecycleApi;
    private IVsApi vsApi;
    private IVosApi vosApi;
    private IDirApi dirApi;
    private IRouteApi routeApi;

    //other
    private String rootViewXml;
    private Integer rootViewResId;
    private MsgSubscriber subscriber;
    private Observable.Transformer transformer;
    private ViewGroup container;
    private Class<? extends View> viewClz;
    private Class<? extends IControllerApi> controllerApiClz;

    private List<OnAddListener> addListeners;

    public CoreControllerApi(C controller) {
        super(controller);
        initController(controller);
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
    public IActivityLifecycleCallbacksApi activityLifecycleApi() {
        if (activityLifecycleApi == null) {
            if (isApplication()) {
                activityLifecycleApi = newActivityLifecycleApi();
            } else {
                if (getApplication() instanceof BaseControllerApiApp) {
                    IControllerApi api = ((BaseControllerApiApp) getApplication()).controllerApi();
                    activityLifecycleApi = api.activityLifecycleApi();
                }
            }
            if (activityLifecycleApi == null) {
                throw new NullPointerException("newActivityLifecycleApi 不能为空");
            }
        }
        return activityLifecycleApi;
    }

    @Override
    public IActivityLifecycleCallbacksApi newActivityLifecycleApi() {
        return null;
    }

    @Override
    public ICameraApi cameraApi() {
        if (cameraApi == null) {
            cameraApi = newCameraApi();
            if (cameraApi == null) {
                throw new NullPointerException("newCameraApi 不能为空");
            }
        }
        return cameraApi;
    }

    @Override
    public ICameraApi newCameraApi() {
        return null;
    }

    @Override
    public IVsApi vsApi() {
        if (vsApi == null) {
            vsApi = newVsApi();
            if (vsApi == null) {
                throw new NullPointerException("newVsApi 不能为空");
            }
        }
        return vsApi;
    }

    @Override
    public IVsApi newVsApi() {
        return null;
    }

    @Override
    public IVosApi vosApi() {
        if (vosApi == null) {
            vosApi = newVosApi();
            if (vosApi == null) {
                throw new NullPointerException("newVosApi 不能为空");
            }
        }
        return vosApi;
    }

    @Override
    public IVosApi newVosApi() {
        return null;
    }

    @Override
    public IDirApi dirApi() {
        if (dirApi == null) {
            dirApi = newDirApi();
            if (dirApi == null) {
                throw new NullPointerException("newVosApi 不能为空");
            }
        }
        return dirApi;
    }

    @Override
    public IDirApi newDirApi() {
        return null;
    }

    @Override
    public IRouteApi routeApi() {
        if (routeApi == null) {
            routeApi = newRouteApi();
            if (routeApi == null) {
                throw new NullPointerException("newRouteApi 不能为空");
            }
        }
        return routeApi;
    }

    @Override
    public IRouteApi newRouteApi() {
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
    public boolean isApplication() {
        return getController() instanceof Application;
    }

    @Override
    public T setActivity(Activity activity) {
        this.activity = activity;
        return getThis();
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
        if (isDialog() && !getDialog().isShowing()
                && !getActivity().isFinishing()) {
            getDialog().show();
        }
        return getThis();
    }

    @Override
    public T dismiss() {
        if (isDialog() && !getActivity().isFinishing()) {
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

    @Override
    public Integer getViewType() {
        return getRootViewResId();
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
    public String getRootViewXml() {
        return rootViewXml;
    }

    @Override
    public XmlResourceParser getLayoutXmlPullParser(String xml) {
        return openAssetsLayoutXmlPullParser(xml);
    }

    @Override
    public ViewGroup onCreateViewGroup(ViewGroup group, @NonNull View rootView) {
        return null;
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public final Class<? extends View> getRootViewClz() {
        return viewClz;
    }

    @Override
    public Class<? extends IControllerApi> getRootViewClzApi() {
        return controllerApiClz;
    }

    @Override
    public final ViewGroup getRootContainer() {
        return container;
    }

    @Override
    public T setRootContainer(ViewGroup container) {
        this.container = container;
        return getThis();
    }

    @Override
    public T setRootViewXml(String xml) {
        this.rootViewXml = xml;
        return getThis();
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
    public void onNorm(INorm norm, int position) {
        this.norm = norm;
    }

    @Override
    public <A extends INorm> A getNorm() {
        return (A) norm;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    }

    @Override
    public void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    @Override
    public T finishActivity(Object obj) {
        activityLifecycleApi().finishActivity(obj);
        return getThis();
    }

    @Override
    public <B> B getFinish(Type... args) {
        String finish = vr(getActivity(), Activity::getIntent,
                i -> i.getStringExtra(FINISH));
        if (!TextUtils.isEmpty(finish)) {
            final String txt = finish.replaceAll(RX, "/");
            if (!TextUtils.isEmpty(args)) {
                int emptyCount = getEmptyLength(txt);
                for (Type type : args) {
                    if (type != null) {
                        if (TextUtils.equals(type, String.class)) {
                            return (B) txt;
                        } else {
                            Object obj = decode(txt, type);
                            if (obj != null && emptyCount ==
                                    getEmptyLength(encode(obj))) {
                                return (B) obj;
                            }
                        }
                    }
                }
                return null;
            }
            return (B) txt;
        }
        return null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public void onStartActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        if (getRootView() != null) {
            getRootView().requestFocus();
            getRootView().requestFocusFromTouch();
        }
        getActivity().getIntent().putExtra(FINISH, (String) null);
    }

    @Override
    public void initAddAction() {
    }

    @Override
    public LayoutInflater inflater() {
        return LayoutInflater.from(getContext());
    }

    @Override
    public XmlResourceParser openFileLayoutXmlPullParser(File dir, String xml) {
        if (TextUtils.check(dir, xml, getContext()) && dir.exists() && dir.isDirectory()) {
            AssetManager asset = getContext().getResources().getAssets();
            try {
                if (asset != null) {
                    Method method = asset.getClass().getMethod("addAssetPath", String.class);
                    int cookie = (Integer) method.invoke(asset, dir.getPath());
                    return asset.openXmlResourceParser(cookie, xml);
                }
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    @Override
    public XmlResourceParser openAssetsLayoutXmlPullParser(String xml) {
        if (TextUtils.check(getContext(), xml)) {
            try {
                return getContext().getResources().getAssets()
                        .openXmlResourceParser(xml);
            } catch (IOException e) {
//                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void notifyDataChanged() {
    }

    @Override
    public void onStartHttpRequest() {
    }

    @Override
    public void onCom(int what, String com, String msg, Object... args) {
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
        onCreateView(inflater(), getRootContainer(), savedInstanceState);
        if (getRootView() != null) {
            executeNon(onCreateViewGroup(getRootContainer(), getRootView()),
                    this::setRootView);
            if (isActivity()) {
                getActivity().setContentView(getRootView());
            } else if (isDialog()) {
                getDialog().setContentView(getRootView());
            }
        }
        onViewCreated(getRootView(), savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        init(savedInstanceState);
        if (getRootViewResId() != null) {
            if (isView()) {
                setRootView(inflater.inflate(getRootViewResId(), container));
            } else {
                setRootView(inflater.inflate(getRootViewResId(), container, false));
            }
        } else if (getRootViewClz() != null) {
            setRootView(getObject(getRootViewClz()
                    , new Class[]{Context.class}
                    , new Object[]{getContext()}));
        } else if (!TextUtils.isEmpty(getRootViewXml())) {
            XmlResourceParser parser = getLayoutXmlPullParser(getRootViewXml());
            if (null != parser) {
                if (isView()) {
                    setRootView(inflater.inflate(parser, container));
                } else {
                    setRootView(inflater.inflate(parser, container, false));
                }
            }
        }
        if (getRootView() instanceof BaseControllerApiView && getRootViewClzApi() != null) {
            ((BaseControllerApiView) getRootView()).init(getRootViewClzApi());
        }
        return getRootView();
    }

    @Override
    public void init(Bundle savedInstanceState) {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initAddAction();
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
        executeViewControllerApi(obj -> obj.onActivityCreated(savedInstanceState));
    }

    @Override
    public void onStart() {
        executeViewControllerApi(IControllerApi::onStart);
    }

    @Override
    public void onResume() {
        executeViewControllerApi(IControllerApi::onResume);
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
        cameraApi().onActivityResult(requestCode, resultCode, data);
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
    public <V extends View> V findViewWithTag(Object tag) {
        return findViewWithTag(getRootView(), tag);
    }

    @Override
    public <V extends View> V findViewById(View rootView, int resId) {
        return (V) viewApi().findViewById(rootView, resId);
    }

    @Override
    public <V extends View> V findViewWithTag(View rootView, Object tag) {
        return (V) viewApi().findViewWithTag(rootView, tag);
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
                    if (((ISerialVersionTagApi) item).isSerialVersionTag(type.toString())) {
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
    public T setImage(ImageView iv, Object path, ImageAction action) {
        viewApi().setImage(iv, path, action);
        return getThis();
    }

    @Override
    public T setImage(ImageView iv, Object path, float rotate) {
        viewApi().setImage(iv, path, rotate);
        return getThis();
    }

    @Override
    public T setImage(ImageView iv, Object path, float rotate, ImageAction action) {
        viewApi().setImage(iv, path, rotate, action);
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
    public <B> void setNewSubscriber(MsgSubscriber<T, B> newSubscriber) {
        httpApi().setNewSubscriber(newSubscriber);
    }

    @Override
    public <B> T observable(B bean, String type, String baseUrl, String path, Map<String, String> map,
                            int what, String tag) {
        httpApi().setNewSubscriber(newSubscriber());
        httpApi().observable(bean, type, baseUrl, path, map, what, tag);
        return getThis();
    }

    @Override
    public <B> T observable(Observable<B> observable, String type, String baseUrl, String path,
                            Map<String, String> map, int what, String tag) {
        httpApi().setNewSubscriber(newSubscriber());
        httpApi().observable(observable, type, baseUrl, path, map, what, tag);
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
    public String getTable() {
        return dataApi().getTable();
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
    public <B> List<B> getBeanData(@NonNull String key, @NonNull Class<? extends List> lCls,
                                   @NonNull Class<B> cls) {
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
    public <A> T initDepartment(A department) {
        userApi().initDepartment(department);
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
    public String getCastgc() {
        return userApi().getCastgc();
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
    public T load(Object path, ImageView iv, ImageAction action) {
        loadImageApi().load(path, iv, action);
        return getThis();
    }

    @Override
    public T load(Object path, ImageView iv, float rotate) {
        loadImageApi().load(path, iv, rotate);
        return getThis();
    }

    @Override
    public T load(Object path, ImageView iv, float rotate, ImageAction action) {
        loadImageApi().load(path, iv, rotate, action);
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
        executeViewControllerApi(getRootView(), api);
    }

    private void executeViewControllerApi(View view, IObjAction<IControllerApi> api) {
        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            for (int i = 0; i < vg.getChildCount(); i++) {
                View child = vg.getChildAt(i);
                if (child instanceof BaseControllerApiView) {
                    executeNon(((BaseControllerApiView) child).controllerApi(), api);
                }
                executeViewControllerApi(child, api);
            }
        }
    }


    @Override
    public T copy() {
        C c = getController();
        Class clz = c == null ? Object.class : c.getClass();
        return (T) ObjectUtil.getObject(getClass(), new Class[]{clz}, new Object[]{c});
    }

    @Override
    public <A> T add(Class<A> root, IPathMsgAction<A> action) {
        parseApi().add(root, action);
        return getThis();
    }

    @Override
    public <A> T add(TypeToken<A> root, IPathMsgAction<A> action) {
        parseApi().add(root, action);
        return getThis();
    }

    @Override
    public <A, B> T add(Class<A> root, Class<B> clz, IPathMsgAction<B> action) {
        parseApi().add(root, clz, action);
        return getThis();
    }

    @Override
    public <A, B> T addList(Class<A> root, Class<B> clz, IPathMsgAction<List<B>> action) {
        parseApi().addList(root, clz, action);
        return getThis();
    }

    @Override
    public <A, B> T add(Class<A> root, TypeToken<B> token, IPathMsgAction<B> action) {
        parseApi().add(root, token, action);
        return getThis();
    }

    @Override
    public <A, B> T add(TypeToken<A> root, Class<B> clz, IPathMsgAction<B> action) {
        parseApi().add(root, clz, action);
        return getThis();
    }

    @Override
    public <A, B> T addList(TypeToken<A> root, Class<B> clz, IPathMsgAction<List<B>> action) {
        parseApi().addList(root, clz, action);
        return getThis();
    }

    @Override
    public <A, B> T add(TypeToken<A> root, TypeToken<B> token, IPathMsgAction<B> action) {
        parseApi().add(root, token, action);
        return getThis();
    }

    @Override
    public T clearParse() {
        parseApi().clearParse();
        return getThis();
    }

    @Override
    public <B> T parse(B bean) {
        parseApi().parse(bean);
        return getThis();
    }

    @Override
    public List<ParseBean> getParseData() {
        return parseApi().getParseData();
    }

    @Override
    public T init(String type, String baseUrl, String path, Map<String, String> map, int what, String msg) {
        parseApi().init(type, baseUrl, path, map, what, msg);
        return getThis();
    }

    @Override
    public <B> void get(String type, String url, B obj, String path, IObjAction<Map<String, Object>> action,
                        int what, String tag) {
        api().get(type, url, obj, path, action, what, tag);
    }

    @Override
    public <B> void post(String type, String url, B obj, String path, IObjAction<Map<String, Object>> action,
                         int what, String tag) {
        api().post(type, url, obj, path, action, what, tag);
    }

    @Override
    public <B> void jsonPost(String type, String url, B obj, String path, IObjAction<Map<String, Object>> action,
                             int what, String tag) {
        api().jsonPost(type, url, obj, path, action, what, tag);
    }

    @Override
    public <B> void bodyPost(String type, String url, B obj, String path, IObjAction<Map<String, Object>> action,
                             int what, String tag, IProgressListener listener) {
        api().bodyPost(type, url, obj, path, action, what, tag, listener);
    }

    @Override
    public void get(String type, String url, String path, IObjAction<Map<String, Object>> action, int what,
                    String tag) {
        api().get(type, url, path, action, what, tag);
    }

    @Override
    public void post(String type, String url, String path, IObjAction<Map<String, Object>> action, int what,
                     String tag) {
        api().post(type, url, path, action, what, tag);
    }

    @Override
    public void jsonPost(String type, String url, String path, IObjAction<Map<String, Object>> action, int what,
                         String tag) {
        api().jsonPost(type, url, path, action, what, tag);
    }

    @Override
    public void bodyPost(String type, String url, String path, IObjAction<Map<String, Object>> action, int what,
                         String tag, IProgressListener listener) {
        api().bodyPost(type, url, path, action, what, tag, listener);
    }

    @Override
    public Map<String, String> getActionMap(IObjAction<Map<String, Object>> action) {
        return api().getActionMap(action);
    }

    @Override
    public <AA> T vs(AA aa, IObjAction<AA> action) {
        vsApi().vs(aa, action);
        return getThis();
    }

    @Override
    public <AA, BB> BB vr(AA aa, IReturnAction<AA, BB> ab) {
        return (BB) vsApi().vr(aa, ab);
    }

    @Override
    public <AA, BB> T vs(AA aa, IReturnAction<AA, BB> ab, IObjAction<BB> action) {
        vsApi().vs(aa, ab, action);
        return getThis();
    }

    @Override
    public <AA, BB, CC> CC vr(AA aa, IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc) {
        return (CC) vsApi().vr(aa, ab, bc);
    }

    @Override
    public <AA, BB, CC> T vs(AA aa, IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc, IObjAction<CC> action) {
        vsApi().vs(aa, ab, bc, action);
        return getThis();
    }

    @Override
    public <AA, BB, CC, DD> DD vr(AA aa, IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc, IReturnAction<CC, DD> cd) {
        return (DD) vsApi().vr(aa, ab, bc, cd);
    }

    @Override
    public <AA, BB, CC, DD> T vs(AA aa, IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc, IReturnAction<CC, DD> cd, IObjAction<DD> action) {
        vsApi().vs(aa, ab, bc, cd, action);
        return getThis();
    }

    @Override
    public <AA, BB, CC, DD, EE> EE vr(AA aa, IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc, IReturnAction<CC, DD> cd, IReturnAction<DD, EE> de) {
        return (EE) vsApi().vr(aa, ab, bc, cd, de);
    }

    @Override
    public <AA, BB, CC, DD, EE> T vs(AA aa, IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc, IReturnAction<CC, DD> cd, IReturnAction<DD, EE> de, IObjAction<EE> action) {
        vsApi().vs(aa, ab, bc, cd, de, action);
        return getThis();
    }

    @Override
    public <AA, BB, CC, DD, EE, FF> FF vr(AA aa, IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc, IReturnAction<CC, DD> cd, IReturnAction<DD, EE> de, IReturnAction<EE, FF> ef) {
        return (FF) vsApi().vr(aa, ab, bc, cd, de, ef);
    }

    @Override
    public <AA> AA getVo() {
        return (AA) vosApi().getVo();
    }

    @Override
    public <AA> AA newVo() {
        return null;
    }

    @Override
    public <AA> T vos(IObjAction<AA> action) {
        vosApi().vos(action);
        return getThis();
    }

    @Override
    public <AA, BB> BB vor(IReturnAction<AA, BB> ab) {
        return (BB) vosApi().vor(ab);
    }

    @Override
    public <AA, BB> T vos(IReturnAction<AA, BB> ab, IObjAction<BB> action) {
        vosApi().vos(ab, action);
        return getThis();
    }

    @Override
    public <AA, BB, CC> CC vor(IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc) {
        return (CC) vosApi().vor(ab, bc);
    }

    @Override
    public <AA, BB, CC> T vos(IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc, IObjAction<CC> action) {
        vosApi().vos(ab, bc, action);
        return getThis();
    }

    @Override
    public <AA, BB, CC, DD> DD vor(IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc, IReturnAction<CC, DD> cd) {
        return (DD) vosApi().vor(ab, bc, cd);
    }

    @Override
    public <AA, BB, CC, DD> T vos(IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc, IReturnAction<CC, DD> cd, IObjAction<DD> action) {
        vosApi().vos(ab, bc, cd, action);
        return getThis();
    }

    @Override
    public <AA, BB, CC, DD, EE> EE vor(IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc, IReturnAction<CC, DD> cd, IReturnAction<DD, EE> de) {
        return (EE) vosApi().vor(ab, bc, cd, de);
    }

    @Override
    public <AA, BB, CC, DD, EE> T vos(IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc, IReturnAction<CC, DD> cd, IReturnAction<DD, EE> de, IObjAction<EE> action) {
        vosApi().vos(ab, bc, cd, de, action);
        return getThis();
    }

    @Override
    public <AA, BB, CC, DD, EE, FF> FF vor(IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc, IReturnAction<CC, DD> cd, IReturnAction<DD, EE> de, IReturnAction<EE, FF> ef) {
        return (FF) vosApi().vor(ab, bc, cd, de, ef);
    }

    @Override
    public boolean hasSdCard() {
        return dirApi().hasSdCard();
    }

    @Override
    public File getRootDir(String dir) {
        return dirApi().getRootDir(dir);
    }

    @Override
    public File getRootDir() {
        return dirApi().getRootDir();
    }

    @Override
    public T execute(IAction action) {
        return super.execute(action);
    }

}