package com.example.kotlinweatherapp.domain.commands

import com.example.kotlinweatherapp.domain.datasource.ForecastProvider
import com.example.kotlinweatherapp.domain.model.ForecastList

class RequestForecastCommand(
    private val zipCode: Long,
    private val forecastProvider: ForecastProvider = ForecastProvider()
) : Command<ForecastList> {

    companion object {
        const val DAYS = 7
    }

    override fun execute(): ForecastList = forecastProvider.requestByZipCode(zipCode, DAYS)
}