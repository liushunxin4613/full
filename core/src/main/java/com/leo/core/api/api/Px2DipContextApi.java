package com.leo.core.api.api;

/**
 * px转换dip
 */
public class Px2DipContextApi extends ADisplayApi<Px2DipContextApi> {

    @Override
    public Integer convert(Float in, String param) {
        return null;
    }

    @Override
    public float convert(Float in) {
        scale = getDisplayMetrics().density;
        return in / scale + 0.5f;
    }

}
