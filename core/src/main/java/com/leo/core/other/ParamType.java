package com.leo.core.other;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ParamType implements ParameterizedType{

    private Type raw;
    private Type[] arguments;

    public static ParamType get(Type raw, Type... arguments){
        return new ParamType(raw, arguments);
    }

    public ParamType(Type raw, Type... arguments) {
        this.raw = raw;
        this.arguments = arguments;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return arguments;
    }

    @Override
    public Type getRawType() {
        return raw;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(30 * (arguments.length + 1));
        stringBuilder.append(typeToString(raw));
        if (arguments.length == 0) {
            return stringBuilder.toString();
        }
        stringBuilder.append("<").append(typeToString(arguments[0]));
        for (int i = 1; i < arguments.length; i++) {
            stringBuilder.append(", ").append(typeToString(arguments[i]));
        }
        return stringBuilder.append(">").toString();
    }

    private String typeToString(Type type) {
        return type instanceof Class ? ((Class<?>) type).getName() : type.toString();
    }

}
