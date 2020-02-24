package com.example.kotlinweatherapp

import com.example.kotlinweatherapp.domain.datasource.ForecastDataSource
import com.example.kotlinweatherapp.domain.datasource.ForecastProvider
import com.example.kotlinweatherapp.domain.model.Forecast
import com.example.kotlinweatherapp.domain.model.ForecastList
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class ForecastProviderTest {

    @Test
    fun `data source returns a value`() {
        val ds = mock(ForecastDataSource::class.java)
        `when`(ds.requestDayForecast(0)).then {
            Forecast(0, 0, "desc", 20, 0, "url")
        }
        val provider = ForecastProvider(listOf(ds))
        assertNotNull(provider.requestForecast(0))
    }

    @Test
    fun `empty database returns server value`() {
        val db = mock(ForecastDataSource::class.java)
        val server = mock(ForecastDataSource::class.java)
        `when`(server.requestForecastByZipCode(any(Long::class.java), any(Long::class.java)))
            .then { ForecastList(0, "city", "country", listOf()) }
        val provider = ForecastProvider(listOf(db, server))
        assertNotNull(provider.requestByZipCode(0, 0))
    }
}