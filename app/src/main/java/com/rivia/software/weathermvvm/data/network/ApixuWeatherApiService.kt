package com.rivia.software.weathermvvm.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.rivia.software.weathermvvm.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val API_KEY = "e29cb626a89c4609845161952181012"
const val BASE_URL = "https://api.apixu.com/v1/"


interface ApixuWeatherApiService {

    @GET("current.json")
    fun getCurrentWeather(
        @Query("q") location: String,
        @Query("lang" ) languageCode: String = "en"
    ): Deferred<CurrentWeatherResponse>


    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ) : ApixuWeatherApiService {
           val requestInterceptor = Interceptor{chain ->

               val url =  chain.request().url()
                   .newBuilder()
                   .addQueryParameter("key", API_KEY)
                   .build()

               val request = chain.request()
                   .newBuilder()
                   .url(url)
                   .build()

               return@Interceptor chain.proceed(request)
           }
            val okHTTPClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHTTPClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApixuWeatherApiService::class.java)
        }
    }

}