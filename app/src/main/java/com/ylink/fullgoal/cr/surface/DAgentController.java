package com.ylink.fullgoal.cr.surface;

import com.ylink.fullgoal.cr.core.StringController;

import static com.ylink.fullgoal.config.Config.D1;
import static com.ylink.fullgoal.config.Config.D2;
import static com.ylink.fullgoal.config.Config.D3;
import static com.ylink.fullgoal.config.Config.D4;

public class DAgentController<T extends DAgentController> extends StringController<T> {

    @Override
    protected String getOnUBKey(String key) {
        switch (key) {
            case D1:
            case D2:
            case D3:
            case D4:
                return "agent";
        }
        return super.getOnUBKey(key);
    }

    @Override
    protected String getOnUB(String key) {
        switch (key) {
            case D1:
            case D2:
            case D3:
            case D4:
                return getDB();
        }
        return super.getOnUB(key);
    }

}
