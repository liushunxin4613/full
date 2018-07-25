package com.ylink.fullgoal.api.full;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.BankBean;
import com.ylink.fullgoal.controllerApi.surface.RecycleBarControllerApi;
import com.ylink.fullgoal.factory.BankFactory;
import com.ylink.fullgoal.fg.DataFg;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * 设定银行卡信息
 */
public class FullBankControllerApiV1<T extends FullBankControllerApiV1, C> extends RecycleBarControllerApi<T, C> {

    @Bind(R.id.null_tv)
    TextView nullTv;
    @Bind(R.id.null_vg)
    LinearLayout nullVg;

    private final static String BANK = "bank";
    private Map<String, BankBean> beanMap;

    public FullBankControllerApiV1(C controller) {
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
    public void initView() {
        super.initView();
        switchDefault();
        setTitle("选择银行卡");
        setText(nullTv, "你还没有相关的银行卡信息");
        beanMap = new HashMap<>();
        add(DataFg.class, (path, what, msg, fg) -> {
            if (!TextUtils.isEmpty(fg.getBankCardtList2())) {
                clear().showContentView();
                execute(fg.getBankCardtList2(), bank
                        -> add(find(new BankBean(bank.getBankNo(), TextUtils.isEmpty(adapterDataApi()
                        .getData()), (bean, view) -> {
                    getActivity().finish();
                    api().submitBankV1(bank.getBankNo(), bank.getBankName());
                }))));
                notifyDataSetChanged();
            } else {
                showNullView(true);
            }
        });
        add(String.class, (path, what, bankCode, text) -> executeNon(TextUtils.toJSONMap(text), map -> {
            BankBean bean = beanMap.get(bankCode);
            if (bean != null) {
                String bankName = "银行卡";
                Object obj = map.get("bank");
                if (obj instanceof String) {
                    String bank = (String) obj;
                    String name = BankFactory.getInstance().findBankName(bank);
                    if (TextUtils.check(name)) {
                        bankName = name;
                    }
                }
                bean.setName(bankName);
                bean.updateBind();
            }
        }));
        api().queryBankV1();
    }

    private BankBean find(BankBean bean) {
        if (TextUtils.check(bean) && TextUtils.check(bean.getBankNo())
                && !TextUtils.check(bean.getName())) {
            api().queryBankType(bean.getBankNo());
            beanMap.put(bean.getBankNo(), bean);
        }
        return bean;
    }

}