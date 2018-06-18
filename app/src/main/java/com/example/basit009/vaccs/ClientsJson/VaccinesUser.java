package com.example.basit009.vaccs.ClientsJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VaccinesUser {

    @SerializedName("IsSuccess")
    @Expose
    public String IsSuccess;

    @SerializedName("Message")
    @Expose
    public String Message;

    @SerializedName("ResponseData")
    @Expose
    public ArrayList<VaccinesUser.Vaccines> vaccinesList;

    public class Vaccines {

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

    }

}
