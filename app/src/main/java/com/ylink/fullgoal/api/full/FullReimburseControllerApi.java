package com.ylink.fullgoal.api.full;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.leo.core.bean.Bol;
import com.leo.core.iapi.api.IDisplayApi;
import com.leo.core.iapi.inter.IAction;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.net.Exceptions;
import com.leo.core.util.DisneyUtil;
import com.leo.core.util.JavaTypeUtil;
import com.leo.core.util.ResUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.GridBean;
import com.ylink.fullgoal.bean.GridPhotoBean;
import com.ylink.fullgoal.bean.HintDialogBean;
import com.ylink.fullgoal.bean.TvBean;
import com.ylink.fullgoal.bean.TvV2DialogBean;
import com.ylink.fullgoal.bean.VgBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.controllerApi.surface.RecycleBarControllerApi;
import com.ylink.fullgoal.controllerApi.surface.RecycleControllerApi;
import com.ylink.fullgoal.core.BaseBiBean;
import com.ylink.fullgoal.cr.core.AddController;
import com.ylink.fullgoal.cr.core.DoubleController;
import com.ylink.fullgoal.cr.surface.SbumitFlagController;
import com.ylink.fullgoal.cr.surface.SerialNoController;
import com.ylink.fullgoal.fg.ContractPaymentFg;
import com.ylink.fullgoal.fg.CostFg;
import com.ylink.fullgoal.fg.CtripTicketsFg;
import com.ylink.fullgoal.fg.DataFg;
import com.ylink.fullgoal.fg.DepartmentFg;
import com.ylink.fullgoal.fg.ImageFg;
import com.ylink.fullgoal.fg.ProcessFg;
import com.ylink.fullgoal.fg.ProjectFg;
import com.ylink.fullgoal.fg.ResearchReportFg;
import com.ylink.fullgoal.fg.TravelFormFg;
import com.ylink.fullgoal.fg.UserFg;
import com.ylink.fullgoal.vo.DVo;
import com.ylink.fullgoal.vo.ImageVo;
import com.ylink.fullgoal.vo.RVo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

import static com.leo.core.util.TextUtils.getSetData;
import static com.ylink.fullgoal.config.ComConfig.FQ;
import static com.ylink.fullgoal.config.ComConfig.QR;
import static com.ylink.fullgoal.config.ComConfig.TP;
import static com.ylink.fullgoal.config.ComConfig.UPDATE_MONEY;
import static com.ylink.fullgoal.config.ComConfig.XG;
import static com.ylink.fullgoal.config.ComConfig.XQ;
import static com.ylink.fullgoal.config.Config.BILL_TYPE_TITLES;
import static com.ylink.fullgoal.config.Config.DATA_QR;
import static com.ylink.fullgoal.config.Config.SERIAL_NO;
import static com.ylink.fullgoal.config.Config.STATE;
import static com.ylink.fullgoal.config.Config.XG1;
import static com.ylink.fullgoal.config.Config.XG2;
import static com.ylink.fullgoal.config.Config.XG3;
import static com.ylink.fullgoal.config.Config.XG4;
import static com.ylink.fullgoal.config.UrlConfig.FULL_REIMBURSE_QUERY;
import static com.ylink.fullgoal.config.UrlConfig.FULL_REIMBURSE_SUBMIT;

/**
 * 报销
 */
public abstract class FullReimburseControllerApi<T extends FullReimburseControllerApi, C> extends RecycleBarControllerApi<T, C> {

    private final static int PHOTO_REQUEST_CAMERA = 0x101;

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
    @Bind(R.id.content_vg)
    ViewGroup contentVg;

    private String state;
    private String title;

    protected FullReimburseControllerApi(C controller) {
        super(controller);
    }

    public String getState() {
        return state;
    }

    protected String getTitle() {
        return title;
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_reimburse;
    }

    @Override
    public View getContentView() {
        return contentVg;
    }

    /**
     * 报销类型
     */
    protected abstract String getBType();

    /**
     * 报销名称
     */
    protected abstract String getBTitle();

    /**
     * 报销数据回调
     */
    protected abstract void onData();

    @Override
    public DVo getVo() {
        return super.getVo();
    }

    @Override
    public DVo newVo() {
        return new DVo();
    }

    @Override
    public void initView() {
        super.initView();
        executeBundle(bundle -> {
            state = bundle.getString(STATE);
            vos(DVo::getFirst, obj -> obj.initDB(state));
            String serialNo = bundle.getString(SERIAL_NO);
            vos(DVo::getSerialNo, obj -> obj.initDB(serialNo));
            if (TextUtils.equals(state, FQ)) {
                title = getBTitle();
            } else {
                title = getKey(BILL_TYPE_TITLES, state);
                if (!TextUtils.isEmpty(serialNo)) {
                    hideContentView();
                    api().queryMessageBack(serialNo);
                }
            }
            setTitle(title);
            switch (TextUtils.isEmpty(state) ? "" : state) {
                case FQ:
                    setRightTv("提交", v -> submit());
                    break;
                case QR:
                    setRightTv("确认", v -> {
                        String money = vor(DVo::getMoney, DoubleController::getDBMoney);
                        vos(DVo::getCostIndex, obj -> obj.update(money));
                        Map<String, Object> map = getSubmitMap();
                        if (!TextUtils.isEmpty(map)) {
                            if (vor(DVo::getSbumitFlag, SbumitFlagController::isOpen)) {
                                api().submitReimburse(map);
                            } else {
                                routeApi().costIndex(m -> m.put(DATA_QR, encode(map)));
                            }
                        }
                    });
                    break;
                case XG:
                    setVisibility(View.VISIBLE, alterVg).setOnClickListener(sqtpIv, v -> {
                        //申请特批
                        vos(DVo::getLogo, obj -> obj.initDB(XG1));
                        submit();
                    }).setOnClickListener(wbylIv, v -> {
                        //我不要了
                        vos(DVo::getLogo, obj -> obj.initDB(XG2));
                        submit();
                    }).setOnClickListener(xgtjIv, v -> {
                        //修改提交
                        vos(DVo::getLogo, obj -> obj.initDB(XG3));
                        submit();
                    }).setOnClickListener(qxbxIv, v -> {
                        //取消报销
                        vos(DVo::getLogo, obj -> obj.initDB(XG4));
                        submit();
                    });
                    break;
            }
        });
        //test
        vos(DVo::getImageList, obj -> obj.setOnCom(this));
        vos(DVo::getAgent, obj -> obj.initDB(new UserFg(getUId(), getUserName())));
        vos(DVo::getReimbursement, obj -> obj.initDB(new UserFg(getUId(), getUserName())));
        vos(DVo::getDepartment, obj -> obj.initDB(getDepartment()));
        vos(DVo::getBudgetDepartment, obj -> obj.initDB(getDepartment()));
//        ee("core", getVo().toCheckString());
    }

    @Override
    public void initData() {
        super.initData();
        add(Exceptions.class, (path, what, msg, bean) -> vos(DVo::getImageList, obj
                -> obj.onError(msg)));
        add(ImageFg.class, (path, what, msg, bean) -> {
            if (!TextUtils.isEmpty(msg)) {
                vos(DVo::getSerialNo, obj -> obj.initDB(bean.getSerialNo()));
                vos(DVo::getSbumitFlag, SbumitFlagController::open);
                vos(DVo::getImageList, obj -> obj.initImageFg(msg, bean, this));
            }
        });
        add(DataFg.class, (path, what, msg, bean) -> {
            if (bean.isSuccess()) {
                switch (path) {
                    case FULL_REIMBURSE_SUBMIT://报销提交
                        if (!TextUtils.isEmpty(getState())) {
                            switch (getState()) {
                                case FQ://经办人发起
//                                    show("报销成功");
                                    again();
                                    break;
                                case QR://经办人确认
                                    show("报销确认成功");
                                    getActivity().finish();
                                    break;
                                case XG://经办人修改
                                    show("报销修改成功");
                                    getActivity().finish();
                                    break;
                            }
                        }
                        break;
                }
            }
        });
        add(RVo.class, (path, what, msg, bean) -> {
            switch (path) {
                case FULL_REIMBURSE_QUERY://报销获取
                    bean.get(getVo());
                    showContentView();
                    notifyDataChanged();
                    break;
            }
        });
    }

    private void again() {
        HintDialogBean dialogBean = new HintDialogBean("温馨提示", "是否需要添加新的报销", "是",
                "否", (bean, v, dialog) -> {
            dialog.dismiss();
            vos(DVo::getImageList, AddController::clear);
            vos(DVo::getSerialNo, SerialNoController::clear);
            notifyDataChanged();
        }, (bean, v, dialog) -> {
            dialog.dismiss();
            getActivity().finish();
        });
        SurfaceControllerApi api = getDialogControllerApi(getActivity(), SurfaceControllerApi.class,
                dialogBean.getApiType());
        api.dialogShow().onBindViewHolder(dialogBean, 0).execute(() -> {
            Window window = api.getDialog().getWindow();
            if (window != null) {
                window.setGravity(Gravity.CENTER);
                WindowManager.LayoutParams lp = window.getAttributes();
                IDisplayApi.ScreenDisplay display = DisneyUtil.getScreenDisplay();
                lp.width = (int) (display.getX() * 0.8);
                window.setAttributes(lp);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //报销人
        executeSearch(UserFg.class, vo -> vos(DVo::getReimbursement, obj -> obj.initDB(vo.getObj())));
        //预算归属部门
        executeSearch(DepartmentFg.class, vo -> vos(DVo::getBudgetDepartment, obj -> obj.initDB(vo.getObj())));
        //项目
        executeSearch(ProjectFg.class, vo -> vos(DVo::getProject, obj -> obj.initDB(vo.getObj())));
        //合同付款申请单
        executeSearch(ContractPaymentFg.class, vo -> vos(DVo::getContractPayment, obj -> obj.initDB(vo.getObj())));
        //招待申请单
        executeSearch(ProcessFg.class, vo -> vos(DVo::getProcess, obj -> obj.initDB(vo.getObj())));
        //费用指标
        executeSearch(CostFg.class, vo -> vos(DVo::getCostIndex, obj -> obj.initDB(vo.getObj())));
        //出差申请单
        executeSearch(TravelFormFg.class, vo -> vos(DVo::getTrave, obj -> obj.initDB(vo.getObj())));
        //调研报告
        executeSearch(ResearchReportFg.class, vo -> vos(DVo::getReport, obj -> obj.initDB(vo.getObj())));
        //携程机票
        executeSearch(CtripTicketsFg.class, vo -> vos(DVo::getCtrip, obj -> obj.initDB(vo.getObj())));
        //票据修改金额
        execute(getFinish(), ImageVo.class, vo -> vos(DVo::getImageList, obj -> obj.updateMoney(vo)));
        notifyDataChanged();
    }

    @Override
    public void onCom(int what, String com, String msg, Object... args) {
        super.onCom(what, com, msg, args);
        executeNon(com, key -> {
            switch (key) {
                case UPDATE_MONEY:
                    vos(DVo::getMoney, obj -> obj.initDB(JavaTypeUtil.getdouble(msg, 0)));
                    break;
            }
        });
    }

    @Override
    public VgBean addVgBean(IObjAction<List<BaseBiBean>> api) {
        return super.addVgBean(data -> {
            api.execute(data);
            execute(data, item -> item.setEnable(isEnable()));
        });
    }

    //私有的

    private List<GridPhotoBean> getPhotoGridBeanData(int type, List<ImageVo> data) {
        List<GridPhotoBean> gridData = new ArrayList<>();
        execute(data, obj -> gridData.add(newGridPhotoBean(data, obj).setEnable(isEnable())
                .setVisible(isNoneInitiateEnable())));
        if (isEnable()) {
            gridData.add(new GridPhotoBean(R.mipmap.posting_add, null, (bean, view) ->
                    //打开图片
                    cameraApi().openCamera(type, (what, msg, file, args) -> {
                        ImageVo vo = addPhoto(file.getPath(), what);
                        api().imageUpload(vor(DVo::getFirst, obj -> obj.getUB(TP)),
                                what, vord(DVo::getSerialNo), file, file.getPath(), vo);
                    }), null));
        }
        return gridData;
    }

    /**
     * 提交数据
     */
    protected void submit() {
        Map<String, Object> map = getSubmitMap();
        if (!TextUtils.isEmpty(map)) {
            api().submitReimburse(map);
        }
    }

    private Map<String, Object> getSubmitMap() {
        return getCheckMap(getVo().getCheckMap(getBType(), getState()),
                getSetData("报销流水号", "报销类型", "经办人", "报销人", "预算归属部门", "事由",
                        "费用指标"));
    }

    @Override
    public void notifyDataChanged() {
        super.notifyDataChanged();
        executeNon(getVo(), obj -> {
            clear().onData();
            //更新
            notifyDataSetChanged();
        });
    }

    protected void initVgApiBean(String title, IAction action) {
        if (!TextUtils.isEmpty(title)) {
            RecycleControllerApi api = getDialogControllerApi(getActivity(), RecycleControllerApi.class,
                    R.layout.l_dialog);
            api.execute(() -> api.add(new TvBean("删除", (b, v) -> {
                api.dismiss();
                execute(action);
            })).notifyDataSetChanged()).dialogShow()
                    .setText(api.findViewById(R.id.title_tv), title)
                    .setOnClickListener(api.findViewById(R.id.cancel_tv), v -> api.dismiss())
                    .execute(() -> {
                        Window window = api.getDialog().getWindow();
                        if (window != null) {
                            window.setGravity(Gravity.BOTTOM);
                            WindowManager.LayoutParams lp = window.getAttributes();
                            lp.width = DisneyUtil.getScreenDisplay().getX();
                            window.setAttributes(lp);
                        }
                    });
        }
    }

    protected void addVgBean(String title, GridBean bean) {
        if (bean != null && !(!isEnable() && TextUtils.isEmpty(bean.getData()))) {
            if (!TextUtils.isEmpty(title)) {
                addVgBean(new TvBean(title), bean);
            } else {
                addVgBean(bean);
            }
        }
    }

    protected GridBean newGridBean(int type) {
        return new GridBean(getPhotoGridBeanData(type,
                vor(DVo::getImageList, obj -> obj.getFilterDBData(type))));
    }

    protected GridBean newGridBean(int type, List<ImageVo> data) {
        return new GridBean(getPhotoGridBeanData(type, data));
    }

    private GridPhotoBean newGridPhotoBean(List<ImageVo> data, ImageVo vo) {
        return getExecute(vo, obj -> new GridPhotoBean(obj.getPhoto(), obj,
                (bean, view) -> onGridPhotoClick(data, vo),
                this::onGridPhotoLongClick));
    }

    /**
     * 图片点击
     *
     * @param data data
     * @param vo   vo
     */
    private void onGridPhotoClick(List<ImageVo> data, ImageVo vo) {
        executeNon(vo, obj -> routeApi().bill(data, vo, new Bol(isNoneInitiateEnable())));
    }

    /**
     * 图片长按
     *
     * @param bean bean
     * @param view view
     * @return 是否同时响应点击
     */
    private boolean onGridPhotoLongClick(GridPhotoBean bean, View view) {
        TvV2DialogBean db = new TvV2DialogBean("重新上传", "删除", bean.getObj() instanceof ImageVo
                && ((ImageVo) bean.getObj()).isError(), (item, v, dialog) -> {
            dialog.dismiss();
            if (bean.getObj() instanceof ImageVo) {
                ImageVo vo = (ImageVo) bean.getObj();
                vo.onError(false);
                api().imageUpload(vor(DVo::getFirst, obj -> obj.getUB(TP)),
                        vo.getInvoiceUseType(), vord(DVo::getSerialNo),
                        new File(vo.getPath()), vo.getPath(), vo);
            }
        }, (item, v, dialog) -> {
            dialog.dismiss();
            if (bean.getObj() instanceof ImageVo) {
                ImageVo vo = (ImageVo) bean.getObj();
                api().imageDelete(vord(DVo::getSerialNo), vo.getImageID(),
                        vo.getAmount(), vo.getKey(), vo.getImageID());
            }
            notifyDataChanged();
        });
        SurfaceControllerApi api = getDialogControllerApi(getActivity(), SurfaceControllerApi.class,
                db.getApiType());
        api.dialogShow().onBindViewHolder(db, 0);
        Window window = api.getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = DisneyUtil.getScreenDisplay().getX() - ResUtil.getDimenInt(R.dimen.x120);
            window.setAttributes(lp);
        }
        return true;
    }

    /**
     * 不为详情
     */
    protected boolean isEnable() {
        return !TextUtils.equals(state, XQ);
    }

    /**
     * 不为修改
     */
    protected boolean isAlterEnable() {
        return TextUtils.equals(state, XG);
    }

    /**
     * 不为发起
     */
    protected boolean isNoneInitiateEnable() {
        return !TextUtils.equals(state, FQ);
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

    private ImageVo addPhoto(String path, int type) {
        if (!TextUtils.isEmpty(path)) {
            ImageVo vo = new ImageVo(path, type);
            vos(DVo::getImageList, obj -> obj.initDB(vo));
            notifyDataChanged();
            return vo;
        }
        return null;
    }

    protected <D> void checkAdd(List<D> data, String text, D obj) {
        if (data != null && !(!isEnable() && TextUtils.isEmpty(text))) {
            data.add(obj);
        }
    }

}