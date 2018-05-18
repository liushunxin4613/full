package com.ylink.fullgoal.api.surface;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;

import com.leo.core.util.ResUtil;
import com.leo.core.util.SoftInputUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.LineBean;
import com.ylink.fullgoal.bean.TvH2SBean;
import com.ylink.fullgoal.config.Config;
import com.ylink.fullgoal.controllerApi.surface.RecycleControllerApi;
import com.ylink.fullgoal.vo.SearchVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;

public class SearchControllerApi<T extends SearchControllerApi, C> extends RecycleControllerApi<T, C> {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_et)
    EditText nameEt;
    @Bind(R.id.icon_iv)
    ImageView iconIv;

    private String search;

    public SearchControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getRootViewResId() {
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
        setOnClickListener(backIv, view -> onBackPressed());
        setOnClickListener(iconIv, v -> setText(nameEt, null));
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
    }

    @Override
    public void initData() {
        super.initData();
        executeBundle(bundle -> executeNon(bundle.getString(Config.SEARCH), search -> {
            this.search = search;
            String[] args = null;
            switch (search) {
                case SearchVo.REIMBURSEMENT://报销人
                    args = new String[]{"张三", "李四", "王五", "陈六"};
                    break;
                case SearchVo.BUDGET_DEPARTMENT://预算归属部门
                    args = new String[]{"计划财务部", "信息技术部", "人事部", "行政部"};
                    break;
                case SearchVo.PROJECT://项目
                    args = new String[]{"富钱包", "富国工作平台", "富国基金", "经济论坛"};
                    break;
                case SearchVo.CONTRACT_BILL://合同付款申请单
                    args = new String[]{"一季度付款合同", "二季度付款合同", "三季度付款合同", "四季度付款合同"};
                    break;
                case SearchVo.SERVE_BILL://招待申请单
                    args = new String[]{"杨浦招待", "陆家嘴招待", "浦东招待", "松江招待"};
                    break;
                case SearchVo.COST_INDEX://招待申请单
                    args = new String[]{"营销会议费", "招待费"};
                    break;
            }
            initSearchData(getTestData(args, new Random().nextInt(10) + 3));
        }));
    }

    /**
     * 搜索
     *
     * @param keyword 关键字
     */
    protected void search(String keyword) {
        initData();
    }

    private void initSearchData(List<String> data) {
        clear();
        execute(data, obj -> {
            add(new TvH2SBean(obj, null, (bean, view) -> finishActivity(new SearchVo<>(search, obj))));
            add(new LineBean());
        });
        notifyDataSetChanged();
    }

    //test
    private List<String> getTestData(String[] args, int size) {
        if (!TextUtils.isEmpty(args) && size > 0) {
            List<String> data = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                data.add(args[new Random().nextInt(args.length)]);
            }
            return data;
        }
        return null;
    }

}
