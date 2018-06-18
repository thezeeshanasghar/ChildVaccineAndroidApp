package com.example.basit009.vaccs.ClientsJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoseAddData {

    @SerializedName("ID")
    @Expose
    public int ID;

    @SerializedName("Name")
    @Expose
    public String Name;

    @SerializedName("VaccineID")
    @Expose
    public int VaccineID;

    @SerializedName("MinAge")
    @Expose
    public int Minage;

    @SerializedName("MaxAge")
    @Expose
    public int MaxAge;

    @SerializedName("DoseOrder")
    @Expose
    public int DoseOrder;

    @SerializedName("MinGap")
    @Expose
    public int MinGap;

    public DoseAddData(String name,int vaccineid,int minage,int maxAge,int doseOrder,int minGap) {
        this.Name=name;
        this.VaccineID=vaccineid;
        this.Minage=minage;
        this.MaxAge=maxAge;
        this.DoseOrder=doseOrder;
        this.MinGap=minGap;

    }
}
