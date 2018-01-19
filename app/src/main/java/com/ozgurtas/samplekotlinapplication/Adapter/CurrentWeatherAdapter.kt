package com.ozgurtas.samplekotlinapplication.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ozgurtas.samplekotlinapplication.Model.Response.CurrentWeather
import com.ozgurtas.samplekotlinapplication.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_view_current_weather.view.*

/**
 * Created by Ozgur on 18.01.2018.
 */

class CurrentWeatherAdapter(private var resultList: ArrayList<CurrentWeather>) : RecyclerView.Adapter<CurrentWeatherAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: CurrentWeatherAdapter.ViewHolder, position: Int) = holder.relateItems(resultList[position])

    override fun getItemCount(): Int = resultList.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CurrentWeatherAdapter.ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.card_view_current_weather, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //Set data from API to show the User
        fun relateItems(resultItem: CurrentWeather) {
            itemView.tvName.text = "Name : " + resultItem.location?.name
            itemView.tvRegion.text = "Region : " + resultItem.location?.region
            itemView.tvCountry.text = "Country : " + resultItem.location?.country
            itemView.tvLocalTime.text = "Local Time : " + resultItem.location?.localtime
            itemView.tvCondition.text = "Condition : " + resultItem.current?.condition?.text
            val imageUrl = "http:" + resultItem.current?.condition?.icon
            Picasso.with(itemView.context).load(imageUrl).into(itemView.ivCondition)
        }
    }
}