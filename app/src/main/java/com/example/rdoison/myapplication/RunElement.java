package com.example.rdoison.myapplication;

/**
 * Created by rdoison on 30/03/2017.
 */

public class RunElement {
    public static enum ElementType {
        RUN,
        WALK,
        WARMUP,
        COOLDOWN
    };

    ElementType type;
    Integer length;

    public ElementType getType() {
        return type;
    }

    public void setType(ElementType type) {
        this.type = type;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
