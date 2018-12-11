package com.rivia.software.weathermvvm.data.network

import androidx.lifecycle.LiveData
import com.rivia.software.weathermvvm.data.network.response.CurrentWeatherResponse


interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String,
        languageCode: String
    )
}