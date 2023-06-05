package com.example.myapplication.network

import com.example.myapplication.data.BaseResponse
import com.example.myapplication.data.Product
import com.example.myapplication.utils.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MainApi {

@GET(Constants.Url.GET_PRODUCTS)
suspend fun getProducts() : Response<BaseResponse<Product>>
}