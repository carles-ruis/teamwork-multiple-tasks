package com.carles.teamworktechtest.common.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseCloudDatasource {

    private val BASE_URL = "https://yat.teamwork.com"
    private val USERNAME = "yat@triplespin.com"
    private val PASSWORD = "yatyatyat27"

    protected fun buildRetrofit() = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(BasicAuthInterceptor(USERNAME,PASSWORD)).build())
            .build()
}