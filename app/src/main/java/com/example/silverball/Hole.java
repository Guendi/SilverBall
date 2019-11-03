package com.example.silverball;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class Hole {
    @Attribute
    private int x;
    @Attribute
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
