package com.iman.recyclerviewsample;

import androidx.annotation.NonNull;

public class MyFriends {
    private String name;
    private String city;

    public MyFriends(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @NonNull
    @Override
    public String toString() {
        return name + " (" + city + ")";
    }
}
