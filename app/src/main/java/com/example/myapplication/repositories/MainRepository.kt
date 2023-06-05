package com.example.myapplication.repositories

import com.example.myapplication.network.MainApi
import com.kleen.laundrypos.base.BaseRepository
import okhttp3.RequestBody



class MainRepository(private val api: MainApi) : BaseRepository(){

    suspend fun getProducts() = safeApiCall {
        api.getProducts()
    }

}