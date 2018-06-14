package com.ylink.fullgoal.api.surface;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leo.core.bean.BaseApiBean;
import com.leo.core.bean.Completed;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.util.ResUtil;
import com.leo.core.util.SoftInputUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.ApiBean;
import com.ylink.fullgoal.bean.CCSQDBean;
import com.ylink.fullgoal.bean.LineBean;
import com.ylink.fullgoal.bean.TvBean;
import com.ylink.fullgoal.bean.TvH2SBean;
import com.ylink.fullgoal.bean.XCJPBean;
import com.ylink.fullgoal.config.Config;
import com.ylink.fullgoal.controllerApi.surface.RecycleControllerApi;
import com.ylink.fullgoal.hb.CodeHb;
import com.ylink.fullgoal.hb.CompensationHb;
import com.ylink.fullgoal.hb.CtripHb;
import com.ylink.fullgoal.hb.DepartHb;
import com.ylink.fullgoal.hb.ProjectHb;
import com.ylink.fullgoal.hb.ReportHb;
import com.ylink.fullgoal.hb.TraveHb;
import com.ylink.fullgoal.hb.UserHb;
import com.ylink.fullgoal.vo1.SearchVo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.ylink.fullgoal.vo1.SearchVo.SEARCHS;

public class SearchControllerApi<T extends SearchControllerApi, C> extends RecycleControllerApi<T, C> {

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
    private List<String> filterData;

    public SearchControllerApi(C controller) {
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
//        posts(getSearchValue(search));
    }

    private void initAdds() {
        //消息标志
        add(CodeHb.class, (path, what, msg, bean) -> showView(bean.isSuccess()));
        //员工列表
        addList(UserHb.class, (path, what, msg, list) -> initSearchDataAction(data -> execute(list, item
                -> data.add(new TvBean(item.getUserName(), (bean, view)
                -> finishActivity(new SearchVo<>(search, item)))))));
        //部门列表
        addList(DepartHb.class, (path, what, msg, list) -> initSearchDataAction(data -> execute(list, item
                -> data.add(new TvBean(item.getDepartmentName(), (bean, view)
                -> finishActivity(new SearchVo<>(search, item)))))));
        //项目列表
        addList(ProjectHb.class, (path, what, msg, list) -> initSearchDataAction(data -> execute(list, item
                -> data.add(new TvBean(item.getProjectName(), (bean, view)
                -> finishActivity(new SearchVo<>(search, item)))))));
        //合同付款申请单列表
        addList(CompensationHb.class, (path, what, msg, list) -> initSearchDataAction(data -> execute(list, item
                -> data.add(new TvBean(item.getName(), (bean, view)
                -> finishActivity(new SearchVo<>(search, item)))))));
        //出差申请单列表及招待申请单
        addList(TraveHb.class, (path, what, msg, list) -> initSearchDataAction(data -> execute(list, item
                -> data.add(new CCSQDBean(item.getCode(), String.format("%s天", item.getDates()),
                item.getStartDate(), String.format("至%s", item.getEndDate()),
                (bean, view) -> finishActivity(new SearchVo<>(search, item)))))));
        //携程机票列表
        addList(CtripHb.class, (path, what, msg, list) -> initSearchDataAction(data -> execute(list, item
                -> data.add(new XCJPBean(item.getCrew(), item.getTicket(), item.getFlightNumber(),
                String.format("%s 开", item.getTakeoffTime()), String.format("%s 到", item.getArrivelTime()),
                String.format("%s - %s", item.getDeparture(), item.getDestination()),
                (bean, view) -> finishActivity(new SearchVo<>(search, item)))))));
        //投研报告列表
        addList(ReportHb.class, (path, what, msg, list) -> initSearchDataAction(data -> execute(list, item
                -> data.add(new TvH2SBean(item.getReportInfo(), item.getStockName(), (bean, view)
                -> finishActivity(new SearchVo<>(search, item)))))));
        //完成
        add(Completed.class, (path, what, msg, bean) -> search(keyword));
    }

    /**
     * 搜索
     *
     * @param keyword 关键字
     */
    protected void search(String keyword) {
        this.keyword = keyword;
        adapterDataApi().initFilterAction(obj -> {
            String key = null;
            if (obj instanceof ApiBean) {
                key = ((ApiBean) obj).getName();
            } else if (obj instanceof CCSQDBean) {
                key = ((CCSQDBean) obj).getName();
            } else if (obj instanceof XCJPBean) {
                key = ((XCJPBean) obj).getType();
            } else if (obj instanceof LineBean) {
                return !(adapterDataApi().getLastItem(0) instanceof LineBean)
                        && adapterDataApi().getCount() != 0;
            }
            if (!TextUtils.isEmpty(filterData)) {
                for (String filter : filterData) {
                    if (!TextUtils.isEmpty(filter) && !TextUtils.isEmpty(key)
                            && key.contains(filter)) {
                        return false;
                    }
                }
            }
            return TextUtils.isEmpty(keyword) || !TextUtils.isEmpty(key)
                    && key.contains(keyword);
        });
        notifyDataSetChanged();
    }

    private void initSearchDataAction(IObjAction<List<BaseApiBean>> action) {
        clear();
        if (action != null) {
            List<BaseApiBean> data;
            action.execute(data = new ArrayList<>());
            execute(getLineData(data), this::add);
        }
        notifyDataSetChanged();
    }

    private List<BaseApiBean> getLineData(List<BaseApiBean> data) {
        if (!TextUtils.isEmpty(data)) {
            List<BaseApiBean> dat = new ArrayList<>();
            int count = TextUtils.count(data);
            for (int i = 0; i < count; i++) {
                dat.add(data.get(i));
                dat.add(new LineBean());
            }
            return dat;
        }
        return null;
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
