package com.dihemat.myapplication.Session

import android.content.Context
import android.content.SharedPreferences

class SessionManager(private val context: Context?) {
    val privateMode = 0
    val privateName ="login"
    var Pref : SharedPreferences?=context?.getSharedPreferences(privateName,privateMode)
    var editor : SharedPreferences.Editor?=Pref?.edit()

    private val islogin = "login"
    fun setLogin(check: Boolean){
        editor?.putBoolean(islogin,check)
        editor?.commit()
    }

    fun getLogin():Boolean?
    {
        return Pref?.getBoolean(islogin,false)
    }

    private val isverivikasi = "verifikasi"
    fun setVerifikasi(check: Boolean){
        editor?.putBoolean(isverivikasi,check)
        editor?.commit()
    }

    fun getVerifikasi():Boolean?
    {
        return Pref?.getBoolean(isverivikasi,false)
    }

    private val isuploadutama = "uploadutama"
    fun setuploadutama(check: Boolean){
        editor?.putBoolean(isuploadutama,check)
        editor?.commit()
    }

    fun getuploadutama():Boolean?
    {
        return Pref?.getBoolean(isuploadutama,false)
    }

    private val status = "status"
    fun setstatuslelang(check:String?)
    {
        editor?.putString(status,check)
        editor?.commit()
    }

    fun getstatuslelang():String?
    {
        return Pref?.getString(status,"")
    }


    private val isloginadmin = "loginadmin"
    fun setLoginadmin(check: Boolean){
        editor?.putBoolean(isloginadmin,check)
        editor?.commit()
    }

    fun getLoginadmin():Boolean?
    {
        return Pref?.getBoolean(isloginadmin,false)
    }

    private val id = "id"
    fun setid(check:Int)
    {
        editor?.putInt(id,check)
        editor?.commit()
    }

    fun getuid():Int?
    {
        return Pref?.getInt(id,0)
    }

    private val is_status_toko = "is_status_toko"
    fun setis_status_toko(check:Int)
    {
        editor?.putInt(is_status_toko,check)
        editor?.commit()
    }

    fun getis_status_toko():Int?
    {
        return Pref?.getInt(is_status_toko,0)
    }

    private val namatoko = "namatoko"
    fun setnamatoko(check:String)
    {
        editor?.putString(namatoko,check)
        editor?.commit()
    }

    fun getnamatoko():String?
    {
        return Pref?.getString(namatoko,"")
    }

    private val alamat = "alamat"
    fun setalamat(check:String)
    {
        editor?.putString(alamat,check)
        editor?.commit()
    }

    fun getalamat():String?
    {
        return Pref?.getString(alamat,"")
    }



}