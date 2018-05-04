package com.ylink.fullgoal.api.surface;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.ItemBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class ItemControllerApi<T extends ItemControllerApi, C> extends SurfaceControllerApi<T, C> {

    private TextView nameTv;
    private TextView detailTv;
    private TextView dateTv;
    private TextView moneyTv;
    private EditText nameEt;
    private ImageView dateIv;
    private ImageView rightIv;
    private ImageView iconIv;
    private ViewGroup vg;

    public ItemControllerApi(C controller) {
        super(controller);
    }

    @Override
    public void initView() {
        super.initView();
        findView();
        initCallback();
    }

    private void findView(){
        nameTv = findViewById(R.id.name_tv);
        detailTv = findViewById(R.id.detail_tv);
        nameEt = findViewById(R.id.name_et);
    }

    //监听相关对象
    private void initCallback() {
        putBindBeanCallback(ItemBean.class, (bean, position) -> {
            setName(bean.getNickname())
                    .setDetail(bean.getDetail())
                    .setTextHint(nameEt, bean.getNickname())
                    .setOnClickListener(getRootView(), bean.getOnClickListener());
        });
    }

    //私有的

    protected T setName(String name) {
        setViewText(nameTv, name);
        return getThis();
    }

    protected T setDetail(String detail) {
        setViewText(detailTv, detail);
        return getThis();
    }

    protected T setMoney(double money) {
        setViewText(moneyTv, formatString("%.2f", money));
        return getThis();
    }

    protected T setDate(String date) {
        setViewText(dateTv, date);
        return getThis();
    }

    protected T setDateOnClickListener(View.OnClickListener listener) {
        setOnClickListener(dateIv, listener);
        return getThis();
    }

    protected T setIcon(Object res) {
        setImage(iconIv, res);
        return getThis();
    }

    protected T setSettingView(View v) {
        setOnClickListener(v, view -> {

        });
        return getThis();
    }

    private String getEnd(double m){
        String str = formatString("%.2f", m);
        if(!TextUtils.isEmpty(str) && str.contains(".")){
            return str.substring(str.indexOf(".") + 1);
        }
        return null;
    }

    private String getDm(double dm){
        if(dm % 1 == 0){
            return formatString("%d", (int)dm);
        } else {
            return formatString("%.2f", dm);
        }
    }

}
