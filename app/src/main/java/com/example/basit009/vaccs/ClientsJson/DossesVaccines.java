package com.example.basit009.vaccs.ClientsJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DossesVaccines {

    @SerializedName("IsSuccess")
    @Expose
    public String IsSuccess;

    @SerializedName("Message")
    @Expose
    public String Message;

    @SerializedName("ResponseData")
    @Expose
    public ArrayList<DossesVaccines.Dosses> dossesList;

    public class Dosses {

        @SerializedName("ID")
        @Expose
        public int ID;

        @SerializedName("Name")
        @Expose
        public String Name;

        @SerializedName("MinAge")
        @Expose
        public int Minage;

        @SerializedName("MaxAge")
        @Expose
        public int MaxAge;

        @SerializedName("DoseOrder")
        @Expose
        public int DoseOrder;

        @SerializedName("VaccineId")
        @Expose
        public int VaccineId;

        @SerializedName("MinGap")
        @Expose
        public int MinGap;





    }
}
