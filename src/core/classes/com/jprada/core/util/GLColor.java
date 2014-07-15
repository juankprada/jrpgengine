package com.jprada.core.util;

/**
 * Created by Juan Camilo Prada on 25/06/2014.
 */
public class GLColor {

    private float r;
    private float g;
    private float b;
    private float a;

    public GLColor(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public GLColor(float r, float g, float b) {
       this(r, g, b, 0);
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public float getG() {
        return g;
    }

    public void setG(float g) {
        this.g = g;
    }

    public float getB() {
        return b;
    }

    public void setB(float b) {
        this.b = b;
    }

    public float getA() {
        return a;
    }

    public void setA(float a) {
        this.a = a;
    }
}
