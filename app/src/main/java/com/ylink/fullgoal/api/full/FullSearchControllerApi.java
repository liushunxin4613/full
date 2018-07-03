package com.ylink.fullgoal.api.full;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leo.core.bean.Completed;
import com.leo.core.iapi.api.IKeywordApi;
import com.leo.core.search.SearchUtil;
import com.leo.core.util.ResUtil;
import com.leo.core.util.SoftInputUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.LineBean;
import com.ylink.fullgoal.bean.ProjectBean;
import com.ylink.fullgoal.bean.TvBean;
import com.ylink.fullgoal.bean.TvH2SBean;
import com.ylink.fullgoal.bean.XCJPBean;
import com.ylink.fullgoal.config.Config;
import com.ylink.fullgoal.controllerApi.surface.RecycleControllerApi;
import com.ylink.fullgoal.fg.ContractPaymentFg;
import com.ylink.fullgoal.fg.CostFg;
import com.ylink.fullgoal.fg.CtripTicketsFg;
import com.ylink.fullgoal.fg.DataFg;
import com.ylink.fullgoal.fg.DepartmentFg;
import com.ylink.fullgoal.fg.DimenFg;
import com.ylink.fullgoal.fg.DimenListFg;
import com.ylink.fullgoal.fg.ProcessFg;
import com.ylink.fullgoal.fg.ProjectFg;
import com.ylink.fullgoal.fg.ResearchReportFg;
import com.ylink.fullgoal.fg.TravelFormFg;
import com.ylink.fullgoal.fg.UserFg;
import com.ylink.fullgoal.vo.SearchVo;

import java.util.List;

import butterknife.Bind;

import static com.ylink.fullgoal.vo.SearchVo.BUDGET_DEPARTMENT;
import static com.ylink.fullgoal.vo.SearchVo.BUSINESS;
import static com.ylink.fullgoal.vo.SearchVo.CONTRACT_BILL;
import static com.ylink.fullgoal.vo.SearchVo.COST_INDEX;
import static com.ylink.fullgoal.vo.SearchVo.COST_INDEX_DIMEN;
import static com.ylink.fullgoal.vo.SearchVo.PROJECT;
import static com.ylink.fullgoal.vo.SearchVo.REIMBURSEMENT;
import static com.ylink.fullgoal.vo.SearchVo.REPORT;
import static com.ylink.fullgoal.vo.SearchVo.SEARCHS;
import static com.ylink.fullgoal.vo.SearchVo.SERVE_BILL;
import static com.ylink.fullgoal.vo.SearchVo.XC_AIR;

public class FullSearchControllerApi<T extends FullSearchControllerApi, C> extends RecycleControllerApi<T, C> {

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
    private String search;
    private String keyword;
    private DimenFg dimenFg;
    private List<String> filterData;

    public FullSearchControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_search;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getActivity().finish();
    }

    @Override
    public T finishActivity(Object obj) {
        SoftInputUtil.hidSoftInput(nameEt);
        return super.finishActivity(obj);
    }

    @Override
    public void initView() {
        super.initView();
        getRecyclerView().setBackgroundColor(ResUtil.getColor(R.color.white));
        adapterDataApi().setEmptyAction(this::showView);
        setOnClickListener(backIv, view -> onBackPressed());
        setOnClickListener(iconIv, v -> setText(nameEt, null));
        setText(nullTv, "没有更多的数据");
        setNullView(nullVg);
        executeBundle(bundle -> {
            executeNon(bundle.getString(Config.SEARCH), search -> this.search = search);
            executeNon(bundle.getString(Config.VALUE), search -> this.dimenFg = decode(search, DimenFg.class));
            executeNon(bundle.getStringArrayList(Config.FILTERS), data -> this.filterData = data);
        });
        nameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                setIcon(iconIv, count > 0).search(text.toString());
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
        initAdds();
    }

    @Override
    public void initData() {
        super.initData();
        query(search);
    }

    private void initAdds() {
        //消息标志
        add(DataFg.class, (path, what, msg, bean) -> showView(bean.isSuccess()));
        //员工列表
        addList(UserFg.class, (path, what, msg, list) -> initDataAction(data -> execute(list, item
                -> data.add(new TvH2SBean(item.getUserName(), item.getUserDepartment(), (bean, view)
                -> finishActivity(new SearchVo<>(search, item)))))));
        //部门列表
        addList(DepartmentFg.class, (path, what, msg, list) -> initDataAction(data -> execute(list, item
                -> data.add(new TvBean(item.getDepartmentName(), (bean, view)
                -> finishActivity(new SearchVo<>(search, item)))))));
        //项目列表
        addList(ProjectFg.class, (path, what, msg, list) -> initDataAction(data -> execute(list, item
                -> data.add(new ProjectBean(item.getProjectName(), item.getApplicationDate(),
                item.getLeader(), item.getLeadDepartment(), item.getAmount(), item.getJudtification(),
                (bean, view) -> finishActivity(new SearchVo<>(search, item)))))));
        //合同付款申请单列表
        addList(ContractPaymentFg.class, (path, what, msg, list) -> initDataAction(data -> execute(list, item
                -> data.add(new ProjectBean(item.getName(), item.getApplicationDate(),
                item.getLeader(), item.getLeadDepartment(), item.getFileNumber(), item.getStatus(),
                (bean, view) -> finishActivity(new SearchVo<>(search, item)))))));
        //招待申请单列表
        addList(ProcessFg.class, (path, what, msg, list) -> initDataAction(data -> execute(list, item
                -> data.add(new ProjectBean(item.getApplicant(), item.getDate(),
                item.getApplyDepartment(), null, item.getAdvAmount(), item.getCause(),
                (bean, view) -> finishActivity(new SearchVo<>(search, item)))))));
        //费用指标列表
        addList(CostFg.class, (path, what, msg, list) -> initDataAction(data -> execute(list, item
                -> data.add(new TvH2SBean(item.getCostIndex(), item.getShare(), (bean, view)
                -> finishActivity(new SearchVo<>(search, item)))))));
        //费用指标维度列表
        addList(DimenListFg.class, (path, what, msg, list) -> initDataAction(data -> execute(list, item
                -> data.add(new TvBean(item.getName(), (bean, view)
                -> finishActivity(new SearchVo<>(search, getExecute(dimenFg, DimenFg::getCode), item)))))));
        //出差申请单
        addList(TravelFormFg.class, (path, what, msg, list) -> initDataAction(data -> execute(list, item
                -> data.add(new XCJPBean(item.getAmount(), String.format("%s天", item.getDates()),
                item.getWorkName(), String.format("%s 开", item.getStartDate()), String.format("%s 到",
                item.getEndDate()), item.getDestination(),
                (bean, view) -> finishActivity(new SearchVo<>(search, item)))
                .setFilter(item.getAmount())))));
        //投研报告列表
        addList(ResearchReportFg.class, (path, what, msg, list) -> initDataAction(data -> execute(list, item
                -> data.add(new ProjectBean(item.getStockName(), item.getEndTime(),
                item.getStockCode(), item.getStatus(), item.getType(), item.getReportInfo(),
                (bean, view) -> finishActivity(new SearchVo<>(search, item)))
                .setFilter(item.getProjectCode())))));
        //携程机票列表
        addList(CtripTicketsFg.class, (path, what, msg, list) -> initDataAction(data -> execute(list, item
                -> data.add(new XCJPBean(item.getCrew(), item.getTicket(), item.getFlightNumber(),
                String.format("%s 开", item.getTakeOffDate()), String.format("%s 到", item.getArrivelTime()),
                String.format("%s - %s", item.getDeparture(), item.getDestination()),
                (bean, view) -> finishActivity(new SearchVo<>(search, item)))
                .setFilter(item.getFlightNumber())))));
        //完成
        add(Completed.class, (path, what, msg, bean) -> search(keyword));
    }

    /**
     * 数据请求
     */
    private void query(String search) {
        if (!TextUtils.isEmpty(search)) {
            switch (search) {
                case REIMBURSEMENT://员工
                    api().queryUserData();
                    break;
                case BUDGET_DEPARTMENT://预算归属部门
                    api().queryDepartmentData();
                    break;
                case PROJECT://项目
                    api().queryProjectData(getDepartmentCode());
                    break;
                case CONTRACT_BILL://合同付款申请单
                    api().queryContractPaymentData();
                    break;
                case SERVE_BILL://招待申请单
                    api().queryProcessData();
                    break;
                case COST_INDEX://费用指标
                    api().queryCostIndexData();
                    break;
                case COST_INDEX_DIMEN://费用指标维度
                    if (TextUtils.check(dimenFg)) {
                        api().queryDimensionInformation(dimenFg.getCode());
                    }
                    break;
                case BUSINESS://出差申请单
                    api().queryTravelFormData();
                    break;
                case REPORT://投研报告
                    api().queryResearchReportData();
                    break;
                case XC_AIR://携程机票
                    api().queryCtripTicketsData();
                    break;
            }
        }
    }

    /**
     * 搜索
     *
     * @param keyword 关键字
     */
    protected void search(String keyword) {
        this.keyword = keyword;
        adapterDataApi().initFilterAction(obj -> {
            if (obj instanceof LineBean) {
                return !(adapterDataApi().getLastItem(0) instanceof LineBean)
                        && adapterDataApi().getCount() != 0;
            } else if (obj instanceof IKeywordApi) {
                String key = ((IKeywordApi) obj).getKeyword();
                String fk = ((IKeywordApi) obj).getFilter();
                if (!TextUtils.isEmpty(filterData)) {
                    for (String filter : filterData) {
                        if (!TextUtils.isEmpty(filter) && !TextUtils.isEmpty(fk)
                                && fk.contains(filter)) {
                            return false;
                        }
                    }
                }
                return TextUtils.isEmpty(keyword) || SearchUtil.search(obj, keyword);
            }
            return true;
        });
        notifyDataSetChanged();
    }

    private String getSearchValue(String key) {
        if (!TextUtils.isEmpty(SEARCHS) && !TextUtils.isEmpty(key)) {
            for (String[] args : SEARCHS) {
                if (!TextUtils.isEmpty(args)) {
                    if (TextUtils.count(args) == 2 &&
                            TextUtils.equals(args[0], key)) {
                        return args[1];
                    }
                }
            }
        }
        return null;
    }

    private void showView(boolean enable) {
        if (enable) {
            showContentView();
        } else {
            showNullView(true);
        }
    }

}
