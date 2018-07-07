package com.leo.core.iapi.api;

import com.leo.core.iapi.core.IApi;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.inter.IReturnAction;

public interface IVosApi<T extends IVosApi> extends IApi {

    <AA> AA getVo();

    <AA> AA newVo();

    <AA> T vos(IObjAction<AA> action);

    <AA, BB> BB vor(IReturnAction<AA, BB> ab);

    <AA, BB> T vos(IReturnAction<AA, BB> ab, IObjAction<BB> action);

    <AA, BB, CC> CC vor(IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc);

    <AA, BB, CC> T vos(IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc,
                      IObjAction<CC> action);

    <AA, BB, CC, DD> DD vor(IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc,
                           IReturnAction<CC, DD> cd);

    <AA, BB, CC, DD> T vos(IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc,
                          IReturnAction<CC, DD> cd, IObjAction<DD> action);

    <AA, BB, CC, DD, EE> EE vor(IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc,
                               IReturnAction<CC, DD> cd, IReturnAction<DD, EE> de);

    <AA, BB, CC, DD, EE> T vos(IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc,
                              IReturnAction<CC, DD> cd, IReturnAction<DD, EE> de,
                              IObjAction<EE> action);

    <AA, BB, CC, DD, EE, FF> FF vor(IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc,
                                   IReturnAction<CC, DD> cd, IReturnAction<DD, EE> de,
                                   IReturnAction<EE, FF> ef);
    
}