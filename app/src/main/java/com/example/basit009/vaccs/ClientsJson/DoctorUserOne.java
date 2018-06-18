package com.example.basit009.vaccs.ClientsJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorUserOne {

    @SerializedName("IsSuccess")
    @Expose
    public String IsSuccess;

    @SerializedName("Message")
    @Expose
    public String Message;

    @SerializedName("ResponseData")
    @Expose
    public Doctor doctorsListOne;

    public class Doctor {

        @SerializedName("FirstName")
        @Expose
        public String firstname;

        @SerializedName("LastName")
        @Expose
        public String lastname;


        @SerializedName("ValidUpto")
        @Expose
        public String validupto;

        @SerializedName("Email")
        @Expose
        public String Email;

        @SerializedName("PMDC")
        @Expose
        public String PMDC;

        @SerializedName("MobileNumber")
        @Expose
        public String MobileNumber;


        @SerializedName("SMSLimit")
        @Expose
        public int smslimit ;

        @SerializedName("AllowInvoice")
        @Expose
        public boolean allowinvoice ;

        @SerializedName("AllowFollowUp")
        @Expose
        public boolean allowfollowup ;

        @SerializedName("AllowChart")
        @Expose
        public boolean allowchart ;

        @SerializedName("AllowInventory")
        @Expose
        public boolean allowinventory ;



    }
}
