package com.leo.core.api.api;

import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.api.main.HasCoreControllerApi;
import com.leo.core.iapi.api.IVosApi;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.inter.IReturnAction;

public class VosApi<T extends VosApi> extends HasCoreControllerApi<T> implements IVosApi<T> {

    public VosApi(CoreControllerApi controllerApi) {
        super(controllerApi);
    }

    @Override
    public <AA> AA getVo() {
        return (AA) controllerApi().getVo();
    }

    @Override
    public <AA> T vos(IObjAction<AA> action) {
        controllerApi().vs((AA) getVo(), action);
        return getThis();
    }

    @Override
    public <AA, BB> BB vor(IReturnAction<AA, BB> ab) {
        return (BB) controllerApi().vr((AA) getVo(), ab);
    }

    @Override
    public <AA, BB> T vos(IReturnAction<AA, BB> ab, IObjAction<BB> action) {
        controllerApi().vs((AA) getVo(), ab, action);
        return getThis();
    }

    @Override
    public <AA, BB, CC> CC vor(IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc) {
        return (CC) controllerApi().vr((AA) getVo(), ab, bc);
    }

    @Override
    public <AA, BB, CC> T vos(IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc,
                             IObjAction<CC> action) {
        controllerApi().vs((AA) getVo(), ab, bc, action);
        return getThis();
    }

    @Override
    public <AA, BB, CC, DD> DD vor(IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc,
                                  IReturnAction<CC, DD> cd) {
        return (DD) controllerApi().vr((AA) getVo(), ab, bc, cd);
    }

    @Override
    public <AA, BB, CC, DD> T vos(IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc,
                                 IReturnAction<CC, DD> cd, IObjAction<DD> action) {
        controllerApi().vs((AA) getVo(), ab, bc, cd, action);
        return getThis();
    }

    @Override
    public <AA, BB, CC, DD, EE> EE vor(IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc,
                                      IReturnAction<CC, DD> cd, IReturnAction<DD, EE> de) {
        return (EE) controllerApi().vr((AA) getVo(), ab, bc, cd, de);
    }

    @Override
    public <AA, BB, CC, DD, EE> T vos(IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc,
                                     IReturnAction<CC, DD> cd, IReturnAction<DD, EE> de,
                                     IObjAction<EE> action) {
        controllerApi().vs(getVo(), ab, bc, cd, de, action);
        return getThis();
    }

    @Override
    public <AA, BB, CC, DD, EE, FF> FF vor(IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc,
                                          IReturnAction<CC, DD> cd, IReturnAction<DD, EE> de,
                                          IReturnAction<EE, FF> ef) {
        return (FF) controllerApi().vr(getVo(), ab, bc, cd, de, ef);
    }

}