package com.example.myapplication.network

import com.example.myapplication.BuildConfig
import com.example.myapplication.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RemoteDataSource {

    fun <Api> buildApi(api: Class<Api>): Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                    OkHttpClient.Builder()
                            .addInterceptor { chain ->
                                chain.proceed(chain.request().newBuilder().also {
                                }.build())

                            }
                        //.addInterceptor(DecryptInterceptor())
                        .also { client ->
                                if (BuildConfig.DEBUG) {
                                    val logging = HttpLoggingInterceptor()
                                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                                    client.addInterceptor(logging)
                                }
                            client.connectTimeout(30, TimeUnit.SECONDS)
                            client.readTimeout(30, TimeUnit.SECONDS)
                            client.writeTimeout(30, TimeUnit.SECONDS)
                        }

                            .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }
}