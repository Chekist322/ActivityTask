package com.example.batrakov.activitytask;

import java.io.Serializable;

/**
 * Created by batrakov on 28.09.17.
 */

public class Cat implements Serializable {
    private String mName;
    private String mBreed;
    private String mAge;

    Cat(){
        mName = null;
        mBreed = null;
        mAge = null;
    }

    Cat(Cat aCat){
        this.mName = aCat.getName();
        this.mBreed = aCat.getBreed();
        this.mAge = aCat.getAge();
    }

    Cat(String aName, String aBreed, String aAge){
        mName = aName;
        mBreed = aBreed;
        mAge = aAge;
    }


    public String getName() {
        return mName;
    }

    public void setName(String aName) {
        this.mName = aName;
    }

    public String getBreed() {
        return mBreed;
    }

    public void setBreed(String aBreed) {
        this.mBreed = aBreed;
    }

    public String getAge() {
        return mAge;
    }

    public void setAge(String aAge) {
        this.mAge = aAge;
    }
}
