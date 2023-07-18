package com.example.myweatherapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
//class ForecastViewModel @Inject constructor(private val service: WeatherApi) : ViewModel() {
//    private val _forecast = MutableStateFlow<DayForecastResponse?>(null)
//    val forecast: StateFlow<DayForecastResponse?> = _forecast
//
//    fun loadData() {
//        viewModelScope.launch {
//            _forecast.value = service.getForecast("55420")
//        }
//    }
//}