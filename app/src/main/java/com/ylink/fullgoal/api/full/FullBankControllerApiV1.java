package com.ylink.fullgoal.api.full;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.TvH2SBean;
import com.ylink.fullgoal.controllerApi.surface.RecycleBarControllerApi;
import com.ylink.fullgoal.factory.BankFactory;
import com.ylink.fullgoal.fg.BankFg;
import com.ylink.fullgoal.fg.DataFg;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

    private BankFg bankFg;
    private final static String BANK = "bank";
    private Map<String, TvH2SBean> beanMap;

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
        bankFg = getBean(BANK, BankFg.class);
        beanMap = new HashMap<>();
        add(DataFg.class, (path, what, msg, fg) -> {
            if (!TextUtils.isEmpty(fg.getBankCardtList2())) {
                clear().showContentView();
                addVgBean(data -> execute(find(fg.getBankCardtList2()), bank
                        -> data.add(find(new TvH2SBean(BankFactory.getInstance().findBank(
                        bank.getBankNo()), bank.getBankNo(), TextUtils.isEmpty(data), (bean, view) -> {
                    saveData(BANK, bank);
                    bankFg = getBean(BANK, BankFg.class);
                    getActivity().finish();
                    api().submitBankV1(bank.getBankNo(), bank.getBankName());
                })))));
                notifyDataSetChanged();
            } else {
                showNullView(true);
            }
        });
        add(String.class, (path, what, bankCode, text) -> executeNon(TextUtils.toJSONMap(text), map -> {
            TvH2SBean bean = beanMap.get(bankCode);
            if(bean != null){
                String bankName = "银行卡";
                Object obj = map.get("bank");
                if (obj instanceof String) {
                    String bank = (String) obj;
                    String name = BankFactory.getInstance().findBankName(bank);
                    if(TextUtils.check(name)){
                        bankName = name;
                    }
                }
                bean.setName(bankName);
                bean.updateBind();
            }
        }));
        api().queryBankV1();
    }

    private TvH2SBean find(TvH2SBean bean) {
        if (TextUtils.check(bean) && TextUtils.check(bean.getDetail())
                && !TextUtils.check(bean.getName())) {
            api().queryBankType(bean.getDetail());
            beanMap.put(bean.getDetail(), bean);
        }
        return bean;
    }

    private List<BankFg> find(List<BankFg> data) {
        if (!TextUtils.isEmpty(data)) {
            int swap = 0;
            for (int i = 0; i < data.size(); i++) {
                BankFg item = data.get(i);
                if (TextUtils.check(item, bankFg)) {
                    if (TextUtils.equals(item.getBankName(), bankFg.getBankName())
                            && TextUtils.equals(item.getBankNo(), bankFg.getBankNo())) {
                        swap = i;
                        break;
                    }
                }
            }
            Collections.swap(data, swap, 0);
        }
        return data;
    }

}