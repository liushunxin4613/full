package com.ylink.fullgoal.hb;

public class ImageHb extends SerialNoHb {

    //普通
    public final static String IMAGE_NONE = "一般";
    //交通
    public final static String IMAGE_JT = "交通";
    //住宿
    public final static String IMAGE_ZS = "住宿";
    //车船机票
    public final static String IMAGE_CCJP = "车船机票";

    private String id;
    private String type;
    private String url;

    public ImageHb(String id, String url) {
        this(id, IMAGE_NONE, url);
    }

    public ImageHb(String id, String type, String url) {
        this.id = id;
        this.type = type;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
