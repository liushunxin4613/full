package com.ylink.fullgoal.api.surface;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.leo.core.bean.BaseApiBean;
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
import com.ylink.fullgoal.bean.ItemOperationBean;
import com.ylink.fullgoal.bean.ReimburseTypeBean;
import com.ylink.fullgoal.bean.SearchWaterfall;
import com.ylink.fullgoal.bean.SelectedTvBean;
import com.ylink.fullgoal.bean.TvBean;
import com.ylink.fullgoal.bean.TvH2Bean;
import com.ylink.fullgoal.bean.TvH2MoreBean;
import com.ylink.fullgoal.bean.TvH2SBean;
import com.ylink.fullgoal.bean.TvH4Bean;
import com.ylink.fullgoal.bean.TvHEt3Bean;
import com.ylink.fullgoal.bean.TvHEtBean;
import com.ylink.fullgoal.bean.TvHEtIconMoreBean;
import com.ylink.fullgoal.bean.TvHTv3Bean;
import com.ylink.fullgoal.bean.TvSBean;
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
    private TextView timeTv;
    private EditText nameEt;
    private EditText detailEt;
    private ImageView iconIv;
    private ViewGroup vg;

    public ItemControllerApi(C controller) {
        super(controller);
    }

    @Override
    public void initView() {
        super.initView();
        initCallback();
    }

    @Override
    public void onFindViewByIds() {
        super.onFindViewByIds();
        vg = findViewById(R.id.vg);
        nameTv = findViewById(R.id.name_tv);
        detailTv = findViewById(R.id.detail_tv);
        startTv = findViewById(R.id.start_tv);
        endTv = findViewById(R.id.end_tv);
        typeTv = findViewById(R.id.type_tv);
        placeTv = findViewById(R.id.place_tv);
        timeTv = findViewById(R.id.time_tv);
        nameEt = findViewById(R.id.name_et);
        detailEt = findViewById(R.id.detail_et);
        iconIv = findViewById(R.id.icon_iv);
    }

    //监听相关对象
    private void initCallback() {
        //文字
        putBindBeanApi(TvBean.class, (api, bean)
                -> api.setText(nameTv, bean.getName())
                .setOnClickListener(bean.getOnClickListener()));
        //双文字
        putBindBeanApi(TvH2SBean.class, (api, bean)
                -> api.setText(nameTv, bean.getName())
                .setText(detailTv, bean.getDetail())
                .setOnClickListener(bean.getOnClickListener()));
        //图片处理
        putBindBeanApi(GridPhotoBean.class, (api, bean)
                -> api.setImage(iconIv, bean.getRes())
                .execute(() -> api.getRootView().setLayoutParams(
                        new ViewGroup.LayoutParams(-1, bean.getUnit())))
                .setOnClickListener(bean.getOnClickListener())
                .setOnLongClickListener(bean.getOnLongClickListener()));
        //dialog双文字按钮处理
        putBindBeanApi(TvV2DialogBean.class, (api, bean)
                -> api.setText(nameTv, bean.getName())
                .execute(() -> bean.setDialog(getDialog()))
                .setText(detailTv, bean.getDetail())
                .setOnClickListener(nameTv, bean.getOnNameClickListener())
                .setOnClickListener(detailTv, bean.getOnDetailClickListener()));
        //图片Bar
        putBindBeanApi(IconBean.class, (api, bean)
                -> api.setOnClickListener(iconIv, bean.getOnClickListener()));
        //文字文字组
        putBindBeanApi(DateArrayBean.class, (api, bean)
                -> api.setText(nameTv, bean.getName())
                .setViewGroupApi(vg, vg -> {
                    vg.removeAllViews();
                    GridRecycleControllerApi gridApi = getViewControllerApi(GridRecycleControllerApi.class);
                    vg.addView(gridApi.getRootView());
                    gridApi.getRecyclerView().setBackgroundColor(ResUtil.getColor(R.color.white));
                    gridApi.addAll(bean.getData()).notifyDataSetChanged();
                }));
        //文字
        putBindBeanApi(SelectedTvBean.class, (api, bean)
                -> api.setText(nameTv, bean.getName())
                .setOnClickListener(v -> {
                    ViewGroup vg = (ViewGroup) v.getParent();
                    if (!TextUtils.equals(vg.getTag(), v) || !v.isSelected()) {
                        if (vg.getTag() instanceof View) {
                            ((View) vg.getTag()).setSelected(false);
                        }
                        v.setSelected(true);
                        vg.setTag(v);
                    }
                    bean.execute(v, bean.getName());
                }));
        //筛选
        putBindBeanApi(ReimburseTypeBean.class, (api, bean)
                -> api.setText(nameTv, bean.getName())
                .setText(detailTv, bean.getDetail())
                .setOnClickListener(nameTv, v -> {
                    api.setSelected(nameTv, true);
                    api.setSelected(detailTv, false);
                    bean.execute(v, bean.getName());
                })
                .setOnClickListener(detailTv, v -> {
                    api.setSelected(detailTv, true);
                    api.setSelected(nameTv, false);
                    bean.execute(v, bean.getDetail());
                }));
        //禁止规则
        putBindBeanApi(InhibitionRuleBean.class, (api, bean)
                -> api.setImage(iconIv, bean.getIconResId())
                .setText(nameTv, bean.getName())
                .setText(detailTv, bean.getDetail()));
        //搜索瀑布流
        putBindBeanApi(SearchWaterfall.class, (api, bean)
                -> api.setViewGroupApi(vg, vg -> {
            bean.setCloseIv(iconIv).setTextView(nameEt);
            api.setText(nameTv, bean.getName());
            StaggeredGridRecycleControllerApi staApi = getViewControllerApi(StaggeredGridRecycleControllerApi.class);
            staApi.getRecyclerView().setBackgroundColor(ResUtil.getColor(R.color.white));
            bean.setApi(staApi);
            vg.removeAllViews();
            vg.addView(staApi.getRootView());
            execute(bean.getBeanData(), obj -> obj.setTextApi(text -> setText(detailTv, text)));
            staApi.addAll(bean.getBeanData()).notifyDataSetChanged();
        }));
        //tvs子项
        putBindBeanApi(TvSBean.class, (api, bean)
                -> api.setText(nameTv, bean.getName())
                .setOnClickListener(v -> {
                    executeNon(bean.getTextApi(), textApi -> textApi.execute(bean.getName()));
                    ViewGroup vg = (ViewGroup) v.getParent();
                    if (!TextUtils.equals(vg.getTag(), v) || !v.isSelected()) {
                        if (vg.getTag() instanceof View) {
                            ((View) vg.getTag()).setSelected(false);
                        }
                        v.setSelected(true);
                        vg.setTag(v);
                    }
                }));
        //出差申请单
        putBindBeanApi(CCSQDBean.class, (api, bean)
                -> api.setText(nameTv, bean.getName())
                .setText(detailTv, bean.getDetail())
                .setText(startTv, bean.getStart())
                .setText(endTv, bean.getEnd())
                .setEnableOnClickListener(bean.getOnClickListener()));
        //携程机票
        putBindBeanApi(XCJPBean.class, (api, bean)
                -> api.setText(nameTv, bean.getName())
                .setText(detailTv, bean.getDetail())
                .setText(typeTv, bean.getType())
                .setText(placeTv, bean.getPlace())
                .setText(startTv, bean.getStart())
                .setText(endTv, bean.getEnd())
                .setOnClickListener(bean.getOnClickListener()));
        //上下集合数据
        putBindBeanApi(VgBean.class, (api, bean)
                -> api.setOnClickListener(bean.getOnClickListener()).setViewGroupApi(vg, vg -> {
            vg.removeAllViews();
            for (int i = 0; i < bean.getLineData().size(); i++) {
                BaseApiBean item = bean.getLineData().get(i);
                if (item instanceof GridBean) {
                    GridRecycleControllerApi gridApi = getViewControllerApi(GridRecycleControllerApi.class,
                            item.getApiType());
                    gridApi.onBindViewHolder(item, i);
                    vg.addView(gridApi.getRootView());
                } else {
                    ItemControllerApi itemApi = getViewControllerApi(ItemControllerApi.class,
                            item.getApiType());
                    itemApi.onBindViewHolder(item, i);
                    vg.addView(itemApi.getRootView());
                }
            }
        }));
        //双文字
        putBindBeanApi(TvH2Bean.class, (api, bean)
                -> api.setText(nameTv, bean.getName())
                .setText(detailTv, bean.getDetail()));
        //图标文字点击
        putBindBeanApi(IconTvMoreBean.class, (api, bean)
                -> api.setImage(iconIv, bean.getIconResId())
                .setText(nameTv, bean.getName())
                .setOnClickListener(bean.getOnClickListener()));
        //图标文字输入图标监听
        putBindBeanApi(TvHEtIconMoreBean.class, (api, bean)
                -> api.setText(nameTv, bean.getName())
                .setText(detailTv, bean.getDetail())
                .execute(() -> bean.setTextView(detailEt))
                .setImage(iconIv, bean.getIconResId())
                .setIcon(iconIv, !TextUtils.isEmpty(bean.getIconResId()))
                .setOnClickListener(iconIv, bean.getOnClickListener())
                .setTextHint(detailEt, bean.getHint())
                .setText(detailEt, bean.getDetail()));
        //双文字点击
        putBindBeanApi(TvH2MoreBean.class, (api, bean)
                -> api.setText(nameTv, bean.getName())
                .setText(detailTv, bean.getDetail())
                .execute(() -> bean.setTextView(detailTv))
                .setText(detailTv, TextUtils.isEmpty(bean.getDetail()) ? bean.getHint() : bean.getDetail())
                .setTextColor(detailTv, getResTvColor(bean.getDetail()))
                .setOnClickListener(bean.getOnClickListener()));
        //文字多行输入
        putBindBeanApi(TvHEt3Bean.class, (api, bean)
                -> api.setText(nameTv, bean.getName())
                .setText(detailTv, bean.getDetail())
                .execute(() -> bean.setTextView(detailEt))
                .setText(detailEt, bean.getDetail())
                .setTextHint(detailEt, bean.getHint()));
        //图片文字点击
        putBindBeanApi(IconTvHBean.class, (api, bean)
                -> api.setImage(iconIv, bean.getIconResId())
                .setText(nameTv, bean.getName())
                .setOnClickListener(bean.getOnClickListener()));
        //文字多行文字
        putBindBeanApi(TvHTv3Bean.class, (api, bean)
                -> api.setText(nameTv, bean.getName())
                .setText(detailTv, bean.getDetail()));
        //文字输入
        putBindBeanApi(TvHEtBean.class, (api, bean)
                -> api.setText(nameTv, bean.getName())
                .execute(() -> bean.setTextView(detailEt))
                .setText(detailEt, bean.getDetail())
                .setTextHint(detailEt, bean.getHint()));
        //审批意见
        putBindBeanApi(TvH4Bean.class, (api, bean) -> api.setText(nameTv, bean.getName())
                .setText(detailTv, bean.getDetail())
                .setText(typeTv, bean.getType())
                .setText(timeTv, bean.getTime()));
        //审批意见
        putBindBeanApi(ItemOperationBean.class, (api, bean) -> api.setText(nameTv, bean.getName())
                .setText(detailTv, bean.getDetail())
                .setOnClickListener(nameTv, bean.getNameOnClickListener())
                .setOnClickListener(detailTv, bean.getDetailOnClickListener()));
    }

}
