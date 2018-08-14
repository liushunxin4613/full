package com.ylink.fullgoal.api.full;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leo.core.api.inter.CoreController;
import com.leo.core.bean.Bol;
import com.leo.core.iapi.core.INorm;
import com.leo.core.iapi.inter.IAction;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.net.Exceptions;
import com.leo.core.util.DisneyUtil;
import com.leo.core.util.JavaTypeUtil;
import com.leo.core.util.ResUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.controllerApi.surface.RecycleBarControllerApi;
import com.ylink.fullgoal.controllerApi.surface.RecycleControllerApi;
import com.ylink.fullgoal.core.SurfaceNorm;
import com.ylink.fullgoal.cr.core.DoubleController;
import com.ylink.fullgoal.cr.core.StringController;
import com.ylink.fullgoal.cr.surface.CostIndexController;
import com.ylink.fullgoal.cr.surface.DepartmentController;
import com.ylink.fullgoal.cr.surface.SbumitFlagController;
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
import com.ylink.fullgoal.norm.GridNorm;
import com.ylink.fullgoal.norm.GridPhotoNorm;
import com.ylink.fullgoal.norm.TvHintNorm;
import com.ylink.fullgoal.norm.TvNorm;
import com.ylink.fullgoal.norm.TvV2DialogNorm;
import com.ylink.fullgoal.norm.VgNorm;
import com.ylink.fullgoal.vo.ApplyVoV2;
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
import static com.ylink.fullgoal.config.ComConfig.HZ;
import static com.ylink.fullgoal.config.ComConfig.MQZ;
import static com.ylink.fullgoal.config.ComConfig.QR;
import static com.ylink.fullgoal.config.ComConfig.QZ;
import static com.ylink.fullgoal.config.ComConfig.TP;
import static com.ylink.fullgoal.config.ComConfig.UPDATE_MONEY;
import static com.ylink.fullgoal.config.ComConfig.XG;
import static com.ylink.fullgoal.config.Config.BILL_TYPE_TITLES;
import static com.ylink.fullgoal.config.Config.DATA_QR;
import static com.ylink.fullgoal.config.Config.JSON;
import static com.ylink.fullgoal.config.Config.MAIN_APP;
import static com.ylink.fullgoal.config.Config.SERIAL_NO;
import static com.ylink.fullgoal.config.Config.STATE;
import static com.ylink.fullgoal.config.Config.TITLE;
import static com.ylink.fullgoal.config.Config.XG1;
import static com.ylink.fullgoal.config.Config.XG2;
import static com.ylink.fullgoal.config.Config.XG3;
import static com.ylink.fullgoal.config.Config.XG4;
import static com.ylink.fullgoal.config.UrlConfig.FULL_APPEAL;
import static com.ylink.fullgoal.config.UrlConfig.FULL_DIMENSION_LIST;
import static com.ylink.fullgoal.config.UrlConfig.FULL_IMAGE_UPLOAD;
import static com.ylink.fullgoal.config.UrlConfig.FULL_REIMBURSE_QUERY;
import static com.ylink.fullgoal.config.UrlConfig.FULL_REIMBURSE_SUBMIT;

/**
 * 报销
 */
public abstract class FullReimburseControllerApi<T extends FullReimburseControllerApi, C> extends RecycleBarControllerApi<T, C> {

    @Bind(R.id.sqtp_tv)
    TextView sqtpTv;
    @Bind(R.id.wbyl_tv)
    TextView wbylTv;
    @Bind(R.id.xgtj_tv)
    TextView xgtjTv;
    @Bind(R.id.qxbx_tv)
    TextView qxbxTv;
    @Bind(R.id.alter_vg)
    LinearLayout alterVg;
    @Bind(R.id.content_vg)
    ViewGroup contentVg;

    private String state;
    private String title;
    private String mainApp;

    protected FullReimburseControllerApi(C controller) {
        super(controller);
    }

    public String getState() {
        return state;
    }

    private String getMainApp() {
        return mainApp;
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
    public void initAddAction() {
        super.initAddAction();
        add(Exceptions.class, (fieldName, path, what, msg, bean) -> vos(DVo::getImageList, obj
                -> obj.onError(msg)));
        add(String.class, (fieldName, path, what, msg, text) -> {
            if (TextUtils.equals(path, FULL_IMAGE_UPLOAD) && TextUtils.isNotJsonString(text)) {
                vos(DVo::getImageList, obj -> obj.onError(msg));
            }
        });
        add(ImageFg.class, (fieldName, path, what, msg, bean) -> {
            if (!TextUtils.isEmpty(msg)) {
                vos(DVo::getSerialNo, obj -> obj.initDB(bean.getSerialNo()));
                vos(DVo::getSbumitFlag, SbumitFlagController::open);
                vos(DVo::getImageList, obj -> obj.initImageFg(msg, bean, this));
            }
        });
        add(DataFg.class, (fieldName, path, what, msg, bean) -> {
            switch (path) {
                case FULL_REIMBURSE_SUBMIT://报销提交
                    if (bean.isSuccess() && !TextUtils.isEmpty(getState())) {
                        switch (getState()) {
                            case FQ://经办人发起
                                again();
                                break;
                            case QR://经办人确认
                                show("报销确认成功");
                                if (TextUtils.equals(getMainApp(), MAIN_APP)) {
                                    activityLifecycleApi().finishAllActivity();
                                } else {
                                    getActivity().finish();
                                }
                                break;
                            case XG://经办人修改
                                String logo = vor(DVo::getLogo, StringController::getDB);
                                show(String.format("%s成功", !TextUtils.isEmpty(logo)
                                        ? logo : "报销修改"));
                                if (TextUtils.equals(getMainApp(), MAIN_APP)) {
                                    dialog("是否留在报销平台", "是", "否", (bean1, v, dialog) -> {
                                        dialog.dismiss();
                                        getActivity().finish();
                                    }, (bean1, v, dialog) -> {
                                        dialog.dismiss();
                                        activityLifecycleApi().finishAllActivity();
                                    });
                                } else {
                                    getActivity().finish();
                                }
                                break;
                        }
                    }
                    break;
                case FULL_APPEAL://申诉任务
                    if (bean.isSuccess()) {
                        getActivity().finish();
                    } else {
                        show(bean.getMessage());
                    }
                    break;
                case FULL_DIMENSION_LIST://分摊维度列表
                    vos(DVo::getIsShare, obj -> obj.initDB(!TextUtils.isEmpty(bean.getDimen())));
                    break;
            }
        });
        add(RVo.class, (fieldName, path, what, msg, bean) -> {
            switch (path) {
                case FULL_REIMBURSE_QUERY://报销获取
                    bean.get(getVo());
                    notifyDataChanged();
                    showContentView();
                    dismissLoading();
                    break;
            }
        });
    }

    @Override
    public void initView() {
        super.initView();
        executeBundle(bundle -> {
            state = bundle.getString(STATE);
            title = bundle.getString(TITLE);
            mainApp = bundle.getString(MAIN_APP);
            String json = bundle.getString(JSON);
            vos(DVo::getFirst, obj -> obj.initDB(state));
            String serialNo = bundle.getString(SERIAL_NO);
            vos(DVo::getSerialNo, obj -> obj.initDB(serialNo));
            if (TextUtils.equals(state, FQ)) {
                title = getBTitle();
            } else {
                if (TextUtils.isEmpty(title)) {
                    title = getKey(BILL_TYPE_TITLES, state, state);
                }
                if (!TextUtils.isEmpty(serialNo)) {
                    hideContentView();
                    api().queryMessageBack(serialNo);
                } else if (!TextUtils.isEmpty(json)) {
                    api().queryJsonMessageBack(json);
                }
            }
            setTitle(title);
            switch (TextUtils.isEmpty(state) ? "" : state) {
                case FQ:
                    setRightTv("提交", v -> submit());
                    break;
                case QR:
                    setRightTv("确认", v -> submit());
                    break;
                case XG:
                    setRightTv("申诉", v -> api().appeal(vor(DVo::getSerialNo,
                            StringController::getDB)));
                    setVisibility(View.VISIBLE, alterVg).setOnClickListener(sqtpTv, v -> {
                        //申请特批
                        vos(DVo::getLogo, obj -> obj.initDB(XG1));
                        submit();
                    }).setOnClickListener(wbylTv, v -> {
                        //我不要了
                        vos(DVo::getLogo, obj -> obj.initDB(XG2));
                        submit();
                    }).setOnClickListener(xgtjTv, v -> {
                        //修改提交
                        vos(DVo::getLogo, obj -> obj.initDB(XG3));
                        submit();
                    }).setOnClickListener(qxbxTv, v -> {
                        //取消报销
                        vos(DVo::getLogo, obj -> obj.initDB(XG4));
                        submit();
                    });
                    break;
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        vos(DVo::getImageList, obj -> obj.setOnCom(this));
        vos(DVo::getAgent, obj -> obj.initDB(new UserFg(getUId(), getUserName())));
        vos(DVo::getReimbursement, obj -> obj.initDB(new UserFg(getUId(), getUserName())));
        vos(DVo::getDepartment, obj -> obj.initDB(getDepartment()));
        vos(DVo::getBudgetDepartment, obj -> obj.initDB(getDepartment()));
    }

    @Override
    public void onResume() {
        super.onResume();
        //报销人
        executeSearch(UserFg.class, vo -> {
            UserFg fg = vo.getObj();
            if (!TextUtils.equals(fg.getApiCode(), vorc(DVo::getReimbursement))) {//code不同时修改相关数据
                //清理费用指标数据后续
                vos(DVo::getCostIndex, CoreController::clear);//清理费用指标数据
                vos(DVo::getIsShare, CoreController::clear);//清理是否分摊数据
                //其他
                vos(DVo::getTrave, CoreController::clear);//清理出差申请单数据
                vos(DVo::getReport, CoreController::clear);//清理调研报告数据
                vos(DVo::getCtrip, CoreController::clear);//清理携程机票数据
                vos(DVo::getApply, CoreController::clear);//清理申请单数据
                //重新处理部门数据
                vos(DVo::getBudgetDepartment, obj -> obj.initDB(new DepartmentFg(
                        fg.getUserDepartmentCode(), fg.getUserDepartment())));//更新部门数据
                vos(DVo::getProject, CoreController::clear);//清理项目数据
            }
            vos(DVo::getReimbursement, obj -> obj.initDB(fg));
        });
        //预算归属部门
        executeSearch(DepartmentFg.class, vo -> {
            DepartmentFg fg = vo.getObj();
            if (!TextUtils.equals(fg.getApiCode(), vorc(DVo::getBudgetDepartment))) {//code不同时修改相关数据
                vos(DVo::getProject, CoreController::clear);//清理项目数据
                vos(DVo::getCostIndex, CoreController::clear);//清理费用指标数据
                vos(DVo::getIsShare, CoreController::clear);//清理费用指标数据时,同清理是否分摊数据
                vos(DVo::getTrave, CoreController::clear);//清理出差申请单数据
                vos(DVo::getReport, CoreController::clear);//清理调研报告数据
                vos(DVo::getApply, CoreController::clear);//清理申请单数据
            }
            vos(DVo::getBudgetDepartment, obj -> obj.initDB(vo.getObj()));
        });
        //项目
        executeSearch(ProjectFg.class, vo -> vos(DVo::getProject, obj -> obj.initDB(vo.getObj())));
        //合同付款申请单
        executeSearch(ContractPaymentFg.class, vo -> vos(DVo::getContractPayment, obj -> obj.initDB(vo.getObj())));
        //招待申请单
        executeSearch(ProcessFg.class, vo -> vos(DVo::getProcess, obj -> obj.initDB(vo.getObj())));
        //费用指标
        executeSearch(CostFg.class, vo -> {
            CostFg fg = vo.getObj();
            vos(DVo::getCostIndex, obj -> obj.initDB(fg));
            api().queryIsDimensionList(fg.getCostCode(), vor(DVo::getBudgetDepartment,
                    DepartmentController::getApiCode));//重定是否分摊数据
            vos(DVo::getApply, CoreController::clear);//清理申请单数据
        });
        //出差申请单
        executeSearch(TravelFormFg.class, vo -> vos(DVo::getTrave, obj -> obj.initDB(vo.getObj())));
        //调研报告
        executeSearch(ResearchReportFg.class, vo -> vos(DVo::getReport, obj -> obj.initDB(vo.getObj())));
        //携程机票
        executeSearch(CtripTicketsFg.class, vo -> vos(DVo::getCtrip, obj -> obj.initDB(vo.getObj())));
        //票据修改金额
        execute(getFinish(), ImageVo.class, vo -> vos(DVo::getImageList, obj -> obj.updateMoney(vo)));
        //申请单
        executeSearch(ApplyVoV2.class, vo -> getVo().setApply(vo.getObj().getApply()));
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
    public VgNorm addVgNorm(IObjAction<List<INorm>> api) {
        return super.addVgNorm(data -> {
            api.execute(data);
            execute(data, item -> {
                if (item instanceof SurfaceNorm) {
                    ((SurfaceNorm) item).setEnable(isEnable());
                }
            });
        });
    }

    String getParams() {
        return encode(map(map -> map.put("reimbursement",
                vor(DVo::getReimbursement, CoreController::getApiCode))
                .put("budgetDepartment", vor(DVo::getBudgetDepartment,
                        CoreController::getApiCode))
                .put("cost", vor(DVo::getCostIndex,
                        CoreController::getApiCode))));
    }

    //私有的

    private void again() {
        dialog("是否需要添加新的报销", "是",
                "否", (bean, v, dialog) -> {
                    dialog.dismiss();
                    getVo().initNewFields();
                    initData();
                    notifyDataChanged();
                }, (bean, v, dialog) -> {
                    dialog.dismiss();
                    getActivity().finish();
                });
    }

    private List<GridPhotoNorm> getPhotoGridBeanData(int type, List<ImageVo> data) {
        List<GridPhotoNorm> gridData = new ArrayList<>();
        execute(data, obj -> gridData.add(newGridPhotoBean(data, obj).setEnable(isEnable())
                .setVisible(isNoneInitiateEnable())));
        if (isEnable()) {
            gridData.add(new GridPhotoNorm(R.mipmap.posting_add, null, (bean, view) ->
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
    private void submit() {
        vos(DVo::getSbumitFlag, obj -> obj.initDB(String.valueOf(getFlag())));
        vos(DVo::getCostIndex, obj -> obj.update((String) vor(DVo::getMoney,
                DoubleController::getDBMoney)));
        Map<String, Object> map = getSubmitMap();
        checkAction(() -> {
            if (!TextUtils.isEmpty(map)) {
                if (getVo().getIsShare().is() && !getVo().getSbumitFlag().isOpen()) {
                    vos(DVo::getImageList, obj -> obj.updateCostFg(vor(DVo::getCostIndex,
                            CostIndexController::getDB)));//更新分摊金额
                    routeApi().costIndex(m -> m.put(DATA_QR, encode(map))
                            .put(MAIN_APP, getMainApp()));
                } else {
                    api().submitReimburse(map);
                }
            }
        });
    }

    private int getFlag() {
        if (TextUtils.equals(state, FQ) || getVo().getImageList().isNewAddImage()) {
            return 1;//新影像
        } else if (vor(DVo::getSame, obj -> obj.equals(getVo().getInfoMap()))) {
            return 2;//无任何修改
        }
        return 3;//有修改
    }

    private void checkAction(IAction action) {
        if (TextUtils.equals(vor(DVo::getCostIndex, CostIndexController::getCostName),
                "其他招待（办公）")) {
            double money = vor(DVo::getMoney, DoubleController::getdouble);
            ee("money", money);
            if (money > 3000) {
                dialog("您的报销金额超过三千元,请关联招待申请单", "确认", null, (bean, v, dialog)
                        -> dialog.dismiss(), null);
                return;
            } else if (money > 10000) {
                dialog("您的报销金额超过一万元,请问是否填错流程,是对公付款还是对私付款", "确认", null, (bean, v, dialog) -> {
                    dialog.dismiss();
                    execute(action);
                }, null);
                return;
            }
        }
        execute(action);
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

    void initVgApiBean(String title, IAction action) {
        if (!TextUtils.isEmpty(title)) {
            RecycleControllerApi api = getDialogControllerApi(getActivity(), RecycleControllerApi.class,
                    R.layout.l_dialog);
            api.execute(() -> api.add(new TvNorm("删除", (b, v) -> {
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

    void addVgNorm(String title, GridNorm norm) {
        if (norm != null && !(!isEnable() && TextUtils.isEmpty(norm.getData()))) {
            if (!TextUtils.isEmpty(title)) {
                addVgNorm(new TvHintNorm(title, isEnable()), norm);
            } else {
                addVgNorm(norm);
            }
        }
    }

    GridNorm newGridNorm(int type) {
        return new GridNorm(getPhotoGridBeanData(type,
                vor(DVo::getImageList, obj -> obj.getFilterDBData(type))));
    }

    GridNorm newGridNorm(int type, List<ImageVo> data) {
        return new GridNorm(getPhotoGridBeanData(type, data));
    }

    private GridPhotoNorm newGridPhotoBean(List<ImageVo> data, ImageVo vo) {
        return getExecute(vo, obj -> new GridPhotoNorm(obj.getBindPhoto(), obj,
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
     * @param photoNorm photoNorm
     * @param view      view
     * @return 是否同时响应点击
     */
    private boolean onGridPhotoLongClick(GridPhotoNorm photoNorm, View view) {
        TvV2DialogNorm norm = new TvV2DialogNorm("重新上传", "删除", photoNorm.getObj() instanceof ImageVo
                && ((ImageVo) photoNorm.getObj()).isError(), (item, v, dialog) -> {
            dialog.dismiss();
            if (photoNorm.getObj() instanceof ImageVo) {
                ImageVo vo = (ImageVo) photoNorm.getObj();
                vo.onError(false);
                api().imageUpload(vor(DVo::getFirst, obj -> obj.getUB(TP)),
                        vo.getInvoiceUseType(), vord(DVo::getSerialNo),
                        new File(vo.getPath()), vo.getPath(), vo);
            }
        }, (item, v, dialog) -> {
            dialog.dismiss();
            if (photoNorm.getObj() instanceof ImageVo) {
                ImageVo vo = (ImageVo) photoNorm.getObj();
                if (TextUtils.check(vo.getImageID())) {
                    api().imageDelete(vord(DVo::getSerialNo), vo.getImageID(),
                            vo.getAmount(), vo.getKey(), vo.getImageID());
                } else {
                    vos(DVo::getImageList, obj -> obj.remove(vo.getPath()));
                }
            }
            notifyDataChanged();
        });
        SurfaceControllerApi api = getDialogControllerApi(getActivity(), SurfaceControllerApi.class,
                norm.getApiType());
        api.dialogShow().onNorm(norm, 0);
        Window window = api.getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = DisneyUtil.getScreenDisplay().getX() - ResUtil.getDimenInt(R.dimen.x120);
            window.setAttributes(lp);
        }
        return true;
    }

    /**
     * 数据是否可以修改
     */
    boolean isEnable() {
        return !(TextUtils.equals(state, QZ)
                || TextUtils.equals(state, MQZ)
                || TextUtils.equals(state, HZ));
    }

    /**
     * 是否为修改
     */
    boolean isAlterEnable() {
        return TextUtils.equals(state, XG);
    }

    /**
     * 是否有金额
     */
    boolean isNoneInitiateEnable() {
        return !(TextUtils.equals(state, FQ)
                || TextUtils.equals(state, QZ));
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

    <D> void checkAdd(List<D> data, String text, D obj) {
        if (data != null && !(!isEnable() && TextUtils.isEmpty(text))) {
            data.add(obj);
        }
    }

}