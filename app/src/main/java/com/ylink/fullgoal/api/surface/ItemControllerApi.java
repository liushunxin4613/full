package com.ylink.fullgoal.api.surface;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.leo.core.iapi.IBindItemCallback;
import com.leo.core.iapi.IRunApi;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.GridPhotoBean;
import com.ylink.fullgoal.bean.IconTvMoreBean;
import com.ylink.fullgoal.bean.TvH2Bean;
import com.ylink.fullgoal.bean.TvH2MoreBean;
import com.ylink.fullgoal.bean.TvHEt3Bean;
import com.ylink.fullgoal.bean.TvHEtIconMoreBean;
import com.ylink.fullgoal.bean.TvV2DialogBean;
import com.ylink.fullgoal.bean.VgBean;
import com.ylink.fullgoal.controllerApi.surface.BaseItemControllerApi;

public class ItemControllerApi<T extends ItemControllerApi, C> extends BaseItemControllerApi<T, C> {

    private TextView nameTv;
    private TextView detailTv;
    private TextView dateTv;
    private TextView moneyTv;
    private EditText nameEt;
    private EditText detailEt;
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
        findView(getRootView());
        initCallback();
    }

    private void findView(View view) {
        nameTv = findViewById(view, R.id.name_tv);
        iconIv = findViewById(view, R.id.icon_iv);
        detailTv = findViewById(view, R.id.detail_tv);
        detailEt = findViewById(view, R.id.detail_et);
    }

    //监听相关对象
    private void initCallback() {
        //总的数据
        putBindBeanCallback(VgBean.class, (bean, position) -> execute(bean.getData(), item -> addView(vg -> {
            ItemControllerApi api = getViewControllerApi(getClass(), item.getApiType());
            vg.addView(api.getRootView());
            findView(api.getRootView());
            IBindItemCallback itemApi = getItemControllerApi(item.getClass());
            if (itemApi != null) {
                itemApi.onItem(api, item);
            }
        })));
        //双文字
        putBindItemCallback(TvH2Bean.class, (api, bean) -> api.setName(bean.getName())
                .setDetail(bean.getDetail()));
        //图标文字点击
        putBindItemCallback(IconTvMoreBean.class, (api, bean) -> api.setIcon(bean.getIconResId())
                .setName(bean.getName())
                .setOnClickListener(bean.getOnClickListener()));
        //图标文字输入图标监听
        putBindItemCallback(TvHEtIconMoreBean.class, (api, bean) -> {
            bean.setTextView(detailEt);
            api.setName(bean.getName())
                    .setImage(iconIv, bean.getIconResId())
                    .setOnClickListener(iconIv, bean.getOnClickListener())
                    .setTextHint(detailEt, bean.getHint())
                    .setText(detailEt, bean.getDetail());
        });
        //双文字点击
        putBindItemCallback(TvH2MoreBean.class, (api, bean) -> api.setName(bean.getName())
                .setDetail(bean.getDetail())
                .setOnClickListener(bean.getOnClickListener()));
        //文字多行输入
        putBindItemCallback(TvHEt3Bean.class, (api, bean) -> api.setName(bean.getName())
                .setDetail(bean.getDetail())
                .setTextHint(detailEt, bean.getHint()));
        //图片处理
        putBindBeanCallback(GridPhotoBean.class, (bean, position) -> {
            getRootView().setLayoutParams(new ViewGroup.LayoutParams(-1, bean.getUnit()));
            setIcon(bean.getRes())
                    .setOnClickListener(getRootView(), bean.getOnClickListener())
                    .setOnLongClickListener(getRootView(), bean.getOnLongClickListener());
        });
        //dialog双文字按钮处理
        putBindBeanCallback(TvV2DialogBean.class, (bean, position) -> setName(bean.getName())
                .setDetail(bean.getDetail())
                .setOnClickListener(nameTv, bean.getOnNameClickListener())
                .setOnClickListener(detailTv, bean.getOnDetailClickListener()));
    }

    //私有的

    protected T setName(CharSequence name) {
        setText(nameTv, name);
        return getThis();
    }

    protected T setDetail(String detail) {
        setText(detailTv, detail);
        return getThis();
    }

    protected T setMoney(double money) {
        setText(moneyTv, formatString("%.2f", money));
        return getThis();
    }

    protected T setDate(String date) {
        setText(dateTv, date);
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

    private T setOnClickListener(View.OnClickListener listener) {
        setOnClickListener(getRootView(), listener);
        return getThis();
    }

    private T addView(IRunApi<ViewGroup> api) {
        if (api != null) {
            api.execute((ViewGroup) getRootView());
        }
        return getThis();
    }

    protected T setSettingView(View v) {
        setOnClickListener(v, view -> {

        });
        return getThis();
    }

}
