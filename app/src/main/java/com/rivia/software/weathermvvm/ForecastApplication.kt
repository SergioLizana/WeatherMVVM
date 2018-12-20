package com.rivia.software.weathermvvm

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.rivia.software.weathermvvm.data.db.ForecastDatabase
import com.rivia.software.weathermvvm.data.network.*
import com.rivia.software.weathermvvm.data.provider.LocationProvider
import com.rivia.software.weathermvvm.data.provider.LocationProviderImpl
import com.rivia.software.weathermvvm.data.provider.UnitProvider
import com.rivia.software.weathermvvm.data.provider.UnitProviderImpl
import com.rivia.software.weathermvvm.data.repository.ForecastRepository
import com.rivia.software.weathermvvm.data.repository.ForecastRepositoryImpl
import com.rivia.software.weathermvvm.internal.CurrentWeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication: Application(),KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao()}
        bind() from singleton { instance<ForecastDatabase>().weatherLocationDao()}
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApixuWeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<LocationProvider>()with singleton { LocationProviderImpl() }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(),instance(),instance(),instance()) }
        bind<UnitProvider>() with singleton{ UnitProviderImpl(instance())}
        bind() from provider { CurrentWeatherViewModelFactory(instance(),instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}