package com.ozgurtas.samplekotlinapplication.Activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import com.ozgurtas.samplekotlinapplication.Adapter.ForecastWeatherAdapter
import com.ozgurtas.samplekotlinapplication.Connection.RestControllerFactory
import com.ozgurtas.samplekotlinapplication.Model.Forecastday
import com.ozgurtas.samplekotlinapplication.Model.Response.ForecastWeather
import com.ozgurtas.samplekotlinapplication.R
import com.ozgurtas.samplekotlinapplication.Utils.Constants
import kotlinx.android.synthetic.main.activity_forecast_weather.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Ozgur on 18.01.2018.
 */

class ForeCastWeatherActivity : BaseActivity() {

    override fun onCreateFinished(savedInstanceState: Bundle?) {
        val latitude = intent.getDoubleExtra("latitude", 0.0)
        val longitude = intent.getDoubleExtra("longitude", 0.0)
        val day = intent.getStringExtra("day")

        getForecastResult(latitude, longitude, day)
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_forecast_weather
    }

    //Connect the Forecast Weather API
    private fun getForecastResult(latitude: Double, longitude: Double, day: String) {
        val coords: String = latitude.toString() + "," + longitude.toString()
        showLoadingDialog()
        val call = RestControllerFactory().getInstance().getWeatherFactory()?.getForecastWeather(Constants().apiKey, coords, day)
        call?.enqueue(object : Callback<ForecastWeather> {

            override fun onResponse(call: Call<ForecastWeather>?, response: Response<ForecastWeather>?) {
                if (response != null) {
                    hideLoadingDialog()
                    if (response.isSuccessful) {
                        if (response.code() == 200) {
                            val resultList: ArrayList<Forecastday> = ArrayList()
                            resultList.addAll(response.body()!!.forecast!!.forecastday!!)
                            tvName.text = "Name : " + response.body()?.location?.name
                            tvRegion.text = "Region : " + response.body()?.location?.region
                            tvCountry.text = "Country : " + response.body()?.location?.country
                            tvLocalTime.text = "Local Time : " + response.body()?.location?.localtime
                            val adapter = ForecastWeatherAdapter(resultList)
                            rvResultForecast.layoutManager = LinearLayoutManager(this@ForeCastWeatherActivity, LinearLayout.VERTICAL, false)
                            rvResultForecast.adapter = adapter
                        } else {
                            showToast(response.message()?.toString())
                        }
                    } else {
                        showToast(response.message()?.toString())
                    }
                }
            }

            override fun onFailure(call: Call<ForecastWeather>?, t: Throwable?) {
                hideLoadingDialog()
                Log.e("onFailure", t.toString())
            }
        })
    }
}
