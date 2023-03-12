package com.dihemat.myapplication.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dihemat.myapplication.R
import com.dihemat.myapplication.model.TokoTerdekatModel
import com.dihemat.myapplication.webservice.ApiClientBackend
import com.google.gson.Gson

class DetailCartActivity : AppCompatActivity() {
    var api = ApiClientBackend.instance()
    var produkmodel : TokoTerdekatModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_cart)

        val gson = Gson()
        produkmodel =
            gson.fromJson(intent.getStringExtra("tokomodel"), TokoTerdekatModel::class.java)

    }
}