package com.ozgurtas.samplekotlinapplication.Connection

import com.ozgurtas.samplekotlinapplication.Model.Response.CurrentWeather
import com.ozgurtas.samplekotlinapplication.Model.Response.ForecastWeather
import com.ozgurtas.samplekotlinapplication.Model.Response.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Ozgur on 13.01.2018.
 */

interface WeatherMapService {

    @GET("current.json?")
    fun getCurrentWeather(@Query("key") key: String, @Query("q") cityName: String): Call<CurrentWeather>

    @GET("forecast.json?")
    fun getForecastWeather(@Query("key") key: String, @Query("q") cityName: String, @Query("days") days: String): Call<ForecastWeather>

    @GET("search.json?")
    fun getSearchResult(@Query("key") key: String, @Query("q") searchTerm: String): Call<List<SearchResult>>

}
