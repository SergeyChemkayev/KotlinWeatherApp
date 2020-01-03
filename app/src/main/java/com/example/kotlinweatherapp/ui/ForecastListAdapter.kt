package com.example.kotlinweatherapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinweatherapp.R
import com.example.kotlinweatherapp.domain.model.Forecast
import com.example.kotlinweatherapp.domain.model.ForecastList
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_forecast.view.*

class ForecastListAdapter(
    private val weekForecast: ForecastList,
    private val itemClick: (Forecast) -> Unit
) :
    RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_forecast, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun getItemCount() = weekForecast.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindForecast(weekForecast[position])
    }

    class ViewHolder(override val containerView: View, private val itemClick: (Forecast) -> Unit) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindForecast(forecast: Forecast) {
            with(forecast) {
                Picasso.get().load(iconUrl).into(containerView.icon)
                containerView.date.text = date
                containerView.description.text = description
                containerView.maxTemperature.text =
                    itemView.resources.getString(R.string.temperature, high)
                containerView.minTemperature.text =
                    itemView.resources.getString(R.string.temperature, low)
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}