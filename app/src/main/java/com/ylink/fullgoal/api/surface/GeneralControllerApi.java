package com.ylink.fullgoal.api.surface;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.GridBean;
import com.ylink.fullgoal.bean.InhibitionRuleBean;
import com.ylink.fullgoal.bean.TvH2Bean;
import com.ylink.fullgoal.bean.TvH2MoreBean;
import com.ylink.fullgoal.bean.TvHEt3Bean;
import com.ylink.fullgoal.bean.TvHEtIconMoreBean;
import com.ylink.fullgoal.vo.BillVo;
import com.ylink.fullgoal.vo.InhibitionRuleVo;
import com.ylink.fullgoal.vo.ReimburseVo;

import static com.ylink.fullgoal.vo.InhibitionRuleVo.STATE_RED;
import static com.ylink.fullgoal.vo.InhibitionRuleVo.STATE_YELLOW;

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
        testReimburseVo();
        initReimburseVo(getVo());
    }

    private void testReimburseVo() {
        //test
        if(!TextUtils.equals(getState(), ReimburseVo.STATE_INITIATE) || TextUtils.orEquals(getReimburseType(), ReimburseVo.REIMBURSE_TYPE_GENERAL_DEDICATED, ReimburseVo.REIMBURSE_TYPE_EVECTION_DEDICATED)){
            getVo().setReimbursement("李四");
            getVo().setBudgetDepartment("信息技术部");
            getVo().setProject("第一财经中国经济论坛");
            getVo().setContractBill("FGMC-CC2018-7715");
            getVo().setServeBill("FGMC-ZD2018-7715");
            getVo().setCause("参加第一财经中国经济论坛, 到上海出差,报销差旅费用");
            getVo().setBillData(TextUtils.getListData(new BillVo(R.mipmap.test_photo, getHasEnable("199.00")),
                    new BillVo(R.mipmap.test_photo, getHasEnable("2009.00")),
                    new BillVo(R.mipmap.test_photo, getHasEnable("1231.00")),
                    new BillVo(R.mipmap.test_photo, getHasEnable("3229.00"))));
            if (isAlterEnable()) {
                String agent = getVo().getAgent();
                getVo().setInhibitionRuleData(TextUtils.getListData(
                        new InhibitionRuleVo("差旅费报销中不能有餐费报销", STATE_RED,
                                String.format("%s的差旅费报销中含有餐费发票", agent)),
                        new InhibitionRuleVo("招待费用超标", STATE_YELLOW,
                                String.format("%s招待费用超出标准%s元,请申请特批", agent, "2000")),
                        new InhibitionRuleVo("差旅费报销中不能有餐费报销", STATE_RED,
                                String.format("%s的差旅费报销中含有餐费发票", agent)),
                        new InhibitionRuleVo("招待费用超标", STATE_YELLOW,
                                String.format("%s招待费用超出标准%s元,请申请特批", agent, "2000"))
                ));
            }
        }
        //经办人确认、经办人修改
        if (!TextUtils.equals(getState(), ReimburseVo.STATE_INITIATE)) {
            getVo().setTotalAmountLower("20000.00");
        }
    }

    @Override
    protected void onReimburseVo(ReimburseVo vo) {
        super.onReimburseVo(vo);
        //VgBean 基本信息组
        addVgBean(data -> {
            //经办人、部门
            data.add(new TvH2Bean(vo.getAgent(), vo.getDepartment()));
            data.add(new TvHEtIconMoreBean(R.mipmap.test_icon_user, "报销人", vo.getReimbursement(), "请输入报销人", (bean, view) -> {
                show(bean.getName() + ", " + bean.getText());
            }));
            data.add(new TvH2MoreBean("预算归属部门", vo.getBudgetDepartment(), "请选择预算归属部门", (bean, view) -> {
                show(bean.getName());
            }));
            data.add(new TvH2MoreBean("项目", vo.getProject(), "请选择项目", (bean, view) -> {
                show(bean.getName());
            }));
            data.add(new TvH2MoreBean("合同付款申请单", vo.getContractBill(), "请选择合同付款申请单", (bean, view) -> {
                show(bean.getName());
            }));
            data.add(new TvH2MoreBean("招待申请单", vo.getServeBill(), "请选择招待申请单", (bean, view) -> {
                show(bean.getName());
            }));
            //经办人确认、经办人修改
            if (!TextUtils.equals(vo.getState(), ReimburseVo.STATE_INITIATE)) {
                data.add(new TvHEtIconMoreBean("金额", vo.getTotalAmountLower(), "请输入金额"));
            }
            data.add(new TvHEt3Bean("事由", vo.getCause(), "请输入事由"));
        });
        //禁止规则
        if (isAlterEnable() && !TextUtils.isEmpty(vo.getInhibitionRuleData())) {
            execute(vo.getInhibitionRuleData(), obj -> add(new InhibitionRuleBean(obj.getState(),
                    obj.getName(), obj.getDetail())));
        }
        //GridBean 添加票据
        addVgBean(new GridBean(getPhotoGridBeanData(vo.getBillData())));
    }

}
