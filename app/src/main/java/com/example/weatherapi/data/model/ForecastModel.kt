package com.example.weatherapi.data.model


import com.google.gson.annotations.SerializedName

data class ForecastModel(
    @SerializedName("clouds")
    val clouds: CloudsModel? = CloudsModel(),
    @SerializedName("dt")
    val dt: Int? = 0,
    @SerializedName("dt_txt")
    val dtTxt: String? = "",
    @SerializedName("main")
    val main: MainModel? = MainModel(),
    @SerializedName("pop")
    val pop: Double? = 0.0,
    @SerializedName("rain")
    val rain: RainModel? = RainModel(),
    @SerializedName("sys")
    val sys: SysModel? = SysModel(),
    @SerializedName("visibility")
    val visibility: Int? = 0,
    @SerializedName("weather")
    val weather: List<WeatherModel?>? = listOf(),
    @SerializedName("wind")
    val wind: WindModel? = WindModel()
)