package com.leo.core.other;

public class Number {

    private double number;

    public Number(double... args) {
        for (double d : args) {
            add(d);
        }
    }

    public Number add(int number) {
        this.number += number;
        return this;
    }

    public Number add(float number) {
        this.number += number;
        return this;
    }

    public Number add(double number) {
        this.number += number;
        return this;
    }

    public int getint() {
        return (int) get();
    }

    public float getfloat() {
        return (float) get();
    }

    public double getdouble() {
        return get();
    }

    public double get(){
        return number;
    }

}