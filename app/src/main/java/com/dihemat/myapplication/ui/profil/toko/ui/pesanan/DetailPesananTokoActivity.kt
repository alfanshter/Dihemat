package com.dihemat.myapplication.ui.profil.toko.ui.pesanan

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
import com.dihemat.myapplication.Utils.Constant
import com.dihemat.myapplication.adapter.CartPesananViewHolder
import com.dihemat.myapplication.databinding.ActivityDetailPesananBinding
import com.dihemat.myapplication.databinding.ActivityDetailPesananTokoBinding
import com.dihemat.myapplication.model.PostResponse
import com.dihemat.myapplication.model.cart.CartOrderModel
import com.dihemat.myapplication.model.cart.CartOrderResponse
import com.dihemat.myapplication.model.pesan.PesananModel
import com.dihemat.myapplication.ui.pesanan.DetailPesananActivity
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

class DetailPesananTokoActivity : AppCompatActivity() {
    lateinit var binding : ActivityDetailPesananTokoBinding
    var api = ApiClientBackend.instance()

    val TAG = "dihemat"
    lateinit var progressdialog: ProgressDialog
    //adapter
    lateinit var sessionManager: SessionManager
    private var mAdapter: CartPesananViewHolder? = null
    var pesananmodel : PesananModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail_pesanan_toko)
        binding.lifecycleOwner = this
        progressdialog = ProgressDialog(this)
        val gson = Gson()
        pesananmodel =
            gson.fromJson(intent.getStringExtra("pesananmodel"), PesananModel::class.java)
        val formatter: NumberFormat = DecimalFormat("#,###")

        binding.txttotal.text = "Rp. ${formatter.format(pesananmodel!!.harga)}"
        binding.txtongkir.text = "Rp. ${formatter.format(pesananmodel!!.ongkir)}"
        binding.txthargatotal.text = "Rp. ${formatter.format(pesananmodel!!.hargaTotal)}"
        binding.txtalamat.text = pesananmodel!!.alamatCustomer
    }

    override fun onStart() {
        super.onStart()
        cart()
    }
    fun cart() {

        binding.rvcart.layoutManager =
            LinearLayoutManager(this)
        binding.rvcart.setHasFixedSize(true)
        (binding.rvcart.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL

        api.cart_order(pesananmodel!!.kodePesanan!!)
            .enqueue(object : Callback<CartOrderResponse> {
                override fun onResponse(
                    call: Call<CartOrderResponse>,
                    response: Response<CartOrderResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<CartOrderModel>()

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
                                        CartPesananViewHolder(
                                            notesList,
                                            this@DetailPesananTokoActivity
                                        )
                                    binding.rvcart.adapter = mAdapter
                                    mAdapter!!.setDialog(object : CartPesananViewHolder.Dialog {
                                        override fun onClick(
                                            position: Int,
                                            note: CartOrderModel
                                        ) {

                                            val gson = Gson()
                                            val noteJson = gson.toJson(note)

                                            val i = Intent(
                                                this@DetailPesananTokoActivity,
                                                DetailPesananActivity::class.java
                                            )
                                            i.putExtra("CartOrderModel", noteJson)
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

                override fun onFailure(call: Call<CartOrderResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }

            })

        if (pesananmodel!!.isStatus == 0){
            binding.btnselesai.visibility = View.VISIBLE
        }
        if (pesananmodel!!.isStatus == 1){
            binding.btnselesai.visibility = View.GONE
        }

        binding.btnselesai.setOnClickListener {
            loading(true)
            selesaikan(it)
        }


    }
    
    fun selesaikan( view : View){
        api.pesanan_diselesaikan(pesananmodel!!.kodePesanan!!).enqueue(object :Callback<PostResponse>{
            override fun onResponse(
                call: Call<PostResponse>,
                response: Response<PostResponse>
            ) {
                if (response.isSuccessful){
                    val data = response.body()
                    //ketika berhasil
                    if (data!!.status == 1){
                        toast("Pesanan diselesaikan")
                        finish()
                    }else
                    {
                        toast("Silahkan diulangi liagi")
                    }
                }else{
                    loading(false)
                    Snackbar.make(view,"Kesalahan response",3000).show()

                }
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                loading(false)
                Snackbar.make(view,"Kesalahan jaringan",3000).show()

            }

        })
    }

    fun loading(status: Boolean) {
        if (status) {
            progressdialog.setTitle(Constant.tunggu)
            progressdialog.setCanceledOnTouchOutside(false)
            progressdialog.show()
        } else {
            progressdialog.dismiss()
        }

    }
}