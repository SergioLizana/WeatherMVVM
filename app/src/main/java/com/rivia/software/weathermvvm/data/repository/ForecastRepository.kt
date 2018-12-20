package com.rivia.software.weathermvvm.data.repository

import androidx.lifecycle.LiveData
import com.rivia.software.weathermvvm.data.db.entity.WeatherLocation
import com.rivia.software.weathermvvm.data.db.unlocalized.UnitSpecificCurrentWeatherEntry
import com.rivia.software.weathermvvm.data.db.unlocalized.WeatherLocationDao

interface ForecastRepository {

    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>
    suspend fun getWeatherLocation(): LiveData<WeatherLocation>
}