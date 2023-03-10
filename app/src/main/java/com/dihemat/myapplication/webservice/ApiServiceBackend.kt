package com.dihemat.myapplication.webservice

import com.dihemat.myapplication.model.LoginResponse
import com.dihemat.myapplication.model.PostCartRequest
import com.dihemat.myapplication.model.PostResponse
import com.dihemat.myapplication.model.TokoTerdekatResponse
import com.dihemat.myapplication.model.cart.CartOrderResponse
import com.dihemat.myapplication.model.cart.CartResponse
import com.dihemat.myapplication.model.pesan.PesananRequest
import com.dihemat.myapplication.model.pesan.PesananResponse
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
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    //Register
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("email") email: String,
        @Field("nama") nama: String,
        @Field("password") password: String,
        @Field("nomor_telepon") nomor_telepon: String
    ): Call<LoginResponse>

    //profil
    @GET("profil")
    fun profil(
        @Query("id") id: Int
    ): Call<ProfilResponse>

    //toko terdekat
    @GET("toko_terdekat")
    fun toko_terdekat(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String
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
        @Part("id") id: RequestBody
    ): Call<PostResponse>

    //Daftar TOko
    @Multipart
    @POST("tambah_produk")
    fun tambah_produk(
        @Part foto: MultipartBody.Part?,
        @Part("nama_produk") nama_produk: RequestBody,
        @Part("harga") harga: RequestBody,
        @Part("keterangan") keterangan: RequestBody,
        @Part("user_id") user_id: RequestBody
    ): Call<PostResponse>

    //Daftar TOko
    @Multipart
    @POST("update_produk")
    fun update_produk(
        @Part foto: MultipartBody.Part?,
        @Part("nama_produk") nama_produk: RequestBody,
        @Part("harga") harga: RequestBody,
        @Part("keterangan") keterangan: RequestBody,
        @Part("oldImage") oldImage: RequestBody,
        @Part("id") id: RequestBody
    ): Call<PostResponse>

    //Daftar TOko
    @Multipart
    @POST("update_produk")
    fun update_produk_withoutfoto(
        @Part("nama_produk") nama_produk: RequestBody,
        @Part("harga") harga: RequestBody,
        @Part("keterangan") keterangan: RequestBody,
        @Part("id") id: RequestBody
    ): Call<PostResponse>

    //toko terdekat
    @GET("produk_toko")
    fun produk_toko(
        @Query("user_id") user_id: Int
    ): Call<ProdukTokoResponse>

    //cart

    @Headers("Content-Type: application/json")
    @POST("cart")
    fun cart(@Body post: PostCartRequest): Call<PostResponse>

    //toko terdekat
    @GET("getcart")
    fun getcart(
        @Query("user_id") user_id: Int,
        @Query("toko_id") toko_id: Int
    ): Call<CartResponse>

    //pesan
    @Headers("Content-Type: application/json")
    @POST("pesan")
    fun pesan(@Body post: PesananRequest): Call<PostResponse>


    //get pesan
    @GET("getpesanan")
    fun getpesanan(
        @Query("user_id") user_id: Int
    ): Call<PesananResponse>

    //get pesan
    @GET("getpesanan_toko")
    fun getpesanan_toko(
        @Query("toko_id") toko_id: Int
    ): Call<PesananResponse>

    //get cart_order
    @GET("cart_order")
    fun cart_order(
        @Query("kode_pesanan") kode_pesanan: String
    ): Call<CartOrderResponse>

    //pesanan_diselesaikan
    //Register
    @FormUrlEncoded
    @POST("pesanan_diselesaikan")
    fun pesanan_diselesaikan(
        @Field("kode_pesanan") kode_pesanan: String
    ): Call<PostResponse>


}