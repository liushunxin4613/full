package com.leo.core.api.api;

import com.leo.core.api.core.ThisApi;
import com.leo.core.iapi.api.IVsApi;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.inter.IReturnAction;

import static com.leo.core.util.TextUtils.checkNull;

public class VsApi<T extends VsApi> extends ThisApi<T> implements IVsApi<T> {

    @Override
    public <AA> T vs(AA aa, IObjAction<AA> action) {
        if (checkNull(aa, action)) {
            action.execute(aa);
        }
        return getThis();
    }

    @Override
    public <AA, BB> BB vr(AA aa, IReturnAction<AA, BB> ab) {
        if (checkNull(aa, ab)) {
            return ab.execute(aa);
        }
        return null;
    }

    @Override
    public <AA, BB> T vs(AA aa, IReturnAction<AA, BB> ab, IObjAction<BB> action) {
        BB obj = vr(aa, ab);
        if (checkNull(obj, action)) {
            action.execute(obj);
        }
        return getThis();
    }

    @Override
    public <AA, BB, CC> CC vr(AA aa, IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc) {
        BB obj = vr(aa, ab);
        if (checkNull(obj, bc)) {
            return bc.execute(obj);
        }
        return null;
    }

    @Override
    public <AA, BB, CC> T vs(AA aa, IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc,
                             IObjAction<CC> action) {
        CC obj = vr(aa, ab, bc);
        if (checkNull(obj, action)) {
            action.execute(obj);
        }
        return getThis();
    }

    @Override
    public <AA, BB, CC, DD> DD vr(AA aa, IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc,
                                  IReturnAction<CC, DD> cd) {
        CC obj = vr(aa, ab, bc);
        if (checkNull(obj, cd)) {
            return cd.execute(obj);
        }
        return null;
    }

    @Override
    public <AA, BB, CC, DD> T vs(AA aa, IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc,
                                 IReturnAction<CC, DD> cd, IObjAction<DD> action) {
        DD obj = vr(aa, ab, bc, cd);
        if (checkNull(obj, action)) {
            action.execute(obj);
        }
        return getThis();
    }

    @Override
    public <AA, BB, CC, DD, EE> EE vr(AA aa, IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc,
                                      IReturnAction<CC, DD> cd, IReturnAction<DD, EE> de) {
        DD obj = vr(aa, ab, bc, cd);
        if (checkNull(obj, de)) {
            return de.execute(obj);
        }
        return null;
    }

    @Override
    public <AA, BB, CC, DD, EE> T vs(AA aa, IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc,
                                     IReturnAction<CC, DD> cd, IReturnAction<DD, EE> de,
                                     IObjAction<EE> action) {
        EE obj = vr(aa, ab, bc, cd, de);
        if (checkNull(obj, action)) {
            action.execute(obj);
        }
        return getThis();
    }

    @Override
    public <AA, BB, CC, DD, EE, FF> FF vr(AA aa, IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc,
                                          IReturnAction<CC, DD> cd, IReturnAction<DD, EE> de,
                                          IReturnAction<EE, FF> ef) {
        EE obj = vr(aa, ab, bc, cd, de);
        if (checkNull(obj, ef)) {
            return ef.execute(obj);
        }
        return null;
    }

}