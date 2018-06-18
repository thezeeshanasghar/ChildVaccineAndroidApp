package com.example.basit009.vaccs.ClientsJson;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface VaccsClient {

    @POST("user/login")
    @FormUrlEncoded
    Call<AdminUser> user(@Field("MobileNumber") String MobileNumber, @Field("Password") String Password, @Field("UserType") String UserType);

    @GET("doctor/approved")
    Call<DoctorUser> doctor();

    @GET("doctor/{ID}")
    Call<DoctorUserOne> doctor(@Path("ID") int id);

    @GET("child")
    Call<ChildrenUser> child();

    @GET("child/{ID}")
    Call<ChildrenUser> child(@Path("ID") int ID);

    @GET("vaccine")
    Call<VaccinesUser> vaccines();

    @POST("vaccine")
    Call<VaccinesAddData> addVaccines(@Body VaccinesAddData data);

    @GET("vaccine/{ID}/brands")
    Call<BrandVaccines> brands(@Path("ID") int ID);

    @GET("vaccine/{ID}/dosses")
    Call<DossesVaccines> dosses(@Path("ID") int ID);

    @POST("brand")
    Call<BrandsAddData> addBrand(@Body BrandsAddData data);

    @POST("dose")
    Call<DoseAddData> addDose(@Body DoseAddData data);

    @DELETE("vaccine/{ID}")
    Call<VaccinesUserDelete> delete(@Path("ID") int id);

    @DELETE("brand/{ID}")
    Call<BrandVaccinesDelete> deleteBrands(@Path("ID") int id);

    @DELETE("dose/{ID}")
    Call<DossesVaccinesDelete> deleteDoses(@Path("ID")int id);

}