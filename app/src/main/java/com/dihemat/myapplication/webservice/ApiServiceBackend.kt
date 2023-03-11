package com.dihemat.myapplication.webservice

import com.dihemat.myapplication.model.LoginResponse
import com.dihemat.myapplication.model.PostResponse
import com.dihemat.myapplication.model.TokoTerdekatResponse
import com.dihemat.myapplication.model.produktoko.ProdukTokoResponse
import com.dihemat.myapplication.model.profil.ProfilResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

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

    //toko terdekat
    @GET("toko_terdekat")
    fun toko_terdekat(
        @Query("latitude") latitude :String,
        @Query("longitude") longitude :String
    ): Call<TokoTerdekatResponse>

    //Daftar TOko
    @Multipart
    @POST("daftartoko")
    fun daftartoko(
        @Part foto: MultipartBody.Part?,
        @Part("namatoko") namatoko: RequestBody,
        @Part("alamat") alamat: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody,
        @Part("id") id: RequestBody    ): Call<PostResponse>

    //Daftar TOko
    @Multipart
    @POST("tambah_produk")
    fun tambah_produk(
        @Part foto: MultipartBody.Part?,
        @Part("nama_produk") nama_produk: RequestBody,
        @Part("harga") harga: RequestBody,
        @Part("keterangan") keterangan: RequestBody,
        @Part("user_id") user_id: RequestBody   ): Call<PostResponse>

    //toko terdekat
    @GET("produk_toko")
    fun produk_toko(
        @Query("user_id") user_id :Int
    ): Call<ProdukTokoResponse>

}