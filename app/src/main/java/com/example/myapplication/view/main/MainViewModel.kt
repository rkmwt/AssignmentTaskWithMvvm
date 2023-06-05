package com.example.myapplication.view.main

import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.base.Event
import com.example.myapplication.data.BaseResponse
import com.example.myapplication.data.Product
import com.example.myapplication.network.Resource
import com.example.myapplication.repositories.MainRepository
import kotlinx.coroutines.launch


class MainViewModel(private val repository: MainRepository): BaseViewModel() {

    var text= MutableLiveData("")

    // Get Products
    private val _receiveProductsResponse: MutableLiveData<Resource<BaseResponse<Product>>> = MutableLiveData()
    val receiveProductsResponse: LiveData<Resource<BaseResponse<Product>>>
        get() = _receiveProductsResponse

    fun getProducts() = viewModelScope.launch {
        event.value = Event<Any>(Event.EventType.PROGRESS_SHOW)
        _receiveProductsResponse.value = repository.getProducts()
    }

    fun observerProducts() = Observer<Resource<BaseResponse<Product>>> {
        event.value = Event<Any>(Event.EventType.PROGRESS_HIDE)
        when (it) {
            is Resource.Success -> {
                if (it.value.status == "success") {
                    event.value = Event(Event.EventType.RESPONSE, it.value)
                } else {
                    if (TextUtils.isEmpty(it.value.message))
                        event.value = Event(Event.EventType.SHOW_TOAST, Event.UserMessage("Could not connect"))
                    else
                        event.value = Event(Event.EventType.SHOW_TOAST, Event.UserMessage(it.value.message!!))
                }
            }
            is Resource.Failure -> {
                event.value = Event(Event.EventType.SHOW_TOAST, Event.UserMessage("Could not connect"))
            }
            is Resource.TokenExpired -> {
                event.value = Event<Any>(Event.EventType.TOKEN_EXPIRED)
            }
        }
    }
    fun setObservers(){
        receiveProductsResponse.observeForever(observerProducts())
    }
    override fun onCleared() {
        receiveProductsResponse.removeObserver(observerProducts())
        super.onCleared()
    }
}