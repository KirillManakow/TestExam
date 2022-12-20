package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Query;

public interface RetrofitDelete {
    @DELETE("Autoes/")
    Call <DataModal> deleteData(@Query("ID")int ID);
}
