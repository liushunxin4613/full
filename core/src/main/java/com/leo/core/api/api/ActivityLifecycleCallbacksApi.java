package com.leo.core.api.api;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.leo.core.core.BaseControllerApiActivity;
import com.leo.core.iapi.api.IActivityLifecycleCallbacksApi;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.inter.IbolAction;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static com.leo.core.config.Config.FINISH;
import static com.leo.core.util.TextUtils.check;

public class ActivityLifecycleCallbacksApi extends VsApi<ActivityLifecycleCallbacksApi>
        implements IActivityLifecycleCallbacksApi {

    private Stack<Activity> stacks;

    public ActivityLifecycleCallbacksApi() {
        stacks = new Stack<>();
    }

    @Override
    public Stack<Activity> getActivityStack() {
        return stacks;
    }

    @Override
    public <A extends Activity> void remove(Class<A> clz, IObjAction<A> action) {
        if (check(clz)) {
            List<Activity> data = new ArrayList<>();
            execute(stacks, activity -> {
                if (clz.isInstance(activity)) {
                    data.add(activity);
                }
            });
            execute(data, a -> executeNon(a, activity -> {
                if (action != null) {
                    action.execute((A) activity);
                }
                stacks.remove(activity);
            }));
        }
    }

    @Override
    public int getCount() {
        return getActivityStack().size();
    }

    @Override
    public Activity getItem(int position) {
        if (position >= 0 && position < getCount()) {
            return getActivityStack().get(position);
        }
        return null;
    }

    @Override
    public void finishActivity(IbolAction<Activity> action) {
        if (TextUtils.check(action)) {
            execute(getActivityStack(), obj -> {
                if (action.execute(obj)) {
                    obj.finish();
                }
            });
        }
    }

    @Override
    public void finishActivity(Object obj) {
        vs(getItem(getCount() - 2), activity -> putExtra(activity, obj));
        vs(getItem(getCount() - 1), Activity::finish);
    }

    private void putExtra(@NonNull Activity activity, Object obj) {
        vs(activity, Activity::getIntent, intent -> intent.putExtra(FINISH, TextUtils.toGsonString(obj)));
    }

    @Override
    public void finishActivity(Class<? extends IControllerApi> clz, Object obj) {
        for (int i = getCount() - 1; i >= 0; i--) {
            Activity activity = getItem(i);
            if (activity instanceof BaseControllerApiActivity) {
                IControllerApi api = ((BaseControllerApiActivity) activity).controllerApi();
                if (TextUtils.checkNull(clz, api) && clz.isInstance(api)) {
                    putExtra(activity, obj);
                    return;
                }
            }
            activity.finish();
        }
    }

    @SafeVarargs
    @Override
    public final void finishActivity(Object obj, Class<? extends IControllerApi>... args) {
        for (int i = getCount() - 1; i >= 0; i--) {
            Activity activity = getItem(i);
            if (activity instanceof BaseControllerApiActivity) {
                IControllerApi api = ((BaseControllerApiActivity) activity).controllerApi();
                int position = getCount() - 1 - i;
                if (position >= 0 && position < args.length && args[position] != null
                        && args[position].isInstance(api)) {
                    activity.finish();
                } else {
                    putExtra(activity, obj);
                    return;
                }
            }
        }
    }

    @Override
    public void finishAllActivity() {
        for (int i = getCount() - 1; i >= 0; i--) {
            executeNon(getItem(i), Activity::finish);
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        getActivityStack().add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        getActivityStack().remove(activity);
    }

}