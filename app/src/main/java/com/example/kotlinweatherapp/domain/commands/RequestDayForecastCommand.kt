package com.example.kotlinweatherapp.domain.commands

import com.example.kotlinweatherapp.domain.datasource.ForecastProvider
import com.example.kotlinweatherapp.domain.model.Forecast

class RequestDayForecastCommand(
    val id: Long,
    private val forecastProvider: ForecastProvider = ForecastProvider()) :
    Command<Forecast> {

    override fun execute() = forecastProvider.requestForecast(id)
}