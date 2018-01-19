package com.ozgurtas.samplekotlinapplication.Connection

import com.ozgurtas.samplekotlinapplication.BuildConfig
import okhttp3.OkHttpClient.Builder
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Ozgur on 15.01.2018.
 */

class RestControllerFactory {

    private var instance: RestControllerFactory? = null
    private var timeoutInterval: Long = 30

    //Singleton Pattern
    fun getInstance(): RestControllerFactory {
        if (instance == null) {
            instance = RestControllerFactory()
        }
        return instance as RestControllerFactory
    }

    private var weatherMapService: WeatherMapService? = null

    fun getWeatherFactory(): WeatherMapService? = weatherMapService

    //Constructor
    init {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE
        }
        val httpClient = Builder()
                .addInterceptor(logging)
                .connectTimeout(timeoutInterval, TimeUnit.SECONDS)
                .readTimeout(timeoutInterval, TimeUnit.SECONDS)
                .build()
        val mapWeatherService = Retrofit.Builder()
                .baseUrl(BuildConfig.API_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
        weatherMapService = mapWeatherService.create(WeatherMapService::class.java)
    }
}
