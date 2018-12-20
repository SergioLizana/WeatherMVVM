package com.rivia.software.weathermvvm.internal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rivia.software.weathermvvm.data.provider.UnitProvider
import com.rivia.software.weathermvvm.data.repository.ForecastRepository
import com.rivia.software.weathermvvm.ui.weather.current.CurrentWeatherViewModel


class CurrentWeatherViewModelFactory(
    private val forecastRepository: ForecastRepository,
    private val unitProvider: UnitProvider
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(forecastRepository,unitProvider) as T
    }

}