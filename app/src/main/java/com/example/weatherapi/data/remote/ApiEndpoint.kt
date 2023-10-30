package com.example.weatherapi.data.remote

import com.example.weatherapi.data.model.ForecastModel
import com.example.weatherapi.data.remote.ApiDetails.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiEndpoint {



    @GET(ApiDetails.FORECAST_ENDPOINT)
    fun getCurrentWeatherByCoordinates(
        @Query("lat")
        lat: String,
        @Query("lon")
        lon: String,
        @Query("appid")
        appid: String = API_KEY

    ): Response<ArrayList<ForecastModel>>


    @GET(ApiDetails.FORECAST_ENDPOINT)
    fun getCurrentWeatherByCity(
        @Query("q")
        city: String,
        @Query("appid")
        appid: String = API_KEY

    ): Response<ArrayList<ForecastModel>>
}