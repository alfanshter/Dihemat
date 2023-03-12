package com.dihemat.myapplication.Utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Geocoder
import android.net.Uri
import android.provider.MediaStore
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


object Constant {

    val ip_backend = "http://192.168.1.12:8000/api/"
    val STORAGE = "http://192.168.1.12:8000/storage/"
    //Loading
    val tunggu = "Tunggu Sebentar ...."

    //tampilkan alamat
    fun tampilkan_kota(lat: Double, lon: Double, context: Context): String {
        var name = ""
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(lat, lon, 1)

            if (addresses!!.size > 0) {
                val fetchedAddress = addresses.get(0)
                val strAddress = StringBuilder()

                name = fetchedAddress.locality

            }

        } catch (e: Exception) {
        }
        return name
    }



}