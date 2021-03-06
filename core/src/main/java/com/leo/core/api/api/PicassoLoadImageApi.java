package com.leo.core.api.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.leo.core.api.core.ThisApi;
import com.leo.core.iapi.api.ILoadImageApi;
import com.leo.core.iapi.inter.ImageAction;
import com.leo.core.util.TextUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;
import java.util.Map;

public class PicassoLoadImageApi<T extends PicassoLoadImageApi> extends ThisApi<T> implements ILoadImageApi<T> {

    private final static int CALLBACK_SUCCESS = 200;
    public final static int CALLBACK_ERROR = 201;

    private Picasso picasso;
    private Integer defaultRes;
    private Integer errorRes;
    private boolean cacheEnable;
    private Map<String, ?> map;

    public PicassoLoadImageApi(Context context, Integer defaultRes, Integer errorRes) {
        this.picasso = Picasso.with(context);
        this.defaultRes = defaultRes;
        this.errorRes = errorRes;
        this.cacheEnable = true;
    }

    @Override
    public Picasso getDrive() {
        return picasso;
    }

    @Override
    public Integer getDefaultRes() {
        return defaultRes;
    }

    @Override
    public Integer getErrorRes() {
        return errorRes;
    }

    @Override
    public T setCacheEnable(boolean enable) {
        this.cacheEnable = enable;
        return getThis();
    }

    @Override
    public T init(Map<String, ?> map) {
        this.map = map;
        return getThis();
    }

    @Override
    public T load(Object path, ImageView iv) {
        load(path, iv, -1, null);
        return getThis();
    }

    @Override
    public T load(Object path, ImageView iv, ImageAction action) {
        load(path, iv, -1, action);
        return getThis();
    }

    @Override
    public T load(Object path, ImageView iv, float rotate) {
        load(path, iv, rotate, null);
        return getThis();
    }

    @Override
    public T load(Object path, ImageView iv, float rotate, ImageAction action) {
        if (iv != null) {
            RequestCreator rc = getRc(path);
            init(rc, rotate);
            if (rc != null) {
                rc.into(iv, new Callback() {
                    @Override
                    public void onSuccess() {
                        if (action != null) {
                            action.execute(path, iv, rotate, true);
                        }
                    }

                    @Override
                    public void onError() {
                        if (action != null) {
                            action.execute(path, iv, rotate, false);
                        }
                    }
                });
            }
        }
        return getThis();
    }

    //私有的

    private void switchParam(String key, Object value) {
        if (!TextUtils.isEmpty(key) && value != null) {
            switch (key) {
                default:
                case "":
                    break;
            }
        }
    }

    private void init(RequestCreator rc, float rotate) {
        if (rc != null) {
            rc.config(Bitmap.Config.RGB_565);
            if (!cacheEnable) {
                rc.skipMemoryCache();
            }
            if (getDefaultRes() != null) {
                rc.placeholder(getDefaultRes());
            }
            if (getErrorRes() != null) {
                rc.error(getErrorRes());
            }
            if(rotate >= 0){
                rc.rotate(rotate);
            }
            if (!TextUtils.isEmpty(map)) {
                for (Map.Entry<String, ?> entry : map.entrySet()) {
                    switchParam(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    private RequestCreator getRc(Object path) {
        if (path instanceof String) {
            String str = (String) path;
            if (str.startsWith("/")) {
                return getDrive().load(new File(str));
            } else if (!TextUtils.isHttpUrl(str)) {
                str = null;
            }
            return getDrive().load(str);
        } else if (path instanceof Integer) {
            if ((int) path != 0) {
                return getDrive().load((int) path);
            }
        } else if (path instanceof Uri) {
            return getDrive().load((Uri) path);
        } else if (path instanceof File) {
            return getDrive().load((File) path);
        }
        return null;
    }

}
