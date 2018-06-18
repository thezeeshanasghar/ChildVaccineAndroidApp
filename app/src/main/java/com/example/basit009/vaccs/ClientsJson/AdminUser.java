package com.example.basit009.vaccs.ClientsJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminUser {

    @SerializedName("IsSuccess")
    @Expose
    public boolean IsSuccess;

    @SerializedName("Message")
    @Expose
    public String Message;



}
