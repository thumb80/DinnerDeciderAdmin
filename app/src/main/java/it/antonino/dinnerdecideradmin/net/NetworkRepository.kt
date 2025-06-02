package it.antonino.dinnerdecideradmin.net

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import it.antonino.dinnerdecideradmin.model.Food
import it.antonino.dinnerdecideradmin.singleton.SingletonHolder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkRepository(
    private val networkInterface: NetworkInterface
) {

    companion object: SingletonHolder<NetworkRepository,NetworkInterface>(::NetworkRepository)

    fun getAll(): MutableLiveData<JsonArray?> {
        val respObj = MutableLiveData<JsonArray?>()
        networkInterface.getFoodList().enqueue(object: Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                respObj.postValue(response.body())
            }
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                respObj.postValue(null)
            }

        })
        return respObj
    }

    fun reset(): MutableLiveData<Int?> {
        val respObj = MutableLiveData<Int?>()
        networkInterface.reset().enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                respObj.postValue(response.raw().code())
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                respObj.postValue(null)
            }
        })
        return respObj
    }

    fun getVoted(): MutableLiveData<JsonObject?> {
        val respObj = MutableLiveData<JsonObject?>()
        networkInterface.getVoted().enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                respObj.postValue(response.body())
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                respObj.postValue(null)
            }
        })
        return respObj
    }

    fun insertFood(food: Food): MutableLiveData<JsonObject?> {
        val respObj = MutableLiveData<JsonObject?>()
        networkInterface.insertFood(food).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                respObj.postValue(response.body())
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                respObj.postValue(null)
            }
        })
        return respObj
    }

    fun voteFood(food: Food): MutableLiveData<Int?> {
        val respObj = MutableLiveData<Int?>()
        networkInterface.voteFood(food).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                respObj.postValue(response.raw().code())
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                respObj.postValue(null)
            }
        })
        return respObj
    }

}