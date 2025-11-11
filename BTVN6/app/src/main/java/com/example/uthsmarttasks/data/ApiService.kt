package com.example.uthsmarttasks.data

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

// Interface định nghĩa các API
interface ApiService {

    @GET("tasks")
    suspend fun getTasks(): List<TaskSummary>

    @GET("task/{id}")
    suspend fun getTaskDetail(
        @Path("id") taskId: String
    ): TaskDetail

    @DELETE("task/{id}")
    suspend fun deleteTask(
        @Path("id") taskId: String
    ): Response<Unit> // Dùng Response<Unit> để biết có xóa thành công không
}

// Đối tượng Singleton để khởi tạo Retrofit (chỉ 1 lần)
object RetrofitInstance {

    private const val BASE_URL = "https://amock.io/api/researchUTH/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}