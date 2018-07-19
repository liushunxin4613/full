package com.ylink.fullgoal.config;

import android.content.res.XmlResourceParser;
import android.view.View;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IBindControllerApi;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBiBean;

public class ViewBean extends SurfaceBiBean<ViewBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, ViewBean> newDefApi() {
        return new ViewBi();
    }

    private String tv0;
    private String tv1;
    private String tv2;
    private String tv3;
    private String tv4;
    private String tv5;
    private String tv6;
    private String tv7;
    private String tv8;
    private String tv9;

    private transient View.OnClickListener onClickListener;

    private String get(String[] args, int position) {
        return position >= 0 && position <
                TextUtils.count(args) ? args[position] : null;
    }

    ViewBean(XmlResourceParser parser){
        setApiXmlResourceParser(parser);
    }

    ViewBean(String... args) {
        this.tv0 = get(args, 0);
        this.tv1 = get(args, 1);
        this.tv2 = get(args, 2);
        this.tv3 = get(args, 3);
        this.tv4 = get(args, 4);
        this.tv5 = get(args, 5);
        this.tv6 = get(args, 6);
        this.tv7 = get(args, 7);
        this.tv8 = get(args, 8);
        this.tv9 = get(args, 9);
    }

    public void update(String key, String value){
        if(TextUtils.check(key)){
            switch (key){
                case "tv0":
                    setTv0(value);
                    break;
                case "tv1":
                    setTv1(value);
                    break;
                case "tv2":
                    setTv2(value);
                    break;
                case "tv3":
                    setTv3(value);
                    break;
                case "tv4":
                    setTv4(value);
                    break;
                case "tv5":
                    setTv5(value);
                    break;
                case "tv6":
                    setTv6(value);
                    break;
                case "tv7":
                    setTv7(value);
                    break;
                case "tv8":
                    setTv8(value);
                    break;
                case "tv9":
                    setTv9(value);
                    break;
            }
        }
    }

    public String getTv0() {
        return tv0;
    }

    public void setTv0(String tv0) {
        this.tv0 = tv0;
    }

    public String getTv1() {
        return tv1;
    }

    public void setTv1(String tv1) {
        this.tv1 = tv1;
    }

    public String getTv2() {
        return tv2;
    }

    public void setTv2(String tv2) {
        this.tv2 = tv2;
    }

    public String getTv3() {
        return tv3;
    }

    public void setTv3(String tv3) {
        this.tv3 = tv3;
    }

    public String getTv4() {
        return tv4;
    }

    public void setTv4(String tv4) {
        this.tv4 = tv4;
    }

    public String getTv5() {
        return tv5;
    }

    public void setTv5(String tv5) {
        this.tv5 = tv5;
    }

    public String getTv6() {
        return tv6;
    }

    public void setTv6(String tv6) {
        this.tv6 = tv6;
    }

    public String getTv7() {
        return tv7;
    }

    public void setTv7(String tv7) {
        this.tv7 = tv7;
    }

    public String getTv8() {
        return tv8;
    }

    public void setTv8(String tv8) {
        this.tv8 = tv8;
    }

    public String getTv9() {
        return tv9;
    }

    public void setTv9(String tv9) {
        this.tv9 = tv9;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnBVClickListener<ViewBean> listener) {
        this.onClickListener = getOnBVClickListener(listener);
    }

}