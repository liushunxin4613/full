package com.leo.core.update;

public interface IRunAction {
	boolean isRun(long timeMillis);
	void onListen(int count, long timeMillis);
	void run();
}