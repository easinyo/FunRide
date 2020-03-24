package com.dubinostech.rideshareapp.repository.Data;

public class LoggedUser {
    public static String token = "";
    public static String name = "Emmanuel Asinyo";
    public static String email = "easin081@uottawa.ca";
    public static String phone_number = "+1(613)805-7454";

    public LoggedUser(String token, String name, String email) {
        this.token = token;
        this.name = name;
        this.email = email;
    }

    public static String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
