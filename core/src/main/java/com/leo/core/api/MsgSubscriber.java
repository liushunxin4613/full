package com.leo.core.api;

import com.leo.core.iapi.IPathMsgApi;

import rx.Subscriber;

public abstract class MsgSubscriber<T, B> extends Subscriber<B> implements IPathMsgApi<T> {
}
