package com.example.kotlinweatherapp

import com.example.kotlinweatherapp.domain.commands.RequestDayForecastCommand
import com.example.kotlinweatherapp.domain.datasource.ForecastProvider
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class RequestDayForecastCommandTest {

    @Test
    fun `provider is called when command executed`(){
        val forecastProvider = mock(ForecastProvider::class.java)
        val command = RequestDayForecastCommand(2,forecastProvider)
        runBlocking { command.execute() }
        verify(forecastProvider).requestForecast(2)
    }
}