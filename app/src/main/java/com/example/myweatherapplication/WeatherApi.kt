package com.example.myweatherapplication

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getWeatherData(
        @Query("zip") zipCode: String,
        @Query("units") units: String = "imperial",
        @Query("appid") appId: String = "326dcf6708e0a90b0f48b9818036e58e"
    ): Response<WeatherData> // Update the response type to WeatherData

    @GET("forecast")
    suspend fun getForecast(
        @Query("zip") zipCode: String,
        @Query("units") units: String = "imperial",
        @Query("appid") apiKey: String = "326dcf6708e0a90b0f48b9818036e58e"
    ): Response<DayForecastResponse> // Replace DayForecastResponse with the actual response type
}