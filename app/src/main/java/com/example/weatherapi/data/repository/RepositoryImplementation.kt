package com.example.weatherapi.data.repository

import com.example.weatherapi.data.model.ForecastModel
import com.example.weatherapi.data.remote.ApiEndpoint
import retrofit2.Response
import javax.inject.Inject

class RepositoryImplementation @Inject constructor(
    private val service: ApiEndpoint
) : Repository {



    override suspend fun getCurrentWeatherByCoordinates(
        lat: String,
        lon: String
    ): Response<ArrayList<ForecastModel>> {
        return service.getCurrentWeatherByCoordinates(lat, lon)
    }


    override suspend fun getCurrentWeatherByCity(city: String)
        :Response<ArrayList<ForecastModel>> {
        return service.getCurrentWeatherByCity(city)
    }


}