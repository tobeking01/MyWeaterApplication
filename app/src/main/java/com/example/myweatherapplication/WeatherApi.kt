package com.example.myweatherapplication

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getWeatherData(
        @Query("zip") zipCode: String = ZIPCODE,
        @Query("units") units: String = "imperial",
        @Query("appid") appId: String = "326dcf6708e0a90b0f48b9818036e58e"
    ): Response<WeatherData>

    @GET("forecast/daily")
    suspend fun getForecast(
        @Query("zip") zipCode: String = ZIPCODE,
        @Query("cnt") count: Int = FORECAST_COUNT,
        @Query("units") units: String = "imperial",
        @Query("appid") appId: String = "326dcf6708e0a90b0f48b9818036e58e"
    ): Response<Forecast>
}