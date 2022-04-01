package com.broll.godfight.utils;


public class Vector4 {

    public float x,y,z,w;

    public Vector4(float x, float y, float z, float w){
        this.x =x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4(Vector4 v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
    }
}
