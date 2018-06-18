package com.example.basit009.vaccs.ClientsJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DoctorUser {

    @SerializedName("IsSuccess")
    @Expose
    public String IsSuccess;

    @SerializedName("Message")
    @Expose
    public String Message;

    @SerializedName("ResponseData")
    @Expose
    public ArrayList<Doctors> doctorsList;

    public class Doctors {


        @SerializedName("ID")
        @Expose
        public int ID;


        @SerializedName("FirstName")
        @Expose
        public String FirstName;

        @SerializedName("LastName")
        @Expose
        public String LastName;

        @SerializedName("MobileNumber")
        @Expose
        public String MobileNumber;

    }

}
