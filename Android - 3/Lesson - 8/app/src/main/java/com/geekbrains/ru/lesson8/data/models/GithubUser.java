package com.geekbrains.ru.lesson8.data.models;

import com.google.gson.annotations.SerializedName;


public class GithubUser {
    private String login = "";

    @SerializedName("avatar_url")
    private String avatar;
    public String getAvatar() {
        return avatar;
    }
    public String getLogin() {
        return login;
    }
}
