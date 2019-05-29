package com.example.ademsimapp;

public class User {

    private String id, username, email;
    private int img;

    public User(String id, String username, String email, int img) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public void save(){
        UserData.save(this);
    }
}
