package com.ylink.fullgoal.api.surface;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.leo.core.bean.BaseApiBean;
import com.leo.core.iapi.IRunApi;
import com.leo.core.util.DisneyUtil;
import com.leo.core.util.ResUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.GridBean;
import com.ylink.fullgoal.bean.GridPhotoBean;
import com.ylink.fullgoal.bean.TvBean;
import com.ylink.fullgoal.bean.TvV2DialogBean;
import com.ylink.fullgoal.controllerApi.surface.BillControllerApi;
import com.ylink.fullgoal.controllerApi.surface.RecycleBarControllerApi;
import com.ylink.fullgoal.vo.BillVo;
import com.ylink.fullgoal.vo.ReimburseVo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.ylink.fullgoal.config.Config.REIMBURSE_TYPE;
import static com.ylink.fullgoal.config.Config.STATE;

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
    private String state;
    private String reimburseType;

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

    public String getState() {
        return state;
    }

    public String getReimburseType() {
        return reimburseType;
    }

    @Override
    public Integer getRootViewResId() {
        return R.layout.l_reimburse;
    }

    @Override
    public void initView() {
        super.initView();
        executeBundle(bundle -> {
            state = bundle.getString(STATE);
            reimburseType = bundle.getString(REIMBURSE_TYPE);
            String title = TextUtils.isEmpty(state) ? reimburseType : state;
            title = TextUtils.equals(title, ReimburseVo.STATE_INITIATE) ? reimburseType : title;
            setTitle(title);
            switch (TextUtils.isEmpty(state) ? "" : state) {
                case ReimburseVo.STATE_INITIATE:
                    setRightTv("提交", v -> show("提交"));
                    break;
                case ReimburseVo.STATE_CONFIRM:
                    setRightTv("确认", v -> show("确认"));
                    break;
                case ReimburseVo.STATE_ALTER:
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
                    break;
            }
        });
        //test
        getVo().setAgent("张三");
        getVo().setDepartment("计划财务部");
    }

    protected boolean isEnable() {
        return !TextUtils.equals(state, ReimburseVo.STATE_DETAIL);
    }

    protected boolean isAlterEnable() {
        return TextUtils.equals(state, ReimburseVo.STATE_ALTER);
    }

    protected boolean isNoneInitiateEnable() {
        return !TextUtils.equals(state, ReimburseVo.STATE_INITIATE);
    }

    protected <B> B getEnable(B a, B b) {
        return isEnable() ? a : b;
    }

    protected <B> B getEnable(B a) {
        return isEnable() ? a : null;
    }

    protected <B> B getHasEnable(B a) {
        return isNoneInitiateEnable() ? a : null;
    }

    /**
     * 初始化报销数据
     */
    protected void initReimburseVo(ReimburseVo vo) {
        executeNon(vo, obj -> {
            clear().onReimburseVo(obj);
            //更新
            notifyDataSetChanged();
        });
    }

    /**
     * 报销数据回调
     */
    protected void onReimburseVo(ReimburseVo vo) {
        //报销类型
        vo.setState(state);
        //报销状态
        vo.setReimburseType(reimburseType);
    }

    protected List<GridPhotoBean> getPhotoGridBeanData(List<BillVo> data) {
        List<GridPhotoBean> gridData = new ArrayList<>();
        execute(data, obj -> gridData.add(new GridPhotoBean(obj.getPhoto(), obj,
                this::onGridPhotoClick, this::onGridPhotoLongClick).setEnable(isEnable())));
        if (isEnable()) {
            gridData.add(new GridPhotoBean(R.mipmap.posting_add, null, (bean, view) -> {
                //添加图片
                show("添加票据");
            }, null));
        }
        return gridData;
    }

    @Override
    public T addVgBean(IRunApi<List<BaseApiBean>> api) {
        return super.addVgBean(data -> {
            api.execute(data);
            execute(data, item -> item.setEnable(isEnable()));
        });
    }

    public T addVgBean(String title, List<BillVo> data) {
        if (!TextUtils.isEmpty(title) && !(!isEnable() && TextUtils.isEmpty(data))) {
            addVgBean(new TvBean(title), new GridBean(getPhotoGridBeanData(data)));
        }
        return getThis();
    }

    /**
     * 图片点击
     *
     * @param bean bean
     * @param view view
     */
    private void onGridPhotoClick(GridPhotoBean bean, View view) {
        executeNon(bean, obj -> startSurfaceActivity(getBundle(bean.getObj()), BillControllerApi.class));
    }

    /**
     * 图片长按
     *
     * @param bean bean
     * @param view view
     * @return 是否同时响应点击
     */
    private boolean onGridPhotoLongClick(GridPhotoBean bean, View view) {
        TvV2DialogBean db = new TvV2DialogBean("重新上传", "删除", (item, v, dialog) -> {
            show(item.getName());
            dialog.dismiss();
        }, (item, v, dialog) -> {
            show(item.getDetail());
            dialog.dismiss();
        });
        ItemControllerApi api = getDialogControllerApi(ItemControllerApi.class, db.getApiType());
        api.dialogShow().onBindViewHolder(db, 0);
        Window window = api.getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = DisneyUtil.getScreenDisplay().getX() - ResUtil.getDimenInt(R.dimen.x120);
            window.setAttributes(lp);
        }
        return true;
    }

}
