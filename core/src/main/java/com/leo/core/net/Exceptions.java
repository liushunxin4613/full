package com.leo.core.net;

public class Exceptions<T extends Throwable> {

    private String message;
    private int code;
    private T e;

    public Exceptions(String message, int code, T e) {
        this.message = message;
        this.code = code;
        this.e = e;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getE() {
        return e;
    }

    public void setE(T e) {
        this.e = e;
    }

}
