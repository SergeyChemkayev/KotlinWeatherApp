package com.example.kotlinweatherapp.domain.datasource

import com.example.kotlinweatherapp.domain.model.Forecast
import com.example.kotlinweatherapp.domain.model.ForecastList

interface ForecastDataSource {

    fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList?

    fun requestDayForecast(id: Long): Forecast?
}