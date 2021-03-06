package com.ylink.fullgoal.api.full;

import com.google.gson.reflect.TypeToken;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.controllerApi.surface.BaseSearchBarControllerApi;
import com.ylink.fullgoal.cr.surface.ApplyMapControllerV2;
import com.ylink.fullgoal.fg.ApplyDataFgV2;
import com.ylink.fullgoal.fg.ApplyFgV2;
import com.ylink.fullgoal.norm.TvH2MoreNorm;
import com.ylink.fullgoal.vo.ApplyVoV2;
import com.ylink.fullgoal.vo.SearchVo;

import java.util.List;
import java.util.Map;

import static com.leo.core.util.TextUtils.getJsonStringValue;
import static com.ylink.fullgoal.vo.SearchVo.APPLY;
import static com.ylink.fullgoal.vo.SearchVo.APPLY_CONTENT;

public class FullSearchControllerApiV1<T extends FullSearchControllerApiV1, C> extends BaseSearchBarControllerApi<T, C> {

    private String path;
    private Map<String, Object> map;
    private Map<String, String> params;

    public FullSearchControllerApiV1(C controller) {
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
        setTitle("申请单类型");
        setRightTv("确定", v -> finishActivity(new SearchVo<>(getSearch(), getVo())));
        initAdds();
    }

    private void initAdds() {
        //申请单
        addList(ApplyFgV2.class, (type, baseUrl, path, map, what, msg, field, list)
                -> initData(path, map, list));
    }

    private void initData(String path, Map<String, String> map, List<ApplyFgV2> list) {
        execute(list, item -> vos(ApplyVoV2::getApply, obj -> obj.initKey(item)));
        vos(ApplyVoV2::getApply, obj -> obj.insert(decode(getValue(), ApplyMapControllerV2.class)));
        initActionData(path, map);
    }

    private void initActionData(String path, Map<String, String> map) {
        this.path = path;
        this.params = map;
        initDataAction(path, map, data -> execute(getDataMap(), (key, value) -> data.add(new TvH2MoreNorm(
                vr(value, ApplyDataFgV2::getKey, ApplyFgV2::getApplyName), value.getViewValue(),
                String.format("请选择%s", vr(value, ApplyDataFgV2::getKey, ApplyFgV2::getApplyName)),
                (bean, view) -> routeApi().searchApplyContent(APPLY_CONTENT, encode(map(getMap(), m
                        -> m.put("applyType", key))), value.getApiCode()), (bean, view)
                -> value.setMap(null)).setEnable(true))));
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
        initActionData(path, params);
    }

}