package com.ylink.fullgoal.api.full;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leo.core.iapi.core.IMNApi;
import com.leo.core.update.IRunAction;
import com.leo.core.update.Update;
import com.leo.core.util.SoftInputUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.controllerApi.surface.BaseSearchControllerApi;
import com.ylink.fullgoal.factory.CoreJsonFactory;
import com.ylink.fullgoal.fg.DimenFg;
import com.ylink.fullgoal.fg.ResearchReportFg;

import butterknife.Bind;

import static com.ylink.fullgoal.config.Config.SEARCH_EVECTION;
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
    private boolean allShow;

    public FullSearchControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_search;
    }

    @Override
    public void initView() {
        super.initView();
        allShow = CoreJsonFactory.getInstance().reportAllContains(vr(TextUtils.toJSONMap(
                String.class, String.class, getKey()), m -> m.get("budgetDepartmentName")));
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
    public T add(IMNApi bean) {
        if (bean instanceof ResearchReportFg) {
            ((ResearchReportFg) bean).setAllShow(allShow);
        }
        return super.add(bean);
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