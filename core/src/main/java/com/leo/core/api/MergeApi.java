package com.leo.core.api;

import com.leo.core.R;
import com.leo.core.iapi.IMergeApi;
import com.leo.core.util.TextUtils;

public class MergeApi<T extends MergeApi> implements IMergeApi<T> {

    @Override
    public <R> R[] newArray(Class clz, int length) {
        if(Class.class.isAssignableFrom(clz)){
            return (R[]) new Class[length];
        } else if(int.class.isAssignableFrom(clz) || Integer.class.isAssignableFrom(clz)){
            return (R[]) new Integer[length];
        } else if(String.class.isAssignableFrom(clz)){
            return (R[]) new String[length];
        }
        return null;
    }

    @Override
    public <R> R[] merge(Class<R> clz, boolean end, R[] args, R... args1) {
        if (TextUtils.isEmpty(args)) {
            return args1;
        } else if (TextUtils.isEmpty(args1)) {
            return args;
        } else {
            R[] array = newArray(clz, args.length + args1.length);
            if (!TextUtils.isEmpty(array)) {
                if (end) {
                    for (int i = 0; i < args1.length; i++) {
                        array[i] = args1[i];
                    }
                    for (int i = 0; i < args.length; i++) {
                        array[i + args1.length] = args[i];
                    }
                } else {
                    for (int i = 0; i < args.length; i++) {
                        array[i] = args[i];
                    }
                    for (int i = 0; i < args1.length; i++) {
                        array[i + args.length] = args1[i];
                    }
                }
            }
            return array;
        }
    }

}
