package com.example.treehouseguidebook;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String email;
    public String name;
    public String username;
    public String uni;
    public String pwd;
    public String role;
    //public List<Section> bookmarks;

    /*public User(String username,String name,String email, String uni, String pwd, String role,List<Section> marks) {
        this.username = username;
        this.name = name;
        this.uni = uni;
        this.pwd = pwd;
        this.role = role;
        this.email=email;
    }*/
    public User(String username,String name,String email, String uni, String pwd, String role) {
        this.username = username;
        this.name = name;
        this.uni = uni;
        this.pwd = pwd;
        this.role = role;
        this.email=email;
    }
    public User(String school,String role){
        this.uni=school;
        this.role=role;
    }

    public User(){ }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getUni() {
        return uni;
    }

    public String getPwd() {
        return pwd;
    }

    public String getRole() {
        return role;
    }

    /*public List<Section> getBookmarks() { return bookmarks; }

    public void setBookmarks(List<Section> bookmarks) {
        this.bookmarks = bookmarks;
    }*/

    public String getName() {return name;}


}
