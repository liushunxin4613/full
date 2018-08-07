package com.ylink.fullgoal.api.full;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leo.core.iapi.main.IApiBean;
import com.leo.core.other.Transformer;
import com.leo.core.update.IRunAction;
import com.leo.core.update.Update;
import com.leo.core.util.SoftInputUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.ChuchaiBean;
import com.ylink.fullgoal.bean.DepartmentBean;
import com.ylink.fullgoal.bean.DiaoyanBean;
import com.ylink.fullgoal.bean.PersonBean;
import com.ylink.fullgoal.bean.ProjectBeanV1;
import com.ylink.fullgoal.bean.ShareBean;
import com.ylink.fullgoal.bean.TvBean;
import com.ylink.fullgoal.bean.XiechengBean;
import com.ylink.fullgoal.controllerApi.surface.BaseSearchControllerApi;
import com.ylink.fullgoal.fg.ApplyContentFgV1;
import com.ylink.fullgoal.fg.CostFg;
import com.ylink.fullgoal.fg.CtripTicketsFg;
import com.ylink.fullgoal.fg.DataFgV1;
import com.ylink.fullgoal.fg.DepartmentFg;
import com.ylink.fullgoal.fg.DimenFg;
import com.ylink.fullgoal.fg.DimenListFg;
import com.ylink.fullgoal.fg.ProjectFg;
import com.ylink.fullgoal.fg.ResearchReportFg;
import com.ylink.fullgoal.fg.TravelFormFg;
import com.ylink.fullgoal.fg.UserFg;
import com.ylink.fullgoal.vo.SearchVo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;

import static com.ylink.fullgoal.config.Config.SEARCH_EVECTION;
import static com.ylink.fullgoal.config.Config.VERSION;
import static com.ylink.fullgoal.config.Config.VERSION_APP;
import static com.ylink.fullgoal.vo.SearchVo.APPLY;
import static com.ylink.fullgoal.vo.SearchVo.APPLY_CONTENT;
import static com.ylink.fullgoal.vo.SearchVo.BUDGET_DEPARTMENT;
import static com.ylink.fullgoal.vo.SearchVo.BUSINESS;
import static com.ylink.fullgoal.vo.SearchVo.CONTRACT_BILL;
import static com.ylink.fullgoal.vo.SearchVo.COST_INDEX;
import static com.ylink.fullgoal.vo.SearchVo.COST_INDEX_DIMEN;
import static com.ylink.fullgoal.vo.SearchVo.PROJECT;
import static com.ylink.fullgoal.vo.SearchVo.REIMBURSEMENT;
import static com.ylink.fullgoal.vo.SearchVo.REPORT;
import static com.ylink.fullgoal.vo.SearchVo.SERVE_BILL;
import static com.ylink.fullgoal.vo.SearchVo.XC_AIR;

public class FullSearchControllerApi<T extends FullSearchControllerApi, C> extends BaseSearchControllerApi<T, C> {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_et)
    EditText nameEt;
    @Bind(R.id.icon_iv)
    ImageView iconIv;
    @Bind(R.id.null_tv)
    TextView nullTv;
    @Bind(R.id.null_vg)
    LinearLayout nullVg;

    private long time;
    private Update update;

    public FullSearchControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_search;
    }

    @Override
    public void initAddAction() {
        super.initAddAction();
        //员工列表
        addList(UserFg.class, (fieldName, path, what, msg, list) -> initDataAction(data -> execute(list, item
                -> addDataOfCode(data, item, new PersonBean(item.getUserName(),
                item.getUserCode(), item.getUserDepartment(),
                (bean, view) -> finishActivity(new SearchVo<>(getSearch(), item)))))));
        //部门列表
        addList(DepartmentFg.class, (fieldName, path, what, msg, list) -> initDataAction(data -> execute(list, item
                -> addDataOfCode(data, item, new DepartmentBean(item.getDepartmentName(), (bean, view)
                -> finishActivity(new SearchVo<>(getSearch(), item)))))));
        //项目列表
        addList(ProjectFg.class, (fieldName, path, what, msg, list) -> initDataAction(data -> execute(list, item
                -> addDataOfCode(data, item, new ProjectBeanV1(item.getProjectName(), item.getProjectCode(),
                item.getStatus(), item.getLeader(), item.getLeadDepartment(),
                (bean, view) -> finishActivity(new SearchVo<>(getSearch(), item)))))));
        /*//合同付款申请单列表
        addList(ContractPaymentFg.class, (fieldName, path, what, msg, list) -> initDataAction(data -> execute(list, item
                -> data.add(new ProjectBean(item.getName(), item.getApplicationDate(),
                item.getLeader(), item.getLeadDepartment(), item.getFileNumber(), item.getStatus(),
                (bean, view) -> finishActivity(new SearchVo<>(getSearch(), item)))))));
        //招待申请单列表
        addList(ProcessFg.class, (fieldName, path, what, msg, list) -> initDataAction(data -> execute(list, item
                -> data.add(new ProjectBean(item.getApplicant(), item.getDate(),
                item.getApplyDepartment(), null, item.getAdvAmount(), item.getCause(),
                (bean, view) -> finishActivity(new SearchVo<>(getSearch(), item)))))));*/
        //费用指标列表
        addList(CostFg.class, (fieldName, path, what, msg, list) -> initDataAction(data -> execute(list, item
                -> addDataOfCode(data, item, new ShareBean(item.getCostIndex(), (bean, view)
                -> finishActivity(new SearchVo<>(getSearch(), item)))))));
        //费用指标维度列表
        /*addList(DimenListFg.class, (fieldName, path, what, msg, list) -> initDataAction(data -> execute(list, item
                -> addDataOfCode(data, item, new ShareBean(item.getName(), (bean, view)
                -> finishActivity(new SearchVo<>(getSearch(), getExecute(decode(getKey(),
                DimenFg.class), DimenFg::getCode), item)))))));*/
        addList(DimenListFg.class, (fieldName, path, what, msg, list) -> Observable.create(subscriber -> {
            List<IApiBean> itemData = new ArrayList<>();
            execute(list, item -> addDataOfCode(itemData, item, new ShareBean(item.getName(),
                    (bean, view) -> finishActivity(new SearchVo<>(getSearch(), getExecute(
                            decode(getKey(), DimenFg.class), DimenFg::getCode), item)))));
            clear().adapterDataApi().addAll(getLineData(itemData));
            subscriber.onNext(null);
            subscriber.onCompleted();
        }).compose(Transformer.getInstance())
                .subscribe(obj -> notifyDataSetChanged()
                        .dismissLoading()));
        //申请单内容
        addList(DataFgV1.class, ApplyContentFgV1.class, (fieldName, path, what, msg, list) -> initDataAction(data
                -> execute(list, item -> addDataOfCode(data, item, new TvBean(item.getName(), (bean, view)
                -> finishActivity(new SearchVo<>(getSearch(), getKey(), item)))))));
        //出差申请单
        addList(TravelFormFg.class, (fieldName, path, what, msg, list) -> initDataAction(data -> execute(list, item
                -> addDataOfCode(data, item, new ChuchaiBean(item.getCode(), item.getAmount(), item.getDestination(),
                item.getDates(), item.getStartDate(), item.getEndDate(), item.getWorkName(),
                (bean, view) -> finishActivity(new SearchVo<>(getSearch(), item)))
                .setFilter(item.getAmount())))));
        //投研报告列表
        addList(ResearchReportFg.class, (fieldName, path, what, msg, list) -> initDataAction(data -> execute(list, item
                -> addDataOfCode(data, item, new DiaoyanBean(item.getStockCode(), item.getStockName(), item.getType(),
                item.getStatus(), item.getUploadTime(), item.getEndTime(), item.getReportInfo(),
                (bean, view) -> finishActivity(new SearchVo<>(getSearch(), item)))
                .setFilter(item.getProjectCode())))));
        //携程机票列表
        addList(CtripTicketsFg.class, (fieldName, path, what, msg, list) -> initDataAction(data -> execute(list, item
                -> addDataOfCode(data, item, new XiechengBean(item.getFlightNumber(), item.getDeparture(), item.getDestination(),
                item.getCrew(), item.getTakeOffDate(), item.getTakeOffTime(), item.getTicket(), item.getArrivelDate(),
                item.getArrivelTime(), (bean, view) -> finishActivity(new SearchVo<>(getSearch(), item)))
                .setFilter(item.getFlightNumber())))));
    }

    @Override
    public void initView() {
        super.initView();
        setOnClickListener(backIv, view -> onBackPressed());
        setOnClickListener(iconIv, v -> setText(nameEt, null));
        setText(nullTv, "没有更多的数据");
        setNullView(nullVg);
        setTextHint(nameEt, getSearchTitle());
        nameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                setIcon(iconIv, count > 0);
                FullSearchControllerApi.this.lazySearch(text.toString());
            }

            @Override
            public void afterTextChanged(Editable edit) {
            }
        });
        nameEt.setOnEditorActionListener((v, actionId, event) -> {
            switch (actionId) {
                default:
                    return false;
                case EditorInfo.IME_ACTION_SEARCH://搜索
                    SoftInputUtil.hidSoftInput(v);
                    search(v.getText().toString());
                    return true;
            }
        });
    }

    private void initUpdate() {
        update = new Update(new IRunAction() {
            @Override
            public boolean isRun(long timeMillis) {
                return time > timeMillis;
            }

            @Override
            public void onListen(int count, long timeMillis) {
            }

            @Override
            public void run() {
                search(getKeyword());
            }
        }).setInterval(200);
    }

    private void lazySearch(String keyword) {
        if (update != null) {
            setKeyword(keyword);
            time = update.getAddCurrentTimeMillis(600);
            update.update();
        } else {
            search(keyword);
        }
    }

    @Override
    protected void query() {
        super.query();
        String search = getSearch();
        if (!TextUtils.isEmpty(search)) {
            switch (search) {
                case REIMBURSEMENT://员工
                    api().queryUserData();
                    break;
                case BUDGET_DEPARTMENT://预算归属部门
                    api().queryDepartmentData();
                    break;
                case PROJECT://项目
                    api().queryProjectData(getKey());
                    break;
                case CONTRACT_BILL://合同付款申请单
                    api().queryContractPaymentData();
                    break;
                case SERVE_BILL://招待申请单
                    api().queryProcessData();
                    break;
                case COST_INDEX://费用指标
                    if (TextUtils.equals(getTag(), SEARCH_EVECTION)) {
                        api().queryJsonCostIndexData();
                    } else {
                        api().queryCostIndexData(getKey());
                    }
                    break;
                case APPLY_CONTENT://申请单内容
                    api().queryApplyContent(getKey());
                    break;
                case APPLY://费用指标
                    api().queryApply(getKey());
                    break;
                case COST_INDEX_DIMEN://费用指标维度
                    DimenFg dimenFg = decode(getKey(), DimenFg.class);
                    if (TextUtils.check(dimenFg)) {
                        api().queryDimensionInformation(dimenFg.getCode());
                    }
                    break;
                case BUSINESS://出差申请单
                    api().queryTravelFormData(getKey());
                    break;
                case REPORT://投研报告
                    api().queryResearchReportData(getKey());
                    break;
                case XC_AIR://携程机票
                    api().queryCtripTicketsData(getKey());
                    break;
            }
        }
    }

}