package com.example.ciromine.sqliteexample;

import java.util.ArrayList;

/**
 * Created by ciromine on 31-08-15.
 */
public class User {

    int id;
    String name;

    public User(){}

    public User(int id, String name){
        this.id = id;
        this.name = name;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
