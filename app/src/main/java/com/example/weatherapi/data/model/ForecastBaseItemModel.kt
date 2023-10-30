package com.example.weatherapi.data.model


import com.google.gson.annotations.SerializedName

data class ForecastBaseItemModel(
    @SerializedName("city")
    val city: CityModel? = CityModel(),
    @SerializedName("cnt")
    val cnt: Int? = 0,
    @SerializedName("cod")
    val cod: String? = "",
    @SerializedName("list")
    val list: List<ForecastModel>? = null,
    @SerializedName("message")
    val message: Int? = 0
)