package com.ylink.fullgoal.api.surface;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.leo.core.iapi.IBindItemCallback;
import com.leo.core.iapi.IRunApi;
import com.leo.core.util.ResUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.CCSQDBean;
import com.ylink.fullgoal.bean.DateArrayBean;
import com.ylink.fullgoal.bean.GridBean;
import com.ylink.fullgoal.bean.GridPhotoBean;
import com.ylink.fullgoal.bean.IconBean;
import com.ylink.fullgoal.bean.IconTvHBean;
import com.ylink.fullgoal.bean.IconTvMoreBean;
import com.ylink.fullgoal.bean.InhibitionRuleBean;
import com.ylink.fullgoal.bean.SXBean;
import com.ylink.fullgoal.bean.SelectedTvBean;
import com.ylink.fullgoal.bean.TvBean;
import com.ylink.fullgoal.bean.TvH2Bean;
import com.ylink.fullgoal.bean.TvH2MoreBean;
import com.ylink.fullgoal.bean.TvHEt3Bean;
import com.ylink.fullgoal.bean.TvHEtIconMoreBean;
import com.ylink.fullgoal.bean.TvHTv3Bean;
import com.ylink.fullgoal.bean.TvV2DialogBean;
import com.ylink.fullgoal.bean.VgBean;
import com.ylink.fullgoal.bean.XCJPBean;
import com.ylink.fullgoal.controllerApi.surface.BaseItemControllerApi;

public class ItemControllerApi<T extends ItemControllerApi, C> extends BaseItemControllerApi<T, C> {

    private TextView nameTv;
    private TextView detailTv;
    private TextView startTv;
    private TextView endTv;
    private TextView typeTv;
    private TextView placeTv;
    private EditText nameEt;
    private EditText detailEt;
    private EditText minEt;
    private EditText maxEt;
    private ImageView iconIv;

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
        detailTv = findViewById(view, R.id.detail_tv);
        startTv = findViewById(view, R.id.start_tv);
        endTv = findViewById(view, R.id.end_tv);
        typeTv = findViewById(view, R.id.type_tv);
        placeTv = findViewById(view, R.id.place_tv);
        nameEt = findViewById(view, R.id.name_et);
        detailEt = findViewById(view, R.id.detail_et);
        minEt = findViewById(view, R.id.min_et);
        maxEt = findViewById(view, R.id.max_et);
        iconIv = findViewById(view, R.id.icon_iv);
    }

    //监听相关对象
    private void initCallback() {
        //图片处理
        putBindBeanCallback(GridPhotoBean.class, (bean, position) -> {
            getRootView().setLayoutParams(new ViewGroup.LayoutParams(-1, bean.getUnit()));
            setIcon(bean.getRes())
                    .setOnClickListener(getRootView(), bean.getOnClickListener())
                    .setOnLongClickListener(getRootView(), bean.getOnLongClickListener());
        });
        //dialog双文字按钮处理
        putBindBeanCallback(TvV2DialogBean.class, (bean, position) -> {
            bean.setDialog(getDialog());
            setName(bean.getName())
                    .setDetail(bean.getDetail())
                    .setOnClickListener(nameTv, bean.getOnNameClickListener())
                    .setOnClickListener(detailTv, bean.getOnDetailClickListener());
        });
        //图片Bar
        putBindBeanCallback(IconBean.class, (bean, position) -> setOnClickListener(iconIv, bean.getOnClickListener()));
        //文字文字组
        putBindBeanCallback(DateArrayBean.class, (bean, position) -> {
            setName(bean.getName());
            addView(vg -> {
                vg.removeAllViews();
                GridItemControllerApi api = getViewControllerApi(GridItemControllerApi.class, R.layout.l_recycle);
                vg.addView(api.getRootView());
                api.getRecyclerView().setBackgroundColor(ResUtil.getColor(R.color.white));
                api.addAll(bean.getData()).notifyDataSetChanged();
            });
        });
        //文字
        putBindBeanCallback(SelectedTvBean.class, (bean, position) -> setName(bean.getName())
                .setOnClickListener(v -> {
                    ViewGroup vg = (ViewGroup) v.getParent();
                    if (!TextUtils.equals(vg.getTag(), v)) {
                        if (vg.getTag() instanceof View) {
                            ((View) vg.getTag()).setSelected(false);
                        }
                        v.setSelected(true);
                        vg.setTag(v);
                    }
                }));
        //筛选
        putBindBeanCallback(SXBean.class, (bean, position) -> {
            bean.setMinTv(minEt);
            bean.setMaxTv(maxEt);
            bean.setTextTv(nameEt);
        });
        //禁止规则
        putBindBeanCallback(InhibitionRuleBean.class, (bean, position) -> setIcon(bean.getIconResId())
                .setName(bean.getName())
                .setDetail(bean.getDetail()));
        //VgBean ************* 总的数据 *************
        putBindBeanCallback(VgBean.class, (bean, position) -> addView(vg -> {
            vg.removeAllViews();
            execute(bean.getData(), item -> {
                if (item instanceof GridBean) {
                    GridItemControllerApi api = getViewControllerApi(GridItemControllerApi.class, item.getApiType());
                    vg.addView(api.getRootView());
                    api.onBindViewHolder(item, position);
                } else {
                    ItemControllerApi api = getViewControllerApi(ItemControllerApi.class, item.getApiType());
                    vg.addView(api.getRootView());
                    findView(api.getRootView());
                    IBindItemCallback itemApi = api.getItemControllerApi(item.getClass());
                    if (itemApi != null) {
                        itemApi.onItem(api, item);
                    }
                }
            });
        }));
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
                    .setDetail(bean.getDetail())
                    .setImage(iconIv, bean.getIconResId())
                    .setIcon(iconIv, !TextUtils.isEmpty(bean.getIconResId()))
                    .setOnClickListener(iconIv, bean.getOnClickListener())
                    .setTextHint(detailEt, bean.getHint())
                    .setText(detailEt, bean.getDetail());
        });
        //双文字点击
        putBindItemCallback(TvH2MoreBean.class, (api, bean) -> api.setName(bean.getName())
                .setDetail(nb(bean.getDetail(), bean.getHint()))
                .setTextColor(detailTv, getResTvColor(bean.getDetail()))
                .setOnClickListener(getRootView(), bean.getOnClickListener()));
        //文字多行输入
        putBindItemCallback(TvHEt3Bean.class, (api, bean) -> api.setName(bean.getName())
                .setDetail(bean.getDetail())
                .setText(detailEt, bean.getDetail())
                .setTextHint(detailEt, bean.getHint()));
        //文字
        putBindItemCallback(TvBean.class, (api, bean) -> api.setName(bean.getName()));
        //图片文字点击
        putBindItemCallback(IconTvHBean.class, (api, bean) -> api.setIcon(bean.getIconResId())
                .setName(bean.getName())
                .setOnClickListener(bean.getOnClickListener()));
        //出差申请单
        putBindItemCallback(CCSQDBean.class, (api, bean) -> api.setName(bean.getName())
                .setDetail(bean.getDetail())
                .setText(startTv, bean.getStart())
                .setText(endTv, bean.getEnd()));
        //携程机票
        putBindItemCallback(XCJPBean.class, (api, bean) -> api.setName(bean.getName())
                .setDetail(bean.getDetail())
                .setText(typeTv, bean.getType())
                .setText(placeTv, bean.getPlace())
                .setText(startTv, bean.getStart())
                .setText(endTv, bean.getEnd()));
        //文字多行文字
        putBindItemCallback(TvHTv3Bean.class, (api, bean) -> api.setName(bean.getName())
                .setDetail(bean.getDetail()));
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

    protected T setIcon(Object res) {
        setImage(iconIv, res);
        return getThis();
    }

    protected T setOnClickListener(View.OnClickListener listener) {
        setOnClickListener(getRootView(), listener);
        return getThis();
    }

    private T addView(IRunApi<ViewGroup> api) {
        ViewGroup vg = findViewById(R.id.vg);
        if (api != null && vg != null) {
            api.execute(vg);
        }
        return getThis();
    }

    private <B> B nb(B old, B nw) {
        return TextUtils.count(old) > 0 ? old : nw;
    }

    private int getResTvColor(CharSequence text) {
        return !TextUtils.isEmpty(text) ? R.color.tv : R.color.tv1;
    }

}
