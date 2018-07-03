package com.leo.core.queue;

import java.util.LinkedList;

/**
 * 队列
 */
public class Queue<A> implements IQueue<A>{

    private LinkedList<A> data;

    public Queue() {
        data = new LinkedList<>();
    }

    @Override
    public void clear() {
        data.clear();
    }

    @Override
    public int count() {
        return data.size();
    }

    @Override
    public void enqueue(A obj) {
        if(obj != null){
            data.addLast(obj);
        }
    }

    @Override
    public A dequeue() {
        if(count() > 0){
            return data.removeFirst();
        }
        return null;
    }

    @Override
    public A head() {
        return data.getFirst();
    }

    @Override
    public A end() {
        return data.getLast();
    }

}