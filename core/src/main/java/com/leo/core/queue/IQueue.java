package com.leo.core.queue;

public interface IQueue<A> {

    /**
     * 清除队列
     */
    void clear();

    /**
     * 队列长度
     */
    int count();

    /**
     * 入队
     */
    void enqueue(A obj);

    /**
     * 出队
     */
    A dequeue();

    /**
     * 查看队首
     */
    A head();

    /**
     * 查看尾端
     */
    A end();

}