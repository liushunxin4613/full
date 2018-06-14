package com.ylink.fullgoal.fg;

public class MImageFg {

    /**
     * amount : 11
     * imageID : 4028948163e2b8180163e3793f530004
     * imageURL : http://192.168.8.108:8088/ssca/public/20180609\001\1528529764086527.jpg
     * invoiceUse : 交通
     */

    private String amount;
    private String imageID;
    private String imageURL;
    private String invoiceUse;
    private transient Object photo;
    private transient boolean show;

    public MImageFg(Object photo, String invoiceUse) {
        this.photo = photo;
        this.invoiceUse = invoiceUse;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getInvoiceUse() {
        return invoiceUse;
    }

    public void setInvoiceUse(String invoiceUse) {
        this.invoiceUse = invoiceUse;
    }

    //************************ 私有的 ************************

    public Object getPhoto() {
        if (photo instanceof Double) {
            photo = ((Double) photo).intValue();
        }
        if(photo == null){
            return getImageURL();
        }
        return photo;
    }

    public void setPhoto(Object photo) {
        this.photo = photo;
    }

    public boolean isShow() {
        return show;
    }

    public MImageFg setShow(boolean show) {
        this.show = show;
        return this;
    }

}