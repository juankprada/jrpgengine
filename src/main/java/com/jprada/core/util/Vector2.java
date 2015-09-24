package com.jprada.core.util;

/**
 * Created by Juan Camilo Prada on 17/10/2014.
 */
public class Vector2 {

    public float x;
    public float y;
    
    

    /** Constructs a new vector at (0,0) */
    public Vector2() {

    }

    /** Constructs a vector with the given components
     * @param x The x-component
     * @param y The y-component */
    public Vector2(float x, float y) {
        this.x = 0;
        this.y = 0;
    }

    /** Constructs a vector from the given vector
     * @param v The vector */
    public Vector2(Vector2 v) {
        //set(v);
    }

    public Vector2 cpy () {
        return new Vector2(this);
    }

    public static float len (float x, float y) {
        return (float)Math.sqrt(x * x + y * y);
    }


    public float len () {
        return (float)Math.sqrt(x * x + y * y);
    }

    public Vector2 set (Vector2 v) {
        x = v.x;
        y = v.y;
        return this;
    }

    /** Sets the components of this vector
     * @param x The x-component
     * @param y The y-component
     * @return This vector for chaining */
    public Vector2 set (float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }


    public Vector2 sub (Vector2 v2) {
        x -= v2.x;
        y -= v2.y;
        return this;
    }


    public Vector2 nor () {
        float len = len();
        if (len != 0) {
            x /= len;
            y /= len;
        }
        return this;
    }

    public Vector2 add(Vector2 v2) {
        this.x += v2.x;
        this.y += v2.y;

        return this;
    }

    public float dot (Vector2 v2) {
        return x * v2.x + y * v2.y;
    }

    public Vector2 scl (float scalar) {
        x *= scalar;
        y *= scalar;
        return this;
    }

    public Vector2 scl (float x, float y) {
        this.x *= x;
        this.y *= y;
        return this;
    }


}
