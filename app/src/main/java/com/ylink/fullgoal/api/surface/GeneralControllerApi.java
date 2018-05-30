package com.ylink.fullgoal.api.surface;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.InhibitionRuleBean;
import com.ylink.fullgoal.bean.TvH2Bean;
import com.ylink.fullgoal.bean.TvH2MoreBean;
import com.ylink.fullgoal.bean.TvH4Bean;
import com.ylink.fullgoal.bean.TvHEt3Bean;
import com.ylink.fullgoal.bean.TvHEtIconMoreBean;
import com.ylink.fullgoal.vo.BillVo;
import com.ylink.fullgoal.vo.InhibitionRuleVo;
import com.ylink.fullgoal.vo.ProcessVo;
import com.ylink.fullgoal.vo.ReimburseVo;
import com.ylink.fullgoal.vo.SearchVo;

import java.util.Map;

import static com.leo.core.util.TextUtils.getListData;
import static com.leo.core.util.TextUtils.getSetData;
import static com.ylink.fullgoal.config.Config.DEBUG;
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
        if (DEBUG) {
            testReimburseVo();
        }
        initReimburseVo(getVo());
    }

    @Override
    protected void submit() {
        super.submit();
        Map<String, String> checkMap = getCheck(getVo().init(), getSetData("报销类型", "是否专票",
                "经办人", "报销人", "预算归属部门", "事由", "影像集合"), getSetData("报销流水号",
                "金额", "项目", "合同申请单", "招待申请单", "投研报告", "提交标志"));
        if (!TextUtils.isEmpty(checkMap)) {
            api().post("FkSbumitCompensation", checkMap);
        }
    }

    private void testReimburseVo() {
        //test
        if (!TextUtils.equals(getState(), ReimburseVo.STATE_INITIATE)) {
//        if (!TextUtils.equals(getState(), ReimburseVo.STATE_INITIATE) ||
//                TextUtils.orEquals(getReimburseType(), ReimburseVo.REIMBURSE_TYPE_GENERAL_DEDICATED,
//                        ReimburseVo.REIMBURSE_TYPE_EVECTION_DEDICATED)) {
            getVo().setReimbursement("李四");
            getVo().setBudgetDepartment("信息技术部");
            getVo().setProject("第一财经中国经济论坛");
            getVo().setPaymentRequest("FGMC-CC2018-7715");
            getVo().setServeBill("FGMC-ZD2018-7715");
            getVo().setCause("参加第一财经中国经济论坛, 到上海出差,报销差旅费用");
            getVo().setBillData(getListData(new BillVo(R.mipmap.test_photo, getHasEnable("199.00")),
                    new BillVo(R.mipmap.test_photo, getHasEnable("2009.00")),
                    new BillVo(R.mipmap.test_photo, getHasEnable("1231.00")),
                    new BillVo(R.mipmap.test_photo, getHasEnable("3229.00"))));
            if (isAlterEnable()) {
                String agent = getVo().getAgent();
                getVo().setInhibitionRuleData(getListData(
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
        if (!isEnable()) {
            getVo().setProcessData(getListData(
                    new ProcessVo("2018-02-05 18:18:55", "张三", "发票认证", "同意"),
                    new ProcessVo("2018-02-07 11:23:12", "李四", "发票验证", "验真"),
                    new ProcessVo("2018-02-11 16:54:34", "王五", "财务审核", "同意")
            ));
        }
    }

    @Override
    protected void onReimburseVo(ReimburseVo vo) {
        super.onReimburseVo(vo);
        //VgBean 基本信息组
        addVgBean(data -> {
            //经办人、部门
            data.add(new TvH2Bean(vo.getAgent(), vo.getDepartment()));
            data.add(new TvHEtIconMoreBean(R.mipmap.test_icon_user, "报销人", vo.getReimbursement(),
                    "请输入报销人", (bean, view) -> startSearch(SearchVo.REIMBURSEMENT), vo::setReimbursement));
            data.add(new TvH2MoreBean("预算归属部门", vo.getBudgetDepartment(), "请选择预算归属部门",
                    (bean, view) -> startSearch(SearchVo.BUDGET_DEPARTMENT)));
            data.add(new TvH2MoreBean("项目", vo.getProject(), "请选择项目",
                    (bean, view) -> startSearch(SearchVo.PROJECT)));
            data.add(new TvH2MoreBean("合同付款申请单", vo.getPaymentRequest(), "请选择合同付款申请单",
                    (bean, view) -> startSearch(SearchVo.CONTRACT_BILL)));
            data.add(new TvH2MoreBean("招待申请单", vo.getServeBill(), "请选择招待申请单",
                    (bean, view) -> startSearch(SearchVo.SERVE_BILL)));
            //经办人确认、经办人修改
            if (!TextUtils.equals(vo.getState(), ReimburseVo.STATE_INITIATE)) {
                data.add(new TvHEtIconMoreBean("金额", vo.getTotalAmountLower(), "请输入金额"));
            }
            data.add(setCauseBean(new TvHEt3Bean("事由", vo.getCause(), "请输入事由", vo::setCause)));
        });
        //禁止规则
        if (isAlterEnable() && !TextUtils.isEmpty(vo.getInhibitionRuleData())) {
            execute(vo.getInhibitionRuleData(), obj -> add(new InhibitionRuleBean(obj.getState(),
                    obj.getName(), obj.getDetail())));
        }
        //GridBean 添加票据
        addVgBean(null, newGridBean(vo.getBillData()));
        //添加流程
        if (!isEnable() && !TextUtils.isEmpty(vo.getProcessData())) {
            addVgBean(data -> {
                data.add(new TvH4Bean());
                execute(vo.getProcessData(), item -> data.add(new TvH4Bean(item.getUser(),
                        item.getNode(), item.getApprovalOpinion(), item.getTime())));
            });
        }
    }

}
