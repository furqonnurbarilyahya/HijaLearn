package com.bangkit.hijalearn.data.remote.retrofit

import android.content.Context
import com.bangkit.hijalearn.data.pref.UserPreference
import com.bangkit.hijalearn.data.pref.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    fun getApiService(): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://hijalearn-be-e6mqsjvzxq-et.a.run.app/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }

    fun getAuthApiService(context: Context): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AuthInterceptor(context))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://hijalearn-be-e6mqsjvzxq-et.a.run.app/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}

class AuthInterceptor(private val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val userPreference = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { userPreference.getSession().first() }
        val bearerToken = "Bearer ${user.token}"

        val req = chain.request()
        val requestHeaders = req.newBuilder()
            .addHeader("Authorization", bearerToken)
            .build()

        return chain.proceed(requestHeaders)
    }
}