package com.leo.core.iapi.inter;

import java.util.List;

public interface IList<A> extends ICode, IShow {
    List<A> getData();
}
