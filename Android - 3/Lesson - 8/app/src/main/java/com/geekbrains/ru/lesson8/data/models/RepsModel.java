package com.geekbrains.ru.lesson8.data.models;


import com.google.gson.annotations.SerializedName;


public class RepsModel {
    public String name = "name";
    public GithubUser owner = new GithubUser();

    @SerializedName("full_name")
    public String fullName;
}
