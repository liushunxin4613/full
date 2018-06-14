package com.ylink.fullgoal.api.full;

import android.Manifest;
import android.annotation.SuppressLint;
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
import com.leo.core.bean.Bol;
import com.leo.core.iapi.inter.IAction;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.util.BitmapUtil;
import com.leo.core.util.DisneyUtil;
import com.leo.core.util.FileSizeUtil;
import com.leo.core.util.ResUtil;
import com.leo.core.util.SoftInputUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.api.surface.ItemControllerApi;
import com.ylink.fullgoal.bean.GridBean;
import com.ylink.fullgoal.bean.GridPhotoBean;
import com.ylink.fullgoal.bean.TvBean;
import com.ylink.fullgoal.bean.TvV2DialogBean;
import com.ylink.fullgoal.bean.VgBean;
import com.ylink.fullgoal.controllerApi.surface.RecycleBarControllerApi;
import com.ylink.fullgoal.controllerApi.surface.RecycleControllerApi;
import com.ylink.fullgoal.cr.ImageListController;
import com.ylink.fullgoal.cr.main.DVo;
import com.ylink.fullgoal.fg.ContractPaymentFg;
import com.ylink.fullgoal.fg.CostIndexFg;
import com.ylink.fullgoal.fg.CtripTicketsFg;
import com.ylink.fullgoal.fg.DataFg;
import com.ylink.fullgoal.fg.DepartmentFg;
import com.ylink.fullgoal.fg.ImageFg;
import com.ylink.fullgoal.fg.MImageFg;
import com.ylink.fullgoal.fg.MessageBackFg;
import com.ylink.fullgoal.fg.ProcessFg;
import com.ylink.fullgoal.fg.ProjectFg;
import com.ylink.fullgoal.fg.ResearchReportFg;
import com.ylink.fullgoal.fg.TravelFormFg;
import com.ylink.fullgoal.fg.UserFg;
import com.ylink.fullgoal.fg.UserList;
import com.ylink.fullgoal.hb.ImageHb;
import com.ylink.fullgoal.vo.BillVo;
import com.ylink.fullgoal.vo.ReimburseVo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static android.app.Activity.RESULT_OK;
import static com.ylink.fullgoal.config.Config.SERIAL_NO;
import static com.ylink.fullgoal.config.Config.STATE;
import static com.ylink.fullgoal.config.UrlConfig.FULL_REIMBURSE_QUERY;
import static com.ylink.fullgoal.config.UrlConfig.FULL_REIMBURSE_SUBMIT;

/**
 * 报销
 */
public class FullReimburseControllerApi<T extends FullReimburseControllerApi, C> extends RecycleBarControllerApi<T, C> {

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
    private String title;
    private int photoType;
    private File rootFile;
    private String photo;
    private String serialNo;

    FullReimburseControllerApi(C controller) {
        super(controller);
        rootFile = getContext().getExternalFilesDir("photo");
    }

    public String getState() {
        return state;
    }

    protected String getTitle() {
        return title;
    }

    protected String getSerialNo() {
        return serialNo;
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_reimburse;
    }

    @Override
    protected void startSearch(String search, ArrayList<String> filterData) {
        SoftInputUtil.hidSoftInput(getRootView());
        super.startSearch(search, filterData);
    }

    @Override
    public void initView() {
        super.initView();
        addRootType(ImageFg.class);
        addRootType(MessageBackFg.class);
        executeBundle(bundle -> {
            state = bundle.getString(STATE);
            serialNo = bundle.getString(SERIAL_NO);
            if (TextUtils.equals(state, ReimburseVo.STATE_INITIATE)) {
                if (this instanceof FullGeneralControllerApi) {
                    title = ReimburseVo.REIMBURSE_TYPE_GENERAL;
                } else if (this instanceof FullEvectionControllerApi) {
                    title = ReimburseVo.REIMBURSE_TYPE_EVECTION;
                }
            } else {
                title = state;
                if (!TextUtils.isEmpty(serialNo)) {
                    uApi().queryMessageBack(serialNo);
                }
            }
            setTitle(title);
            switch (TextUtils.isEmpty(state) ? "" : state) {
                case ReimburseVo.STATE_INITIATE:
                    setRightTv("提交", v -> submit());
                    break;
                case ReimburseVo.STATE_CONFIRM:
                    setRightTv("确认", v -> {

                    });
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
                        submit();
                    }).setOnClickListener(qxbxIv, v -> {
                        //取消报销
                        show("取消报销");
                    });
                    break;
            }
        });
        //test
        iso(DVo::getAgent, obj -> obj.initDB(getUser()));
        iso(DVo::getReimbursement, obj -> obj.initDB(getUser()));
        iso(DVo::getDepartment, obj -> obj.initDB(getDepartment()));
        iso(DVo::getBudgetDepartment, obj -> obj.initDB(getDepartment()));
        ee("core", getVo().toCheckString());
    }

    private void onBillData(List<MImageFg> data, String msg, ImageFg bean) {
        if (!TextUtils.isEmpty(data) && !TextUtils.isEmpty(msg)) {
            for (MImageFg vo : data) {
                if (TextUtils.equals(msg, vo.getPhoto())) {
                    vo.setImageID(bean.getImageId());
                    show("图片上传成功");
                } else if (TextUtils.equals(msg, vo.getImageID())) {
//                    getVo().getBillData().remove(vo); //TODO 删除图片
//                    notifyDataChanged();
                    return;
                }
            }
        }
    }

    @Override
    public void initData() {
        super.initData();
        add(ImageFg.class, (path, what, msg, bean) -> {
            if (!TextUtils.isEmpty(msg)) {
                serialNo = bean.getSerialNo();
                iso(DVo::getSerialNo, obj -> obj.initDB(serialNo));
                switch (what) {
                    case TYPE_NONE://普通
                        onBillData(gt(DVo::getImageList, ImageListController::getYbData), msg, bean);
                        break;
                    case TYPE_JT://交通费
                        onBillData(gt(DVo::getImageList, ImageListController::getJtfData), msg, bean);
                        break;
                    case TYPE_ZS://住宿费
                        onBillData(gt(DVo::getImageList, ImageListController::getZsfData), msg, bean);
                        break;
                    case TYPE_CCJP://车船机票费
                        onBillData(gt(DVo::getImageList, ImageListController::getCcjpfData), msg, bean);
                        break;
                }
            }
        });
        add(DataFg.class, (path, what, msg, bean) -> {
            if (bean.isSuccess()) {
                switch (path) {
                    case FULL_REIMBURSE_SUBMIT://报销提交
                        if (!TextUtils.isEmpty(getState())) {
                            switch (getState()) {
                                case ReimburseVo.STATE_INITIATE://经办人发起
                                    show("报销成功");
                                    break;
                                case ReimburseVo.STATE_CONFIRM://经办人确认
                                    show("报销确认成功");
                                    break;
                                case ReimburseVo.STATE_ALTER://经办人修改
                                    show("报销修改成功");
                                    break;
                            }
                        }
                        getActivity().finish();
                        break;
                }
            }
        });
        add(MessageBackFg.class, (path, what, msg, bean) -> {
            switch (path) {
                case FULL_REIMBURSE_QUERY://报销获取
//                    setVo(new ReimburseVo(bean, getSerialNo()));
                    /*getVo().setAgent(getUser());
                    getVo().setReimbursement(getUser());
                    getVo().setDepartment(getDepartmentCode());
                    getVo().setDepartmentShow(getDepartment());
                    ee("vo", getVo());*/
                    notifyDataChanged();
                    break;
            }
        });
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onResume() {
        super.onResume();
        //报销人
        executeSearch(UserFg.class, fg -> iso(DVo::getReimbursement,
                obj -> obj.initDB(new UserList(fg.getUserCode(), fg.getUserName()))));
        //预算归属部门
        executeSearch(DepartmentFg.class, fg -> iso(DVo::getBudgetDepartment, obj -> obj.initDB(fg)));
        //项目
        executeSearch(ProjectFg.class, fg -> iso(DVo::getProject, obj -> obj.initDB(fg)));
        //合同付款申请单
        executeSearch(ContractPaymentFg.class, fg -> iso(DVo::getContractPayment, obj -> obj.initDB(fg)));
        //招待申请单
        executeSearch(ProcessFg.class, fg -> iso(DVo::getProcess, obj -> obj.initDB(fg)));
        //费用指标
        executeSearch(CostIndexFg.class, fg -> iso(DVo::getCostIndex, obj -> obj.initDB(fg)));
        //出差申请单
        executeSearch(TravelFormFg.class, fg -> iso(DVo::getTrave, obj -> obj.initDB(fg)));
        //调研报告
        executeSearch(ResearchReportFg.class, fg -> iso(DVo::getReport, obj -> obj.initDB(fg)));
        //携程机票
        executeSearch(CtripTicketsFg.class, fg -> iso(DVo::getCtrip, obj -> obj.initDB(fg)));
        //票据修改金额
        /*executeNon(getFinish(BillVo.class), (BillVo vo) -> execute((BillVo billVo) -> {
                    if (TextUtils.equals(vo.getId(), billVo.getId())) {
                        billVo.setMoney(vo.getMoney());
                        getVo().initTotalAmountLower();
                    }
                }, getVo().getBillData(), getVo().getStayBillData(), getVo().getTrafficBillData(),
                getExecute(getVo().getAirDataVo(), AirDataVo::getAirBillData)));*/
        notifyDataChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PHOTO_REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {
                    executeNon(new File(rootFile, photo), file -> {
                        BitmapUtil.saveBitmapFile(BitmapUtil.getOptionBitmap(file.getPath(), 2), file);
                        Luban.with(getContext())
                                .load(file)
                                .setTargetDir(rootFile.getPath())
                                .ignoreBy(100)
                                .setCompressListener(new OnCompressListener() {
                                    @Override
                                    public void onStart() {
                                    }

                                    @Override
                                    public void onSuccess(File lubanFile) {
                                        file.delete();
                                        ee("lubanFile.getPath()", lubanFile.getPath());
                                        ee("lubanFile", FileSizeUtil.getFormetFileSize(lubanFile));
                                        String type = getImageType(photoType);
                                        addPhoto(lubanFile.getPath(), type);
                                        uApi().imageUpload(getImageFirst(), getSerialNo(),
                                                type, lubanFile, photoType, lubanFile.getPath());
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        e.printStackTrace();
                                    }
                                }).launch();
                    });
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
        ee("core", getVo().toCheckString());
    }

//    /**
//     * 初始化提交数据
//     */
//    ReimburseUpFg getReimburseUpFg(IReturnAction<ReimburseVo, ReimburseUpFg> action) {
//        return getExecute(getVo(), action);
//    }

    @Override
    public void notifyDataChanged() {
        super.notifyDataChanged();
        executeNon(getVo(), obj -> {
            clear().onData();
            //更新
            notifyDataSetChanged();
        });
    }

    /**
     * 报销数据回调
     */
    protected void onData() {
    }

    String getFirst() {
        if (!TextUtils.isEmpty(getState())) {
            switch (getState()) {
                case ReimburseVo.STATE_INITIATE://发起
                    return "1";
                case ReimburseVo.STATE_CONFIRM://确认
                    return "2";
                case ReimburseVo.STATE_ALTER://修改
                    return "3";
            }
        }
        return null;
    }

    private String getImageFirst() {
        String first = getFirst();
        return TextUtils.equals(first, "1") && !TextUtils.isEmpty(getSerialNo()) ? "6" : first;
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

    GridBean newGridBean(int type, List<MImageFg> data) {
        return new GridBean(getPhotoGridBeanData(type, data));
    }

    private GridPhotoBean newGridPhotoBean(List<MImageFg> data, MImageFg vo) {
        return getExecute(vo, obj -> new GridPhotoBean(obj.getPhoto(), obj,
                (bean, view) -> onGridPhotoClick(data, vo),
                (bean, view) -> onGridPhotoLongClick(data, bean, view)));
    }

    /**
     * 图片点击
     *
     * @param data data
     * @param vo   vo
     */
    private void onGridPhotoClick(List<MImageFg> data, MImageFg vo) {
        executeNon(vo, obj -> startSurfaceActivity(getBundle(data, vo,
                new Bol(isNoneInitiateEnable())), FullBillControllerApi.class));
    }

    /**
     * 图片长按
     *
     * @param bean bean
     * @param view view
     * @return 是否同时响应点击
     */
    private boolean onGridPhotoLongClick(List<MImageFg> data, GridPhotoBean bean, View view) {
        TvV2DialogBean db = new TvV2DialogBean("重新上传", "删除", (item, v, dialog) -> {
            dialog.dismiss();
            show(item.getName());
        }, (item, v, dialog) -> {
            dialog.dismiss();
            if (bean.getObj() instanceof BillVo) {
                BillVo vo = (BillVo) bean.getObj();
                uApi().imageDelete(getSerialNo(), vo.getId(), vo.getMoney(),
                        getPhotoType(vo.getType()), vo.getId());
            }
            notifyDataChanged();
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

    private List<GridPhotoBean> getPhotoGridBeanData(int type, List<MImageFg> data) {
        List<GridPhotoBean> gridData = new ArrayList<>();
        ee("data", data);
        execute(data, obj -> gridData.add(newGridPhotoBean(data, obj).setEnable(isEnable())
                .setVisible(isNoneInitiateEnable())));
        if (isEnable()) {
            gridData.add(new GridPhotoBean(R.mipmap.posting_add, null, (bean, view) ->
                    openCamera(type), null));
        }
        return gridData;
    }

    private void addPhoto(String path, String type) {
        if (!TextUtils.isEmpty(path)) {
            iso(DVo::getImageList, obj -> obj.initDB(new MImageFg(path, type)));
            notifyDataChanged();
        }
    }

    private void openCamera(int type) {
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            this.photoType = type;
            // 激活相机
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri photoUri;
            photo = System.currentTimeMillis() + ".jpg";
            File photoFile = new File(rootFile, photo);
            if (android.os.Build.VERSION.SDK_INT < 24) {
                // 从文件中创建uri
                photoUri = Uri.fromFile(photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            } else {
                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, photoFile.getAbsolutePath());
                //检查是否有存储权限，以免崩溃
                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    show("请开启存储权限");
                    return;
                }
                photoUri = getActivity().getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            }
            // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAMERA
            getActivity().startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
        }
    }

    private String getImageType(int photoType) {
        switch (photoType) {
            default:
            case TYPE_NONE:
                return ImageHb.IMAGE_NONE;
            case TYPE_JT:
                return ImageHb.IMAGE_JT;
            case TYPE_ZS:
                return ImageHb.IMAGE_ZS;
            case TYPE_CCJP:
                return ImageHb.IMAGE_CCJP;
        }
    }

    private int getPhotoType(String imageType) {
        switch (imageType == null ? "" : imageType) {
            default:
            case ImageHb.IMAGE_NONE:
                return TYPE_NONE;
            case ImageHb.IMAGE_JT:
                return TYPE_JT;
            case ImageHb.IMAGE_ZS:
                return TYPE_ZS;
            case ImageHb.IMAGE_CCJP:
                return TYPE_CCJP;
        }
    }

    <D> void checkAdd(List<D> data, String text, D obj) {
        if (data != null && !(!isEnable() && TextUtils.isEmpty(text))) {
            data.add(obj);
        }
    }

}
