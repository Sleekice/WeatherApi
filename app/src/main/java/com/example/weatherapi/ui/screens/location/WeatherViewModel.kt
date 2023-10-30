package com.example.weatherapi.ui.screens.location


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapi.data.model.ForecastModel
import com.example.weatherapi.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _weatherData = MutableStateFlow<ArrayList<ForecastModel>>(ArrayList())
    val weatherData: StateFlow<ArrayList<ForecastModel>> = _weatherData

    fun fetchWeatherByCoordinates(lat: String, lon: String) {
        viewModelScope.launch {
            val response = repository.getCurrentWeatherByCoordinates(lat, lon)
            _weatherData.value = (response.body() ?: ArrayList())
        }
    }

    fun fetchWeatherByCity(city: String) {
        viewModelScope.launch {
            val response = repository.getCurrentWeatherByCity(city)
            _weatherData.value = (response.body() ?: ArrayList())
        }
    }

    fun refreshWeatherData(city: String) {
        viewModelScope.launch {
            try {
                val response = repository.getCurrentWeatherByCity(city)
                if (response.isSuccessful) {
                    _weatherData.value = (response.body() ?: ArrayList())
                } else {
                    // Handle the error case
                }
            } catch (e: Exception) {
                // Handle exceptions if any
            }
        }
    }



}
