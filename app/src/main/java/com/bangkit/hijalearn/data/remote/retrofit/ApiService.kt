package com.bangkit.hijalearn.data.remote.retrofit


import com.bangkit.hijalearn.model.DoaResponseItem
import com.bangkit.hijalearn.data.remote.response.PredictionResponse
import com.bangkit.hijalearn.data.remote.response.ProgressResponse
import com.bangkit.hijalearn.data.remote.response.SingleModuleProgressResponse
import com.bangkit.hijalearn.model.ListSurahResponseItem
import com.bangkit.hijalearn.model.SurahResponseItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Multipart
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("username") username: String,
    ): String

    @GET("progress")
    suspend fun getProgress(): ProgressResponse

    @GET("progress/{modulId}")
    suspend fun getSingleProgress(
        @Path("modulId") modulId: Int
    ): SingleModuleProgressResponse

    @Multipart
    @POST("prediction")
    suspend fun postPrediction(
        @Part audioFile: MultipartBody.Part,
        @Part("caraEja") caraEja: RequestBody,
        @Part("moduleId") moduleId: RequestBody,
        @Part("done") done: RequestBody
    ): PredictionResponse

    @GET("99c279bb173a6e28359c/data")
    suspend fun getListSurah(): List<ListSurahResponseItem>

    @GET("99c279bb173a6e28359c/surat/{id}")
    suspend fun getSurahById(@Path("id") id: String): List<SurahResponseItem>

    @GET("api")
    suspend fun getDoa(): List<DoaResponseItem>




}