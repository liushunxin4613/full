package com.ylink.fullgoal.api.surface;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.CCSQDBean;
import com.ylink.fullgoal.bean.GridBean;
import com.ylink.fullgoal.bean.IconTvHBean;
import com.ylink.fullgoal.bean.TvBean;
import com.ylink.fullgoal.bean.TvH2Bean;
import com.ylink.fullgoal.bean.TvH2MoreBean;
import com.ylink.fullgoal.bean.TvHEt3Bean;
import com.ylink.fullgoal.bean.TvHEtIconMoreBean;
import com.ylink.fullgoal.bean.XCJPBean;
import com.ylink.fullgoal.vo.AirDataVo;
import com.ylink.fullgoal.vo.AirVo;
import com.ylink.fullgoal.vo.BillVo;
import com.ylink.fullgoal.vo.BusinessVo;
import com.ylink.fullgoal.vo.ReimburseVo;

/**
 * 出差费用报销
 */
public class EvectionControllerApi<T extends EvectionControllerApi, C> extends ReimburseControllerApi<T, C> {

    public EvectionControllerApi(C controller) {
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
        if (!TextUtils.equals(getState(), ReimburseVo.STATE_INITIATE) || TextUtils.orEquals(getReimburseType(), ReimburseVo.REIMBURSE_TYPE_GENERAL_DEDICATED, ReimburseVo.REIMBURSE_TYPE_EVECTION_DEDICATED)) {
            getVo().setReimbursement("李四");
            getVo().setBudgetDepartment("信息技术部");
            getVo().setProject("第一财经中国经济论坛");
            getVo().setCause("参加第一财经中国经济论坛, 到上海出差,报销差旅费用");
            getVo().setTrafficBillData(TextUtils.getListData(new BillVo(R.mipmap.test_photo),
                    new BillVo(R.mipmap.test_photo),
                    new BillVo(R.mipmap.test_photo),
                    new BillVo(R.mipmap.test_photo)));
            getVo().setBusinessData(TextUtils.getListData(
                    new BusinessVo("FGMC-P2018-00021", "3天", "2018-03-04", "2018-03-06"),
                    new BusinessVo("FGMC-P2018-00105", "1天", "2018-03-09", "2018-03-10"),
                    new BusinessVo("FGMC-P2018-00237", "5天", "2018-03-21", "2018-03-25")));
            getVo().setAirDataVo(new AirDataVo(TextUtils.getListData(
                    new AirVo("张三", "单程", "1324.00", "2018-02-23 12:23", "次日 16:40", "上海", "北京"),
                    new AirVo("张三", "单程", "1023.00", "2018-03-12 18:23", "次日 01:20", "深圳", "北京"),
                    new AirVo("张三", "单程", "1390.00", "2018-05-04 08:00", "20:00", "上海", "深圳"),
                    new AirVo("张三", "单程", "900.00", "2018-04-26 10:55", "次日 02:30", "广州", "南京")),
                    TextUtils.getListData(new BillVo(R.mipmap.test_photo),
                            new BillVo(R.mipmap.test_photo),
                            new BillVo(R.mipmap.test_photo),
                            new BillVo(R.mipmap.test_photo),
                            new BillVo(R.mipmap.test_photo))));
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
            //经办人确认、经办人修改
            if (!TextUtils.equals(vo.getState(), ReimburseVo.STATE_INITIATE)) {
                data.add(new TvHEtIconMoreBean("金额", vo.getTotalAmountLower(), "请输入金额"));
            }
            data.add(new TvHEt3Bean("事由", vo.getCause(), "请输入事由"));
        });
        addVgBean(data -> {
            data.add(new TvBean("出差申请单添加"));
            execute(vo.getBusinessData(), item -> data.add(new CCSQDBean(item.getSerialNo(),
                    item.getDays(), item.getStartDate(), item.getEndDate())));
            data.add(new IconTvHBean("添加出差申请单", (bean, view) -> {
                show(bean.getName());
            }));
        });
        //GridBean 交通费报销
        addVgBean(new TvBean("交通费报销"), new GridBean(getPhotoGridBeanData(vo.getTrafficBillData())));
        //GridBean 住宿费报销
        addVgBean(new TvBean("住宿费报销"), new GridBean(getPhotoGridBeanData(vo.getStayBillData())));
        //GridBean 车船机票费报销
        addVgBean(data -> {
            data.add(new TvBean("车船机票费报销"));
            IconTvHBean iconTvHBean = new IconTvHBean("添加携程机票", (bean, view) -> {
                show(bean.getName());
            });
            AirDataVo airDataVo = vo.getAirDataVo();
            if (airDataVo != null) {
                execute(airDataVo.getXcAirData(), item -> data.add(new XCJPBean(item.getUser(),
                        item.getMoney(), item.getType(), String.format("%s 开", item.getStartTime()),
                        String.format("%s 到", item.getEndTime()), String.format("%s - %s",
                        item.getStartPlace(), item.getEndPlace()))));
            }
            data.add(iconTvHBean);
            data.add(new GridBean(getPhotoGridBeanData(airDataVo == null ? null : airDataVo.getAirBillData())));
        });
        //GridBean 其他报销
        addVgBean(new TvBean("其他报销"), new GridBean(getPhotoGridBeanData(vo.getOtherBillData())));
    }

}