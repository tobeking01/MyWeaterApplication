package com.example.myweatherapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class CurrentConditionsViewModel @Inject constructor(private val apiService: WeatherApi) : ViewModel() {

    private val _weatherData: MutableLiveData<WeatherData> = MutableLiveData()
    val weatherData: LiveData<WeatherData>
        get() = _weatherData

    val textFieldText: MutableLiveData<String> = MutableLiveData()

    // Function to perform input validation for the city name
    private fun isValidCity(city: String): Boolean {
        return city.isNotBlank()
    }

    fun fetchWeatherDataByCity(zipCode: String) {
        if (isValidCity(zipCode)) {
            viewModelScope.launch {
                try {
                    val response = apiService.getWeatherData(zipCode)
                    if (response.isSuccessful) {
                        val weatherData = response.body()
                        _weatherData.value = weatherData
                    } else {
                        // Handle error case
                        Log.e(
                            "CurrentConditionsViewModel",
                            "API call failed: ${response.errorBody()}"
                        )
                        // Handle invalid city input (e.g., show an error message to the user)
                        Log.e("CurrentConditionsViewModel", "Invalid city: $zipCode")
                    }
                } catch (e: Exception) {
                    // Handle exception
                    Log.e("CurrentConditionsViewModel", "Exception: $e")
                }
            }
        }
    }

    fun updateTextFieldText(newText: String) {
        textFieldText.value = newText
    }
}