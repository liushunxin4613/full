package com.ylink.fullgoal.vo;

import com.leo.core.iapi.inter.IAction;
import com.leo.core.iapi.inter.IProgressListener;
import com.leo.core.iapi.inter.IbooleanAction;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.fg.ImageDetailFg;

public class ImageVo implements IProgressListener {

    public final static int FILTER_YB = 0;
    public final static int FILTER_JTF = 1;
    public final static int FILTER_ZSF = 2;
    public final static int FILTER_CCJPF = 3;

    private final static String FILTERS[] = {
            "一般",
            "交通费",
            "住宿费",
            "车船机票费",
    };

    public static int getKey(String value) {
        if (!TextUtils.isEmpty(FILTERS)) {
            for (int i = 0; i < FILTERS.length; i++) {
                if (TextUtils.equals(FILTERS[i], value)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static String getValue(int key) {
        if (key > -1 && key < FILTERS.length) {
            return FILTERS[key];
        }
        return null;
    }

    public int getKey() {
        return getKey(getInvoiceUse());
    }

    /**
     * imageID : 4028948163e2b8180163e3793f530004
     * imageURL : http://192.168.8.108:8088/ssca/public/20180609\001\1528529764086527.jpg
     * invoiceUse : 交通费
     */

    private String imageID;
    private String imageURL;
    private String invoiceUse;
    private String serialNo;
    private String imageType;
    private ImageDetailFg detail;
    private Object photo;
    private String path;
    private transient boolean show;
    private transient boolean error;
    private transient int invoiceUseType;
    private transient IProgressListener listener;
    private transient IbooleanAction errorAction;
    private transient IAction action;

    public ImageVo() {
    }

    public ImageVo(Object photo, String invoiceUse) {
        this.photo = photo;
        this.invoiceUse = invoiceUse;
    }

    public ImageVo(String path, int invoiceUseType) {
        this.path = path;
        this.photo = path;
        this.invoiceUseType = invoiceUseType;
        this.invoiceUse = getValue(invoiceUseType);
    }

    public ImageVo setProgressListener(IAction action, IProgressListener listener) {
        this.action = action;
        this.listener = listener;
        this.init();
        return this;
    }

    public void init(){
        if(action != null && !TextUtils.check(imageID) && !isError()){
            action.execute();
        }
    }

    public void setErrorAction(IbooleanAction errorAction) {
        this.errorAction = errorAction;
    }

    public void onError(boolean error) {
        this.error = error;
        if (errorAction != null) {
            errorAction.execute(error);
        }
    }

    public int getInvoiceUseType() {
        return invoiceUseType;
    }

    public boolean isError() {
        return error;
    }

    public String getPath() {
        return path;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getAmount(){
        return TextUtils.check(getDetail()) ? getDetail().getAmount() : null;
    }

    public void setAmount(String amount){
       if(TextUtils.check(getDetail())){
           getDetail().setAmount(amount);
       }
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
        this.setPhoto(imageURL);
    }

    public String getInvoiceUse() {
        return invoiceUse;
    }

    public void setInvoiceUse(String invoiceUse) {
        this.invoiceUse = invoiceUse;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public ImageDetailFg getDetail() {
        return detail;
    }

    public void setDetail(ImageDetailFg detail) {
        this.detail = detail;
    }

    //************************ 私有的 ************************

    public Object getBindPhoto() {
        if (photo instanceof Double) {
            photo = ((Double) photo).intValue();
        }
        if (photo == null) {
            return getImageURL();
        }
        return photo;
    }

    public Object getPhoto(){
        return photo;
    }

    public void setPhoto(Object photo) {
        this.photo = photo;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public boolean isShow() {
        return show;
    }

    public ImageVo setShow(boolean show) {
        this.show = show;
        return this;
    }

    @Override
    public void onLoading(long progress, long total) {
        if (listener != null) {
            listener.onLoading(progress, total);
        }
    }

}