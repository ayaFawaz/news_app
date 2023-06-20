package com.example.dailynews.api

import com.example.dailynews.data.models.NewsList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    fun getTopHeadline(@Query("pageSize") pageSize: Int) : Call<NewsList>
}