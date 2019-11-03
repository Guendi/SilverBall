package com.example.silverball;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class Obstacle {
    @Attribute
    private String type;
    @Attribute
    private int x;
    @Attribute
    private int y;
    @Attribute
    private int w;
    @Attribute
    private int h;

    public String getType() {
        return type;
    }

    public int getH() {
        return h;
    }

    public int getW() {
        return w;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
