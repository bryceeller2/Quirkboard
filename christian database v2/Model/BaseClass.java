package com.example.ckraft.myapplication.Model;

import com.google.gson.Gson;

import java.util.UUID;

/**
 * Created by theckraft on 9/1/16.
 */
public abstract class BaseClass {

    public abstract String getURLName();


    private int id;
    private String dateCreated;
    private String dateUpdated;

    public BaseClass() {
    }

    public BaseClass(BaseClass baseClass) {
        this.id = baseClass.getID();
        this.dateCreated = baseClass.getDateCreated();
        this.dateUpdated = baseClass.getDateUpdated();
    }

    public int getID() {
        return id;
    }

    public void setID(int ID) {
        this.id = ID;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String toString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }


}

