package com.example.kotlinweatherapp.domain.mappers

import com.example.kotlinweatherapp.data.Forecast
import com.example.kotlinweatherapp.data.ForecastResult
import com.example.kotlinweatherapp.domain.model.ForecastList
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import com.example.kotlinweatherapp.domain.model.Forecast as ModelForecast

class ForecastDataMapper {

    fun convertFromDataModel(forecast: ForecastResult): ForecastList =
        ForecastList(
            forecast.city.name,
            forecast.city.country,
            convertForecastListToDomain(forecast.list)
        )

    private fun convertForecastListToDomain(list: List<Forecast>): List<ModelForecast> =
        list.mapIndexed { i, forecast ->
            val dt = Calendar.getInstance().timeInMillis + TimeUnit.DAYS.toMillis(i.toLong())
            convertForecastItemToDomain(forecast.copy(dt = dt))
        }


    private fun convertForecastItemToDomain(forecast: Forecast): ModelForecast = ModelForecast(
        convertDate(forecast.dt),
        forecast.weather[0].description,
        forecast.temp.max.toInt(),
        forecast.temp.min.toInt()
    )

    private fun convertDate(date: Long): String {
        val df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
        return df.format(date)
    }
}