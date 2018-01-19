package com.ozgurtas.samplekotlinapplication.Model

/**
 * Created by Ozgur on 17.01.2018.
 */

class Current {
    var last_updated_epoch: Long? = null
    var last_updated: String? = null
    var temp_c: Float? = null
    var temp_f: Float? = null
    var is_day: Int? = null
    var condition: Condition? = null
    var wind_mph: Float? = null
    var wind_kph: Float? = null
    var wind_degree: Int? = null
    var wind_dir: String? = null
    var pressure_mb: Float? = null
    var pressure_in: Float? = null
    var precip_mm: Float? = null
    var precip_in: Float? = null
    var humidity: Int? = null
    var cloud: Int? = null
    var feelslike_c: Float? = null
    var feelslike_f: Float? = null
    var vis_km: Float? = null
    var vis_miles: Float? = null
}
