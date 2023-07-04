package com.example.myweatherapplication

data class DayForecast(
    val date: String,
    val sunrise: String,
    val sunset: String,
    val temp: ForecastTemp,
    val precipitation: Float,
    val humidity: Int
)