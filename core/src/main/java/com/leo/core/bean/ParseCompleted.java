package com.leo.core.bean;

import com.leo.core.util.TextUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ParseCompleted {

    private List<String> data;

    public ParseCompleted() {
        this.data = new ArrayList<>();
    }

    public void add(Type type) {
        if (type != null) {
            this.data.add(getName(type));
        }
    }

    public boolean contains(Type type) {
        if (type != null) {
            String typeName = getName(type);
            for (String item : data) {
                if(TextUtils.equals(typeName, item)){
                    return true;
                }
            }
        }
        return false;
    }

    public List<String> getData() {
        return data;
    }

    private String getName(Type type) {
        return type == null ? null : (type instanceof Class
                ? ((Class) type).getName() : type.toString());
    }

}
