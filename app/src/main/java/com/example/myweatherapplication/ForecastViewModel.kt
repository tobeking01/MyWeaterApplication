package com.example.myweatherapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(private val apiService: WeatherApi) : ViewModel() {
    private val _forecastLiveData: MutableLiveData<List<DayForecast>> = MutableLiveData()
    val forecastData: LiveData<List<DayForecast>>
        get() = _forecastLiveData

    fun fetchWeatherForecast(zipCode: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getForecast(zipCode)
                if (response.isSuccessful) {
                    val forecastData = response.body()
                    if (forecastData != null) {
                        _forecastLiveData.value = forecastData.forecasts
                    } else {
                        // Handle null data or empty list
                        Log.e("ForecastViewModel", "Null data or empty list")
                    }
                } else {
                    // Handle error response
                    Log.e("ForecastViewModel", "API call failed: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                // Handle exception
                Log.e("ForecastViewModel", "Exception: $e")
            }
        }
    }
}