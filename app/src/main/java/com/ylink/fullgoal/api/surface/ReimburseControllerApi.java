package com.ylink.fullgoal.api.surface;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.leo.core.bean.BaseApiBean;
import com.leo.core.iapi.IAction;
import com.leo.core.iapi.IObjAction;
import com.leo.core.iapi.IReturnAction;
import com.leo.core.util.DisneyUtil;
import com.leo.core.util.ResUtil;
import com.leo.core.util.SoftInputUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.GridBean;
import com.ylink.fullgoal.bean.GridPhotoBean;
import com.ylink.fullgoal.bean.TvBean;
import com.ylink.fullgoal.bean.TvV2DialogBean;
import com.ylink.fullgoal.bean.VgBean;
import com.ylink.fullgoal.controllerApi.surface.BillControllerApi;
import com.ylink.fullgoal.controllerApi.surface.RecycleBarControllerApi;
import com.ylink.fullgoal.controllerApi.surface.RecycleControllerApi;
import com.ylink.fullgoal.hb.CompensationHb;
import com.ylink.fullgoal.hb.CtripHb;
import com.ylink.fullgoal.hb.DataHb;
import com.ylink.fullgoal.hb.DepartHb;
import com.ylink.fullgoal.hb.ImageHb;
import com.ylink.fullgoal.hb.ProjectHb;
import com.ylink.fullgoal.hb.ReimburseHb;
import com.ylink.fullgoal.hb.ReportHb;
import com.ylink.fullgoal.hb.TraveHb;
import com.ylink.fullgoal.hb.UserHb;
import com.ylink.fullgoal.vo.AirDataVo;
import com.ylink.fullgoal.vo.BillVo;
import com.ylink.fullgoal.vo.ReimburseVo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static android.app.Activity.RESULT_OK;
import static com.ylink.fullgoal.config.Config.REIMBURSE_TYPE;
import static com.ylink.fullgoal.config.Config.STATE;

/**
 * 报销
 */
public class ReimburseControllerApi<T extends ReimburseControllerApi, C> extends RecycleBarControllerApi<T, C> {

    final static int TYPE_NONE = 0x101;
    final static int TYPE_JT = 0x102;
    final static int TYPE_ZS = 0x103;
    final static int TYPE_CCJP = 0x104;

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

    private String state;
    private int photoType;
    private File rootFile;
    private File photoFile;
    private ReimburseVo vo;
    private String reimburseType;

    ReimburseControllerApi(C controller) {
        super(controller);
        rootFile = getContext().getExternalFilesDir("photo");
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
    public Integer getDefRootViewResId() {
        return R.layout.l_reimburse;
    }

    @Override
    protected void startSearch(String search) {
        SoftInputUtil.hidSoftInput(getRootView());
        super.startSearch(search);
    }

    @Override
    public void initView() {
        super.initView();
        executeBundle(bundle -> {
            state = bundle.getString(STATE);
            reimburseType = bundle.getString(REIMBURSE_TYPE);
            if (!TextUtils.isEmpty(reimburseType)) {
                switch (reimburseType) {
                    case ReimburseVo.REIMBURSE_TYPE_GENERAL:
                        getVo().setBillType(ReimburseVo.BILL_TYPE_Y);
                        break;
                    case ReimburseVo.REIMBURSE_TYPE_EVECTION:
                        getVo().setBillType(ReimburseVo.BILL_TYPE_C);
                        break;
                }
            }
            String title = TextUtils.isEmpty(state) ? reimburseType : state;
            title = TextUtils.equals(title, ReimburseVo.STATE_INITIATE) ? reimburseType : title;
            setTitle(title);
            switch (TextUtils.isEmpty(state) ? "" : state) {
                case ReimburseVo.STATE_INITIATE:
                    setRightTv("提交", v -> submit());
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
        getVo().setAgent(getUserName());
        getVo().setDepartment(getDepartment());
    }

    @Override
    public void initData() {
        super.initData();
        add(ImageHb.class, (what, msg, bean) -> {
            if (TextUtils.isHttpUrl(bean.getUrl()) && !TextUtils.isEmpty(msg)) {
                switch (what) {
                    case TYPE_NONE://普通
                        execute(getVo().getBillData(), bill -> {
                            if (TextUtils.equals(msg, bill.getPhoto())) {
                                bill.setId(bean.getId());
                                bill.setUrl(bean.getUrl());
                            }
                        });
                        break;
                    case TYPE_JT://交通费
                        execute(getVo().getTrafficBillData(), bill -> {
                            if (TextUtils.equals(msg, bill.getPhoto())) {
                                bill.setId(bean.getId());
                                bill.setUrl(bean.getUrl());
                            }
                        });
                        break;
                    case TYPE_ZS://住宿费
                        execute(getVo().getStayBillData(), bill -> {
                            if (TextUtils.equals(msg, bill.getPhoto())) {
                                bill.setId(bean.getId());
                                bill.setUrl(bean.getUrl());
                            }
                        });
                        break;
                    case TYPE_CCJP://车船机票费
                        if (getVo().getAirDataVo() != null) {
                            execute(getVo().getAirDataVo().getAirBillData(), bill -> {
                                if (TextUtils.equals(msg, bill.getPhoto())) {
                                    bill.setId(bean.getId());
                                    bill.setUrl(bean.getUrl());
                                }
                            });
                        }
                        break;
                }
                show("图片上传成功");
            }
        });
        add(DataHb.class, (what, msg, bean) -> {
            if (bean.isSuccess() && bean.getImage() == null) {
                show("报销成功");
                getActivity().finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //报销人
        executeSearch(UserHb.class, hb -> getVo().setReimbursement(
                getExecute(hb, UserHb::getUserName)));
        //预算归属部门
        executeSearch(DepartHb.class, hb -> getVo().setBudgetDepartment(
                getExecute(hb, DepartHb::getDepartmentName)));
        //项目
        executeSearch(ProjectHb.class, hb -> getVo().setProject(
                getExecute(hb, ProjectHb::getProjectName)));
        //合同付款申请单
        executeSearch(CompensationHb.class, hb -> getVo().setPaymentRequest(
                getExecute(hb, CompensationHb::getName)));
        //出差申请单及招待申请单
        executeSearch(TraveHb.class, hb -> {
            getVo().setServeBill(getExecute(hb, TraveHb::getCode));
            if (getVo().getTraveData() == null) {
                getVo().setTraveData(new ArrayList<>());
            }
            getVo().getTraveData().add(hb);
        });
        //携程机票
        executeSearch(CtripHb.class, hb -> {
            if (getVo().getAirDataVo() == null) {
                getVo().setAirDataVo(new AirDataVo(new ArrayList<>(), new ArrayList<>()));
            } else {
                if (getVo().getAirDataVo().getCtripData() == null) {
                    getVo().getAirDataVo().setCtripData(new ArrayList<>());
                }
            }
            getVo().getAirDataVo().getCtripData().add(hb);
        });
        //调研报告
        executeSearch(ReportHb.class, hb -> {
            if (getVo().getReportData() == null) {
                getVo().setReportData(new ArrayList<>());
            }
            getVo().getReportData().add(hb);
        });
        notifyReimburseVoChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PHOTO_REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {
                    executeNon(photoFile, file -> Luban.with(getContext())
                            .load(file)
                            .setTargetDir(rootFile.getPath())
                            .ignoreBy(80)
                            .setCompressListener(new OnCompressListener() {
                                @Override
                                public void onStart() {
                                }

                                @Override
                                public void onSuccess(File lubanFile) {
                                    file.delete();
                                    ee("lubanFile.getPath()", lubanFile.getPath());
                                    addPhoto(lubanFile.getPath());
                                    api().uploadBase64Image(lubanFile, lubanFile.getPath(),
                                            photoType, getImageType(photoType));
                                }

                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace();
                                }
                            }).launch());
                }
                break;

        }
    }

    @Override
    public VgBean addVgBean(IObjAction<List<BaseApiBean>> api) {
        return super.addVgBean(data -> {
            api.execute(data);
            execute(data, item -> item.setEnable(isEnable()));
        });
    }

    //私有的

    /**
     * 提交数据
     */
    protected void submit() {
    }

    /**
     * 初始化提交数据
     */
    protected ReimburseHb getReimburseHb(IReturnAction<ReimburseVo, ReimburseHb> action) {
        return getExecute(getVo(), action);
    }

    /**
     * 初始化报销数据
     */
    void notifyReimburseVoChanged() {
        executeNon(getVo(), obj -> {
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

    void initVgApiBean(String title, IAction action) {
        if (!TextUtils.isEmpty(title)) {
            RecycleControllerApi api = getDialogControllerApi(RecycleControllerApi.class,
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

    private static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    void addVgBean(String title, GridBean bean) {
        if (bean != null && !(!isEnable() && TextUtils.isEmpty(bean.getData()))) {
            if (!TextUtils.isEmpty(title)) {
                addVgBean(new TvBean(title), bean);
            } else {
                addVgBean(bean);
            }
        }
    }

    GridBean newGridBean(int type, List<BillVo> data) {
        return new GridBean(getPhotoGridBeanData(type, data));
    }

    private GridPhotoBean newGridPhotoBean(List<BillVo> data, BillVo vo) {
        return getExecute(vo, obj -> new GridPhotoBean(obj.getPhoto(), obj, this::onGridPhotoClick,
                (bean, view) -> onGridPhotoLongClick(data, bean, view)));
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
    private boolean onGridPhotoLongClick(List<BillVo> data, GridPhotoBean bean, View view) {
        TvV2DialogBean db = new TvV2DialogBean("重新上传", "删除", (item, v, dialog) -> {
            dialog.dismiss();
            show(item.getName());
        }, (item, v, dialog) -> {
            dialog.dismiss();
            data.remove(bean.getObj());
            notifyReimburseVoChanged();
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

    boolean isEnable() {
        return !TextUtils.equals(state, ReimburseVo.STATE_DETAIL);
    }

    boolean isAlterEnable() {
        return TextUtils.equals(state, ReimburseVo.STATE_ALTER);
    }

    private boolean isNoneInitiateEnable() {
        return !TextUtils.equals(state, ReimburseVo.STATE_INITIATE);
    }

    protected <B> B getEnable(B a, B b) {
        return isEnable() ? a : b;
    }

    protected <B> B getEnable(B a) {
        return isEnable() ? a : null;
    }

    <B> B getHasEnable(B a) {
        return isNoneInitiateEnable() ? a : null;
    }

    private List<GridPhotoBean> getPhotoGridBeanData(int type, List<BillVo> data) {
        List<GridPhotoBean> gridData = new ArrayList<>();
        execute(data, obj -> gridData.add(newGridPhotoBean(data, obj).setEnable(isEnable())));
        if (isEnable()) {
            gridData.add(new GridPhotoBean(R.mipmap.posting_add, null, (bean, view) ->
                    openCamera(type), null));
        }
        return gridData;
    }

    private void addPhoto(String path) {
        if (!TextUtils.isEmpty(path)) {
            switch (photoType) {
                case TYPE_NONE://普通票据
                    if (getVo().getBillData() == null) {
                        getVo().setBillData(new ArrayList<>());
                    }
                    getVo().getBillData().add(new BillVo(path, null));
                    break;
                case TYPE_JT://交通费
                    if (getVo().getTrafficBillData() == null) {
                        getVo().setTrafficBillData(new ArrayList<>());
                    }
                    getVo().getTrafficBillData().add(new BillVo(path, null));
                    break;
                case TYPE_ZS://住宿费
                    if (getVo().getStayBillData() == null) {
                        getVo().setStayBillData(new ArrayList<>());
                    }
                    getVo().getStayBillData().add(new BillVo(path, null));
                    break;
                case TYPE_CCJP://车船机票费
                    if (getVo().getAirDataVo() == null) {
                        getVo().setAirDataVo(new AirDataVo(new ArrayList<>(), new ArrayList<>()));
                    } else if (getVo().getAirDataVo().getAirBillData() == null) {
                        getVo().getAirDataVo().setAirBillData(new ArrayList<>());
                    }
                    getVo().getAirDataVo().getAirBillData().add(new BillVo(path, null));
                    break;
            }
            notifyReimburseVoChanged();
        }
    }

    private void openCamera(int type) {
        this.photoType = type;
        // 激活相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            Uri photoUri;
            photoFile = new File(rootFile, System.currentTimeMillis() + ".jpg");
            if (android.os.Build.VERSION.SDK_INT < 24) {
                // 从文件中创建uri
                photoUri = Uri.fromFile(photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            } else {
                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, photoFile.getAbsolutePath());
                //检查是否有存储权限，以免崩溃
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    show("请开启存储权限");
                    return;
                }
                photoUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            }
        }
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAMERA
        getActivity().startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
    }

    private String getImageType(int photoType) {
        switch (photoType) {
            default:
            case TYPE_NONE:
                return null;
            case TYPE_JT:
                return "1";
            case TYPE_ZS:
                return "2";
            case TYPE_CCJP:
                return "3";
        }
    }

}
