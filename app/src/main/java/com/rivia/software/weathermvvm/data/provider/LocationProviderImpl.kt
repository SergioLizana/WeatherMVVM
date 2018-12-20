package com.rivia.software.weathermvvm.data.provider

import com.rivia.software.weathermvvm.data.db.entity.WeatherLocation

class LocationProviderImpl : LocationProvider {
    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        return true
    }

    override suspend fun getPreferredLocationString(): String {
       return "Los Angeles"
    }
}