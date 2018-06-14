package com.leo.core.api.api;

/**
 * dip转换px
 */
public class Dip2PxContextApi extends ADisplayApi<Dip2PxContextApi> {

    @Override
    public Integer convert(Float in, String param) {
        return null;
    }

    @Override
    public int convert(Float in) {
        scale = getDisplayMetrics().density;
        return (int) (in * scale + 0.5f);
    }

}