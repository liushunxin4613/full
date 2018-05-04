package com.leo.core.util;

/**
 * 父子类泛型工具
 */
public class RPUtil {

    public static <R extends P, P> R getObj(P obj) {
        try {
            return (R) obj;
        } catch (Exception e) {
            return null;
        }
    }

}
