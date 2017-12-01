package com.cesarsmith.thefloowtest.background.pojos;

/**
 * Created by Softandnet on 30/11/2017.
 */

public class Place {

    private String id,name;

    public Place(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }
}
