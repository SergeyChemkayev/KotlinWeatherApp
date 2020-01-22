package com.example.kotlinweatherapp.data.db

import com.example.kotlinweatherapp.domain.datasource.ForecastDataSource
import com.example.kotlinweatherapp.domain.model.ForecastList
import com.example.kotlinweatherapp.extensions.clear
import com.example.kotlinweatherapp.extensions.parseList
import com.example.kotlinweatherapp.extensions.parseOpt
import com.example.kotlinweatherapp.extensions.toVarargArray
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class ForecastDb(
    private val forecastDbHelper: ForecastDbHelper = ForecastDbHelper.instance,
    private val dataMapper: DbDataMapper = DbDataMapper()
) : ForecastDataSource {

    override fun requestForecastByZipCode(zipCode: Long, date: Long) = forecastDbHelper.use {
        val dailyRequest = "${DayForecastTable.CITY_ID} = ? AND ${DayForecastTable.NAME} >= ?"
        val dailyForecast = select(DayForecastTable.NAME)
            .whereSimple(dailyRequest, zipCode.toString(), date.toString())
            .parseList {
                DayForecast(HashMap(it))
            }
        val city = select(CityForecastTable.NAME)
            .whereSimple("${CityForecastTable.ID} = ?", zipCode.toString())
            .parseOpt { CityForecast(HashMap(it), dailyForecast) }
        if (city != null) dataMapper.convertToDomain(city) else null
    }

    fun saveForecast(forecast: ForecastList) = forecastDbHelper.use {
        clear(CityForecastTable.NAME)
        clear(DayForecastTable.NAME)
        with(dataMapper.convertFromDomain(forecast)) {
            insert(CityForecastTable.NAME, *map.toVarargArray())
            dailyForecast.forEach {
                insert(DayForecastTable.NAME, *it.map.toVarargArray())
            }
        }
    }
}