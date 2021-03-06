package com.example.kotlinweatherapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinweatherapp.R
import com.example.kotlinweatherapp.domain.commands.RequestForecastCommand
import com.example.kotlinweatherapp.extensions.DelegateExt
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

class MainActivity : CoroutineScopeActivity(), ToolbarManager {

    private var zipCode: Long
            by DelegateExt.preference(
                this,
                SettingsActivity.ZIP_CODE,
                SettingsActivity.DEFAULT_ZIP
            )

    override val toolbar: Toolbar by lazy {
        findViewById<Toolbar>(R.id.toolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        forecastList.layoutManager = LinearLayoutManager(this)
        attachToScroll(forecastList)
    }

    override fun onResume() {
        super.onResume()
        loadForecast()
    }

    private fun loadForecast() = launch {
        val result = RequestForecastCommand(zipCode).execute()
        forecastList.adapter =
            ForecastListAdapter(result) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.ID, it.id)
                intent.putExtra(DetailActivity.CITY_NAME, result.city)
                startActivity(intent)
            }
        toolbarTitle = "${result.city} (${result.country})"
    }
}
