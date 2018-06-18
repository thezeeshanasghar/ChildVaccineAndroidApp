package com.example.basit009.vaccs.ClientsJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VaccinesAddData {

    @SerializedName("ID")
    @Expose
    public int ID;

    @SerializedName("Name")
    @Expose
    public String Name;

    @SerializedName("MinAge")
    @Expose
    public int MinAge;

    @SerializedName("MaxAge")
    @Expose
    public int MaxAge;


    public VaccinesAddData(String name, int minage, int maxage) {
        this.Name = name;
        this.MinAge = minage;
        this.MaxAge = maxage;
    }
}
