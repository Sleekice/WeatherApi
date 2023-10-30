
package com.example.weatherapi.data.remote

import com.example.weatherapi.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiDetails {

    //https://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=fafdc6533ef599c278770bf599914755

    //http://api.openweathermap.org/data/2.5/forecast?lat=44.34&lon=10.99&appid=bbb4ddd180567d73e8edfceb807b7a59

    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    const val FORECAST_ENDPOINT = "forecast?"
   // const val SEARCH_ENDPOINT = "search/users"

    const val API_KEY = BuildConfig.API_KEY
}



