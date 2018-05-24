package com.leo.core.other;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ParamType<A> implements ParameterizedType {

    private Type rawType;
    private Type ownerType;
    private Type[] arguments;

    public ParamType(Class<A> clz, Type... arguments) {
        this.rawType = clz;
        this.arguments = arguments;
    }

    public static <A> ParamType get(Class<A> clz, Type... arguments) {
        return new ParamType(clz, arguments);
    }

    @Override
    public Type[] getActualTypeArguments() {
        return arguments;
    }

    @Override
    public Type getRawType() {
        return rawType;
    }

    @Override
    public Type getOwnerType() {
        return ownerType;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(30 * (arguments.length + 1));
        stringBuilder.append(typeToString(rawType));
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
