package com.rivia.software.weathermvvm.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rivia.software.weathermvvm.data.db.entity.CurrentWeatherEntry
import com.rivia.software.weathermvvm.data.db.entity.WeatherLocation
import com.rivia.software.weathermvvm.data.db.unlocalized.WeatherLocationDao


@Database(
    entities = [CurrentWeatherEntry::class, WeatherLocation::class],
    version = 2,
    exportSchema = false
)
abstract class ForecastDatabase : RoomDatabase(){
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun weatherLocationDao(): WeatherLocationDao

    companion object {
        @Volatile private var instance: ForecastDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext
                    ,ForecastDatabase::class.java,"forecast.db")
                    .build()
    }
}