package com.dihemat.myapplication.ui.dashboard

import android.app.Activity
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dihemat.myapplication.HomeActivity
import com.dihemat.myapplication.R
import com.dihemat.myapplication.Utils.Constant
import com.dihemat.myapplication.adapter.TokoViewHolder
import com.dihemat.myapplication.databinding.FragmentDashboardBinding
import com.dihemat.myapplication.databinding.FragmentProfilBinding
import com.dihemat.myapplication.model.TokoTerdekatModel
import com.dihemat.myapplication.model.TokoTerdekatResponse
import com.dihemat.myapplication.ui.profil.toko.ui.dashboard.DetailBarangTokoActivity
import com.dihemat.myapplication.webservice.ApiClientBackend
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.toast.toast

class DashboardFragment : Fragment() {

    lateinit var binding : FragmentDashboardBinding
    
    var api = ApiClientBackend.instance()

    val TAG = "dihemat"
    //adapter
    private var mAdapter: TokoViewHolder? = null
    var shouldStopLoop = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard,container,false)
        binding.lifecycleOwner = this

        //tampilkan lokasi
        if (HomeActivity.latitudePosisi!=null){
            binding.txtlokasi.text = Constant.tampilkan_kota(HomeActivity.latitudePosisi!!.toDouble(),HomeActivity.longitudePosisi!!.toDouble(),requireContext().applicationContext)
        }


        return  binding.root
    }

    override fun onStart() {
        super.onStart()
        tokoterdekat()
    }


    fun tokoterdekat() {

        binding.rvtoko.layoutManager =
            LinearLayoutManager(requireContext().applicationContext)
        binding.rvtoko.setHasFixedSize(true)
        (binding.rvtoko.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL

        val mHandler = Handler()
        val runnable: Runnable = object : Runnable {
            override fun run() {
                val r = this

                if (HomeActivity.latitudePosisi != null && HomeActivity.longitudePosisi != null) {
                    api.toko_terdekat( HomeActivity.latitudePosisi!!, HomeActivity.longitudePosisi!!)
                        .enqueue(object : Callback<TokoTerdekatResponse> {
                            override fun onResponse(
                                call: Call<TokoTerdekatResponse>,
                                response: Response<TokoTerdekatResponse>
                            ) {
                                try {
                                    if (response.isSuccessful) {
                                        shouldStopLoop = true
                                        val notesList = mutableListOf<TokoTerdekatModel>()

                                        if (response.body()!!.data!!.isEmpty()) {
                                            notesList.clear()
                                            binding.rvtoko.visibility = View.GONE
                                            binding.shimmertoko.startShimmer()
                                            binding.shimmertoko.visibility = View.GONE
                                            binding.txtnodata.visibility = View.VISIBLE
                                        } else {
                                            binding.shimmertoko.startShimmer()
                                            binding.txtnodata.visibility = View.GONE
                                            binding.shimmertoko.visibility = View.GONE
                                            binding.rvtoko.visibility = View.VISIBLE
                                            val data = response.body()

                                            for (hasil in data!!.data!!) {
                                                notesList.add(hasil!!)
                                                mAdapter =
                                                    TokoViewHolder(
                                                        notesList,
                                                        requireContext().applicationContext
                                                    )
                                                binding.rvtoko.adapter = mAdapter
                                                mAdapter!!.setDialog(object : TokoViewHolder.Dialog {
                                                    override fun onClick(position: Int, note: TokoTerdekatModel) {

                                                        val gson = Gson()
                                                        val noteJson = gson.toJson(note)

                                                        val i = Intent(
                                                            activity!!.baseContext,
                                                            DetailTokoActivity::class.java
                                                        )
                                                        i.putExtra("tokomodel", noteJson)
                                                        startActivity(i)
                                                    }

                                                })
                                                mAdapter!!.notifyDataSetChanged()
                                            }
                                        }

                                    } else {
                                        if (!shouldStopLoop) {
                                            mHandler.postDelayed(r, 1000)
                                        }
                                        toast("gagal mendapatkan response")
                                    }
                                } catch (e: Exception) {
                                    if (!shouldStopLoop) {
                                        mHandler.postDelayed(r, 1000)
                                    }

                                    Log.d(TAG, "onResponse: ${e.message}")
                                }
                            }

                            override fun onFailure(call: Call<TokoTerdekatResponse>, t: Throwable) {
                                if (!shouldStopLoop) {
                                    mHandler.postDelayed(r, 1000)
                                }

                                Log.d(TAG, "onFailure: ${t.message}")
                            }

                        })


                }else{
                    if (!shouldStopLoop) {
                        mHandler.postDelayed(r, 1000)
                    }

                }




            }
        }

        runnable.run()

    }
}