package com.example.kotlinweatherapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinweatherapp.R
import com.example.kotlinweatherapp.domain.commands.RequestForecastCommand
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity(), ToolbarManager {

    override val toolbar: Toolbar by lazy {
        findViewById<Toolbar>(R.id.toolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        forecastList.layoutManager = LinearLayoutManager(this)
        attachToScroll(forecastList)
        doAsync {
            val result = RequestForecastCommand(94043).execute()
            uiThread {
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
    }
}
