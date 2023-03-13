package com.dihemat.myapplication.ui.dashboard

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.dihemat.myapplication.HomeActivity
import com.dihemat.myapplication.R
import com.dihemat.myapplication.Session.SessionManager
import com.dihemat.myapplication.adapter.CartViewHolder
import com.dihemat.myapplication.databinding.ActivityDetailCartBinding
import com.dihemat.myapplication.databinding.ActivityDetailTokoBinding
import com.dihemat.myapplication.model.PostCartRequest
import com.dihemat.myapplication.model.PostResponse
import com.dihemat.myapplication.model.TokoTerdekatModel
import com.dihemat.myapplication.model.cart.CartModel
import com.dihemat.myapplication.model.cart.CartResponse
import com.dihemat.myapplication.model.pesan.PesananRequest
import com.dihemat.myapplication.webservice.ApiClientBackend
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.activities.start
import splitties.toast.toast
import java.text.DecimalFormat
import java.text.NumberFormat

class DetailCartActivity : AppCompatActivity() {
    var api = ApiClientBackend.instance()
    var produkmodel : TokoTerdekatModel? = null
    lateinit var binding : ActivityDetailCartBinding
    private var mAdapter: CartViewHolder? = null
    val TAG = "tester"
    lateinit var sessionManager: SessionManager
    lateinit var progressDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail_cart)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)
        sessionManager = SessionManager(this)

        val gson = Gson()
        produkmodel =
            gson.fromJson(intent.getStringExtra("tokomodel"), TokoTerdekatModel::class.java)


    }



    fun cart() {

        binding.rvcart.layoutManager =
            LinearLayoutManager(this)
        binding.rvcart.setHasFixedSize(true)
        (binding.rvcart.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL
        api.getcart( sessionManager.getuid()!!,produkmodel!!.id!!)
            .enqueue(object : Callback<CartResponse> {
                override fun onResponse(
                    call: Call<CartResponse>,
                    response: Response<CartResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<CartModel>()
                            //convert int ke money
                            val body = response.body()
                            val formatter: NumberFormat = DecimalFormat("#,###")
                            binding.txttotal.text =   "Rp. ${formatter.format(body!!.harga_total)}"
                            val total = response.body()!!.harga_total!! + 15000
                            binding.txthargatotal.text = "Rp. ${formatter.format(total)}"
                            if (response.body()!!.data!!.isEmpty()) {
                                notesList.clear()
                                binding.rvcart.visibility = View.GONE
                                binding.shimmertoko.startShimmer()
                                binding.shimmertoko.visibility = View.GONE
                                binding.txtnodata.visibility = View.VISIBLE
                            } else {
                                binding.shimmertoko.startShimmer()
                                binding.txtnodata.visibility = View.GONE
                                binding.shimmertoko.visibility = View.GONE
                                binding.rvcart.visibility = View.VISIBLE
                                val data = response.body()

                                for (hasil in data!!.data!!) {
                                    notesList.add(hasil!!)
                                    mAdapter =
                                        CartViewHolder(
                                            notesList,
                                            this@DetailCartActivity
                                        )
                                    binding.rvcart.adapter = mAdapter

                                    mAdapter!!.notifyDataSetChanged()
                                }
                            }

                            binding.btnpesan.setOnClickListener {
                                if (body.data!!.isEmpty()){
                                    Snackbar.make(it,"Tidak ada keranjang",3000).show()

                                }else{
                                    val alamat = binding.edtalamat.text.toString().trim()

                                    if (alamat.isNotEmpty()){
                                        pesan(alamat,body.harga_total!!,15000,total)
                                    }else{
                                        Snackbar.make(it,"Jangan kosongi kolom",3000).show()
                                    }

                                }

                            }

                        } else {
                            toast("gagal mendapatkan response")
                        }
                    } catch (e: Exception) {
                        Log.d(TAG, "onResponse: ${e.message}")
                    }
                }

                override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }

            })



    }

    fun pesan(alamat : String,harga : Int,ongkir : Int,hargatotal : Int,){
        loading(true)
        val PostResponse = PesananRequest(alamat,harga,ongkir,sessionManager.getuid(),hargatotal,produkmodel!!.id)
        api.pesan(PostResponse).enqueue(object : Callback<PostResponse> {
            override fun onResponse(
                call: Call<PostResponse>,
                response: Response<PostResponse>
            ) {
                try {
                    if (response.isSuccessful) {
                        if (response.body()!!.status == 1) {
                            loading(false)
                            toast("Sedang diproses")
                            start<SudahProsesActivity> {  }
                            finish()

                        } else {
                            loading(false)
                            toast("pesan gagal")
                        }
                    } else {
                        loading(false)
                        toast("pesan gagal")

                    }
                } catch (e: Exception) {
                    loading(false)
                    Log.d(TAG, "onResponse: ${e.message}")

                }
            }

            override fun onFailure(
                call: Call<PostResponse>,
                t: Throwable
            ) {
                loading(false)
                toast("Kesalahan jaringan")
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })

    }

    fun loading(state: Boolean) {
        if (state) {
            progressDialog.setTitle("Tunggu ...")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()

        } else {
            progressDialog.dismiss()

        }
    }
    override fun onStart() {
        super.onStart()
        cart()
    }
}