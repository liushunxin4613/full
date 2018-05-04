package com.leo.core.api;

/**
 * px转换dip
 */
public class Px2DipContextApi extends ADisplayApi<Px2DipContextApi> {

    @Override
    public Integer convert(Float in, String param) {
        return null;
    }

    @Override
    public int convert(Float in) {
        scale = getDisplayMetrics().density;
        return (int) (in / scale + 0.5f);
    }

}
