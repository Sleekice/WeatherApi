package com.example.weatherapi.data.repository

import com.example.weatherapi.data.model.ForecastModel
import retrofit2.Response


interface Repository {
    suspend fun getCurrentWeatherByCoordinates(lat: String, lon: String): Response<ArrayList<ForecastModel>>
    suspend fun getCurrentWeatherByCity(city: String): Response<ArrayList<ForecastModel>>

}