package com.example.pawpetshop.app

import com.example.pawpetshop.model.ResponModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("register") // "http://192.168.0.104/api/register/"
    fun register(
        @Field("name") name :String,
        @Field("email") email :String,
        @Field("phone") nomortlp :String,
        @Field("password") password :String
    ): Call<ResponModel>

    @FormUrlEncoded
    @POST("login") // "http://192.168.0.104/api/login/"
    fun login(
        @Field("email") email :String,
        @Field("password") password :String
    ): Call<ResponModel>

}