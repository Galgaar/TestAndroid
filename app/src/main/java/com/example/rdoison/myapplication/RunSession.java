package com.example.rdoison.myapplication;

import java.util.List;

/**
 * Created by rdoison on 30/03/2017.
 */

public class RunSession {
    String name;
    List<RunElement> session;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RunElement> getSession() {
        return session;
    }

    public void setSession(List<RunElement> session) {
        this.session = session;
    }
}
