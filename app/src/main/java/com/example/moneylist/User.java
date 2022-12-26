package com.example.moneylist;

import java.util.Date;
import java.util.UUID;

public class User {
    private UUID mid;
    private String username;
    private String password;

    public User(){
        this(UUID.randomUUID());
    }
    public User(UUID id){
        mid = id;
    }



    public UUID getMid() {
        return mid;
    }

    public void setMid(UUID mid) {
        this.mid = mid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
