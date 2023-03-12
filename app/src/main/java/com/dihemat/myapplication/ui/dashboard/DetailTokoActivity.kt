package com.dihemat.myapplication.ui.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dihemat.myapplication.HomeActivity
import com.dihemat.myapplication.R
import com.dihemat.myapplication.Utils.Constant
import com.dihemat.myapplication.adapter.ItemProdukViewHolder
import com.dihemat.myapplication.databinding.ActivityDetailTokoBinding
import com.dihemat.myapplication.databinding.ActivityTambahBarangBinding
import com.dihemat.myapplication.model.TokoTerdekatModel
import com.dihemat.myapplication.model.produktoko.ProdukTokoModel
import com.dihemat.myapplication.model.produktoko.ProdukTokoResponse
import com.dihemat.myapplication.ui.profil.toko.ui.dashboard.DetailBarangTokoActivity
import com.dihemat.myapplication.webservice.ApiClientBackend
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.toast.toast
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class DetailTokoActivity : AppCompatActivity() {
    lateinit var binding : ActivityDetailTokoBinding
    var api = ApiClientBackend.instance()
    var produkmodel : TokoTerdekatModel? = null
    val TAG = "dihemat"
    //adapter
    private var mAdapter: ItemProdukViewHolder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail_toko)
        binding.lifecycleOwner = this

        val gson = Gson()
        produkmodel =
            gson.fromJson(intent.getStringExtra("tokomodel"), TokoTerdekatModel::class.java)


        binding.btnback.setOnClickListener {
            finish()
        }

        binding.btnkeranjang.setOnClickListener {

            val gson = Gson()
            val noteJson = gson.toJson(produkmodel)

            val i = Intent(
                this,
                DetailCartActivity::class.java
            )
            i.putExtra("tokomodel", noteJson)
            startActivity(i)
        }

        val jarak = produkmodel!!.distance
        val number3digits: Double = (jarak!! * 1000.0).roundToInt() / 1000.0
        val number2digits: Double = (number3digits * 100.0).roundToLong() / 100.0
        val hasiljarak: Double = (number2digits * 10.0).roundToLong() / 10.0

        Picasso.get().load(Constant.STORAGE+produkmodel!!.foto).centerCrop().fit().into(binding.imgfoto)
        binding.txtjarak.text = "${hasiljarak.toString()} KM"
    }

    override fun onStart() {
        super.onStart()
        produk()
    }

    fun produk() {

        binding.rvproduk.layoutManager =
            LinearLayoutManager(this)
        binding.rvproduk.setHasFixedSize(true)
        (binding.rvproduk.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL

        if (HomeActivity.latitudePosisi != null && HomeActivity.longitudePosisi != null) {
            api.produk_toko( produkmodel!!.id!!)
                .enqueue(object : Callback<ProdukTokoResponse> {
                    override fun onResponse(
                        call: Call<ProdukTokoResponse>,
                        response: Response<ProdukTokoResponse>
                    ) {
                        try {
                            if (response.isSuccessful) {
                                val notesList = mutableListOf<ProdukTokoModel>()

                                if (response.body()!!.data!!.isEmpty()) {
                                    notesList.clear()
                                    binding.rvproduk.visibility = View.GONE
                                    binding.shimmertoko.startShimmer()
                                    binding.shimmertoko.visibility = View.GONE
                                    binding.txtnodata.visibility = View.VISIBLE
                                } else {
                                    binding.shimmertoko.startShimmer()
                                    binding.txtnodata.visibility = View.GONE
                                    binding.shimmertoko.visibility = View.GONE
                                    binding.rvproduk.visibility = View.VISIBLE
                                    val data = response.body()

                                    for (hasil in data!!.data!!) {
                                        notesList.add(hasil!!)
                                        mAdapter =
                                            ItemProdukViewHolder(
                                                notesList,
                                                this@DetailTokoActivity
                                            )
                                        binding.rvproduk.adapter = mAdapter
                                        mAdapter!!.setDialog(object : ItemProdukViewHolder.Dialog {

                                            override fun onClick(
                                                position: Int,
                                                note: ProdukTokoModel
                                            ) {
                                                val gson = Gson()
                                                val noteJson = gson.toJson(note)

                                                val i = Intent(
                                                    this@DetailTokoActivity,
                                                    CartActivity::class.java
                                                )

                                                i.putExtra("itemmodel", noteJson)
                                                startActivity(i)

                                            }

                                        })
                                        mAdapter!!.notifyDataSetChanged()
                                    }
                                }

                            } else {
                                toast("gagal mendapatkan response")
                            }
                        } catch (e: Exception) {
                            Log.d(TAG, "onResponse: ${e.message}")
                        }
                    }

                    override fun onFailure(call: Call<ProdukTokoResponse>, t: Throwable) {
                        Log.d(TAG, "onFailure: ${t.message}")
                    }

                })


        }


    }
}