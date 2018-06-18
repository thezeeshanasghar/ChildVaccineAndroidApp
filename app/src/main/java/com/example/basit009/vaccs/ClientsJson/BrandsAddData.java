package com.example.basit009.vaccs.ClientsJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BrandsAddData {

    @SerializedName("ID")
    @Expose
    public int ID;

    @SerializedName("Name")
    @Expose
    public String Name;

    @SerializedName("VaccineID")
    @Expose
    public int VaccineID;

    public BrandsAddData(String name,int vaccineID) {
        this.Name=name;
        this.VaccineID=vaccineID;
    }
}
