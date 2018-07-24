package com.ylink.fullgoal.api.full;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.leo.core.util.SoftInputUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.config.JsonHelper;
import com.ylink.fullgoal.config.MVCFactory;
import com.ylink.fullgoal.config.MVCFactoryV1;
import com.ylink.fullgoal.config.Node;
import com.ylink.fullgoal.controllerApi.surface.BaseSearchControllerApi;
import com.ylink.fullgoal.vo.SearchVo;

import java.util.List;
import java.util.Map;

import butterknife.Bind;

import static com.leo.core.util.TextUtils.toJsonString;
import static com.ylink.fullgoal.config.Config.TEMPLATE_FULL;
import static com.ylink.fullgoal.vo.SearchVo.APPLY_CONTENT;

public class FullAutoSearchControllerApi<T extends FullAutoSearchControllerApi, C> extends BaseSearchControllerApi<T, C> {

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

    public FullAutoSearchControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_search;
    }

    @Override
    public void initView() {
        super.initView();
        setOnClickListener(backIv, view -> onBackPressed());
        setOnClickListener(iconIv, v -> setText(nameEt, null));
        setText(nullTv, "没有更多的数据");
        setNullView(nullVg);
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

    private void initAdds() {
        add(String.class, (path, what, msg, response)
                -> JsonHelper.newBuilder()
                .add(List.class, (parent, list) -> onData(path, msg, list),
                        new Node("applyCodeResult2"))
                .execute(response));
    }

    private void onData(String path, String params, List list) {
        if (!TEMPLATE_FULL) {
            MVCFactory.getInstance().onData(path, toJsonString(getKey(), true, "applyType"), list, d
                    -> initDataAction(data -> execute(d, item -> {
                item.setOnClickListener((bean, view) -> finishActivity(new SearchVo<>(getSearch(),
                        getKey(), bean.getMap(), new TypeToken<SearchVo<Map<String, String>>>() {
                })));
                data.add(item);
            })));
        } else {
            MVCFactoryV1.getInstance().onData(path, toJsonString(getKey(), true, "applyType"), list, d
                    -> initDataAction(data -> execute(d, item -> {
                item.setOnClickListener((bean, view) -> finishActivity(new SearchVo<>(getSearch(),
                        getKey(), bean.getMap(), new TypeToken<SearchVo<Map<String, String>>>() {
                })));
                data.add(item);
            })));
        }
    }

    @Override
    protected void query() {
        super.query();
        String search = getSearch();
        if (!TextUtils.isEmpty(search)) {
            switch (search) {
                case APPLY_CONTENT://申请单内容
                    api().queryApplyContent(TextUtils.toJSONMap(getKey()));
                    break;
            }
        }
    }

}