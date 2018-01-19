package com.ozgurtas.samplekotlinapplication.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ozgurtas.samplekotlinapplication.Model.Forecastday
import com.ozgurtas.samplekotlinapplication.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_view_forecast_weather.view.*

/**
 * Created by Ozgur on 18.01.2018.
 */

class ForecastWeatherAdapter(private var resultList: ArrayList<Forecastday>) : RecyclerView.Adapter<ForecastWeatherAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ForecastWeatherAdapter.ViewHolder, position: Int) = holder.relateItems(resultList[position])

    override fun getItemCount(): Int = resultList.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ForecastWeatherAdapter.ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.card_view_forecast_weather, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //Set data from API to show the User
        fun relateItems(resultItem: Forecastday) {
            itemView.tvDate.text = "" + resultItem.date
            itemView.tvAvgTemp.text = "Average Temperature : " + resultItem.day?.avgtemp_c + " °C"
            itemView.tvCondition.text = "Condition : " + resultItem.day?.condition?.text
            val imageUrl = "http:" + resultItem.day?.condition?.icon
            Picasso.with(itemView.context).load(imageUrl).into(itemView.ivCondition)
        }
    }
}