package com.example.kotlinweatherapp.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.kotlinweatherapp.R
import com.example.kotlinweatherapp.domain.commands.RequestDayForecastCommand
import com.example.kotlinweatherapp.domain.model.Forecast
import com.example.kotlinweatherapp.extensions.toDateString
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.launch
import java.text.DateFormat

class DetailActivity : CoroutineScopeActivity(), ToolbarManager {

    override val toolbar: Toolbar by lazy {
        findViewById<Toolbar>(R.id.toolbar)
    }

    companion object {
        const val ID = "DetailActivity:id"
        const val CITY_NAME = "DetailActivity:cityName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initToolbar()
        toolbarTitle = intent.getStringExtra(CITY_NAME) ?: ""
        enableHomeAsUp { onBackPressed() }
        launch {
            val result = RequestDayForecastCommand(intent.getLongExtra(ID, -1)).execute()
            bindForecast(result)
        }
    }

    private fun bindForecast(forecast: Forecast) = with(forecast) {
        Picasso.get().load(iconUrl).into(icon)
        supportActionBar?.subtitle = date.toDateString(DateFormat.FULL)
        weatherDescription.text = description
        bindWeather(high to maxTemperature, low to minTemperature)
    }

    private fun bindWeather(vararg views: Pair<Int, TextView>) = views.forEach {
        it.second.text = "${it.first}"
        it.second.setTextColor(
            when (it.first) {
                in -50..0 -> ContextCompat.getColor(this, android.R.color.holo_red_dark)
                in 0..15 -> ContextCompat.getColor(this, android.R.color.holo_orange_dark)
                else -> ContextCompat.getColor(this, android.R.color.holo_green_dark)
            }
        )
    }
}
