package com.leo.core.bean;

import android.support.v4.app.Fragment;
import android.widget.RadioButton;

import com.leo.core.iapi.main.IControllerApi;

import java.util.Random;

public class RadioButtonBean {

    private int id;
    private int drawableResId;
    private String text;
    private RadioButton button;
    private int position;
    private Class<? extends IControllerApi> clz;
    private Fragment fragment;

    public RadioButtonBean(int drawableResId, String text) {
        this.id = getRandomId();
        this.drawableResId = drawableResId;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDrawableResId() {
        return drawableResId;
    }

    public void setDrawableResId(int drawableResId) {
        this.drawableResId = drawableResId;
    }

    public RadioButton getButton() {
        return button;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setButton(RadioButton button) {
        this.button = button;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Class<? extends IControllerApi> getClz() {
        return clz;
    }

    public void setClz(Class<? extends IControllerApi> clz) {
        this.clz = clz;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    //私有的

    private int getRandomId(){
        return new Random().nextInt(Integer.MAX_VALUE);
    }

}
