package com.ylink.fullgoal.api.full;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.TvH2SBean;
import com.ylink.fullgoal.controllerApi.surface.RecycleBarControllerApi;
import com.ylink.fullgoal.fg.BankFg;
import com.ylink.fullgoal.fg.DataFg;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;

/**
 * 设定银行卡信息
 */
public class FullBankControllerApi<T extends FullBankControllerApi, C> extends RecycleBarControllerApi<T, C> {

    @Bind(R.id.null_tv)
    TextView nullTv;
    @Bind(R.id.null_vg)
    LinearLayout nullVg;

    private BankFg bankFg;
    private final static String BANK = "bank";

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
    public void initView() {
        super.initView();
        setTitle("选择银行卡");
        setText(nullTv, "你还没有相关的银行卡信息");
        bankFg = getBean(BANK, BankFg.class);
        add(DataFg.class, (path, what, msg, fg) -> {
            if (!TextUtils.isEmpty(fg.getBank())) {
                clear().showContentView();
                addVgBean(data -> execute(find(fg.getBank()), bank
                        -> data.add(new TvH2SBean(bank.getBankName(), bank.getBankNo(),
                        TextUtils.isEmpty(data), (bean, view) -> {
                    saveData(BANK, bank);
                    getActivity().finish();
                }))));
                notifyDataSetChanged();
            } else {
                showNullView(true);
            }
        });
        uApi().queryBank();
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