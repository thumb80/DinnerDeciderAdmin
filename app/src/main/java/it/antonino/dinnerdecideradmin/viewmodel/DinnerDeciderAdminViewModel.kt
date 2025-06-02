package it.antonino.dinnerdecideradmin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import it.antonino.dinnerdecideradmin.model.Food
import it.antonino.dinnerdecideradmin.net.NetworkRepository
import kotlinx.coroutines.launch

class DinnerDeciderAdminViewModel(
    private val newtworkRepository: NetworkRepository
) : ViewModel() {

    fun getAll(): LiveData<JsonArray?> {
        var ret: MutableLiveData<JsonArray?> = MutableLiveData()
        viewModelScope.launch {
            ret = newtworkRepository.getAll()
        }
        return ret
    }

    fun getVoted(): LiveData<JsonObject?> {
        var ret: MutableLiveData<JsonObject?> = MutableLiveData()
        viewModelScope.launch {
            ret = newtworkRepository.getVoted()
        }
        return ret
    }

    fun reset(): LiveData<Int?> {
        var ret: MutableLiveData<Int?> = MutableLiveData()
        viewModelScope.launch {
            ret = newtworkRepository.reset()
        }
        return ret
    }

    fun insertFood(food: Food): LiveData<JsonObject?> {
        var ret: MutableLiveData<JsonObject?> = MutableLiveData()
        viewModelScope.launch {
            ret = newtworkRepository.insertFood(food)
        }
        return ret
    }

    fun voteFood(food: Food): LiveData<Int?> {
        var ret: MutableLiveData<Int?> = MutableLiveData()
        viewModelScope.launch {
            ret = newtworkRepository.voteFood(food)
        }
        return ret
    }

}