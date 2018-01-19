package com.ozgurtas.samplekotlinapplication.Model.Response

import com.ozgurtas.samplekotlinapplication.Model.Current
import com.ozgurtas.samplekotlinapplication.Model.Forecast
import com.ozgurtas.samplekotlinapplication.Model.LocationCity

/**
 * Created by Ozgur on 17.01.2018.
 */

class ForecastWeather{
    var location: LocationCity? = null
    var current: Current? = null
    var forecast: Forecast? = null
}
