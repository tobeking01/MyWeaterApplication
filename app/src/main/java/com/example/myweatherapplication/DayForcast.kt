package com.example.myweatherapplication

data class DayForecast(
    val date: Long,
    val sunrise: Long,
    val sunset: Long,
    val temp: ForecastTemp,
    val precipitation: Float,
    val humidity: Int
)