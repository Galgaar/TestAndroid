package com.example.rdoison.myapplication;

import java.util.HashMap;

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

    public static HashMap<ElementType, String> elementString = new HashMap<ElementType, String>(){{
        put(ElementType.RUN, "Run");
        put(ElementType.WALK, "Walk");
        put(ElementType.WARMUP, "Warm Up");
        put(ElementType.COOLDOWN, "Cool down");
    }};

    ElementType type;
    Integer length;


    public RunElement() {
    }

    public RunElement(ElementType type, Integer length) {
        this.type = type;
        this.length = length;
    }

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
