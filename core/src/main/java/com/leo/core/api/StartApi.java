package com.leo.core.api;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.api.main.HasCoreControllerApi;
import com.leo.core.config.Config;
import com.leo.core.iapi.IStartApi;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.TextUtils;

public class StartApi<T extends StartApi> extends HasCoreControllerApi<T> implements IStartApi<T> {

    public StartApi(CoreControllerApi controllerApi) {
        super(controllerApi);
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
        if(controllerApi().getActivity() != null && clz != null){
            int count = TextUtils.count(args);
            Intent intent = new Intent(controllerApi().getActivity(), clz);
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
            controllerApi().getActivity().startActivity(intent);
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
        if(controllerApi().getActivity() != null && clz != null){
            saveData(Config.LAST_FINISH_ACTIVITY, (String) getExecute(controllerApi().getActivity(),
                    activity -> activity.getClass().getName()));
            controllerApi().getActivity().finish();
            startActivity(clz, bundle, args);
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
            Fragment fragment = (Fragment) controllerApi().getObject(clz);
            if(fragment != null){
                fragment.setArguments(getBundle(bundle, args));
                return fragment;
            }
        }
        return null;
    }

}
