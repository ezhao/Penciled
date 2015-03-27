package com.herokuapp.ezhao.penciled.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("PencilPerson")
public class PencilPerson extends ParseObject {
    public PencilPerson() {
        super();
    }

    public PencilPerson(String name, String email) {
        super();
        setName(name);
        setEmail(email);
    }

    public String getName() {
        return getString("name");
    }

    public String getEmail() {
        return getString("email");
    }

    public void setName(String name) {
        put("name", name);
    }

    public void setEmail(String email) {
        put("email", email);
    }
}
