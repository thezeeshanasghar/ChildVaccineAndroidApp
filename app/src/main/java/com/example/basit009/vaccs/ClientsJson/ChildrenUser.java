package com.example.basit009.vaccs.ClientsJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChildrenUser {

    @SerializedName("IsSuccess")
    @Expose
    public String IsSuccess;

    @SerializedName("Message")
    @Expose
    public String Message;

    @SerializedName("ResponseData")
    @Expose
    public ArrayList<Childrens> childrensList;

    public class Childrens {

        @SerializedName("Name")
        @Expose
        public String Name;

        @SerializedName("DOB")
        @Expose
        public String DOB;

        @SerializedName("Gender")
        @Expose
        public String Gender;

        @SerializedName("City")
        @Expose
        public String City;


    }

}
