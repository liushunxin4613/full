package com.ylink.fullgoal.cr.surface;

import com.ylink.fullgoal.cr.core.MapController;
import com.ylink.fullgoal.fg.DimenListFg;
import com.ylink.fullgoal.fg.ShareDimensionItemFg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.leo.core.util.TextUtils.check;
import static com.ylink.fullgoal.config.ComConfig.QR;

public class CostItemController<T extends CostItemController> extends MapController<T, String,
        DimenListFg, List<ShareDimensionItemFg>>{

    @Override
    public T initDB(String key, DimenListFg dimenListFg) {
        return super.initDB(key, dimenListFg);
    }

    @Override
    public T put(String key, DimenListFg dimenListFg) {
        return super.put(key, dimenListFg);
    }

    @Override
    public DimenListFg getValue(String key) {
        return super.getValue(key);
    }

    @Override
    public DimenListFg getDB() {
        return super.getDB();
    }

    @Override
    public Map<String, DimenListFg> getMap() {
        return super.getMap();
    }

    @Override
    public Map<String, DimenListFg> getViewBean() {
        return super.getViewBean();
    }

    @Override
    public List<ShareDimensionItemFg> getUB(String... args) {
        return super.getUB(args);
    }

    @Override
    protected String getOnUBKey(String key) {
        switch (key){
            case QR:
                return "dimenList";
        }
        return super.getOnUBKey(key);
    }

    @Override
    protected List<ShareDimensionItemFg> getOnUB(String key) {
        switch (key){
            case QR:
                List<ShareDimensionItemFg> data = new ArrayList<>();
                execute(getMap(), (kk, vv) -> {
                    if(check(kk, vv)){
                        data.add(new ShareDimensionItemFg(kk, vv.getCode(), vv.getName()));
                    }
                });
                return data;
        }
        return super.getOnUB(key);
    }

}