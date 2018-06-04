package com.ylink.fullgoal.api.surface;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.InhibitionRuleBean;
import com.ylink.fullgoal.bean.TvH2Bean;
import com.ylink.fullgoal.bean.TvH2MoreBean;
import com.ylink.fullgoal.bean.TvH4Bean;
import com.ylink.fullgoal.bean.TvHEt3Bean;
import com.ylink.fullgoal.bean.TvHEtIconMoreBean;
import com.ylink.fullgoal.hb.ImageHb;
import com.ylink.fullgoal.hb.ReimburseHb;
import com.ylink.fullgoal.vo.ReimburseVo;
import com.ylink.fullgoal.vo.SearchVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.leo.core.util.TextUtils.getSetData;

/**
 * 一般费用报销
 */
public class GeneralControllerApi<T extends GeneralControllerApi, C> extends ReimburseControllerApi<T, C> {

    public GeneralControllerApi(C controller) {
        super(controller);
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
            data.add(new TvHEt3Bean("事由", vo.getCause(), "请输入事由", vo::setCause));
        });
        //禁止规则
        if (isAlterEnable() && !TextUtils.isEmpty(vo.getInhibitionRuleData())) {
            execute(vo.getInhibitionRuleData(), obj -> add(new InhibitionRuleBean(obj.getState(),
                    obj.getName(), obj.getDetail())));
        }
        //GridBean 添加票据
        addVgBean(null, newGridBean(TYPE_NONE, vo.getBillData()));
        //添加流程
        if (!isEnable() && !TextUtils.isEmpty(vo.getProcessData())) {
            addVgBean(data -> {
                data.add(new TvH4Bean());
                execute(vo.getProcessData(), item -> data.add(new TvH4Bean(item.getUser(),
                        item.getNode(), item.getApprovalOpinion(), item.getTime())));
            });
        }
    }

    @Override
    protected void submit() {
        super.submit();
        ReimburseHb hb = getReimburseHb(vo -> {
            List<ImageHb> data = new ArrayList<>();
            execute(vo.getBillData(), bill -> data.add(new ImageHb(bill.getId(), bill.getUrl())));
            return new ReimburseHb(getUserName(), vo.getReimbursement(), vo.getBudgetDepartment(),
                    vo.getProject(), vo.getCause(), vo.getPaymentRequest(), vo.getServeBill(), data);
        });
        Map<String, String> checkMap = getCheck(hb, getSetData("报销类型",
                "经办人", "报销人", "预算归属部门", "事由", "影像集合"));
        if (!TextUtils.isEmpty(checkMap)) {
            post("FkSbumitCompensation", map -> map.putAll(checkMap));
        }
    }

}
