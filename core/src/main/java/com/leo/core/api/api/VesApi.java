package com.leo.core.api.api;

import com.leo.core.iapi.api.IVosApi;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.inter.IReturnAction;

public class VesApi<T extends VesApi> extends VsApi<T> implements IVosApi<T> {

    private Object obj;

    @Override
    public <AA> AA getVo() {
        return newVo();
    }

    @Override
    public <AA> AA newVo() {
        return null;
    }

    @Override
    public <AA> T vos(IObjAction<AA> action) {
        vs(getVo(), action);
        return getThis();
    }

    @Override
    public <AA, BB> BB vor(IReturnAction<AA, BB> ab) {
        return vr(getVo(), ab);
    }

    @Override
    public <AA, BB> T vos(IReturnAction<AA, BB> ab, IObjAction<BB> action) {
        vs(getVo(), ab, action);
        return getThis();
    }

    @Override
    public <AA, BB, CC> CC vor(IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc) {
        return vr(getVo(), ab, bc);
    }

    @Override
    public <AA, BB, CC> T vos(IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc,
                              IObjAction<CC> action) {
        vs(getVo(), ab, bc, action);
        return getThis();
    }

    @Override
    public <AA, BB, CC, DD> DD vor(IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc,
                                   IReturnAction<CC, DD> cd) {
        return vr(getVo(), ab, bc, cd);
    }

    @Override
    public <AA, BB, CC, DD> T vos(IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc,
                                  IReturnAction<CC, DD> cd, IObjAction<DD> action) {
        vs(getVo(), ab, bc, cd, action);
        return getThis();
    }

    @Override
    public <AA, BB, CC, DD, EE> EE vor(IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc,
                                       IReturnAction<CC, DD> cd, IReturnAction<DD, EE> de) {
        return vr(getVo(), ab, bc, cd, de);
    }

    @Override
    public <AA, BB, CC, DD, EE> T vos(IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc,
                                      IReturnAction<CC, DD> cd, IReturnAction<DD, EE> de,
                                      IObjAction<EE> action) {
        vs(getVo(), ab, bc, cd, de, action);
        return getThis();
    }

    @Override
    public <AA, BB, CC, DD, EE, FF> FF vor(IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc,
                                           IReturnAction<CC, DD> cd, IReturnAction<DD, EE> de,
                                           IReturnAction<EE, FF> ef) {
        return vr(getVo(), ab, bc, cd, de, ef);
    }

}