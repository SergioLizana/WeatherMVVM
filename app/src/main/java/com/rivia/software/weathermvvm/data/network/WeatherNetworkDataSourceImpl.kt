package com.rivia.software.weathermvvm.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rivia.software.weathermvvm.data.network.response.CurrentWeatherResponse
import com.rivia.software.weathermvvm.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(private val apixuWeatherApiService: ApixuWeatherApiService) : WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()

    override suspend fun fetchCurrentWeather(location: String, languageCode: String) {
       try{
           val fetchedCurrentWeather = apixuWeatherApiService
               .getCurrentWeather(location,languageCode)
               .await()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
       }catch (e: NoConnectivityException){
           Log.e("Connectivity","No Internet Connection")
       }
    }

    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather
}