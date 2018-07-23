package com.ylink.fullgoal.api.full;

import com.google.gson.reflect.TypeToken;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.bean.TvH2MoreBean;
import com.ylink.fullgoal.controllerApi.surface.BaseSearchBarControllerApi;
import com.ylink.fullgoal.cr.surface.ApplyMapController;
import com.ylink.fullgoal.fg.ApplyContentFgV1;
import com.ylink.fullgoal.fg.ApplyDataFgV1;
import com.ylink.fullgoal.fg.ApplyFgV1;
import com.ylink.fullgoal.fg.DataFgV1;
import com.ylink.fullgoal.vo.ApplyVo;
import com.ylink.fullgoal.vo.SearchVo;

import java.util.List;
import java.util.Map;

import static com.ylink.fullgoal.config.ComConfig.DQ;
import static com.ylink.fullgoal.vo.SearchVo.APPLY;
import static com.ylink.fullgoal.vo.SearchVo.APPLY_CONTENT;

public class FullSearchControllerApiV1<T extends FullSearchControllerApiV1, C> extends BaseSearchBarControllerApi<T, C> {

    public FullSearchControllerApiV1(C controller) {
        super(controller);
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("单据内容");
        setRightTv("确定", v -> finishActivity(new SearchVo<>(getSearch(),
                getVo().getCheckMap(DQ).get(DQ))));
        initAdds();
    }

    private void initAdds() {
        //申请单
        addList(DataFgV1.class, ApplyFgV1.class, (path, what, msg, list) -> initDataAction(list));
    }

    private void initDataAction(List<ApplyFgV1> list) {
        execute(list, item -> vos(ApplyVo::getApply, obj -> obj.initKey(item)));
        vos(ApplyVo::getApply, obj -> obj.initDB(decode(getValue(), TypeToken.getParameterized(
                List.class, ApplyDataFgV1.class).getType())));
        initActionData();
    }

    private void initActionData() {
        Map<String, ApplyDataFgV1> map = vor(ApplyVo::getApply, ApplyMapController::getMap);
        initDataAction(data -> execute(map, (key, value) -> data.add(new TvH2MoreBean(vr(value,
                ApplyDataFgV1::getKey, ApplyFgV1::getName), vr(value, ApplyDataFgV1::getValue,
                ApplyContentFgV1::getName), String.format("请选择%s", vr(value, ApplyDataFgV1::getKey,
                ApplyFgV1::getName)), (bean, view) -> routeApi().search(APPLY_CONTENT,
                vr(value, ApplyDataFgV1::getKey, ApplyFgV1::getCode))))));
    }

    @Override
    protected void query() {
        super.query();
        String search = getSearch();
        if (!TextUtils.isEmpty(search)) {
            switch (search) {
                case APPLY://申请单
                    api().queryApply(getKey());
                    break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //申请单内容
        executeSearch(ApplyContentFgV1.class, vo -> vos(ApplyVo::getApply, obj
                -> obj.initDB(vo.getValue(), vo.getObj())));
        initActionData();
    }

    @Override
    public ApplyVo getVo() {
        return super.getVo();
    }

    @Override
    public ApplyVo newVo() {
        return new ApplyVo();
    }

}