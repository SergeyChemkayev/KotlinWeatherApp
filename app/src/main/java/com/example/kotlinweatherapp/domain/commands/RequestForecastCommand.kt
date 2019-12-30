package com.example.kotlinweatherapp.domain.commands

import com.example.kotlinweatherapp.data.ForecastRequest
import com.example.kotlinweatherapp.domain.mappers.ForecastDataMapper
import com.example.kotlinweatherapp.domain.model.ForecastList

class RequestForecastCommand(val zipCode:String):Command<ForecastList> {


    override fun execute(): ForecastList {
        val forecastRequest = ForecastRequest(zipCode)
        return ForecastDataMapper().convertFromDataModel(forecastRequest.execute())
    }
}