package com.example.treehouseguidebook;

public class Singleton {
    public static  User existing_user= new User();
    private Singleton(){

    }

    public static User getExisting_user() {
        return existing_user;
    }

    public static void setExisting_user(User existing_user) {
        Singleton.existing_user = existing_user;
    }
}
