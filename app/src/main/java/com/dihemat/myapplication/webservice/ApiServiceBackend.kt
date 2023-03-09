package com.dihemat.myapplication.webservice

import com.dihemat.myapplication.model.LoginResponse
import com.dihemat.myapplication.model.profil.ProfilResponse

import retrofit2.Call
import retrofit2.http.*


interface ApiServiceBackend {

    //===================AUTH===============
    //Login
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email :String,
        @Field("password") password :String
    ): Call<LoginResponse>

    //Register
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("email") email :String,
        @Field("nama") nama :String,
        @Field("password") password :String,
        @Field("nomor_telepon") nomor_telepon :String
    ): Call<LoginResponse>

    //profil
    @GET("profil")
    fun profil(
        @Query("id") id :Int
    ): Call<ProfilResponse>



}