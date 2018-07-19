package com.leo.core.api.api;

import android.app.Activity;
import android.os.Bundle;

import com.leo.core.iapi.api.IActivityLifecycleCallbacksApi;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.util.GsonDecodeUtil;

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
    public void finishActivity(Object obj) {
        vs(getItem(getCount() - 2), Activity::getIntent, in
                -> in.putExtra(FINISH, GsonDecodeUtil.encode(obj)));
        vs(getItem(getCount() - 1), Activity::finish);
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