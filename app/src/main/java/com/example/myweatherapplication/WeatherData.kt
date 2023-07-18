package com.example.myweatherapplication

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


data class DayForecastResponse(
    @Json(name = "list")
    val forecastItems: List<DayForecast>,
)


//data class WeatherCondition(
//    val icon: String,
//)
//
//data class Temperature(
//    val high: Int
//)
//data class WeatherData(
//    val conditionDescription: String,
//    private val temperature: Temperature,
//    @Json(name= "weather")
//    private val weatherConditions: List<WeatherCondition>,
//) {
//    val highTemp: Int
//        get() = temperature.high
//
//    val iconUrl: String
//        get() = "https://openweathermap.org/img/wn/${weatherConditions.firstOrNull()?.icon}@2x.png"
//}

@JsonClass(generateAdapter = true)
data class WeatherData(
    @Json(name = "weather") val weather: List<Weather>,
    @Json(name = "main") val main: Main,
    @Json(name = "wind") val wind: Wind,
    @Json(name = "name") val name: String
)

@JsonClass(generateAdapter = true)
data class Main(
    @Json(name = "temp") val temp: Float,
    @Json(name = "feels_like") val feelsLike: Float,
    @Json(name = "temp_min") val tempMin: Float,
    @Json(name = "temp_max") val tempMax: Float,
    @Json(name = "pressure") val pressure: Int,
    @Json(name = "humidity") val humidity: Int
)

@JsonClass(generateAdapter = true)
data class Weather(
    @Json(name = "id") val id: Int,
    @Json(name = "main") val main: String,
    @Json(name = "description") val description: String,
    @Json(name = "icon") val icon: String
)

@JsonClass(generateAdapter = true)
data class Wind(
    @Json(name = "speed") val speed: Float,
    @Json(name = "deg") val degree: Int
)

data class DayForecast(
    @Json(name = "dt") val date: Long,
    @Json(name = "sunrise") val sunrise: Long,
    @Json(name = "sunset") val sunset: Long,
    @Json(name = "temp") val temp: ForecastTemp,
    @Json(name = "precipitation") val precipitation: Float,
    @Json(name = "humidity") val humidity: Int
)

data class ForecastTemp(
    @Json(name = "day") val day: Float,
    @Json(name = "min") val min: Float,
    @Json(name = "max") val max: Float,
    @Json(name = "night") val night: Float,
    @Json(name = "eve") val evening: Float,
    @Json(name = "morn") val morning: Float
)