package com.example.weatherapi.data.model


import com.google.gson.annotations.SerializedName

data class RainModel(
    @SerializedName("3h")
    val h: Double? = 0.0
)