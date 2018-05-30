package com.ylink.fullgoal.api.surface;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.reflect.TypeToken;
import com.leo.core.bean.BaseApiBean;
import com.leo.core.iapi.IObjAction;
import com.leo.core.util.DisneyUtil;
import com.leo.core.util.ResUtil;
import com.leo.core.util.SoftInputUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.GridBean;
import com.ylink.fullgoal.bean.GridPhotoBean;
import com.ylink.fullgoal.bean.TvBean;
import com.ylink.fullgoal.bean.TvHEt3Bean;
import com.ylink.fullgoal.bean.TvV2DialogBean;
import com.ylink.fullgoal.bean.VgBean;
import com.ylink.fullgoal.controllerApi.surface.BillControllerApi;
import com.ylink.fullgoal.controllerApi.surface.RecycleBarControllerApi;
import com.ylink.fullgoal.ht.BaseHt;
import com.ylink.fullgoal.ht.BudgetDepartmentHt;
import com.ylink.fullgoal.ht.ImageHt;
import com.ylink.fullgoal.ht.PaymentRequestHt;
import com.ylink.fullgoal.ht.ProjectHt;
import com.ylink.fullgoal.ht.ReimbursementHt;
import com.ylink.fullgoal.ht.ServeBillHt;
import com.ylink.fullgoal.vo.BillVo;
import com.ylink.fullgoal.vo.ImageVo;
import com.ylink.fullgoal.vo.ReimburseVo;
import com.ylink.fullgoal.vo.SearchVo;

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

    private File rootFile;
    private String state;
    private File photoFile;
    private ReimburseVo vo;
    private String reimburseType;
    private TvHEt3Bean causeBean;

    public ReimburseControllerApi(C controller) {
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

    public TvHEt3Bean getCauseBean() {
        return causeBean;
    }

    public TvHEt3Bean setCauseBean(TvHEt3Bean causeBean) {
        this.causeBean = causeBean;
        return causeBean;
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_reimburse;
    }

    @Override
    protected void startSearch(String search) {
        executeNon(getCauseBean(), obj -> executeNon(obj.getTextView(), SoftInputUtil::hidSoftInput));
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
                    case ReimburseVo.REIMBURSE_TYPE_GENERAL_COMMON:
                        getVo().setBillType(ReimburseVo.BILL_TYPE_Y);
                        getVo().setIsTickets(ReimburseVo.IS_TICKET_ZN);
                        break;
                    case ReimburseVo.REIMBURSE_TYPE_GENERAL_DEDICATED:
                        getVo().setBillType(ReimburseVo.BILL_TYPE_Y);
                        getVo().setIsTickets(ReimburseVo.IS_TICKET_ZY);
                        break;
                    case ReimburseVo.REIMBURSE_TYPE_EVECTION_COMMON:
                        getVo().setBillType(ReimburseVo.BILL_TYPE_C);
                        getVo().setIsTickets(ReimburseVo.IS_TICKET_ZN);
                        break;
                    case ReimburseVo.REIMBURSE_TYPE_EVECTION_DEDICATED:
                        getVo().setBillType(ReimburseVo.BILL_TYPE_C);
                        getVo().setIsTickets(ReimburseVo.IS_TICKET_ZY);
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
        getVo().setAgent("张三");
        getVo().setDepartment("计划财务部");
    }

    @Override
    public void initData() {
        super.initData();
        addRootType(BaseHt.class, ImageHt.class);
        add(ImageHt.class, (what, msg, bean) -> {
            if (TextUtils.isHttpUrl(bean.getUrl()) && !TextUtils.isEmpty(msg)) {
                execute(getVo().getBillData(), bill -> {
                   if(TextUtils.equals(msg, bill.getPhoto())){
                       bill.setId(bean.getId());
                       bill.setUrl(bean.getUrl());
                   }
                });
                show("图片上传成功");
            }
        });
        add(BaseHt.class, (what, msg, bean) -> {
            show("报销成功");
            getActivity().finish();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //报销人
        execute(getFinish(), new TypeToken<SearchVo<ReimbursementHt>>() {
        }, vo -> getVo().setReimbursement(getExecute(vo.getObj(),
                ReimbursementHt::getUserName)));
        //预算归属部门
        execute(getFinish(), new TypeToken<SearchVo<BudgetDepartmentHt>>() {
        }, vo -> getVo().setBudgetDepartment(getExecute(vo.getObj(),
                BudgetDepartmentHt::getDepartmentName)));
        //项目
        execute(getFinish(), new TypeToken<SearchVo<ProjectHt>>() {
        }, vo -> getVo().setProject(getExecute(vo.getObj(),
                ProjectHt::getProjectName)));
        //合同付款申请单
        execute(getFinish(), new TypeToken<SearchVo<PaymentRequestHt>>() {
        }, vo -> getVo().setPaymentRequest(getExecute(vo.getObj(),
                PaymentRequestHt::getName)));
        //招待申请单
        execute(getFinish(), new TypeToken<SearchVo<ServeBillHt>>() {
        }, vo -> getVo().setServeBill(getExecute(vo.getObj(),
                ServeBillHt::getCode)));
        initReimburseVo(getVo());
    }

    protected void submit() {
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
        execute(data, obj -> gridData.add(newGridPhotoBean(data, obj).setEnable(isEnable())));
        if (isEnable()) {
            gridData.add(new GridPhotoBean(R.mipmap.posting_add, null, (bean, view) -> openCamera(), null));
        }
        return gridData;
    }

    private void addPhoto(String path) {
        if(!TextUtils.isEmpty(path)){
            if(getVo().getBillData() == null){
                getVo().setBillData(new ArrayList<>());
            }
            getVo().getBillData().add(new BillVo(path, null));
            initReimburseVo(getVo());
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void openCamera() {
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
                                    api().uploadBase64Image(lubanFile, lubanFile.getPath());
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

    private Point[] getFullImgCropPoints(Drawable drawable) {
        if (drawable != null) {
            Point[] points = new Point[4];
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            points[0] = new Point(0, 0);
            points[1] = new Point(width, 0);
            points[2] = new Point(width, height);
            points[3] = new Point(0, height);
            return points;
        }
        return null;
    }

    /**
     * 判断sdcard是否被挂载
     */
    private static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    @Override
    public VgBean addVgBean(IObjAction<List<BaseApiBean>> api) {
        return super.addVgBean(data -> {
            api.execute(data);
            execute(data, item -> item.setEnable(isEnable()));
        });
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

    protected GridBean newGridBean(List<BillVo> data) {
        return new GridBean(getPhotoGridBeanData(data));
    }

    protected GridPhotoBean newGridPhotoBean(List<BillVo> data, BillVo vo) {
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
            initReimburseVo(getVo());
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
