package com.example.silverball;

import org.simpleframework.xml.Attribute;

public class Ball {
    @Attribute
    private int startx;

    @Attribute
    private int starty;

    public int getStartx() {
        return startx;
    }

    public int getStarty() {
        return starty;
    }
}
