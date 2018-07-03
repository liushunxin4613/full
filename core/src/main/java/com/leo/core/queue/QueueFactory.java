package com.leo.core.queue;

import android.os.Handler;
import android.os.Looper;

import com.leo.core.iapi.inter.IAction;

public class QueueFactory {

    private static QueueFactory instance;

    public static QueueFactory getInstance() {
        if (instance == null) {
            synchronized (QueueFactory.class) {
                if (instance == null) {
                    instance = new QueueFactory();
                }
            }
        }
        return instance;
    }

    private Thread thread;
    private Thread uiThread;
    private int interval = 1000;
    private Queue<IAction> queue;
    private Queue<IAction> uiQueue;
    private Handler handler;

    private QueueFactory() {
        queue = new Queue<>();
        uiQueue = new Queue<>();
        handler = new Handler(Looper.getMainLooper());
        thread = new Thread(() -> {
            while (queue.count() > 0) {
                IAction action = queue.dequeue();
                if (action != null) {
                    action.execute();
                }
                try {
                    if(interval > 0){
                        Thread.sleep(interval);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        uiThread = new Thread(() -> {
            while (uiQueue.count() > 0) {
                IAction action = uiQueue.dequeue();
                if (action != null) {
                    handler.post(action::execute);
                }
                try {
                    if(interval > 0){
                        Thread.sleep(interval);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void enqueue(IAction action){
        if(action != null){
            queue.enqueue(action);
            start();
        }
    }

    private void start() {
        if(!thread.isAlive()){
            thread.start();
        }
    }

    public void uiEnqueue(IAction action){
        if(action != null){
            uiQueue.enqueue(action);
            uiStart();
        }
    }

    private void uiStart() {
        if(!uiThread.isAlive()){
            uiThread.start();
        }
    }

}