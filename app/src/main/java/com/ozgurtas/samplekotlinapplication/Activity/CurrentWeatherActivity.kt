package com.ozgurtas.samplekotlinapplication.Activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import com.ozgurtas.samplekotlinapplication.Adapter.CurrentWeatherAdapter
import com.ozgurtas.samplekotlinapplication.Connection.RestControllerFactory
import com.ozgurtas.samplekotlinapplication.Model.Response.CurrentWeather
import com.ozgurtas.samplekotlinapplication.R
import com.ozgurtas.samplekotlinapplication.Utils.Constants
import kotlinx.android.synthetic.main.activity_weather_result.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Ozgur on 18.01.2018.
 */

class CurrentWeatherActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_result)

        val latitude = intent.getDoubleExtra("latitude", 0.0)
        val longitude = intent.getDoubleExtra("longitude", 0.0)

        getCurrentResult(latitude, longitude)
    }

    //Connect the Current Weather API
    private fun getCurrentResult(latitude: Double, longitude: Double) {
        val coords: String = latitude.toString() + "," + longitude.toString()
        showLoadingDialog()
        val call = RestControllerFactory().getInstance().getWeatherFactory()?.getCurrentWeather(Constants().apiKey, coords)
        call?.enqueue(object : Callback<CurrentWeather> {

            override fun onResponse(call: Call<CurrentWeather>?, response: Response<CurrentWeather>?) {
                if (response != null) {
                    hideLoadingDialog()
                    if (response.isSuccessful) {
                        if (response.code() == 200) {
                            val resultList: ArrayList<CurrentWeather> = ArrayList()
                            resultList.add(response.body()!!)
                            val adapter = CurrentWeatherAdapter(resultList)
                            rvResult.layoutManager = LinearLayoutManager(this@CurrentWeatherActivity, LinearLayout.VERTICAL, false)
                            rvResult.adapter = adapter
                        } else {
                            showToast(response.message()?.toString())
                        }
                    } else {
                        showToast(response.message()?.toString())
                    }
                }
            }

            override fun onFailure(call: Call<CurrentWeather>?, t: Throwable?) {
                hideLoadingDialog()
                Log.e("onFailure", t.toString())
            }
        })
    }
}
