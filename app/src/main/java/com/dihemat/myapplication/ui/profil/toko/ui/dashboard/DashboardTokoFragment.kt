package com.dihemat.myapplication.ui.profil.toko.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dihemat.myapplication.HomeActivity
import com.dihemat.myapplication.R
import com.dihemat.myapplication.Session.SessionManager
import com.dihemat.myapplication.adapter.ProdukTokoViewHolder
import com.dihemat.myapplication.adapter.TokoViewHolder
import com.dihemat.myapplication.databinding.FragmentDashboardTokoBinding
import com.dihemat.myapplication.model.TokoTerdekatModel
import com.dihemat.myapplication.model.produktoko.ProdukTokoModel
import com.dihemat.myapplication.model.produktoko.ProdukTokoResponse
import com.dihemat.myapplication.ui.profil.toko.HomeTokoActivity
import com.dihemat.myapplication.webservice.ApiClientBackend
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.fragments.start
import splitties.toast.toast

class DashboardTokoFragment : Fragment() {
    val TAG = "dihemat"
    //adapter
    private var mAdapter: ProdukTokoViewHolder? = null
    var api = ApiClientBackend.instance()
    lateinit var binding: FragmentDashboardTokoBinding
    lateinit var sessionManager: SessionManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard_toko, container, false)
        binding.lifecycleOwner = this
        sessionManager = SessionManager(requireContext().applicationContext)
        binding.txtnamatoko.text = HomeTokoActivity.tokomodel!!.namaToko
        binding.txtalamat.text = HomeTokoActivity.tokomodel!!.alamat

        binding.btnbuat.setOnClickListener {
            start<TambahBarangActivity> {  }
        }
        return binding.root

    }

    override fun onStart() {
        super.onStart()
        produk()
    }

    fun produk() {

        binding.rvitem.layoutManager =
            GridLayoutManager(requireContext().applicationContext,2)
        binding.rvitem.setHasFixedSize(true)
        (binding.rvitem.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL

        if (HomeActivity.latitudePosisi != null && HomeActivity.longitudePosisi != null) {
            api.produk_toko( sessionManager.getuid()!!)
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
                                    binding.rvitem.visibility = View.GONE
                                    binding.shimmertoko.startShimmer()
                                    binding.shimmertoko.visibility = View.GONE
                                    binding.txtnodata.visibility = View.VISIBLE
                                } else {
                                    binding.shimmertoko.startShimmer()
                                    binding.txtnodata.visibility = View.GONE
                                    binding.shimmertoko.visibility = View.GONE
                                    binding.rvitem.visibility = View.VISIBLE
                                    val data = response.body()

                                    for (hasil in data!!.data!!) {
                                        notesList.add(hasil!!)
                                        mAdapter =
                                            ProdukTokoViewHolder(
                                                notesList,
                                                requireContext().applicationContext
                                            )
                                        binding.rvitem.adapter = mAdapter
                                        mAdapter!!.setDialog(object : ProdukTokoViewHolder.Dialog {

                                            override fun onClick(
                                                position: Int,
                                                note: ProdukTokoModel
                                            ) {
                                                val gson = Gson()
                                                val noteJson = gson.toJson(note)

                                                val i = Intent(
                                                    activity!!.baseContext,
                                                    DetailBarangTokoActivity::class.java
                                                )

                                                i.putExtra("produkmodel", noteJson)
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