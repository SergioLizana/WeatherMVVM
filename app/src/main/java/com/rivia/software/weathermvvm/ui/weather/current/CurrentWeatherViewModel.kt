package com.rivia.software.weathermvvm.ui.weather.current

import androidx.lifecycle.ViewModel;
import com.rivia.software.weathermvvm.data.provider.UnitProvider
import com.rivia.software.weathermvvm.data.repository.ForecastRepository
import com.rivia.software.weathermvvm.internal.UnitSystem
import com.rivia.software.weathermvvm.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC


    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }

    val weatherLocation by lazyDeferred {
        forecastRepository.getWeatherLocation()
    }


}
