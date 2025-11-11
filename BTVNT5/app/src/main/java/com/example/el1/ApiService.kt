package com.example.el1

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
interface ApiService {

    @GET("product")
    suspend fun getProductDetail(): Product
}

object RetrofitInstance {

    private const val BASE_URL = "https://mock.apidog.com/m1/890655-872447-default/v2/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}