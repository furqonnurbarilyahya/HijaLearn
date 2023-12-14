package com.bangkit.hijalearn.data.remote.retrofit

import com.bangkit.hijalearn.model.ListSurahResponseItem
import com.bangkit.hijalearn.model.SurahResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.Call

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("username") username: String,
    ): String

    @GET("99c279bb173a6e28359c/data")
    suspend fun getListSurah(): List<ListSurahResponseItem>




}