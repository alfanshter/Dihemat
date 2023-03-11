package com.dihemat.myapplication.ui.profil.toko.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.dihemat.myapplication.R
import com.dihemat.myapplication.Utils.Constant
import com.dihemat.myapplication.databinding.ActivityDetailBarangTokoBinding
import com.dihemat.myapplication.databinding.ActivityTambahBarangBinding
import com.dihemat.myapplication.model.TokoTerdekatModel
import com.dihemat.myapplication.model.produktoko.ProdukTokoModel
import com.dihemat.myapplication.ui.profil.toko.HomeTokoActivity
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class DetailBarangTokoActivity : AppCompatActivity() {
    lateinit var binding : ActivityDetailBarangTokoBinding
    var produkmodel : ProdukTokoModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail_barang_toko)
        binding.lifecycleOwner = this
        val gson = Gson()
        produkmodel =
            gson.fromJson(intent.getStringExtra("produkmodel"), ProdukTokoModel::class.java)

        binding.edtnamabarang.setText(produkmodel!!.namaProduk)
        binding.edtharga.setText(produkmodel!!.harga.toString())
        binding.edtketerangan.setText(produkmodel!!.keterangan)
        Picasso.get().load(Constant.STORAGE+produkmodel!!.foto).fit().centerCrop().into(binding.gambarMakanan)

    }
}