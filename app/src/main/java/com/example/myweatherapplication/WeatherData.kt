package com.example.myweatherapplication

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


const val ZIPCODE = "55437"
const val FORECAST_COUNT = 16


@JsonClass(generateAdapter = true)
data class WeatherData(
    @Json(name = "main") val main: Main,
    @Json(name = "name") val name: String,
    @Json(name = "weather") private val weatherConditions: List<WeatherConditions>,
    ){
    @Json(name = "icon") val iconUrl: String
        get() = "https://openweathermap.org/img/wn/${weatherConditions.firstOrNull()?.icon}@2x.png"
}

data class Forecast(
    @Json(name = "list") val forecasts: List<DayForecast>,
)
@JsonClass(generateAdapter = true)
data class DayForecast(
    @Json(name = "dt") val dayTime: Long,
    @Json(name = "sunrise") val sunrise: Long,
    @Json(name = "sunset") val sunset: Long,
    @Json(name = "temp") val temp: Temperature,
    @Json(name = "weather") private val weatherConditions: List<WeatherConditions>,
    ){
    @Json(name = "day") val dayTemp: Double
        get() = temp.day
    @Json(name = "min") val lowTemp: Double
        get() = temp.min
    @Json(name = "max") val highTemp: Double
        get() = temp.max
    @Json(name = "icon") val iconUrl: String
        get() = "https://openweathermap.org/img/wn/${weatherConditions.firstOrNull()?.icon}@2x.png"
}


@JsonClass(generateAdapter = true)
data class Main(
    @Json(name = "temp") val temp: Double,
    @Json(name = "feels_like") val feelsLike: Double,
    @Json(name = "temp_min") val tempMin: Double,
    @Json(name = "temp_max") val tempMax: Double,
    @Json(name = "pressure") val pressure: Int,
    @Json(name = "humidity") val humidity: Int
)

@JsonClass(generateAdapter = true)
data class Temperature(
    @Json(name = "day") val day: Double,
    @Json(name = "min") val min: Double,
    @Json(name = "max") val max: Double,
    @Json(name = "night") val night: Double,
    @Json(name = "eve") val eve: Double,
    @Json(name = "morn") val morn: Double
)

data class WeatherConditions(
    @Json(name = "icon") val icon: String,
)
