package com.ylink.fullgoal.api.full;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leo.core.iapi.core.IModel;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.controllerApi.surface.RecycleBarControllerApi;
import com.ylink.fullgoal.fg.DataFg;

import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * 设定银行卡信息
 */
public class FullBankControllerApi<T extends FullBankControllerApi, C> extends RecycleBarControllerApi<T, C> {

    @Bind(R.id.null_tv)
    TextView nullTv;
    @Bind(R.id.null_vg)
    LinearLayout nullVg;

    public FullBankControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_bar_content_recycle;
    }

    @Override
    public View getNullView() {
        return nullVg;
    }

    @Override
    public void onBackPressed() {
        routeApi().main();
    }

    @Override
    protected List<? extends IModel> getOnDataFg(String type, String baseUrl, String path,
                                                 Map<String, String> map, int what, String msg,
                                                 String field, DataFg fg) {
        return fg.getBank();
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("选择银行卡");
        setText(nullTv, "你还没有相关的银行卡信息");
        api().queryBankV1();
    }

}