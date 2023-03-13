package com.dihemat.myapplication.ui.pesanan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.dihemat.myapplication.R
import com.dihemat.myapplication.Session.SessionManager
import com.dihemat.myapplication.adapter.CartPesananViewHolder
import com.dihemat.myapplication.databinding.ActivityCartBinding
import com.dihemat.myapplication.databinding.ActivityDetailPesananBinding
import com.dihemat.myapplication.model.TokoTerdekatModel
import com.dihemat.myapplication.model.cart.CartOrderModel
import com.dihemat.myapplication.model.cart.CartOrderResponse
import com.dihemat.myapplication.model.pesan.PesananModel
import com.dihemat.myapplication.webservice.ApiClientBackend
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.toast.toast
import java.text.DecimalFormat
import java.text.NumberFormat

class DetailPesananActivity : AppCompatActivity() {
    lateinit var binding : ActivityDetailPesananBinding
    var api = ApiClientBackend.instance()

    val TAG = "dihemat"

    //adapter
    lateinit var sessionManager: SessionManager
    private var mAdapter: CartPesananViewHolder? = null
    var pesananmodel : PesananModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail_pesanan)
        binding.lifecycleOwner = this

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
                                            this@DetailPesananActivity
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
                                                this@DetailPesananActivity,
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


    }
}