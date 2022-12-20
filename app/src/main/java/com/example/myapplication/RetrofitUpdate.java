package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RetrofitUpdate {

    @PUT("Autoes/")
    Call<DataModal> updateData(@Query("ID")int ID, @Body DataModal dataModal);
}
