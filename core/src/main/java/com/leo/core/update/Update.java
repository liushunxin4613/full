package com.leo.core.update;

import android.os.Handler;
import android.os.Looper;

import com.leo.core.util.LogUtil;

public class Update {

	private Thread thread;
	private int interval = 1000;
	private IRunAction action;
	private Handler handler;

	public Update(IRunAction action) {
		if (action == null)
			throw new NullPointerException("action不能为空");
		this.action = action;
		this.handler = new Handler(Looper.getMainLooper());
	}

	public Update setInterval(int interval) {
		this.interval = interval;
		return this;
	}

	public void update() {
		if (thread == null || !thread.isAlive()) {
			LogUtil.ee(this, "------- update --------");
			thread = newThread();
			if (thread != null) {
				thread.start();
			}
		}
	}

	public long getAddCurrentTimeMillis(int time){
		return currentTimeMillis() + time;
	}

	private long currentTimeMillis() {
		return System.currentTimeMillis();
	}
	
	private boolean isRun() {
		return action.isRun(currentTimeMillis());
	}

	private void run() {
		action.run();
	}

	private Thread newThread() {
		return new Thread(() -> {
            int count = 0;
            while (isRun()) {
                action.onListen(count++, currentTimeMillis());
                try {
                    if (interval > 0) {
                        Thread.sleep(interval);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
			handler.post(Update.this::run);
        });
	}

}