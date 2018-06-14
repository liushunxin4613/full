package com.leo.core.api.api;

/**
 * px转换sp
 */
public class Px2SpContextApi extends ADisplayApi<Px2SpContextApi> {

    @Override
    public Integer convert(Float in, String param) {
        return null;
    }

    @Override
    public int convert(Float in) {
        scale = getDisplayMetrics().scaledDensity;
        return (int) (in / scale + 0.5f);
    }
}