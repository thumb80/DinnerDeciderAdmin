package it.antonino.dinnerdecideradmin.model

import com.google.gson.annotations.SerializedName

data class Food(
    @SerializedName("name")
    val name: String,
    @SerializedName("count")
    val count: Int
)
