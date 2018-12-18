package com.rivia.software.weathermvvm.ui.weather.current

import androidx.lifecycle.ViewModel;
import com.rivia.software.weathermvvm.data.repository.ForecastRepository
import com.rivia.software.weathermvvm.internal.UnitSystem
import com.rivia.software.weathermvvm.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {

    private val unitSystem = UnitSystem.METRIC

    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC


    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }


}
