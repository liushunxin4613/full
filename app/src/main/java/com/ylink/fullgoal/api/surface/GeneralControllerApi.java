package com.ylink.fullgoal.api.surface;

import com.leo.core.bean.BaseApiBean;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.GridBean;
import com.ylink.fullgoal.bean.GridPhotoBean;
import com.ylink.fullgoal.bean.TvH2Bean;
import com.ylink.fullgoal.bean.TvH2MoreBean;
import com.ylink.fullgoal.bean.TvHEt3Bean;
import com.ylink.fullgoal.bean.TvHEtIconMoreBean;
import com.ylink.fullgoal.bean.VgBean;
import com.ylink.fullgoal.vo.BillVo;
import com.ylink.fullgoal.vo.ReimburseVo;

import java.util.ArrayList;
import java.util.List;

import static com.ylink.fullgoal.config.Config.STATE;
import static com.ylink.fullgoal.config.Config.TYPE;

/**
 * 一般费用报销
 */
public class GeneralControllerApi<T extends GeneralControllerApi, C> extends ReimburseControllerApi<T, C> {

    public GeneralControllerApi(C controller) {
        super(controller);
    }

    @Override
    public void initView() {
        super.initView();
        executeBundle(bundle -> {
            String state = bundle.getString(STATE);
            switch (TextUtils.isEmpty(state) ? "" : state) {
                default:
                case ReimburseVo.STATE_INITIATE:
                    setRightTv("提交", v -> show("提交"));
                    break;
                case ReimburseVo.STATE_CONFIRM:
                    setRightTv("确认", v -> show("确认"));
                    break;
                case ReimburseVo.STATE_ALTER:

                    break;
            }
        });
        testReimburseVo();
        initReimburseVo(getVo());
    }

    private void testReimburseVo() {
        executeBundle(bundle -> {
            //报销类型
            getVo().setState(bundle.getString(STATE));
            //报销状态
            getVo().setReimburseType(bundle.getString(TYPE));
        });

        getVo().setAgent("张三");
        getVo().setDepartment("计划财务部");

        //test
        if(!TextUtils.equals(getVo().getState(), ReimburseVo.STATE_INITIATE) || TextUtils.orEquals(getVo().getReimburseType(), ReimburseVo.REIMBURSE_TYPE_GENERAL_DEDICATED, ReimburseVo.REIMBURSE_TYPE_EVECTION_DEDICATED)){
            getVo().setReimbursement("李四");
            getVo().setBudgetDepartment("信息技术部");
            getVo().setProject("第一财经中国经济论坛");
            getVo().setContractBill("FGMC-CC2018-7715");
            getVo().setServeBill("FGMC-ZD2018-7715");
            getVo().setCause("参加第一财经中国经济论坛, 到上海出差,报销差旅费用");
            getVo().setBillData(TextUtils.getListData(new BillVo(R.mipmap.test_photo),
                    new BillVo(R.mipmap.test_photo),
                    new BillVo(R.mipmap.test_photo),
                    new BillVo(R.mipmap.test_photo)));
        }

        //经办人确认、经办人修改
        if (!TextUtils.equals(getVo().getState(), ReimburseVo.STATE_INITIATE)) {
            getVo().setTotalAmountLower("20000.00");
        }
    }

    @Override
    protected void onReimburseVo(ReimburseVo vo) {
        super.onReimburseVo(vo);
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
        //经办人确认、经办人修改
        if (!TextUtils.equals(vo.getState(), ReimburseVo.STATE_INITIATE)) {
            vgData.add(new TvHEtIconMoreBean("金额", vo.getTotalAmountLower(), "请输入金额"));
        }
        vgData.add(new TvHEt3Bean("事由", vo.getCause(), "请输入事由"));
        add(new VgBean(vgData));
        //GridBean
        List<GridPhotoBean> gridData = new ArrayList<>();
        if (!TextUtils.isEmpty(vo.getBillData())) {
            for (BillVo billVo : vo.getBillData()) {
                if (billVo != null && billVo.getPhoto() != null) {
                    gridData.add(new GridPhotoBean(billVo.getPhoto(), this::onGridPhotoClick, this::onGridPhotoLongClick));
                }
            }
        }
        gridData.add(new GridPhotoBean(R.mipmap.posting_add, (bean, view) -> {
            //添加图片
            show("添加票据");
        }, null));
        add(new GridBean(gridData));
    }

}
