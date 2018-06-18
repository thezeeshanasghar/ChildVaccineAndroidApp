package com.example.basit009.vaccs.ClientsJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BrandVaccines {

    @SerializedName("IsSuccess")
    @Expose
    public String IsSuccess;

    @SerializedName("Message")
    @Expose
    public String Message;

    @SerializedName("ResponseData")
    @Expose
    public ArrayList<BrandVaccines.Brands> brandsList;

    public class Brands {

        @SerializedName("ID")
        @Expose
        public int ID;

        @SerializedName("Name")
        @Expose
        public String Name;

    }
}
