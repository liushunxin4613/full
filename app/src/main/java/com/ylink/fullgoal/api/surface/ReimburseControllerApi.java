package com.ylink.fullgoal.api.surface;

import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.DisneyUtil;
import com.leo.core.util.ResUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.GridBean;
import com.ylink.fullgoal.bean.GridPhotoBean;
import com.ylink.fullgoal.bean.TvV2DialogBean;
import com.ylink.fullgoal.controllerApi.surface.RecycleBarControllerApi;
import com.ylink.fullgoal.vo.ReimburseVo;

import butterknife.Bind;

import static com.ylink.fullgoal.config.Config.STATE;
import static com.ylink.fullgoal.config.Config.TYPE;

/**
 * 报销
 */
public class ReimburseControllerApi<T extends ReimburseControllerApi, C> extends RecycleBarControllerApi<T, C> {

    @Bind(R.id.sqtp_iv)
    ImageView sqtpIv;
    @Bind(R.id.wbyl_iv)
    ImageView wbylIv;
    @Bind(R.id.xgtj_iv)
    ImageView xgtjIv;
    @Bind(R.id.qxbx_iv)
    ImageView qxbxIv;
    @Bind(R.id.alter_vg)
    LinearLayout alterVg;

    private ReimburseVo vo;

    public ReimburseControllerApi(C controller) {
        super(controller);
    }

    protected ReimburseVo getVo() {
        if (vo == null) {
            vo = new ReimburseVo();
        }
        return vo;
    }

    protected T setVo(ReimburseVo vo) {
        this.vo = vo;
        return getThis();
    }

    @Override
    public Integer getRootViewResId() {
        return R.layout.l_reimburse;
    }

    @Override
    public IControllerApi createControllerApi(ViewGroup container, int resId) {
        switch (resId) {
            case GridBean.LAYOUT_RES_ID:
                return new GridItemControllerApi(null);
        }
        return super.createControllerApi(container, resId);
    }

    @Override
    public void initView() {
        super.initView();
        executeBundle(bundle -> {
            String state = bundle.getString(STATE);
            String type = bundle.getString(TYPE);
            String title = TextUtils.isEmpty(state) ? type : state;
            title = TextUtils.equals(title, ReimburseVo.STATE_INITIATE) ? type : title;
            setTitle(title);
            if (TextUtils.equals(state, ReimburseVo.STATE_ALTER)) {
                setVisibility(View.VISIBLE, alterVg).setOnClickListener(sqtpIv, v -> {
                    //申请特批
                    show("申请特批");
                }).setOnClickListener(wbylIv, v -> {
                    //我不要了
                    show("我不要了");
                }).setOnClickListener(xgtjIv, v -> {
                    //修改提交
                    show("修改提交");
                }).setOnClickListener(qxbxIv, v -> {
                    //取消报销
                    show("取消报销");
                });
            }
        });
    }

    /**
     * 初始化报销数据
     */
    protected void initReimburseVo(ReimburseVo vo) {
        executeNon(vo, obj -> {
            clear();
            onReimburseVo(obj);
            //更新
            notifyDataSetChanged();
        });
    }

    /**
     * 报销数据回调
     */
    protected void onReimburseVo(ReimburseVo vo) {
    }

    /**
     * 图片点击
     *
     * @param bean bean
     * @param view view
     */
    protected void onGridPhotoClick(GridPhotoBean bean, View view) {
        show("图片");
    }

    /**
     * 图片长按
     *
     * @param bean bean
     * @param view view
     * @return 是否同时响应点击
     */
    protected boolean onGridPhotoLongClick(GridPhotoBean bean, View view) {
        TvV2DialogBean db = new TvV2DialogBean("重新上传", "删除", (item, v) -> {
            show(item.getName());
        }, (item, v) -> {
            show(item.getDetail());
        });
        ItemControllerApi api = getDialogControllerApi(ItemControllerApi.class, db.getApiType());
        api.dialogShow().onBindViewHolder(db, 0);
        Window window = api.getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = DisneyUtil.getScreenDisplay().getX() - ResUtil.getDimenInt(R.dimen.x120);
            window.setAttributes(lp);
        }
        return false;
    }
}
