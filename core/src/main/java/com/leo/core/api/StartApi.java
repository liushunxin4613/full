package com.leo.core.api;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.leo.core.api.core.ThisApi;
import com.leo.core.iapi.IObjectApi;
import com.leo.core.iapi.IStartApi;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.LogUtil;
import com.leo.core.util.TextUtils;

public class StartApi<T extends StartApi> extends ThisApi<T> implements IStartApi<T> {

    private Activity activity;
    private IObjectApi objectApi;

    public StartApi(Activity activity, IObjectApi objectApi) {
        this.activity = activity;
        this.objectApi = objectApi;
    }

    @Override
    public Activity getActivity() {
        return activity;
    }

    @SafeVarargs
    @Override
    public final Intent getIntent(Class<? extends Activity> clz, Class<? extends IControllerApi>... args) {
        return getIntent(clz, null, args);
    }

    @SafeVarargs
    @Override
    public final T startActivity(Class<? extends Activity> clz, Class<? extends IControllerApi>... args) {
        return startActivity(clz, null, args);
    }

    @SafeVarargs
    @Override
    public final Intent getIntent(Class<? extends Activity> clz, Bundle bundle, Class<? extends IControllerApi>... args) {
        if(getActivity() != null && clz != null){
            int count = TextUtils.count(args);
            Intent intent = new Intent(getActivity(), clz);
            if(bundle != null){
                intent.putExtras(bundle);
            }
            if(count > 0){
                intent.putExtra(CONTROLLER_API, args[0]);
            }
            if(count > 1){
                intent.putExtra(ROOT_VIEW_CLZ_API, args[1]);
            }
            return intent;
        }
        return null;
    }

    @SafeVarargs
    @Override
    public final T startActivity(Class<? extends Activity> clz, Bundle bundle, Class<? extends IControllerApi>... args) {
        Intent intent = getIntent(clz, bundle, args);
        if(intent != null){
            getActivity().startActivity(intent);
        }
        return getThis();
    }

    @SafeVarargs
    @Override
    public final T startFinishActivity(Class<? extends Activity> clz, Class<? extends IControllerApi>... args) {
        return startFinishActivity(clz, null, args);
    }

    @SafeVarargs
    @Override
    public final T startFinishActivity(Class<? extends Activity> clz, Bundle bundle, Class<? extends IControllerApi>... args) {
        if(getActivity() != null && clz != null){
            startActivity(clz, bundle, args);
            getActivity().finish();
        }
        return getThis();
    }

    @Override
    public Bundle getBundle(Class<? extends IControllerApi>... args) {
        return getBundle(null, args);
    }

    @Override
    public Fragment getFragment(Class<? extends Fragment> clz, Class<? extends IControllerApi>... args) {
        return getFragment(clz, null, args);
    }

    @Override
    public Bundle getBundle(Bundle bundle, Class<? extends IControllerApi>... args) {
        int count = TextUtils.count(args);
        if(bundle == null){
            bundle = new Bundle();
        }
        if(count > 0){
            bundle.putSerializable(CONTROLLER_API, args[0]);
        }
        if(count > 1){
            bundle.putSerializable(ROOT_VIEW_CLZ_API, args[1]);
        }
        return bundle;
    }

    @Override
    public Fragment getFragment(Class<? extends Fragment> clz, Bundle bundle, Class<? extends IControllerApi>... args) {
        if(clz != null){
            Fragment fragment = (Fragment) objectApi.getObject(clz);
            if(fragment != null){
                fragment.setArguments(getBundle(bundle, args));
                return fragment;
            }
        }
        return null;
    }

}
