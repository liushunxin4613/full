package com.ylink.fullgoal.db.core;

import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.other.MMap;
import com.leo.core.util.RunUtil;
import com.leo.core.util.TextUtils;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLOperator;
import com.raizlabs.android.dbflow.sql.language.property.Property;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.ModelAdapter;

import java.util.ArrayList;
import java.util.List;

public class DBModel extends BaseModel {

    protected static SQLOperator[] ops(Class<? extends BaseModel> modelClz, IObjAction<MMap<String, Object>> action) {
        if (TextUtils.check(modelClz, action)) {
            List<SQLOperator> data = new ArrayList<>();
            RunUtil.execute(TextUtils.map(action), (key, value) -> {
                if (TextUtils.check(key, value)) {
                    try {
                        ModelAdapter adapter = (ModelAdapter) FlowManager.getInstanceAdapter(modelClz);
                        Property property = adapter.getProperty(key);
                        if (property != null) {
                            data.add(property.eq(value));
                        }
                    } catch (Exception ignored) {
                    }
                }
            });
            if (TextUtils.check(data)) {
                SQLOperator[] args = new SQLOperator[data.size()];
                RunUtil.executePos(data, (item, position) -> args[position] = item);
                return args;
            }
        }
        return new SQLOperator[]{};
    }

}