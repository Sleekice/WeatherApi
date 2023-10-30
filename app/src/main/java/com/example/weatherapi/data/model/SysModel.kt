package com.example.weatherapi.data.model


import com.google.gson.annotations.SerializedName

data class SysModel(
    @SerializedName("pod")
    val pod: String? = ""
)