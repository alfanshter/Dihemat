package com.dihemat.myapplication.ui.profil.toko.ui.pesanan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.dihemat.myapplication.R
import com.dihemat.myapplication.Session.SessionManager
import com.dihemat.myapplication.adapter.PesananViewHolder
import com.dihemat.myapplication.databinding.FragmentPesananBinding
import com.dihemat.myapplication.databinding.FragmentPesananTokoBinding
import com.dihemat.myapplication.model.pesan.PesananModel
import com.dihemat.myapplication.model.pesan.PesananResponse
import com.dihemat.myapplication.ui.pesanan.DetailPesananActivity
import com.dihemat.myapplication.webservice.ApiClientBackend
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.toast.toast

class PesananTokoFragment : Fragment() {
    lateinit var binding: FragmentPesananTokoBinding

    var api = ApiClientBackend.instance()

    val TAG = "dihemat"

    //adapter
    lateinit var sessionManager: SessionManager
    private var mAdapter: PesananViewHolder? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pesanan_toko, container, false)
        binding.lifecycleOwner = this

        sessionManager = SessionManager(requireContext().applicationContext)

        return  binding.root
    }

    override fun onStart() {
        super.onStart()
        pesanan()
    }


    fun pesanan() {

        binding.rvpesanan.layoutManager =
            LinearLayoutManager(requireContext().applicationContext)
        binding.rvpesanan.setHasFixedSize(true)
        (binding.rvpesanan.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL

        api.getpesanan_toko(sessionManager.getuid()!!)
            .enqueue(object : Callback<PesananResponse> {
                override fun onResponse(
                    call: Call<PesananResponse>,
                    response: Response<PesananResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<PesananModel>()

                            if (response.body()!!.data!!.isEmpty()) {
                                notesList.clear()
                                binding.rvpesanan.visibility = View.GONE
                                binding.shimmertoko.startShimmer()
                                binding.shimmertoko.visibility = View.GONE
                                binding.txtnodata.visibility = View.VISIBLE
                            } else {
                                binding.shimmertoko.startShimmer()
                                binding.txtnodata.visibility = View.GONE
                                binding.shimmertoko.visibility = View.GONE
                                binding.rvpesanan.visibility = View.VISIBLE
                                val data = response.body()

                                for (hasil in data!!.data!!) {
                                    notesList.add(hasil!!)
                                    mAdapter =
                                        PesananViewHolder(
                                            notesList,
                                            requireContext().applicationContext
                                        )
                                    binding.rvpesanan.adapter = mAdapter
                                    mAdapter!!.setDialog(object : PesananViewHolder.Dialog {
                                        override fun onClick(
                                            position: Int,
                                            note: PesananModel
                                        ) {

                                            val gson = Gson()
                                            val noteJson = gson.toJson(note)

                                            val i = Intent(
                                                activity!!.baseContext,
                                                DetailPesananTokoActivity::class.java
                                            )
                                            i.putExtra("pesananmodel", noteJson)
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

                override fun onFailure(call: Call<PesananResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }

            })


    }

}