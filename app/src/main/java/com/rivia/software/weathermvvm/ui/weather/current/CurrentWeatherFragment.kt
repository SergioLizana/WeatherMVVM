package com.rivia.software.weathermvvm.ui.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.rivia.software.weathermvvm.R
import com.rivia.software.weathermvvm.data.network.ApixuWeatherApiService
import com.rivia.software.weathermvvm.data.network.ConnectivityInterceptorImpl
import com.rivia.software.weathermvvm.data.network.WeatherNetworkDataSourceImpl
import com.rivia.software.weathermvvm.internal.CurrentWeatherViewModelFactory
import com.rivia.software.weathermvvm.internal.glide.GlideApp
import com.rivia.software.weathermvvm.ui.base.ScopeFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopeFragment(), KodeinAware {


    override val kodein by closestKodein()
    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this,viewModelFactory)
            .get(CurrentWeatherViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch{
        val currentWeather = viewModel.weather.await()
        val currentWeatherLocation = viewModel.weatherLocation.await()

        currentWeatherLocation.observe(this@CurrentWeatherFragment, Observer { location ->
            if(location == null) return@Observer
            updateLocation(location = location.name)
        })

        currentWeather.observe(this@CurrentWeatherFragment, Observer {
            if(it == null) return@Observer

            group_loading.visibility = View.GONE
            updateDateToday()
            updateTemperature(it.temperature,it.feelsLikeTemperature)
            updateCondition(it.conditionText)
            updatePrecipitation(it.precipitationVolume)
            updateWind(it.windDirection,it.windSpeed)
            updateVisibility(it.visibilityDistance)

            GlideApp.with(this@CurrentWeatherFragment)
                .load("http:${it.conditionIconUrl}")
                .into(imageView_condition_icon)

        })
    }

    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String{
        return  if(viewModel.isMetric) metric else imperial
    }

    private fun updateLocation(location: String){
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToday(){
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    private fun updateTemperature(temperature: Double, feelsLike: Double){
        val unitAbbrevation = chooseLocalizedUnitAbbreviation("ºC","ºF")
        textView_temperature.text = "$temperature$unitAbbrevation"
        textView_feels_like_temperature.text = "Feels like $feelsLike$unitAbbrevation"
    }
    private fun updateCondition(condition: String){
        textView_condition.text = condition
    }

    private fun updatePrecipitation(precipitationVolume: Double){
        val unitAbbrevation = chooseLocalizedUnitAbbreviation("mm","in")
        textView_precipitation.text = "Precipitation: $precipitationVolume $unitAbbrevation"

    }

    private fun updateWind(windDirection: String,windSpeed: Double){
        val unitAbbrevation = chooseLocalizedUnitAbbreviation("kph","mph")
        textView_wind.text = "Wind: $windDirection ,$windSpeed,$unitAbbrevation"
    }

    private fun updateVisibility(visibilityDistance: Double){
        val unitAbbrevation = chooseLocalizedUnitAbbreviation("km","mi.")
                textView_wind.text = "Visibility: $visibilityDistance ,$unitAbbrevation"
    }



}
