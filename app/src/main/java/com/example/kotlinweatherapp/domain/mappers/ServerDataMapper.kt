package com.example.kotlinweatherapp.domain.mappers

import com.example.kotlinweatherapp.data.Forecast
import com.example.kotlinweatherapp.data.ForecastResult
import com.example.kotlinweatherapp.domain.model.ForecastList
import java.util.*
import java.util.concurrent.TimeUnit
import com.example.kotlinweatherapp.domain.model.Forecast as ModelForecast

class ServerDataMapper {

    fun convertToDomain(zipCode: Long, forecast: ForecastResult) = with(forecast) {
        ForecastList(zipCode, city.name, city.country, convertForecastListToDomain(list))
    }

    private fun convertForecastListToDomain(list: List<Forecast>): List<ModelForecast> =
        list.mapIndexed { i, forecast ->
            val dt = Calendar.getInstance().timeInMillis + TimeUnit.DAYS.toMillis(i.toLong())
            convertForecastItemToDomain(forecast.copy(dt = dt))
        }

    private fun convertForecastItemToDomain(forecast: Forecast): ModelForecast = ModelForecast(
        forecast.dt,
        forecast.weather[0].description,
        forecast.temp.max.toInt(),
        forecast.temp.min.toInt(),
        generateIconUrl(forecast.weather[0].icon)
    )

    private fun generateIconUrl(iconCode: String) =
        "http://openweathermap.org/img/w/$iconCode.png"
}