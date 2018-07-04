package com.leo.core.iapi.api;

import com.leo.core.iapi.core.IApi;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.inter.IReturnAction;

public interface IVsApi<T extends IVsApi> extends IApi {

    <AA> T vs(AA aa, IObjAction<AA> action);

    <AA, BB> BB vr(AA aa, IReturnAction<AA, BB> ab);

    <AA, BB> T vs(AA aa, IReturnAction<AA, BB> ab, IObjAction<BB> action);

    <AA, BB, CC> CC vr(AA aa, IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc);

    <AA, BB, CC> T vs(AA aa, IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc,
                      IObjAction<CC> action);

    <AA, BB, CC, DD> DD vr(AA aa, IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc,
                           IReturnAction<CC, DD> cd);

    <AA, BB, CC, DD> T vs(AA aa, IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc,
                          IReturnAction<CC, DD> cd, IObjAction<DD> action);

    <AA, BB, CC, DD, EE> EE vr(AA aa, IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc,
                              IReturnAction<CC, DD> cd, IReturnAction<DD, EE> de);

    <AA, BB, CC, DD, EE> T vs(AA aa, IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc,
                              IReturnAction<CC, DD> cd, IReturnAction<DD, EE> de,
                              IObjAction<EE> action);

    <AA, BB, CC, DD, EE, FF> FF vr(AA aa, IReturnAction<AA, BB> ab, IReturnAction<BB, CC> bc,
                              IReturnAction<CC, DD> cd, IReturnAction<DD, EE> de,
                              IReturnAction<EE, FF> ef);

}
