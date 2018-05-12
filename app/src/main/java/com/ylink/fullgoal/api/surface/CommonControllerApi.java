package com.ylink.fullgoal.api.surface;

import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.leo.core.bean.BaseApiBean;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.DisneyUtil;
import com.leo.core.util.ResUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.GridBean;
import com.ylink.fullgoal.bean.GridPhotoBean;
import com.ylink.fullgoal.bean.TvH2Bean;
import com.ylink.fullgoal.bean.TvH2MoreBean;
import com.ylink.fullgoal.bean.TvHEt3Bean;
import com.ylink.fullgoal.bean.TvHEtIconMoreBean;
import com.ylink.fullgoal.bean.TvV2DialogBean;
import com.ylink.fullgoal.bean.VgBean;
import com.ylink.fullgoal.controllerApi.surface.RecycleBarControllerApi;
import com.ylink.fullgoal.vo.BillVo;
import com.ylink.fullgoal.vo.ReimburseVo;

import java.util.ArrayList;
import java.util.List;

import static com.ylink.fullgoal.config.Config.STATE;
import static com.ylink.fullgoal.config.Config.TITLE;

/**
 * 一般费用报销
 */
public class CommonControllerApi<T extends CommonControllerApi, C> extends RecycleBarControllerApi<T, C> {

    private ReimburseVo reimburseVo;

    public CommonControllerApi(C controller) {
        super(controller);
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
        executeNon(getActivity().getIntent().getExtras(), bundle -> setTitle(bundle.getString(TITLE)));
        setRightTv("提交", v -> show("提交"));
        testReimburseVo();
        initReimburseVo(reimburseVo);
    }

    private void testReimburseVo() {
        reimburseVo = new ReimburseVo();
        executeNon(getActivity().getIntent().getExtras(), bundle -> reimburseVo.setReimburseType(bundle.getString(STATE)));
        reimburseVo.setAgent("张三");
        reimburseVo.setDepartment("计划财务部");

        //test
        reimburseVo.setReimbursement("李四");
        reimburseVo.setBudgetDepartment("信息技术部");
        reimburseVo.setProject("第一财经中国经济论坛");
        reimburseVo.setContractBill("FGMC-CC2018-7715");
        reimburseVo.setServeBill("FGMC-ZD2018-7715");
        reimburseVo.setCause("参加第一财经中国经济论坛, 到上海出差,报销差旅费用");
        reimburseVo.setBillData(TextUtils.getListData(new BillVo(R.mipmap.test_photo),
                new BillVo(R.mipmap.test_photo),
                new BillVo(R.mipmap.test_photo),
                new BillVo(R.mipmap.test_photo)));
    }

    /**
     * 初始化数据
     */
    private void initReimburseVo(ReimburseVo vo) {
        executeNon(vo, obj -> {
            clear();
            //VgBean
            List<BaseApiBean> vgData = new ArrayList<>();
            vgData.add(new TvH2Bean(vo.getAgent(), vo.getDepartment()));
            vgData.add(new TvHEtIconMoreBean(R.mipmap.test_icon_user, "报销人", vo.getReimbursement(), "请输入报销人", (bean, view) -> {
                show(bean.getName() + ", " + bean.getText());
            }));
            vgData.add(new TvH2MoreBean("预算归属部门", vo.getBudgetDepartment(), "请选择预算归属部门", (bean, view) -> {
                show(bean.getName());
            }));
            vgData.add(new TvH2MoreBean("项目", vo.getProject(), "请选择项目", (bean, view) -> {
                show(bean.getName());
            }));
            vgData.add(new TvH2MoreBean("合同付款申请单", vo.getContractBill(), "请选择合同付款申请单", (bean, view) -> {
                show(bean.getName());
            }));
            vgData.add(new TvH2MoreBean("招待申请单", vo.getServeBill(), "请选择招待申请单", (bean, view) -> {
                show(bean.getName());
            }));
            vgData.add(new TvHEt3Bean("事由", vo.getCause(), "请输入事由"));
            add(new VgBean(vgData));
            //GridBean
            List<GridPhotoBean> gridData = new ArrayList<>();
            if (!TextUtils.isEmpty(vo.getBillData())) {
                for (BillVo billVo : vo.getBillData()) {
                    if(billVo != null && billVo.getPhoto() != null){
                        gridData.add(new GridPhotoBean(billVo.getPhoto(), this::onGridPhotoClick, this::onGridPhotoLongClick));
                    }
                }
            }
            gridData.add(new GridPhotoBean(R.mipmap.posting_add, (bean, view) -> {
                //添加图片
                show("添加票据");
            }, null));
            add(new GridBean(gridData));
            //更新
            notifyDataSetChanged();
        });
    }


    /**
     * 图片点击
     *
     * @param bean bean
     * @param view view
     */
    private void onGridPhotoClick(GridPhotoBean bean, View view) {
        show("图片");
    }

    /**
     * 图片长按
     *
     * @param bean bean
     * @param view view
     * @return 是否同时响应点击
     */
    private boolean onGridPhotoLongClick(GridPhotoBean bean, View view) {
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
