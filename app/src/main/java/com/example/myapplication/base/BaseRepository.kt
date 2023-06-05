package com.kleen.laundrypos.base

import android.util.Log
import com.example.myapplication.network.Resource
import retrofit2.Response

abstract class BaseRepository {

    protected suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): Resource<T> {
        var response: Response<T>? = null
        try {
            response = call.invoke()
            if (response.code() == 200) {
                return Resource.Success(response.body()!!)
            }
            else if (response.code() == 401){
                return Resource.TokenExpired()
            }
            else{
                val errorBody = response.errorBody()
                return Resource.Failure(false, response.code(), errorBody?.string() ?: "error")
            }
        } catch (t: Exception) {
                Log.e("Fail",t.toString())
                return Resource.Failure(true, null, t.toString())
        }

    }

}