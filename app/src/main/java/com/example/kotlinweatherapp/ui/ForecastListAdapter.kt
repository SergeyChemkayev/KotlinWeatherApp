package com.example.kotlinweatherapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinweatherapp.R
import com.example.kotlinweatherapp.domain.model.Forecast
import com.example.kotlinweatherapp.domain.model.ForecastList
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_forecast.view.*

class ForecastListAdapter(
    private val weekForecast: ForecastList,
    private val itemClick: OnItemClickListener
) :
    RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_forecast, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int = weekForecast.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindForecast(weekForecast[position])
    }

    class ViewHolder(private val view: View, private val itemClick: OnItemClickListener) :
        RecyclerView.ViewHolder(view) {
        fun bindForecast(forecast: Forecast) {
            with(forecast) {
                Picasso.get().load(iconUrl).into(view.icon)
                view.date.text = date
                view.description.text = description
                view.maxTemperature.text = "$high"
                view.minTemperature.text = "$low"
                itemView.setOnClickListener {
                    itemClick(this)
                }
            }

        }
    }

    interface OnItemClickListener {
        operator fun invoke(forecast: Forecast)
    }
}