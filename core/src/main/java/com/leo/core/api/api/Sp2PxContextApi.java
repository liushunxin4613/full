package com.leo.core.api.api;

/**
 * sp转换px
 */
public class Sp2PxContextApi extends ADisplayApi<Sp2PxContextApi> {

    @Override
    public Integer convert(Float in, String param) {
        return null;
    }

    @Override
    public float convert(Float in) {
        scale = getDisplayMetrics().density;
        return in * scale + 0.5f;
    }
}