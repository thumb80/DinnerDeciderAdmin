package it.antonino.dinnerdecideradmin.net

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import it.antonino.dinnerdecideradmin.model.Food
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NetworkInterface {

    @GET("getall")
    fun getFoodList(): Call<JsonArray>

    @GET("getvoted")
    fun getVoted(): Call<JsonObject>

    @GET("reset")
    fun reset(): Call<Void>

    @POST("votefood")
    fun voteFood(@Body food: Food): Call<Void>

    @POST("insertfood")
    fun insertFood(@Body food: Food): Call<JsonObject>

}