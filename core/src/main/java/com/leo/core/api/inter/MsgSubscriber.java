package com.leo.core.api.inter;

import com.leo.core.iapi.api.IPathMsgApi;

import rx.Subscriber;

public abstract class MsgSubscriber<T, B> extends Subscriber<B> implements IPathMsgApi<T> {
}
