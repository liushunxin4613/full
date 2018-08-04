package com.ylink.fullgoal.api.full;

import com.google.gson.reflect.TypeToken;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.bean.TvH2MoreBean;
import com.ylink.fullgoal.controllerApi.surface.BaseSearchBarControllerApi;
import com.ylink.fullgoal.cr.surface.ApplyMapControllerV2;
import com.ylink.fullgoal.fg.ApplyDataFgV2;
import com.ylink.fullgoal.fg.ApplyFgV2;
import com.ylink.fullgoal.fg.DataFgV2;
import com.ylink.fullgoal.vo.ApplyVoV2;
import com.ylink.fullgoal.vo.SearchVo;

import java.util.List;
import java.util.Map;

import static com.leo.core.util.TextUtils.getJsonStringValue;
import static com.ylink.fullgoal.vo.SearchVo.APPLY;
import static com.ylink.fullgoal.vo.SearchVo.APPLY_CONTENT;

public class FullSearchControllerApiV2<T extends FullSearchControllerApiV2, C> extends BaseSearchBarControllerApi<T, C> {

    private Map<String, Object> map;

    public FullSearchControllerApiV2(C controller) {
        super(controller);
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    private Map<String, ApplyDataFgV2> getDataMap() {
        return vor(ApplyVoV2::getApply, ApplyMapControllerV2::getMap);
    }

    @Override
    public ApplyVoV2 getVo() {
        return super.getVo();
    }

    @Override
    public ApplyVoV2 newVo() {
        return new ApplyVoV2();
    }

    @Override
    public void initView() {
        super.initView();
        setMap(TextUtils.toJSONMap(getKey()));
        setTitle("单据内容");
        setRightTv("确定", v -> finishActivity(new SearchVo<>(getSearch(), getVo())));
        initAdds();
    }

    private void initAdds() {
        //申请单
        addList(DataFgV2.class, ApplyFgV2.class, (fieldName, path, what, msg, list) -> initDataAction(list));
    }

    private void initDataAction(List<ApplyFgV2> list) {
        execute(list, item -> vos(ApplyVoV2::getApply, obj -> obj.initKey(item)));
        vos(ApplyVoV2::getApply, obj -> obj.insert(decode(getValue(), ApplyMapControllerV2.class)));
        initActionData();
    }

    private void initActionData() {
        initDataAction(data -> execute(getDataMap(), (key, value) -> data.add(new TvH2MoreBean(
                vr(value, ApplyDataFgV2::getKey, ApplyFgV2::getApplyName), value.getViewValue(),
                String.format("请选择%s", vr(value, ApplyDataFgV2::getKey, ApplyFgV2::getApplyName)),
                (bean, view) -> routeApi().searchApplyContent(APPLY_CONTENT, encode(map(getMap(), m
                        -> m.put("applyType", key))), value.getApiCode()), (bean, view)
                -> value.setMap(null)))));
    }

    @Override
    protected void query() {
        super.query();
        String search = getSearch();
        if (!TextUtils.isEmpty(search)) {
            switch (search) {
                case APPLY://申请单
                    api().queryApply(getMap());
                    break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        execute(getFinish(), new TypeToken<SearchVo<Map<String, String>>>() {
        }, vo -> vos(ApplyVoV2::getApply, obj -> obj.initDB(getJsonStringValue(vo.getValue(),
                "applyType"), vo.getObj())));
        initActionData();
    }

}