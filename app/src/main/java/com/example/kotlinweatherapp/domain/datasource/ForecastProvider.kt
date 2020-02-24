package com.example.kotlinweatherapp.domain.datasource

import com.example.kotlinweatherapp.data.db.ForecastDb
import com.example.kotlinweatherapp.data.server.ForecastServer
import com.example.kotlinweatherapp.domain.model.Forecast
import com.example.kotlinweatherapp.domain.model.ForecastList
import com.example.kotlinweatherapp.extensions.firstResult

class ForecastProvider(private val sources: List<ForecastDataSource> = SOURCES) {

    companion object {
        const val DAY_IN_MILLIS = 1000 * 60 * 60 * 24
        val SOURCES by lazy { listOf(ForecastDb(), ForecastServer()) }
    }

    fun requestByZipCode(zipCode: Long, days: Int): ForecastList = requestToSources {
        val res = it.requestForecastByZipCode(zipCode, todayTimeSpan())
        if (res != null && res.size >= days) res else null
    }

    fun requestForecast(id: Long): Forecast = requestToSources { it.requestDayForecast(id) }

    private fun todayTimeSpan() = System.currentTimeMillis() / DAY_IN_MILLIS * DAY_IN_MILLIS

    private fun <T : Any> requestToSources(f: (ForecastDataSource) -> T?): T =
        sources.firstResult { f(it) }

}