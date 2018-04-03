package com.ozgurtas.samplekotlinapplication.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ozgurtas.samplekotlinapplication.Model.Response.SearchResult
import com.ozgurtas.samplekotlinapplication.R
import com.ozgurtas.samplekotlinapplication.Utils.NavigationHelper
import kotlinx.android.synthetic.main.card_view_search_item.view.*

/**
 * Created by Ozgur on 18.01.2018.
 */

class SearchAdapter(private var resultList: ArrayList<SearchResult>?) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: SearchAdapter.ViewHolder, position: Int) = holder.relateItems(resultList!![position])

    override fun getItemCount(): Int = resultList!!.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SearchAdapter.ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.card_view_search_item, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //Set data from API to show the User
        fun relateItems(resultItem: SearchResult) {
            itemView.tvName.text = "Name : " + resultItem.name
            itemView.tvRegion.text = "Region : " + resultItem.region
            itemView.tvCountry.text = "Country : " + resultItem.country
            itemView.setOnClickListener {
                //Redirect the User to show current weather of every single item
                NavigationHelper.startCurrentWeatherActivity(itemView.context, resultItem.lat?.toDouble(), resultItem.lon?.toDouble())
            }
        }
    }
}
