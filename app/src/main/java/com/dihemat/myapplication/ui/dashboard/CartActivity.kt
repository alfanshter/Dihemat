package com.dihemat.myapplication.ui.dashboard

import android.app.Dialog
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.dihemat.myapplication.R
import com.dihemat.myapplication.Session.SessionManager
import com.dihemat.myapplication.Utils.Constant
import com.dihemat.myapplication.databinding.ActivityCartBinding
import com.dihemat.myapplication.databinding.ActivityDetailTokoBinding
import com.dihemat.myapplication.model.PostCartRequest
import com.dihemat.myapplication.model.PostResponse
import com.dihemat.myapplication.model.TokoTerdekatModel
import com.dihemat.myapplication.model.produktoko.ProdukTokoModel
import com.dihemat.myapplication.webservice.ApiClientBackend
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.toast.toast
import java.text.DecimalFormat
import java.text.NumberFormat

class CartActivity : AppCompatActivity() {
    var produkmodel : ProdukTokoModel? = null
    lateinit var binding : ActivityCartBinding
    var counter = 0
    var hargaproduk = 0
    val TAG= "coba"
    var api = ApiClientBackend.instance()
    lateinit var progressDialog: Dialog
    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_cart)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)
        val gson = Gson()
        produkmodel =
            gson.fromJson(intent.getStringExtra("itemmodel"), ProdukTokoModel::class.java)
        sessionManager = SessionManager(this)
        Log.d(TAG, "onCreate: $produkmodel ")
        Picasso.get().load(Constant.STORAGE+produkmodel!!.foto).centerCrop().fit().into(binding.imgfoto)
        //convert int ke money
        val formatter: NumberFormat = DecimalFormat("#,###")
        val harga: String =
            formatter.format(produkmodel!!.harga)
        binding.btnplus.setOnClickListener {
            counter += 1
            Log.d(TAG, "onCreate: $counter")
            binding.txtnilai.text = counter.toString()
             hargaproduk = produkmodel!!.harga!! * counter
            binding.txtharga.text =    formatter.format(hargaproduk)


        }

        binding.btnminus.setOnClickListener {
            if (counter>=1){
                counter -= 1
                Log.d(TAG, "onCreate: $counter")
                binding.txtnilai.text = counter.toString()
                 hargaproduk = produkmodel!!.harga!! * counter
                binding.txtharga.text =    formatter.format(hargaproduk)

            }

        }

        binding.btninsert.setOnClickListener {
            if (counter == 0) {
                toast("Masukkan Jumlah Item")

            } else {
                tambahproduk()
            }
        }

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
    fun tambahproduk() {
        loading(true)
        val PostResponse = PostCartRequest(
            produkmodel!!.namaProduk,
            counter,
            produkmodel!!.harga,
            produkmodel!!.foto,
            sessionManager.getuid(),
            produkmodel!!.id,
            produkmodel!!.userId
        )
        api.cart(PostResponse).enqueue(object : Callback<PostResponse> {
            override fun onResponse(
                call: Call<PostResponse>,
                response: Response<PostResponse>
            ) {
                try {
                    if (response.isSuccessful) {
                        if (response.body()!!.status == 1) {
                            loading(false)
                            toast("tambah keranjang berhasil")
                            finish()

                        } else {
                            loading(false)
                            toast("tambah keranjang gagal")
                        }
                    } else {
                        loading(false)
                        toast("tambah keranjang gagal")

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

}