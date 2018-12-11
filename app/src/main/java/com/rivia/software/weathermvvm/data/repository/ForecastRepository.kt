package com.rivia.software.weathermvvm.data.repository

import androidx.lifecycle.LiveData
import com.rivia.software.weathermvvm.data.db.unlocalized.UnitSpecificCurrentWeatherEntry

interface ForecastRepository {

    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>


}