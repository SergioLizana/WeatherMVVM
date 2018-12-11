package com.rivia.software.weathermvvm.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rivia.software.weathermvvm.data.db.entity.CURRENT_WEATHER_ID
import com.rivia.software.weathermvvm.data.db.entity.CurrentWeatherEntry
import com.rivia.software.weathermvvm.data.db.unlocalized.ImperialCurrentWeatherEntry
import com.rivia.software.weathermvvm.data.db.unlocalized.MetricCurrentWeatherEntry
import com.rivia.software.weathermvvm.data.db.unlocalized.UnitSpecificCurrentWeatherEntry

@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)

    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherMetric(): LiveData<MetricCurrentWeatherEntry>

    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherImperial(): LiveData<ImperialCurrentWeatherEntry>

}