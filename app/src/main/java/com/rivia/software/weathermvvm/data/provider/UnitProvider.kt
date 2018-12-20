package com.rivia.software.weathermvvm.data.provider

import com.rivia.software.weathermvvm.internal.UnitSystem

interface UnitProvider {
    fun getUnitSystem(): UnitSystem
}