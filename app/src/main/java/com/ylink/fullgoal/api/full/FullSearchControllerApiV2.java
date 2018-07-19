package com.ylink.fullgoal.api.full;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.bean.TvH2MoreBean;
import com.ylink.fullgoal.controllerApi.surface.BaseSearchBarControllerApi;
import com.ylink.fullgoal.fg.ApplyFgV2;
import com.ylink.fullgoal.fg.DataFgV2;

import java.util.List;
import java.util.Map;

import static com.ylink.fullgoal.vo.SearchVo.APPLY;
import static com.ylink.fullgoal.vo.SearchVo.APPLY_CONTENT;

public class FullSearchControllerApiV2<T extends FullSearchControllerApiV2, C> extends BaseSearchBarControllerApi<T, C> {

    private Map<String, Object> map;

    public FullSearchControllerApiV2(C controller) {
        super(controller);
    }

    @Override
    public void initView() {
        super.initView();
        map = TextUtils.toJSONMap(getKey());
        setTitle("单据内容");
        /*setRightTv("确定", v -> finishActivity(new SearchVo<>(getSearch(),
                getVo().getCheckMap(DQ).get(DQ))));*/
        setRightTv("确定", v -> ee("确定"));
        initAdds();
    }

    private void initAdds() {
        //申请单
        addList(DataFgV2.class, ApplyFgV2.class, (path, what, msg, list) -> initDataAction(list));
    }

    private void initDataAction(List<ApplyFgV2> list) {
        /*execute(list, item -> vos(ApplyVoV2::getApply, obj -> obj.initKey(item)));
        vos(ApplyVoV2::getApply, obj -> obj.initDB(decode(getValue(), TypeToken.getParameterized(
                List.class, ApplyDataFgV1.class).getType())));
        initActionData();*/
        initDataAction(data -> execute(list, item -> data.add(new TvH2MoreBean(item.getApplyName(),
                null, String.format("请选择%s", item.getApplyName()), (bean, view) -> {
            if(TextUtils.check(map)){
                map.put("applyType", item.getApplyType());
            }
            startSearch(FullAutoSearchControllerApi.class, APPLY_CONTENT, encode(map));
        }))));
    }

    private void initActionData() {
        /*Map<String, ApplyDataFgV1> map = vor(ApplyVoV2::getApply, ApplyMapControllerV2::getMap);
        initDataAction(data -> execute(map, (key, value) -> data.add(new TvH2MoreBean(vr(value,
                ApplyDataFgV1::getKey, ApplyFgV1::getName), vr(value, ApplyDataFgV1::getValue,
                ApplyContentFgV1::getName), String.format("请选择%s", vr(value, ApplyDataFgV1::getKey,
                ApplyFgV1::getName)), (bean, view) -> startSearch(FullSearchControllerApi.class,
                APPLY_CONTENT, vr(value, ApplyDataFgV1::getKey, ApplyFgV1::getCode))))));*/
    }

    @Override
    protected void query() {
        super.query();
        String search = getSearch();
        if (!TextUtils.isEmpty(search)) {
            switch (search) {
                case APPLY://申请单
                    api().queryApply(map);
                    break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        /*//申请单内容
        executeSearch(ApplyContentFgV1.class, vo -> vos(ApplyVoV2::getApply, obj
                -> obj.initDB(vo.getValue(), vo.getObj())));
        initActionData();*/
    }

}