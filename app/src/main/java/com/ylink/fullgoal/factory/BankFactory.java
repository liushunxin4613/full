package com.ylink.fullgoal.factory;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.controllerApi.core.AppControllerApi;

import java.util.Map;

public class BankFactory {

    private final static String ASSET_FILE_BANK = "bank.json";
    private final static String ASSET_FILE_BANK_NAME = "bankname.json";

    private static BankFactory instance;

    public static BankFactory getInstance() {
        if (instance == null) {
            synchronized (BankFactory.class) {
                if (instance == null) {
                    instance = new BankFactory();
                }
            }
        }
        return instance;
    }

    private Map<String, String> bankMap;
    private Map<String, String> bankNameMap;
    private AppControllerApi appControllerApi;

    public void init(AppControllerApi appControllerApi) {
        this.bankMap = TextUtils.toJSONMap(String.class, String.class,
                appControllerApi.getAssetsString(ASSET_FILE_BANK));
        this.bankNameMap = TextUtils.toJSONMap(String.class, String.class,
                appControllerApi.getAssetsString(ASSET_FILE_BANK_NAME));
    }

    public String findBank(String bankCode) {
        if (TextUtils.check(bankCode, bankMap) && bankCode.length() >= 6) {
            return bankMap.get(bankCode.substring(0, 6));
        }
        return null;
    }

    public String findBankName(String bank) {
        if (TextUtils.check(bank, bankNameMap)) {
            return bankNameMap.get(bank);
        }
        return null;
    }

}