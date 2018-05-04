package com.ylink.fullgoal.bean;

import android.view.View;

import com.leo.core.bean.BaseApiBean;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;

import java.util.List;

public class ItemBean extends BaseApiBean {

    /**
     * name : user
     * nickname : 事由
     * must : 1
     * type : sp
     * line : 4
     * arrays : ["车船票","住宿费","燃油费"]
     */

    @Override
    public Integer getApiType() {
        if(!TextUtils.isEmpty(getType())){
            switch (getType()){
                case "tv"://TextView
                    return R.layout.l_tv;
                case "et"://EditText
                    return R.layout.l_et;
                case "sp"://滑动数组
                    return R.layout.l_return;
                case "gtv"://详情
                    return null;
            }
        }
        return null;
    }

    private String name;
    private String nickname;
    private String type;
    private String line;
    private String must;
    private List<String> arrays;

    //自有数据
    private String detail;
    private transient View.OnClickListener onClickListener;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getArrays() {
        return arrays;
    }

    public void setArrays(List<String> arrays) {
        this.arrays = arrays;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMust() {
        return must;
    }

    public void setMust(String must) {
        this.must = must;
    }

    //自有数据

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

}
