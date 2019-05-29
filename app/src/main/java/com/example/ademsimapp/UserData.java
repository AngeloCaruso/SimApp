package com.example.ademsimapp;

import java.util.ArrayList;

public class UserData {
    private static ArrayList<User> users = new ArrayList<>();
    public static void save(User c){
        users.add(c);
    }
}
