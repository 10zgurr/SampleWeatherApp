package com.ozgurtas.samplekotlinapplication.Utils

import android.content.Context
import android.content.Intent
import com.ozgurtas.samplekotlinapplication.Activity.CurrentWeatherActivity
import com.ozgurtas.samplekotlinapplication.Activity.ForeCastWeatherActivity
import com.ozgurtas.samplekotlinapplication.Activity.SearchActivity

/**
 * Created by Ozgur on 18.01.2018.
 */

object NavigationHelper {

    fun startCurrentWeatherActivity(context: Context, latitude: Double?, longitude: Double?) {
        val intent = Intent(context, CurrentWeatherActivity::class.java)
        intent.putExtra("latitude", latitude)
        intent.putExtra("longitude", longitude)
        context.startActivity(intent)
    }

    fun startForecastWeatherActivity(context: Context, latitude: Double?, longitude: Double?, day: String) {
        val intent = Intent(context, ForeCastWeatherActivity::class.java)
        intent.putExtra("latitude", latitude)
        intent.putExtra("longitude", longitude)
        intent.putExtra("day", day)
        context.startActivity(intent)
    }

    fun startSearchActivity(context: Context) {
        val intent = Intent(context, SearchActivity::class.java)
        context.startActivity(intent)
    }
}
