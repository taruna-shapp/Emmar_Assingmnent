package com.emmar.emmar_assingment.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserList {
    @SerializedName("results")
    @Expose
    public ArrayList<ResultDetails> users;
}