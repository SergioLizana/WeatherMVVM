package com.rivia.software.weathermvvm.data.network.response

import com.google.gson.annotations.SerializedName
import com.rivia.software.weathermvvm.data.db.entity.CurrentWeatherEntry
import com.rivia.software.weathermvvm.data.db.entity.Location

data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: Location
)